import java.net.*;
import java.io.*;


/*
 * Exp 3C
 * 
 * To run the code:

Compile it using javac FileTransfer.java.
Run the server in one terminal with java FileTransfer server.
Run the client in another terminal with java FileTransfer client.


 */
public class FileTransfer {
    public static void main(String[] args) {
        if (args.length != 1 || (!args[0].equals("client") && !args[0].equals("server"))) {
            System.out.println("Usage: java FileTransfer [client/server]");
            return;
        }

        if (args[0].equals("client")) {
            // Client code
            try {
                long start = System.currentTimeMillis();

                Socket sock = new Socket("127.0.0.1", 13267);
                System.out.println("Connecting...");

                InputStream is = sock.getInputStream();
                FileOutputStream fos = new FileOutputStream("vinay.pdf");
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = is.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }

                long end = System.currentTimeMillis();
                System.out.println("File received in " + (end - start) + " milliseconds.");

                bos.close();
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (args[0].equals("server")) {
            // Server code
            try {
                ServerSocket servsock = new ServerSocket(13267);
                while (true) {
                    System.out.println("Waiting for connection...");
                    Socket sock = servsock.accept();
                    System.out.println("Accepted connection: " + sock);

                    File myFile = new File("vinay.pdf");
                    FileInputStream fis = new FileInputStream(myFile);
                    BufferedInputStream bis = new BufferedInputStream(fis);

                    OutputStream os = sock.getOutputStream();
                    System.out.println("Sending file...");

                    byte[] buffer = new byte[4096];
                    int bytesRead;

                    while ((bytesRead = bis.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }

                    os.flush();
                    sock.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
