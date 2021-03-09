package me.OaSys.Util;
import java.io.File;
import java.net.URISyntaxException;

public class PathFinder {
	
	public String getJARPath() {
		String JARPath = PathFinder.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		return JARPath;
	}
	
	public String getDirPath() {
		File jarDir = null; 
		try {
			jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return jarDir.getPath();
	}
}