


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
import java.util.Scanner;

public class Client implements Runnable {
	
    //format variables
	static String servInf = "[SERVER/INFO] ";
	static String servWar = "[SERVER/WARN] ";
	static String servMsg = "[SERVER/USR_MSG] ";
	static String servCmd = "[SERVER/USR_CMD] ";
    
    //User logger
    public PrintWriter serverPrintOut1 = null;

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
        /*
            Here, we load the user input and output streams
        */

        //Input
		try {
			inputToServer1 = client.getInputStream();
			System.out.println(servInf+"Loaded user input stream");
		} catch (IOException e2) {
			System.out.println(servWar + "Error: could not load user input stream");
			e2.printStackTrace();
        }
        //Output
        OutputStream outputFromServer1 = null;
		try {
			outputFromServer1 = client.getOutputStream();
			System.out.println(servInf+"Loaded server output stream");
		} catch (IOException e2) {
			System.out.println(servWar + "Error: could not load server output stream");
			e2.printStackTrace();
        }
        /*
            Then, we assign these to a scanner and a 
            Print writer
        */

        //User input scanner
        Scanner scanner1 = new Scanner(inputToServer1, "UTF-8");
        
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
        ArrayList<String> users = new ArrayList<String>();
        ArrayList<String> passwords = new ArrayList<String>();

        //If the user is admitted into the server
        boolean verified = false;
        
        
        try{

            /*
                Load the user file and put it in the ArrayList
            */
        	PathFinder pf = new PathFinder();
        	String os = OSFetcher.getOS();
    		String osname;
    		if(os.toLowerCase().contains("windows")) {
    			osname = "windows";
    		}else {
    			osname = "unix";
    		}
    		
    		File ServerUsers = null;
    		
    		switch(osname) {
    		case "windows":
    			ServerUsers = new File(pf.getDirPath() + "\\.ServerUsers.txt");
    			break;
    		case "unix":
    			ServerUsers = new File(pf.getDirPath() + "/.ServerUsers.txt");
    		}
            
            Scanner userReader = new Scanner(ServerUsers);
            while(userReader.hasNextLine()){
                String line = userReader.nextLine();
                users.add(line);
            }

            //Success log -> users
            System.out.println(servInf+"Loaded users");
            userReader.close();
            
            /*
                Load the user file and put it in the ArrayList
            */
            File ServerPass = null;
            switch(osname) {
    		case "windows":
    			ServerPass = new File(pf.getDirPath() + "\\.ServerUserPasswords.txt");
    			break;
    		case "unix":
    			ServerPass = new File(pf.getDirPath() + "/.ServerUserPasswords.txt");    			
    		}

            Scanner PassReader = new Scanner(ServerPass);
            while(PassReader.hasNextLine()){
                String line = PassReader.nextLine();
                passwords.add(line);
            }
            //Success log -> passwords
            System.out.println(servInf+"Loaded passwords");
            PassReader.close();

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
                    Check if the line the user entered is in the ArraList of users
                */

                //If yes then ask for password
                if(!done) {
                	if(users.contains(newUser)){
                        serverPrintOut1.println("Please enter your password");
                        
                        //Get the password
                        String pass = scanner1.nextLine();
                        
                        /* 
                            Check if the index of the user is the 
                            same as the password entered
                        */
                    	int userIndex = users.indexOf(newUser);
                    	if(pass.equals(passwords.get(userIndex))) {

                        //Success
                    	if(getIsAdmin()) {
                    		serverPrintOut1.println("Welcome back, admin " + newUser + "!");
                    	}else {
                        	serverPrintOut1.println("Glad you're back, " + newUser + "!");
                    	}
                        verified = true;
                        System.out.println(servInf+"User Connected: " + newUser);
                    	}else {
                            
                            //Fail password
                            serverPrintOut1.println("Nope, not your password! Log out and back in, please.");
                            done = true;
                    	}
                    }
                
                
                
                /*
                    If the user entered is not in the list,
                    Create a new user for them
                */
                else if(!done){
                    
                	
                	
                    	

                        /*
                            Prompt the user for his new password and
                            write it in the data file
                        */

                        //Prompt password
                        serverPrintOut1.println("Please enter a new password");

                        //Get the password
                        String pass = scanner1.nextLine();
                        
                        if(pass.contains("'") || pass.contains(" ") || pass == null || pass == "\n" || pass.length() == 0){
                            serverPrintOut1.println("Not a valid password. ");
                            done = true;
                    	}
                        /*
                    	Else, create the new user, prompt a password and write this
                    	in the server data files
                         */
            	
                        if(!done) {
                        	 //Load the user writer
                            PrintWriter Users = new PrintWriter(new FileWriter(ServerUsers, true));
                        
                            //write the user
                            Users.write(newUser + "\n");

                            //Log the new user to the user
                            serverPrintOut1.println("User created, " + newUser + "!");

                            //Close the file
                            Users.close();

                            //Load the data file
                            PrintWriter passwordWriter = new PrintWriter(new FileWriter(ServerPass, true));

                            //Write the password
                            passwordWriter.write(pass + "\n");

                            //Close the file
                            passwordWriter.close();

                            //Tell the user to remember his password
                            serverPrintOut1.println("Remember your password");

                            //He is verified
                            verified = true;

                            //User creation log
                            System.out.println(servInf+"New User Created: " + newUser);
                        }       
                	}
               }
                
            }
        }catch(FileNotFoundException e){
            System.out.println("An error occured.");
            e.printStackTrace();
        }catch (IOException err) {
        	err.printStackTrace();
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

