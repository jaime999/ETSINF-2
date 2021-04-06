 

import java.net.*;

public class Ejercicio3 {
    public static void main(String[] args) throws SocketException {
        DatagramSocket socket = new DatagramSocket();
        System.out.println("DatagramSocket en el puerto: " + socket.getLocalPort());
    }
}
