// Exp: 5B

/*
 * 
 * 
 * javac RARPClientServer.java
java RARPClientServer server
java RARPClientServer client
example of a MAC address:
6A:1B:56:4F:89:2C

 */

import java.io.*;
import java.net.*;

public class RARPClientServer {
    public static void main(String args[]) {
        if (args.length != 1 || (!args[0].equals("client") && !args[0].equals("server"))) {
            System.out.println("Usage: java RARPClientServer [client/server]");
            return;
        }

        if (args[0].equals("client")) {
            // RARP Client code
            try {
                DatagramSocket client = new DatagramSocket();
                InetAddress addr = InetAddress.getByName("127.0.0.1");
                byte[] sendByte = new byte[1024];
                byte[] receiveByte = new byte[1024];

                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Enter the Physical address (MAC):");
                String str = in.readLine();
                sendByte = str.getBytes();

                DatagramPacket sender = new DatagramPacket(sendByte, sendByte.length, addr, 1309);
                client.send(sender);

                DatagramPacket receiver = new DatagramPacket(receiveByte, receiveByte.length);
                client.receive(receiver);

                String logicalAddress = new String(receiver.getData()).trim();
                System.out.println("The Logical Address is (IP): " + logicalAddress);

                client.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (args[0].equals("server")) {
            // RARP Server code
            try {
                DatagramSocket server = new DatagramSocket(1309);
                while (true) {
                    byte[] sendByte = new byte[1024];
                    byte[] receiveByte = new byte[1024];

                    DatagramPacket receiver = new DatagramPacket(receiveByte, receiveByte.length);
                    server.receive(receiver);

                    String str = new String(receiver.getData());
                    String macAddress = str.trim();
                    InetAddress addr = receiver.getAddress();
                    int port = receiver.getPort();

                    String[] ipAddresses = {"165.165.80.80", "165.165.79.1"};
                    String[] macAddresses = {"6A:08:AA:C2", "8A:BC:E3:FA"};

                    for (int i = 0; i < ipAddresses.length; i++) {
                        if (macAddress.equals(macAddresses[i])) {
                            sendByte = ipAddresses[i].getBytes();
                            DatagramPacket sender = new DatagramPacket(sendByte, sendByte.length, addr, port);
                            server.send(sender);
                            break;
                        }
                    }
                    break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
