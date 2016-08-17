
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Server Class. Fully back-end server
 * @author Yohan Joo
 */
public class server {
    
    public static String motd = "Welcome to the server!";
    private final int serverPort = 6066;
    private ServerSocket serverSocket;
    public static int countOfClients = 0;
    public static ArrayList<miniServer> servers;
    
    public server() throws IOException {
        
        //Initialize ArrayList
        servers = new ArrayList<>();
        
        //Try to listen to port
        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Successfully listened to port " + serverPort + "\nServer is Running!");
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + serverPort);
        }
        
        //If a client connects to the server
        while(true){
            Socket clientSocket = serverSocket.accept();
            countOfClients++;
            
            miniServer mini = new miniServer(clientSocket);
            miniServer s =new miniServer(clientSocket);
            servers.add(s);
            s.start();
            System.out.println("Clients Connected: " + servers.size());
        }
    }
    
    //sendMSGToAllClients
    public static void sendMSGToClients(String msg, String clientAddress) {
        for (miniServer server : servers) {
            try {
                server.out.writeUTF(clientAddress + ": " + msg);
            }catch (NullPointerException ex) {
                System.out.println("Something Wrong, maybe a Client Missing");
            }catch (IOException ex) {
                System.out.println("IOEXCEPTION, LINE 74");
            }
        }
    }
}
