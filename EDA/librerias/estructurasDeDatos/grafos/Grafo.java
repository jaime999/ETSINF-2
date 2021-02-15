package librerias.estructurasDeDatos.grafos;

import librerias.estructurasDeDatos.modelos.*;
import librerias.estructurasDeDatos.jerarquicos.PriorityQColaPrioridad;
import librerias.estructurasDeDatos.jerarquicos.ForestMFSet;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;
import librerias.estructurasDeDatos.lineales.ArrayCola;
import java.math.*;

/** Clase abstracta Grafo: Base de la jerarquia Grafo, que define el 
 *  comportamiento de un grafo.<br> 
 *  No es una interfaz porque incluye el codigo de las operaciones de un 
 *  grafo que son independientes tanto de su tipo como de su implementacion.<br>
 *  
 *  @version Mayo 2018
 */
public abstract class Grafo {

    protected boolean esDirigido; // Indica si un grafo es Dirigido o no
    protected int[] visitados;    // Nodos visitados en un Recorrido
    protected int ordenVisita;    // Orden de visita de nodos en un Recorrido
    protected Cola<Integer> q;    // Cola para un recorrido BFS

    /** Crea un grafo vacio, Dirigido si esDirigido es true
     * o No Dirigido en caso contrario.
     * 
     * @param esDirigido Indica el tipo del grafo, Dirigido o No
     */
    public Grafo(boolean esDirigido) { this.esDirigido = esDirigido; }

    /** Comprueba si un grafo es o no Dirigido.
     *
     * @return boolean true si el grafo es Dirgido y false si es No Dirigido
     */
    public boolean esDirigido() { return esDirigido; }

    /** Devuelve el numero de vertices de un grafo.
     *
     * @return int numero de vertices 
     */
    public abstract int numVertices();

    /** Devuelve el numero de aristas de un grafo.
     *
     * @return int numero de aristas
     */
    public abstract int numAristas();

    /** Comprueba si la arista (i,j) esta en un grafo.
     *
     * @param i    Vertice origen
     * @param j    Vertice destino
     * @return boolean true si (i, j) esta en el grafo y false en caso contrario
     */
    public abstract boolean existeArista(int i, int j);

    /** Devuelve el peso de la arista (i,j) de un grafo, 0 si dicha arista 
     * no esta en el grafo.
     *
     * @param i    Vertice origen
     * @param j    Vertice destino
     * @return double Peso de la arista (i,j), 0 si no existe.
     */
    public abstract double pesoArista(int i, int j);

    /** Si no esta, inserta la arista (i,j) en un grafo No Ponderado.
     *  @param i    Vertice origen
     *  @param j    Vertice destino
     */
    public abstract void insertarArista(int i, int j);

    /** Si no esta, inserta la arista (i,j) de peso p en un grafo Ponderado.
     *
     * @param i    Vertice origen
     * @param j    Vertice destino
     * @param p    Peso de la arista (i,j)
     */
    public abstract void insertarArista(int i, int j, double p);

    /** Devuelve una ListaConPI que contiene los adyacentes al vertice i.
     * 
     * @param i Vertice del que se obtienen los adyacentes
     * @return ListaConPI con los vertices adyacentes a i
     */
    public abstract ListaConPI<Adyacente> adyacentesDe(int i);

    /** Devuelve un String con cada uno de los vertices de un grafo y sus 
     * adyacentes, en orden de insercion.
     * 
     * @return  String que representa a un grafo
     */               
    public String toString() {
        String res = "";  
        for (int  i = 0; i < numVertices(); i++) {
            res += "Vertice: " + i;
            ListaConPI<Adyacente> l = adyacentesDe(i);
            if (l.esVacia()) { res += " sin Adyacentes "; }
            else { res += " con Adyacentes "; } 
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                res +=  l.recuperar() + " ";  
            }
            res += "\n";  
        }
        return res;      
    }

    /** Devuelve un array con cada uno de los vertices de un grafo y sus 
     * adyacentes, en orden BFS.
     *
     * @return  Array de vertices visitados en el recorrido BFS
     */   
    public int[] toArrayBFS() {
        int[] res = new int[numVertices()];
        visitados = new int[numVertices()]; 
        ordenVisita = 0;  
        q = new ArrayCola<Integer>();
        for (int  i = 0; i < numVertices(); i++) {
            if (visitados[i] == 0) { toArrayBFS(i, res); }
        }
        return res;
    }
    // Recorrido BFS del vertice origen, que almacena en res
    // su resultado 
    protected void toArrayBFS(int origen, int[] res) { 
        res[ordenVisita++] = origen;
        visitados[origen] = 1;
        q.encolar(origen);
        while (!q.esVacia()) {
            int u = q.desencolar().intValue(); 
            ListaConPI<Adyacente> l = adyacentesDe(u); 
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                Adyacente a = l.recuperar(); 
                if (visitados[a.destino] == 0) {
                    res[ordenVisita++] = a.destino;
                    visitados[a.destino] = 1;
                    q.encolar(a.destino);
                }
            }  
        }
    }

    /** PRECONDICION: !this.esDirigido()
     * Devuelve un subconjunto de aristas que conectan todos los vertices
     * de un grafo No Diridigo y Conexo, o null si el grafo no es Conexo.
     *  
     * @return Arista[], array con las numV - 1 aristas que conectan  
     *                   los numV vertices del grafo, o null si el grafo 
     *                   no es Conexo
     */ 
    public Arista[] arbolRecubrimientoBFS() {
        /*Arista[] res = new Arista[numVertices()-1];
        visitados = new int[numVertices()-1]; 
        ordenVisita = 0;  
        q = new ArrayCola<Integer>();
        for (int  i = 0; i < numVertices(); i++) {
        if (visitados[i] == 0) { arbolRecubrimientoBFS(i, res); }
        }
        return res;*/        
        // COMPLETAR
        //Paso 1. Crear array resultado y visitados
        Arista[] res = new Arista[numVertices()-1];
        visitados = new int[numVertices()];

        ordenVisita = 0; 
        //Paso 2. Crear una cola de Integer vacia
        q = new ArrayCola<Integer>();
        //Paso 3. Añadir a la cola un vertice y marcarlo como visitado
        q.encolar(ordenVisita); 
        visitados[ordenVisita]=1;
        //Paso 4. Mientas la cola no este vacia: Desencolar el primero, u Recorrer su lista de adyacentes y todo valor adyacente:
        while (!q.esVacia()) {
            int u = q.desencolar().intValue();
            //ordenVisita = u;
            ListaConPI<Adyacente> l = adyacentesDe(u); 
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                Adyacente a = l.recuperar(); 
                //Si el valor no ha sido visitado, se crea la arista correspondiente del arco y se añade al array resultado
                if (visitados[a.destino] == 0) {
                    res[ordenVisita++] = new Arista(u,a.destino,a.peso);
                    //Tambien se marca como visitado y se añade en la cola
                    visitados[a.destino] = 1;
                    q.encolar(a.destino);
                }
            }  
        }
        //Paso 5. Al terminar el bucle, si se han conseguido N-1 arcos se devuelve el array resultado y si no se devuelve null
        if(ordenVisita == (numVertices()-1))
            return res;
        else
            return null;
    }

    /*protected void arbolRecubrimientoBFS(int origen, Arista[] res) {        
    visitados[origen] = 1;
    q.encolar(origen);
    while (!q.esVacia()) {
    int u = q.desencolar().intValue(); 
    ListaConPI<Adyacente> l = adyacentesDe(u); 
    for (l.inicio(); !l.esFin(); l.siguiente()) {
    Adyacente a = l.recuperar(); 
    if (visitados[a.destino] == 0) {
    res[ordenVisita++] = new Arista(u, a.destino, a.peso);
    visitados[a.destino] = 1;
    q.encolar(a.destino);
    }
    }  
    }
    }*/

    /** PRECONDICION: !this.esDirigido()
     * Devuelve un subconjunto de aristas que, con coste minimo,  
     * conectan todos los vertices de un grafo No Dirigido y Conexo, 
     * o null si el grafo no es Conexo.
     * 
     * @return Arista[], array con las numV - 1 aristas que conectan 
     *                   los numV vertices con coste minimo, o null 
     *                   si el grafo no es Conexo
     */  
    public Arista[] kruskal() {
        /**1.-Calcular y eliminar el mínimo de aristas factibles de forma eficiente. Como máximo en el orden del logaritmo. Representar el conjunto aristasFactibles mediante una Cola de Prioridad implementada mediante
         * una PriorityQColaPrioridad, por ejemplo.
         */
        PriorityQColaPrioridad<Arista> aristasFactibles = new PriorityQColaPrioridad<Arista>();
        for(int i = 0; i<numVertices(); i++){
            ListaConPI<Adyacente> l = adyacentesDe(i);
            for(l.inicio(); !l.esFin(); l.siguiente()){
                Adyacente a = l.recuperar();
                Arista ar = new Arista(i,a.destino,a.peso);
                aristasFactibles.insertar(ar);
            }
        }     
        /**       -Inicialmente, al estar E' vacío, el MF-Set cc estará compuesto por N componentes conexas cuyo identificador es el único vértice que contienen.*/
        Arista[] Eprima = new Arista[numVertices()-1];
        MFSet cc = new ForestMFSet(numVertices());
        /**      -Durante cada iteración, se comprobará si la arista (v,w) forma ciclo con las de E' simplemente calculando los idnetificadores de v y w, y comprobando si son distintos; si lo son, se actualizarán las 
         *      componentes conexas de E' y, acto seguido, se añadirá la arista (v,w) al conjunto E'.*/
        int numV = numVertices();
        int cardinalEprima = 0;
        while(cardinalEprima < numV -1 && aristasFactibles.size() > 0 ){
            /*2.-Comprobar si l arista (v,w) forma ciclo con las aristas de E' de forma eficiente. Orden constante. Representar E' mediante un MF-Set cc de N vértices.*/
            Arista vw = aristasFactibles.eliminarMin();
            if(cc.find(vw.getOrigen()) != cc.find(vw.getDestino())){
                cc.merge(vw.getOrigen(),vw.getDestino());
                Eprima[cardinalEprima++] = vw;
            }
        }
        /**      -Finalmente, al concluir el proceso, si el conjunto E' tiene cardinal N-1, todos los elementos del MF-Set cc pertenecerán, por definición de grafo Conexo, a la misma componente conexa.*/
        if(cardinalEprima == numV -1) return Eprima;
        else return null;
    }
}
