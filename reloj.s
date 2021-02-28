                ##########################################################
                # Segmento de datos
                ##########################################################

                .data 0x10000000
reloj:          .word 0                # HH:MM:SS (3 bytes de menor peso)

cad_asteriscos: .asciiz "\n  **************************************"
cad_horas:      .asciiz "\n   Horas: "
cad_minutos:    .asciiz " Minutos: "
cad_segundos:   .asciiz " Segundos: "
cad_reloj_en_s: .asciiz "\n   Reloj en segundos: "

                ##########################################################
                # Segmento de c�digo
                ##########################################################

                .globl __start
                .text 0x00400000

__start:        la $a0, reloj
				li $a1, 0x00173b3b		 # Hora 07:48:21
				jal inicializa_reloj
				
				la $a0, reloj
				jal imprime_reloj

				la $a0, reloj
				jal pasa_segundo
				jal pasa_segundo		# Incrementa el reloj en una hora

				la $a0, reloj
				jal imprime_reloj
             
salir:          li $v0, 10              # C�digo de exit (10)
                syscall                 # �ltima instrucci�n ejecutada
                .end
				
				###########################################################
				# Subrutina que incremeta el reloj en un segundos
				# Entrada: $a0 con la direcci�n del reloj
				###########################################################
				
pasa_segundo: lb $t0, 0($a0)
			  add $t0, $t0, 1
			  li $t1, 60
			  beq $t0, $t1, S60
			  sb $t0, 0($a0)
			  j fin_pasa_segundo
S60:          sb $zero, 0($a0)
			  j pasa_minuto
pasa_minuto:  lb $t0, 1($a0)
			  add $t0, $t0, 1
			  li $t1, 60
			  beq $t0, $t1, M60
			  sb $t0, 1($a0)
			  j fin_pasa_segundo
M60:		  sb $zero, 1($a0)
			  j pasa_hora
fin_pasa_segundo: jr $ra
				
				###########################################################
				# Otra subrutina alternativa que devuelve el reloj en segundos
				# Entrada: $a0 con la direcci�n del reloj
				###########################################################
				
devuelve_reloj_en_s_srd: lb $t0, 2($a0)
						 sll $t1, $t0, 6
						 sll $t2, $t0, 2
						 sub $t1, $t1, $t2
						 lb $t0, 1($a0)
						 add $t0, $t0, $t1
						 sll $t1, $t0, 6
						 sll $t2, $t0, 2
						 sub $t1, $t1, $t2
						 lb $t0, 0($a0)
						 add $v0, $t1, $t0
						 jr $ra
				
				###########################################################
				# Subrutina alternativa que devuelve el reloj en segundos
				# Entrada: $a0 con la direcci�n del reloj
				###########################################################
				
devuelve_reloj_en_s_sd: lb $t0, 2($a0)
						sll $t1, $t0, 5
						sll $t2, $t0, 4
						addu $t1, $t1, $t2
						sll $t2, $t0, 3
						addu $t1, $t1, $t2
						sll $t2, $t0, 2
						addu $t1, $t1, $t2
						lb $t0, 1($a0)
						add $t0, $t0, $t1
						sll $t1, $t0, 5
						sll $t2, $t0, 4
						addu $t1, $t1, $t2
						sll $t2, $t0, 3
						addu $t1, $t1, $t2
						sll $t2, $t0, 2
						addu $v0, $t1, $t2
						lb $t0, 0($a0)
						addu $v0, $v0, $t0
						jr $ra
						
				
devuelve_reloj_en_s: li $t3, 60			# Factor from h<->m and m<->s
				     lb $t0, 2($a0)		# Load hours
					 mult $t0, $t3		# Hours to minutes
					 mflo $t2
					 lb $t0, 1($a0)		# Load minutes
					 add $t0, $t0, $t2	# minutes = hours * 60 + minutes
					 mult $t0, $t3		# Minutes to seconds
					 mflo $t2
					 lb $t0, 0($a0)		# Load seconds
					 add $v0, $t0, $t2	# seconds = minutes * 60 + seconds
					 jr $ra
				
inicializa_reloj: sw $a1, 0($a0)
				  jr $ra


                ########################################################## 
                # Subrutina que imprime el valor del reloj
                # Entrada: $a0 con la direcci�n de la variable reloj
                ########################################################## 

imprime_reloj:  move $t0, $a0
                la $a0, cad_asteriscos  # Direcci�n de la cadena
                li $v0, 4               # C�digo de print_string
                syscall

                la $a0, cad_horas       # Direcci�n de la cadena
                li $v0, 4               # C�digo de print_string
                syscall

                lbu $a0, 2($t0)         # Lee el campo HH
                li $v0, 1               # C�digo de print_int
                syscall

                la $a0, cad_minutos     # Direcci�n de la cadena
                li $v0, 4               # C�digo de print_string
                syscall

                lbu $a0, 1($t0)         # Lee el campo MM
                li $v0, 1               # C�digo de print_int
                syscall

                la $a0, cad_segundos    # Direcci�n de la cadena
                li $v0, 4               # C�digo de print_string
                syscall

                lbu $a0, 0($t0)         # Lee el campo SS
                li $v0, 1               # C�digo de print_int
                syscall

                la $a0, cad_asteriscos  # Direcci�n de la cadena
                li $v0, 4               # C�digo de print_string
                syscall
                jr $ra

                ########################################################## 
                # Subrutina que imprime los segundos calculados
                # Entrada: $a0 con los segundos a imprimir
                ########################################################## 

imprime_s:      move $t0, $a0
                la $a0, cad_asteriscos  # Direcci�n de la cadena
                li $v0, 4               # C�digo de print_string
                syscall


                la $a0, cad_reloj_en_s  # Direcci�n de la cadena
                li $v0, 4               # C�digo de print_string
                syscall

                move $a0, $t0           # Valor entero a imprimir
                li $v0, 1               # C�digo de print_int
                syscall

                la $a0, cad_asteriscos  # Direcci�n de la cadena
                li $v0, 4               # C�digo de print_string
                syscall
                jr $ra
                
                ########################################################## 
                # Subrutina que incrementa el reloj en una hora
                # Entrada: $a0 con la direcci�n del reloj
                # Salida: reloj incrementado en memoria
                # Nota: 23:MM:SS -> 00:MM:SS
                ########################################################## 
                
pasa_hora:      lbu $t0, 2($a0)         # $t0 = HH
                addiu $t0, $t0, 1       # $t0 = HH++
                li $t1, 24
                beq $t0, $t1, H24       # Si HH==24 se pone HH a cero
                sb $t0, 2($a0)          # Escribe HH++
                j fin_pasa_hora
H24:            sb $zero, 2($a0)        # Escribe HH a 0
fin_pasa_hora:  jr $ra
