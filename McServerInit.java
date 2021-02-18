import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class McServerInit {
	
	static String servWar = "[SERVER/WARN] ";
	
	public void downloadMc(String version) {
		try {
			PathFinder pf = new PathFinder();
			String os = OSFetcher.getOS();
    		String osname;
    		if(os.toLowerCase().contains("windows")) {
    			osname = "windows";
    		}else {
    			osname = "unix";
    		}
    		File f = null;
    		if(osname.equals("windows")) {
    			f = new File(pf.getDirPath() + "\\mc-server");
    		}else {
        		f = new File(pf.getDirPath() + "/mc-server");
    		}

			f.mkdir();
			switch(version) {
			case "spigot":
				BufferedInputStream inputStream = new BufferedInputStream(new URL("https://cdn.getbukkit.org/spigot/spigot-1.16.5.jar").openStream());
				FileOutputStream fileOS = null;
				if(osname.equals("windows")) {
					fileOS = new FileOutputStream(pf.getDirPath() + "\\mc-server\\spigot.jar");
				}else {
					fileOS = new FileOutputStream(pf.getDirPath() + "/mc-server/spigot.jar");
				}
			    byte data[] = new byte[1024];
			    int byteContent;
			    while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
			        fileOS.write(data, 0, byteContent);
			    }
				break;
			case "paper":
				BufferedInputStream inputStream2 = new BufferedInputStream(new URL("https://papermc.io/api/v2/projects/paper/versions/1.16.5/builds/457/downloads/paper-1.16.5-457.jar").openStream());
				FileOutputStream fileOS2 = null;
				if(osname.equals("windows")) {
					fileOS2 = new FileOutputStream(pf.getDirPath() + "\\mc-server\\paper.jar");
				}else {
					fileOS2 = new FileOutputStream(pf.getDirPath() + "/mc-server/paper.jar");
				}
			    byte data2[] = new byte[1024];
			    int byteContent2;
			    while ((byteContent2 = inputStream2.read(data2, 0, 1024)) != -1) {
			        fileOS2.write(data2, 0, byteContent2);
			    }
				break;
			case "vanilla":
				BufferedInputStream inputStream3 = new BufferedInputStream(new URL("https://launcher.mojang.com/v1/objects/1b557e7b033b583cd9f66746b7a9ab1ec1673ced/server.jar").openStream());
				FileOutputStream fileOS3 = null;
				if(osname.equals("windows")) {
					fileOS3 = new FileOutputStream(pf.getDirPath() + "\\mc-server\\vanilla-mc-server.jar");
				}else {
					fileOS3 = new FileOutputStream(pf.getDirPath() + "/mc-server/vanilla-mc-server.jar");
				}
			    byte data3[] = new byte[1024];
			    int byteContent3;
			    while ((byteContent3 = inputStream3.read(data3, 0, 1024)) != -1) {
			        fileOS3.write(data3, 0, byteContent3);
			    }
				break;
			default:
				System.out.println(servWar + TextColor.TEXT_YELLOW + "Not a valid minecraft server: " + version  + TextColor.TEXT_RESET);
				System.out.println(servWar + TextColor.TEXT_YELLOW + "Valid options are 'spigot', 'paper' or 'vanilla'" + TextColor.TEXT_RESET);
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
