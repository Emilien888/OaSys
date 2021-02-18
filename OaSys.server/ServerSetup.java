import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class ServerSetup {
	
	static String servInf = "[SERVER/INFO] ";
	static String servWar = "[SERVER/WARN] ";
	
	public boolean isWindows;
	
	public ServerSetup() {
		PathFinder pf = new PathFinder();
		String os = OSFetcher.getOS();
		String osname;
		if(os.toLowerCase().contains("windows")) {
			osname = "windows";
		}else {
			osname = "unix";
		}
		
		String dir = pf.getDirPath();
		
		switch (osname) {
		case "windows":
			File admins = new File(dir + "\\admins.json");
			File config = new File(dir+ "\\config.yml");
			File users = new File(dir+ "\\.ServerUsers.txt");
			File pass = new File(dir+ "\\.ServerUserPasswords.txt");
			try {
				if(admins.createNewFile()) {
					System.out.println(TextColor.TEXT_YELLOW + servInf + "Crreated admins file" + TextColor.TEXT_RESET);
					PrintWriter pw = new PrintWriter(admins);
					pw.println("[]");
					pw.close();
				}else {
					System.out.println(servWar + "file <admins.json> already exists. Please check this is intentional");
				}
				if(config.createNewFile()) {
					System.out.println(TextColor.TEXT_YELLOW + servInf + "Crreated config file" + TextColor.TEXT_RESET);
					PrintWriter pw = new PrintWriter(config);
					pw.println("server-name: Server");
					pw.println("server-description: A Simple Server");
					pw.println("server-pool: 10");
					pw.println("server-port: 22222");
					pw.close();
				}else {
					System.out.println(servWar + "file <config.yml> already exists. Please check this is intentional");
				}
				if(users.createNewFile()) {
					System.out.println(TextColor.TEXT_YELLOW + servInf + "Created users file" + TextColor.TEXT_RESET);
				}else {
					System.out.println(servWar + "file <.ServerUsers.txt> already exists. Please check this is intentional");
				}
				if(pass.createNewFile()) {
					System.out.println(TextColor.TEXT_YELLOW + servInf + "Created password file" + TextColor.TEXT_RESET);
				}else {
					System.out.println(servWar + "file <.ServerUserPasswords.txt> already exists. Please check this is intentional");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "unix":
			File admins1 = new File(dir + "/admins.json");
			File config1 = new File(dir + "/config.yml");
			File users1 = new File(dir + "/.ServerUsers.txt");
			File pass1 = new File(dir + "/.ServerUserPasswords.txt");
			try {
				if(admins1.createNewFile()) {
					System.out.println(TextColor.TEXT_YELLOW + servInf + "Crreated admins file" + TextColor.TEXT_RESET);
					PrintWriter pw = new PrintWriter(admins1);
					pw.println("[]");
					pw.close();
				}else {
					System.out.println(servWar + "file <admins.json> already exists. Please check this is intentional");
				}
				if(config1.createNewFile()) {
					System.out.println(TextColor.TEXT_YELLOW + servInf + "Crreated config file" + TextColor.TEXT_RESET);
					PrintWriter pw = new PrintWriter(config1);
					pw.println("server-name: Server");
					pw.println("server-description: A Simple Server");
					pw.println("server-pool: 10");
					pw.println("server-port: 22222");
					pw.close();
				}else {
					System.out.println(servWar + "file <config.yml> already exists. Please check this is intentional");
				}
				if(users1.createNewFile()) {
					System.out.println(TextColor.TEXT_YELLOW + servInf + "Created users file" + TextColor.TEXT_RESET);
				}else {
					System.out.println(servWar + "file <.ServerUsers.txt> already exists. Please check this is intentional");
				}
				if(pass1.createNewFile()) {
					System.out.println(TextColor.TEXT_YELLOW + servInf + "Created password file" + TextColor.TEXT_RESET);
				}else {
					System.out.println(servWar + "file <.ServerUserPasswords.txt> already exists. Please check this is intentional");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}
}
