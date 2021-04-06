
/**
 * Clase utilizada para representar un círculo.
 * 
 * @author Josep Silva 
 * @version 11 Enero 2017
 */

public class Circulo extends Figura
{
    protected double radio;

    // Constructor
    public Circulo(double a, double b, double c)
    {
        super(a,b);
        radio=c;
    }
}
