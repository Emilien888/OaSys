package me.OaSys.Init;




import java.util.ArrayList;
import java.util.Map;

import me.OaSys.Server.Server;
import me.OaSys.Server.ServerSetup;
import me.OaSys.Server.Data.DataLoader;
import me.OaSys.Util.OSFetcher;
import me.OaSys.Util.TextColor;

public class Main {
	
	//The server is in heavy development :)
	static String version = "0.0000020";
	
	public static String name;
	public static String description;
	public static int pool;
	public static ArrayList<String> admins;

	//Log formats
	static String servInf = "[SERVER/INFO] ";
	static String servWar = "[SERVER/WARN] ";
	
	static DataLoader dl = new DataLoader();
	
	public static void resetAdmins() {
		admins = dl.getJSONAdmins();
		System.out.println(servInf + "Reseted Admins");
	}
	
	//Start the server on port 1
	public static void main( String [] args) {
			try {
				
				new ServerSetup();
				System.out.println(servInf + "OS: " + OSFetcher.getOS());
				System.out.print(servInf + "Collecting config... ");
				Map<String, Object> config = dl.getYMLData();
				name = (String) config.get("server-name");
				description = (String) config.get("server-description");
				pool = (int) config.get("server-pool");
				System.out.print("done \n");
				System.out.print(servInf + "collecting admins... ");
				admins = dl.getJSONAdmins();
				System.out.print("done \n");
		 		System.out.println(servInf + "Launching server version "+ version);
				System.out.println(servInf + "Type 'stop' to stop the server");
				Server.Serverstart((int) config.get("server-port"));
				
			}catch (Exception e) {
				System.out.println(TextColor.TEXT_RED + servWar + "A fatal error occured while launching the server. Please check you have all the files and that their contents is what it is supposed to be." + TextColor.TEXT_RESET);
				e.printStackTrace();
		}
	}
}