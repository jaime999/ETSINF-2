 

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Ejercicio2 {
    public static void main(String[] args) throws UnknownHostException {
        System.out.println(Arrays.toString(InetAddress.getAllByName("www.google.es")));
    }
}
