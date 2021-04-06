
/**
 * Clase utilizada para probar el funcionamiento de GrupoFiguras.
 * 
 * @author Josep Silva 
 * @version 11 Enero 2017
**/

public class UsoGrupoFiguras
{
    public static void main (String args[])
    {
        Circulo cir = new Circulo(10, 5, 3.5);
        Cilindro cil = new Cilindro(0, 0, 2, 2);
        
        //GrupoFiguras<Circulo> g = new GrupoFiguras<Circulo>(cir);       

        //g.anyadeFigura(cir);
        //g.anyadeFigura(cil);
        
        System.out.println("Posicion inicial del cilindro: ("+cil.x+", "+cil.y+")");                
        //cil.desplazar(1,1);        
        System.out.println("Posicion del cilindro tras desplazarlo: ("+cil.x+", "+cil.y+")");        
        
        System.out.println("\nPosiciones de las figuras del grupo:");  
        //g.imprimirPosiciones();
        //g.desplazar(1,1);
        System.out.println("\nPosiciones de las figuras del grupo tras desplazarlas (solo se mueven las que tienen volumen)):");          
        //g.imprimirPosiciones();
        
        //System.out.println("\nPosicion de la figura favorita:: ("+g.getFavorita().x+", "+g.getFavorita().y+")");
    }
}
