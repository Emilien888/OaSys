package me.OaSys.Server;



//Imports
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.OaSys.Init.Main;

public class Server {

    //A format String for the system.out logs
    private static String serverInfo = "[SERVER/INFO] ";

    /*
        Client related variables
        
        'membersAllowed' states how many clients can connect at the same time
        'exeServ' takes care of the client thread handling
    */
    private static ArrayList<Client> clients = new ArrayList<>(); 
    static int membersAllowed = Main.pool;
    static ExecutorService exeServ = Executors.newFixedThreadPool(membersAllowed);
    
    //Start the server in an infinite loop
    public static void Serverstart(int port) {
        //Create the Socket
    	try(ServerSocket serverSocket = new ServerSocket(port)){
    		System.out.println(serverInfo + "starting server on port " + port);
            //Server loop
    		new Console();
    		while (true) {

                //We wait for a connection
                System.out.println(serverInfo + "Awaiting Connection...");
                
                //Someone connects to the server
    			Socket connectionSocket = serverSocket.accept();
                System.out.println(serverInfo + "Someone entered the server!");
                
                //Create the separate client thread
    			Client clientThread = new Client(connectionSocket, clients);
    			clients.add(clientThread);          
    			exeServ.execute(clientThread);
      }
            
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}