
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client class. Connects to server, choose name and send message to people.
 * @author Yohan Joo
 */
public class client {
    
    private static final Scanner scan = new Scanner(System.in);
    private final String name;
    public String serverName = "";
    public DataOutputStream out;
    public DataInputStream in;
    public int port = 6066;
    
    public client() {
        
        //Enter name
        System.out.println("Enter server address: ");
        serverName = scan.next();
        System.out.println("Enter your name: ");
        name = scan.next();
        
        connectToServer();
    }
    
    private void connectToServer() {
        try {
            //Connect to server
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);
            
            //When connected to server
            System.out.println("Connected to server!");
            out = new DataOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());
            
            //Send info about client to server
            out.writeUTF("U:" + name);
            out.writeUTF(client.getRemoteSocketAddress() + " connected");
            
            clientLoop.start();
            checkServerMsg.start();
            
        } catch (IOException ex) {
            //If connecting timed out
            System.out.println("ERROR: Can't connect to server!");
        }
    }
    
    //FUNCTION: Loops and sends message to everyone
    Thread clientLoop = new Thread() {
        @Override
        public void run() {
            while(true) {
                try {
                    //Send Chat to everyone!
                    String userInput = scan.nextLine();
                    if(!"".equals(userInput)) {
                        out.writeUTF("S:" + userInput);
                    }
                } catch (IOException ex) {
                    //If Connection Breaks
                    System.out.println("ERROR: Can't reach server! Restart please");
                }
            }
        }
    };
    
    //FUNCTION: Check for any message from server
    Thread checkServerMsg = new Thread() {
        @Override
        public void run() {
            while(true) {
                try {
                    String serverMsg;
                    if((serverMsg = in.readUTF()) != null) {
                        System.out.println(serverMsg);
                    }
                } catch (IOException ex) {
                    //If Connection Breaks
                    System.out.println("Connection broken... please restart client");
                    break;
                }
            }
        }
    };
    
}
