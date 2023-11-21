import java.io.*;
import java.net.*;
// Exp 4


/*
 * 
 * RUN
 * 
 * javac UdpDnsServerClient.java
 * java UdpDnsServerClient server
java UdpDnsServerClient client


 */
public class UdpDnsServerClient {
    private static int indexOf(String[] array, String str) {
        str = str.trim();
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(str)) return i;
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        String[] hosts = {"yahoo.com", "gmail.com", "cricinfo.com", "facebook.com"};
        String[] ip = {"68.180.206.184", "209.85.148.19", "80.168.92.140", "69.63.189.16"};

        System.out.println("Press Ctrl + C to Quit");

        while (true) {
            DatagramSocket serverSocket = new DatagramSocket(1362);
            byte[] sendData = new byte[1021];
            byte[] receiveData = new byte[1021];

            DatagramPacket recvPacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(recvPacket);

            String received = new String(recvPacket.getData()).trim();
            InetAddress clientAddress = recvPacket.getAddress();
            int clientPort = recvPacket.getPort();

            System.out.println("Request for host " + received);

            String response;
            if (indexOf(hosts, received) != -1) {
                response = ip[indexOf(hosts, received)];
            } else {
                response = "Host Not Found";
            }

            sendData = response.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            serverSocket.send(sendPacket);

            serverSocket.close();
        }
    }
}
