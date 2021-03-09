package me.OaSys.Util;
/*
 * 
 * Still to be made
 * will try to use it as 
 * easier access and organization
 * through code
 * 
 */
public enum Util {
	
	private final static String servInf = "[SERVER/INFO] ";
	static final String servWar = "[SERVER/WARN] ";
	static final String servMsg = "[SERVER/USR_MSG] ";
	static final String servCmd = "[SERVER/USR_CMD] ";
	
	public void log(boolean newLine, String typeOfLog, String color, String msg) {
		if(newLine) {
			switch (typeOfLog) {
			case "warning":
				switch (color) {
				case "red":
					
				}
			}
		}else {
			
		}
	}
}

