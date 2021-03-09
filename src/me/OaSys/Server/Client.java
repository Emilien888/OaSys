package me.OaSys.Server;



//Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import me.OaSys.Auth.EmailAuth;
import me.OaSys.Init.Main;
import me.OaSys.Server.Data.DataWriter;
import me.OaSys.Server.Data.UserLoader;

public class Client implements Runnable {
	
	
	
	
    //format variables
	static String servInf = "[SERVER/INFO] ";
	static String servWar = "[SERVER/WARN] ";
	static String servMsg = "[SERVER/USR_MSG] ";
	static String servCmd = "[SERVER/USR_CMD] ";
    
    //User logger
    public PrintWriter serverPrintOut1 = null;
    public Scanner scanner1 = null;

    //User var
    public String newUser = null;

    //If he leaves the server
    public boolean done = false;
    
	private Socket client;
	ArrayList<Client> clients;
    
    //Constructor for a nes client thread
	public Client(Socket clientSocket, ArrayList<Client> clients) throws IOException {
		this.client = clientSocket;
		this.clients = clients;
	}
    
    //Send a message to all client threads
	public void outToAll(String message) {
		for(Client aClient : clients) {
			if(!(aClient.done)) {
				aClient.serverPrintOut1.println(message);
			}
		}
	}
	
	//End the session
	public static void stopClient() {
		System.exit(0);
	}
	
	//Get if the given user is admin
	public boolean getIsAdmin() {
		if(Main.admins.contains(newUser)) {
			return true;
		}
		return false;
	}
    
    //Get the user per client thread
	public String getClientUser(Client client) {
		return client.newUser;
	}
	
	
	
	
    @Override
    
    /*
        Run the client thread
        the interactions between 
        the client and the server
    */
	public void run() {
    	
    	
        InputStream inputToServer1 = null;
        OutputStream outputFromServer1 = null;
        
        try {
			inputToServer1 = client.getInputStream();
			System.out.println(servInf+"Loaded user input stream");
		} catch (IOException e2) {
			System.out.println(servWar + "Error: could not load user input stream");
			e2.printStackTrace();
        }
        //Output
		try {
			outputFromServer1 = client.getOutputStream();
			System.out.println(servInf+"Loaded server output stream");
		} catch (IOException e2) {
			System.out.println(servWar + "Error: could not load server output stream");
			e2.printStackTrace();
        }
        
        
    	
    	

        /*
            Here, we load the user input and output streams
        */

        //Input
		
        /*
            Then, we assign these to a scanner and a 
            Print writer
        */

        //User input scanner
		scanner1 = new Scanner(inputToServer1, "UTF-8");
        
        //User output Printer
		try {
			serverPrintOut1 = new PrintWriter(new OutputStreamWriter(
			    outputFromServer1, "UTF-8"), true);
			System.out.println(servInf+"Loaded output printer");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        
        /*
            This shows when the user connects to the server
            The interaction begins
        */
        serverPrintOut1.println("Hello user! If you are new to this server ("+Main.name+"), enter a new username. If you already have a username, enter it now.");
        
        /*
            These lists contain all the users and passwords
            in parallel order
        */
        ArrayList<String> users = UserLoader.getUsers();
        Map<String, String> emails = UserLoader.getEmails();

        //If the user is admitted into the server
        boolean verified = false;
        
        
        /*
		    Success message for the input/output streams, Scanner, Printer
		    And the loading of the users and passwords
		*/
		System.out.println(servInf + "Successfully loaded all assets");

		//Take the first input
		while(!verified && scanner1.hasNextLine()){
		    newUser = scanner1.nextLine();
		    
		    /*
		    Check if the user entered contains invalid characters
		    */
		    if(newUser.contains("'") || newUser.contains(" ") || newUser == null || newUser == "\n" || newUser.length() == 0){
		        serverPrintOut1.println("Not a valid username!");
		        done = true;
			}
		    /*
		        Check if the line the user entered is in the ArrayList of users
		    */

		    //If yes then ask for password
		    if(!done) {
		    	if(users.contains(newUser)){
		            if(EmailAuth.sendEmailAuth(newUser, this, emails.get(newUser))) {
		            	serverPrintOut1.println("Glad you're back, " + newUser + "!");
		            	verified = true;
		            }else {
		            	verified = false;
		            	done = true;
		            }
		    	}else {
		    		serverPrintOut1.println("Enter a valid GMail address as your confirmation email");
		    		String email = scanner1.nextLine();
		    		
		    		if(email.contains("@gmail.")) {
		    			serverPrintOut1.println("Created new user " + newUser + "!");
		    			new ServerUser(newUser, email);
		    			verified = true;
		    		}else {
		    			serverPrintOut1.println("This isn't a valid GMail address.");
		    			serverPrintOut1.println("User creation canceled. [LOGOUT]");
		    			done = true;
		    		}
		    	}
		    
		    
		    
		    /*
		        If the user entered is not in the list,
		        Create a new user for them
		    */
		  
		   }
		    
		}
        
        
        /*
            Main loop for the client
            scans the user input, checks if it equals a command
            else send it to the other client threads
        */
        
        while(!done && scanner1.hasNextLine()){
        	boolean kicked = false;
        	ArrayList<String> online = new ArrayList<String>();
        	String line = scanner1.nextLine();
                
            /*
                Here put the commands
                check if the line is equal to a 
                command and then do stuff

                Finally, log 
             */    
                
            //Command to leave the server                
            if(line.toLowerCase().trim().equals("/exit")){
            	for(Client aClient : clients) {
            		if(getClientUser(aClient).equals(newUser)) {
            			online.remove(newUser);            		
            		}
            	}
            	System.out.println(servCmd + newUser + " left the server");
            	clients.remove(this);
                done = true;
                scanner1.close();
                return;
            }
            
            //Command to get the description of the server
            else if(line.toLowerCase().equals("/description")) {
            	serverPrintOut1.println(Main.description);
            }
            
            
            //Command to know all online users
            else if(line.toLowerCase().equals("/online")){
                for (Client aClient : clients){
                    serverPrintOut1.println(getClientUser(aClient));
                }
            }
            
            
            //Command to know commands  
            else if(line.toLowerCase().trim().equals("/help")) {
                /*
                    Available commands 
                    STILL IN PROGRESS
                */
                serverPrintOut1.println("Type /online to see the online users");
                serverPrintOut1.println("Type /help to see this list");
                serverPrintOut1.println("Type /description to get this server's description");
                if(getIsAdmin()) {
                	serverPrintOut1.println("--------[ADMIN COMMANDS]--------");
                	serverPrintOut1.println("Type /stop to stop the server");
                	serverPrintOut1.println("Type /mkadmin followed by a user to make them an admin");
                	serverPrintOut1.println("Type /kick followed by a user to kick their connection");
                }
                System.out.println(servCmd + newUser + " issued server command: /help");
            }
           
                
            //ADMIN commands
            else if(line.toLowerCase().equals("/stop")) {
            	if(getIsAdmin()) {
            		System.out.println(servCmd + newUser + "Stopped the server!");
            		stopClient();
            		System.exit(0);
            	}else {
            		serverPrintOut1.println("This is an admin command!");
            	}
            }

            //Command to kick a user
            else if(line.toLowerCase().startsWith("/kick ")){
            	if(getIsAdmin()) {
            		String userTokick = line.substring(6);
                	for(Client aClient : clients) {
                		if(getClientUser(aClient).equals(userTokick)) {
                			System.out.println(servCmd + newUser + " kicked " + userTokick);
                			aClient.serverPrintOut1.println("You've been kicked by " + newUser);
                			aClient.done = true;
                			kicked = true;
                		}
                	}
                	if(!kicked) {
                		serverPrintOut1.println("Could not find the user you specified");
     
                	}
            	}else {
            		serverPrintOut1.println("This is an admin command!");
            	}      	
            }
            
            else if(line.toLowerCase().startsWith("/mkadmin ")) {
            	if(getIsAdmin()) {
                	DataWriter dw = new DataWriter();
                	String userAdmin = line.substring(9);
                	dw.addAdmin(userAdmin);
                	Main.resetAdmins();
                	System.out.println(servCmd + newUser + " added admin " + userAdmin);
                	serverPrintOut1.println("Added Admin " + userAdmin + "!");
            	}else {
            		serverPrintOut1.println("This is an admin command!");
            	}
            }
                
            //if there are no commands invoked
            else if(!done){
            	if(getIsAdmin()) {
            		String message = "[ADMIN] " + "<"+newUser+"> " + line;
            		System.out.println(servMsg + " "+message);
            		outToAll(message);
            	}else {
                	String message = "<"+newUser+"> "+line;
                    System.out.println(servMsg + " "+message);
                    outToAll(message);
            	}
            }              
        }
	}
}

