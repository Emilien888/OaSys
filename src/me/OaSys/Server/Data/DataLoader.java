package me.OaSys.Server.Data;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.yaml.snakeyaml.Yaml;

import me.OaSys.Util.OSFetcher;
import me.OaSys.Util.PathFinder;

public class DataLoader{
	
	private ArrayList<String> adminUsers = new ArrayList<String>();
	
    public  Map<String, Object> getYMLData(){
    	Yaml yml = new Yaml();
    	InputStream in = this.getClass().getClassLoader().getResourceAsStream("config.yml");
    	Map<String, Object> config = yml.load(in);
		return config;
    }

	public ArrayList<String> getJSONAdmins(){
		PathFinder pf = new PathFinder();
    	JSONParser jp = new JSONParser();
    	String os = OSFetcher.getOS();
		String osname;
		if(os.toLowerCase().contains("windows")) {
			osname = "windows";
		}else {
			osname = "unix";
		}
    	try {
    		FileReader fr = null;
    		switch (osname) {
    		case "windows":
    			fr = new FileReader(pf.getDirPath() + "\\admins.json");
    			break;
    		case "unix":
    			fr = new FileReader(pf.getDirPath() + "/admins.json");
    			break;
    		}
    		
    		
    		Object obj = jp.parse(fr);
    		
    		JSONArray admins = (JSONArray) obj;
    		@SuppressWarnings("unchecked")
			Iterator<String> i = admins.iterator();
    		while(i.hasNext()) {
    			adminUsers.add(i.next());
    		}
    	}catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return adminUsers;
    }

    public ArrayList<String> getJSONBans(){
		return null;



    }
    
    public String test() {
    	InputStream in = this.getClass().getClassLoader().getResourceAsStream("test.txt");
    	Scanner reader = new Scanner(in);
    	String return1 = null;
    	while(reader.hasNextLine()) {
    		return1 = reader.nextLine();
    	}
    	reader.close();
    	return return1;
    }

}