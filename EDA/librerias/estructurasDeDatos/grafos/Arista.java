package librerias.estructurasDeDatos.grafos;

/** Clase Arista: representa una arista de un grafo.<br> 
 *  
 *  @version Mayo 2018
 */

public class Arista implements Comparable<Arista>{

    protected int origen;
    protected int destino;    
    protected double peso;

    /** Crea una arista (v, w) con peso p.
     *
     * @param v  Vertice origen
     * @param w  Vertice destino
     * @param p  Peso
     */
    public Arista(int v, int w, double p) {
        origen = v;
        destino = w;
        peso = p;
    }

    /** Devuelve el vertice origen de una arista.
     *
     * @return int vertice origen
     */
    public int getOrigen() {    
        return origen;
    }

    /** Devuelve el vertice destino de una arista.
     *
     * @return int vertice destino
     */
    public int getDestino() {  
        return destino;
    }

    /** Devuelve el peso de una arista.
     *
     * @return double Peso de la arista
     */
    public double getPeso() {
        return peso;  
    }

    /** Devuelve un String que representa una arista
     * en formato (origen, destino, peso)
     *
     * @return  String que representa la arista
     */
    public String toString() {
        return "("+origen+", "+destino+", "+peso+")";
    }
    
    public int compareTo(Arista a){
        return (int)(peso-a.peso);
    }
}