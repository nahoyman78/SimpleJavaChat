
import java.io.IOException;
import java.util.Scanner;


/**
 * Main Class of the program. Makes so user can either
 * choose to setup a server, or connect as a client.
 * @author Yohan Joo
 */
public class Main {
    
    private static final Scanner scan = new Scanner(System.in);
    
    public static void main(String[] args) throws IOException {
        
        System.out.println("Setup Server or Connect as Client? (1 - Server/2 - Client)");
        int connectType = scan.nextInt();
        
        if(connectType == 1) {
            //Start as Server
            server srvr = new server();
        }else if(connectType == 2) {
            //Start as Client
            client clnt = new client();
        }else{
            //If user inputs something else
            System.out.println("Wrong Input, choose 1 or 2");
        }
    }
    
}
