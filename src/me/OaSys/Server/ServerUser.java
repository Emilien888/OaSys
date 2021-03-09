package me.OaSys.Server;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import me.OaSys.Server.Data.UserLoader;
import me.OaSys.Util.OSFetcher;
import me.OaSys.Util.PathFinder;

public class ServerUser {
	
	
	@SuppressWarnings({ "unchecked" })
	public ServerUser(String name, String email){
		FileWriter fr;
		PathFinder pf = new PathFinder();
		String path = pf.getDirPath();
		JSONArray usersJson = new JSONArray();
		fr = null;
		
		ArrayList<String> users = UserLoader.getUsers();
		Map<String, String> emails = UserLoader.getEmails();
		
		String os = OSFetcher.getOS();
		String osname;
		if(os.toLowerCase().contains("windows")) {
			osname = "windows";
		}else {
			osname = "unix";
		}
		switch (osname) {

			case "windows":
			try {
				fr = new FileWriter(path + "\\server.users.json");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
				break;
			case "unix":
			try {
				fr = new FileWriter(path + "/server.users.json");
			} catch (IOException e) {
				e.printStackTrace();
			}
				break;
			}
		
		for(String user : users) {
			JSONObject temp = new JSONObject();
			temp.put("user", user);
			temp.put("email", emails.get(user));
			usersJson.add(temp);
		}
		JSONObject newUser = new JSONObject();
		newUser.put("user", name);
		newUser.put("email", email);
		usersJson.add(newUser);
		
		try {
			fr.write(usersJson.toJSONString());
			fr.flush();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
