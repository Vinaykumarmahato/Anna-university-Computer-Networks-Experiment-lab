import java.net.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

public class second {

    // exp 2
    public static void main(String args[]) throws Exception {
        if (args.length == 0 || (!args[0].equals("client") && !args[0].equals("server"))) {
            System.out.println("Usage: java ClientServerCombined [client/server]");
            return;
        }

        if (args[0].equals("client")) {
            // Client code
            Socket soc;
            BufferedImage img = null;
            soc = new Socket("localhost", 4000);
            System.out.println("Client is running.");
            try {
                System.out.println("Reading image from disk.");
                img = ImageIO.read(new File("vinay.png"));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                ImageIO.write(img, "png", baos);
                baos.flush();
                byte[] bytes = baos.toByteArray();
                baos.close();
                System.out.println("Sending image to server.");

                OutputStream out = soc.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out);
                dos.writeInt(bytes.length);
                dos.write(bytes, 0, bytes.length);
                System.out.println("Image sent to server.");
                dos.close();
                out.close();
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                soc.close();
            } finally {
                soc.close();
            }
        } else if (args[0].equals("server")) {
            // Server code
            ServerSocket server = null;
            Socket socket = null;

            try {
                server = new ServerSocket(4000);
                System.out.println("Server Waiting for image");
                socket = server.accept();
                System.out.println("Client connected.");
                InputStream in = socket.getInputStream();
                DataInputStream dis = new DataInputStream(in);
                int len = dis.readInt();
                System.out.println("Image Size: " + len / 1024 + "KB");
                byte[] data = new byte[len];
                dis.readFully(data);
                dis.close();
                in.close();
                InputStream ian = new ByteArrayInputStream(data);
                BufferedImage bImage = ImageIO.read(ian);
                JFrame f = new JFrame("Server");
                ImageIcon icon = new ImageIcon(bImage);

                JLabel l = new JLabel();
                l.setIcon(icon);
                f.add(l);
                f.pack();
                f.setVisible(true);
            } catch (IOException e) {
                System.out.println("Exception: " + e.getMessage());
            } finally {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                if (server != null && !server.isClosed()) {
                    server.close();
                }
            }
        }
    }
}

// Runing process:

// cd path/to/directory
// javac ClientServerCombined.java
// java ClientServerCombined server
//  java ClientServerCombined client
