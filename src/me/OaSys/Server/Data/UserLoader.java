package me.OaSys.Server.Data;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import me.OaSys.Util.OSFetcher;
import me.OaSys.Util.PathFinder;


public class UserLoader {
	
	public static ArrayList<String> getUsers(){
		PathFinder pf = new PathFinder();
		String path = pf.getDirPath();
		JSONParser jp = new JSONParser();
		JSONArray jsonUsers = new JSONArray();
		FileReader fr = null;
		JSONObject temp;
		Object obj = null;
		ArrayList<String> users = new ArrayList<String>();
		
		String os = OSFetcher.getOS();
		String osname;
		if(os.toLowerCase().contains("windows")) {
			osname = "windows";
		}else {
			osname = "unix";
		}
		
		try {
			fr = null;
			switch (osname) {
			case "windows":
				fr = new FileReader(path + "\\server.users.json");
				break;
			case "unix":
				fr = new FileReader(path + "/server.users.json");
				break;
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			obj = jp.parse(fr);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		jsonUsers = (JSONArray) obj;
		for(Object o: jsonUsers){
		    if ( o instanceof JSONObject ) {
		        temp = (JSONObject) o;
		        users.add((String) temp.get("user"));
		    }
		}
		return users;
		
		

	}
	
	public static Map<String, String> getEmails(){
		PathFinder pf = new PathFinder();
		String path = pf.getDirPath();
		JSONParser jp = new JSONParser();
		JSONArray jsonUsers = new JSONArray();
		FileReader fr = null;
		JSONObject temp;
		Object obj = null;
		ArrayList<String> users = new ArrayList<String>();
		ArrayList<String> emails = new ArrayList<String>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		String os = OSFetcher.getOS();
		String osname;
		if(os.toLowerCase().contains("windows")) {
			osname = "windows";
		}else {
			osname = "unix";
		}
		
		try {
			fr = null;
			switch (osname) {
			case "windows":
				fr = new FileReader(path + "\\server.users.json");
				break;
			case "unix":
				fr = new FileReader(path + "/server.users.json");
				break;
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			obj = jp.parse(fr);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		jsonUsers = (JSONArray) obj;
		for(Object o: jsonUsers){
		    if ( o instanceof JSONObject ) {
		        temp = (JSONObject) o;
		        users.add((String) temp.get("user"));
		        emails.add((String) temp.get("email"));
		    }
		}
		
		for(String user : users) {
			returnMap.put(user, emails.get(users.indexOf(user)));
		}
		return returnMap;
		
	}
	
}

