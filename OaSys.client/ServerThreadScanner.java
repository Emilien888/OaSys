import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ServerThreadScanner {
	
	public static BufferedReader serverIn;
	
	public ServerThreadScanner() {
		 Thread scanner = new Thread( new Runnable() {

			@Override
			public void run() {
				while(true) {
					serverIn = null;
					try {
						serverIn = new BufferedReader(new InputStreamReader(MainGUI.getConnection().getInputStream()));
					} catch (IOException e) {
						e.printStackTrace();
					}
					while(true) {
						try {
							MainGUI.addToFlux(serverIn.readLine());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			 
		 });
		 scanner.start();
	}
}
