import java.io.*;
import java.net.*;


// exp 3b

/*
 * 
 * cd path/to/directory
javac EchoClientServer.java
java EchoClientServer client
java EchoClientServer server

 * 
 * 
 * 
 * 
 */
public class EchoClientServer {
    public static void main(String args[]) {
        if (args.length == 0 || (!args[0].equals("client") && !args[0].equals("server"))) {
            System.out.println("Usage: java EchoClientServer [client/server]");
            return;
        }

        if (args[0].equals("client")) {
            // Client code
            Socket c = null;
            String line;
            BufferedReader is, is1;
            PrintStream os;

            try {
                c = new Socket("localhost", 8080);
            } catch (IOException e) {
                System.out.println(e);
            }

            try {
                os = new PrintStream(c.getOutputStream());
                is = new BufferedReader(new InputStreamReader(System.in));
                is1 = new BufferedReader(new InputStreamReader(c.getInputStream()));

                do {
                    System.out.println("client");
                    line = is.readLine();
                    os.println(line);
                    if (!line.equals("exit"))
                        System.out.println("server:" + is1.readLine());
                } while (!line.equals("exit"));
            } catch (IOException e) {
                System.out.println("socket closed");
            } finally {
                try {
                    if (c != null) {
                        c.close();
                    }
                } catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
        } else if (args[0].equals("server")) {
            // Server code
            ServerSocket s = null;
            String line;
            BufferedReader is;
            PrintStream ps;
            Socket c = null;

            try {
                s = new ServerSocket(8080);
            } catch (IOException e) {
                System.out.println(e);
            }

            try {
                c = s.accept();
                is = new BufferedReader(new InputStreamReader(c.getInputStream()));
                ps = new PrintStream(c.getOutputStream());

                while (true) {
                    line = is.readLine();
                    System.out.println("msg received and sent back to client");
                    ps.println(line);
                }

            } catch (IOException e) {
                System.out.println(e);
            } finally {
                try {
                    if (c != null) {
                        c.close();
                    }
                    if (s != null) {
                        s.close();
                    }
                } catch (IOException e) {
                    System.out.println("Error closing server socket: " + e.getMessage());
                }
            }
        }
    }
}
