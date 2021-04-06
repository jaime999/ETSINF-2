
/**
 * Clase utilizada para representar una agrupación de figuras geométricas.
 * 
 * @author Josep Silva 
 * @version 11 Enero 2017
 */

public class GrupoFiguras <T extends Figura>
{
    static final int MAX_NUM_FIGURAS = 10;
    private Figura [] listaFiguras = new Figura [MAX_NUM_FIGURAS];
    private int numF=0;

    /**public T getFavorita() 
    {
        return favorita;
    }**/
    
    public int numFiguras() 
    {
        return numF;
    }

    public void anyadeFigura(Figura f) 
    {
        listaFiguras[numF++]= f;
    }
    
    public void imprimirPosiciones()
    {      
        for(int i=0; i<numF; i++)
        {
           System.out.println("Posicion de la figura "+(i+1)+": ("+listaFiguras[i].x+", "+listaFiguras[i].y+")");
        }   
    }

}
