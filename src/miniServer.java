
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Handles the communication between Server and Client.
 * @author Yohan Joo
 */
public class miniServer extends Thread {
    
    public String clientName;
    private Socket socket = null;
    public DataInputStream in;
    public DataOutputStream out;
    
    public miniServer(Socket socket) {
        super("MiniServer");
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
            
            //Call Client just connected, get clients inputstream & outputstream
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            
            //Welcome client to Server
            out.writeUTF(server.motd);
            System.out.println("Looping!");
            
            //Loop and check for client to send a message
            checkClientMsg.start();
            
        } catch (IOException ex) { //If client Disconnects
            
        }
    }
    
    //check client to send msg
    Thread checkClientMsg = new Thread() {
        @Override
        public void run() {
            while(true) {
                try {
                    String clientMsg;
                    if((clientMsg = in.readUTF()) != null) {
                        System.out.println(clientMsg);
                        String[] clientMes = clientMsg.split(":");
                        
                        //If client sends its name
                        if("U".equals(clientMes[0])) {
                            clientName = clientMes[1];
                        }
                        //If Client sends its message to other
                        else if("S".equals(clientMes[0]) && clientMes.length > 1) {
                            server.sendMSGToClients(clientMes[1], clientName);
                        }
                    }
                } catch (IOException ex) {
                    System.out.println(socket.getRemoteSocketAddress() + " Just Disconnected");
                    server.servers.remove(this);
                    server.countOfClients--;
                    System.out.println("There are now: " + server.countOfClients + " online");
                    break;
                }
            }
        }
    };
}
