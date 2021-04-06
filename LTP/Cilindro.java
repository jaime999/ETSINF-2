
/**
 * Clase utilizada para representar un cilindro.
 * 
 * @author Josep Silva 
 * @version 11 Enero 2017
 */

public class Cilindro extends Circulo
{
    protected double altura;
    protected double volumen;

    //Constructor
    public Cilindro(double x, double y, double r, double a)
    {
        super(x,y,r);
        altura = a;
    }

    //Constructor
    public Cilindro(Circulo c, double a)
    {
        this(c.x,c.y,c.radio,a);
    }

    public double volumen()
    {
        return super.radio*super.radio*Math.PI*altura;   
    }    
    
}
