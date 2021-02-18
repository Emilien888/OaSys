

import java.util.Scanner;

public class Console{
    private static Scanner console = new Scanner(System.in);
    private static String line;
    private static String servInf = "[SERVER/INFO] ";
    static String servWar = "[SERVER/WARN] ";

    public Console(){
    	Thread asyncRead = new Thread(new Runnable(){
    		
			public void run() {
				while(true) {
					try{
			            line = console.nextLine();
			            
			            if(line.equals("stop")) {
			            	System.exit(0);
			            }
			            else if(line.toLowerCase().startsWith("mkadmin ")) {
			                DataWriter dw = new DataWriter();
			               	String userAdmin = line.substring(8);
			               	dw.addAdmin(userAdmin);
			               	System.out.println(TextColor.TEXT_YELLOW + servInf + "Added Admin " + userAdmin + "!" + TextColor.TEXT_RESET);
			                Main.resetAdmins();
			            }
			            else if(line.toLowerCase().equals("mkmcserver")) {
			            	System.out.println(servWar + TextColor.TEXT_YELLOW + "You need to specify a version!" + TextColor.TEXT_RESET);
		            		System.out.println(servWar + TextColor.TEXT_YELLOW + "Allowed versions are 'spigot', 'vanilla' and 'paper'" + TextColor.TEXT_RESET);
			            }
			            else if(line.toLowerCase().startsWith("mkmcserver")) {
			            	
				           	String version = line.substring(11);
				           	McServerInit server = new McServerInit();
				           	server.downloadMc(version);
			            	
			            }
			            
			            else if(line.equals("help")) {
			            	System.out.println(servInf + "Type " + TextColor.TEXT_CYAN + "'stop' " + TextColor.TEXT_RESET + "to stop the server");
			            	System.out.println(servInf + "Type " + TextColor.TEXT_CYAN + "'help' " + TextColor.TEXT_RESET + " to see this list");
			            	System.out.println(servInf + "Type " + TextColor.TEXT_CYAN + "'mkadmin'  " + TextColor.TEXT_RESET + "followed by a user to add him to the role admin");
			            	System.out.println(servInf + "Type " + TextColor.TEXT_CYAN + "'mkmcserver' " + TextColor.TEXT_RESET + "by an option to automatically download a minecraft server");
			            } 
			            else {
			            	System.out.println("Not a valid command! Type 'help' for help");
			            }
			        }catch(Exception e){
			            e.printStackTrace();
			        }
				}		
			}
    		
    	}); 
    	asyncRead.start();
    }

}