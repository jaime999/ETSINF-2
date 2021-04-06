
/**
 * Clase utilizada para representar un rectángulo.
 * 
 * @author Josep Silva 
 * @version 11 Enero 2017
 */

public class Rectangulo extends Figura
{
    private double base,altura;

    // Constructor
    public Rectangulo(double cx,double cy, double b, double a)
    {
        super(cx,cy);
        base = b; altura = a;
    }
}
