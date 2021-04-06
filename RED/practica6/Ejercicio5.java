 

import java.io.IOException;
import java.net.*;
import java.util.*;

public class Ejercicio5 {
    public static void main(String[] args) throws IOException {
        Date now = new Date();
        String name = now.toString();
        DatagramSocket ds = new DatagramSocket(7777);
        byte [] buffer = new byte [1000];
        DatagramPacket p = new DatagramPacket(buffer, 1000);
        ds.receive(p);
        p.setData(name.getBytes());
        p.setLength(name.length());
        ds.send(p);
        ds.close();
    }
}
