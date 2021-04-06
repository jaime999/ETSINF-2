 

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
public class Ejercicio1 {
    public static void main(String[] args) throws UnknownHostException {
        System.out.println(InetAddress.getByName("www.google.com"));
    }
}
