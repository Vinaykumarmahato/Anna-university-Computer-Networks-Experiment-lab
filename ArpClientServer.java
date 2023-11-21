import java.io.*;
import java.net.*;

// 5A
/*
 * 
 * The compiler is providing a warning that the code uses or overrides a deprecated API. Deprecated APIs are methods or classes that are still available but are not recommended for use because they may be removed in future versions of Java.

To recompile with deprecation warnings, as suggested by the compiler, you can use the -Xlint:deprecation option. Here's the command:

javac -Xlint:deprecation ArpClientServer.java

javac ArpClientServer.java
java ArpClientServer server
java ArpClientServer client

 */
public class ArpClientServer {
    public static void main(String args[]) throws IOException {
        try {
            if (args.length != 1 || (!args[0].equals("client") && !args[0].equals("server"))) {
                System.out.println("Usage: java ArpClientServer [client/server]");
                return;
            }

            if (args[0].equals("client")) {
                // ARP Client code
                Socket ss = new Socket(InetAddress.getLocalHost(), 1100);
                PrintStream ps = new PrintStream(ss.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String ip;
                System.out.println("Enter the IPADDRESS:");
                ip = br.readLine();
                ps.println(ip);
                String str, data;
                BufferedReader br2 = new BufferedReader(new InputStreamReader(ss.getInputStream()));
                System.out.println("ARP From Server::");
                do {
                    str = br2.readLine();
                    System.out.println(str);
                } while (!(str.equalsIgnoreCase("end")));
                ss.close();
            } else if (args[0].equals("server")) {
                // ARP Server code
                ServerSocket ss = new ServerSocket(1100);
                Socket s = ss.accept();
                PrintStream ps = new PrintStream(s.getOutputStream());
                BufferedReader br1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String ip;
                ip = br1.readLine();
                Runtime r = Runtime.getRuntime();
                Process p = r.exec("arp -a " + ip);
                BufferedReader br2 = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String str;
                while ((str = br2.readLine()) != null) {
                    ps.println(str);
                }
                ss.close();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
