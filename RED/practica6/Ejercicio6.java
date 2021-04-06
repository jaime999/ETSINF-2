 

import java.io.IOException;
import java.net.*;

public class Ejercicio6 {

    public static void main(String[] args) throws IOException {
        String name = new String("Jaime Martínez Sánchez\n");
        DatagramSocket ds = new DatagramSocket(7777);
        DatagramPacket dp = new DatagramPacket(name.getBytes(),
        name.getBytes().length, InetAddress.getByName("localhost"),7777);
        ds.send(dp);
        byte [] buffer = new byte [1000];
        DatagramPacket p = new DatagramPacket(buffer, 1000);
        ds.receive(p);
        String s = new String(p.getData(), 0, p.getLength());
        System.out.println(s);
}
}