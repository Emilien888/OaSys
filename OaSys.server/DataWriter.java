import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;

public class DataWriter {
	
	@SuppressWarnings("unchecked")
	public void addAdmin(String user) {
		DataLoader dl = new DataLoader();
		ArrayList<String> already = dl.getJSONAdmins();
		PathFinder pf = new PathFinder();
    	try {
    		
    		FileWriter fr = new FileWriter(pf.getDirPath() + "/admins.json");
    		JSONArray admins = new JSONArray();
    		Iterator<String> it = already.iterator();
    		while(it.hasNext()) {
    			admins.add(it.next());
    		}
    		admins.add(user);
    		fr.write(admins.toJSONString());
    		fr.close();
    	}catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
