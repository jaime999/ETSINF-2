 

import java.io.IOException;
import java.net.*;

public class Ejercicio4 {
    public static void main(String[] args) throws IOException {
        String name = new String("Jaime Martínez Sánchez\n");
        DatagramSocket ds = new DatagramSocket();
        DatagramPacket dp = new DatagramPacket(name.getBytes(),
        name.getBytes().length, InetAddress.getByName("localhost"),7777);
        ds.send(dp);

        
    }
    
}
