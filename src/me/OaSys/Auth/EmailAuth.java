package me.OaSys.Auth;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import me.OaSys.Server.Client;

public class EmailAuth {
	
	static private boolean isLate = false;
	static private int randomInt;
	private static ArrayList<Integer> codes = new ArrayList<Integer>();
	
	public static boolean sendEmailAuth(String user, Client client, String toEmail) {
		int code = genCode();
		

        final String fromEmail = "oasys.confirmation@gmail.com"; //requires valid gmail id
		final String password = "*********"; // correct password for gmail id
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
       
		
		Thread fiveMin = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(300000);
					isLate = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		PrintWriter out = client.serverPrintOut1;
		Scanner userCodeIn = client.scanner1;
		Integer codeIn;
		
		out.println("Please enter the code sent to " + toEmail);
		out.println("You have five minutes");
		
		fiveMin.start();

		
		
		sendEmail(session, toEmail, "OaSys comfirmation Email", code, user);
		codeIn = userCodeIn.nextInt();
		
		if(codeIn.equals(code) && !isLate) {
			out.println("login successful!");
			rmCode(code);
			return true;
		}else {
			if(isLate) {
				out.println("You did not complete the task in time, logout.");
			}else {
				out.println("You did not enter the correct code, logout.");
			}
			rmCode(code);
			return false;
		}
	}
	
	private static void sendEmail(Session session, String toEmail, String subject, int code, String user){
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setContent("<body>\n"
	      		+ "    <h1>OaSys comfirmation Email</h1>\n"
	      		+ "    <p>\n"
	      		+ "        Welcome back, "+ user + "!\n"
	      		+ "\n"
	      		+ "        <br>\n"
	      		+ "        \n"
	      		+ "        Here is your comfirmation code: "+ code + "\n"
	      		+ "\n"
	      		+ "    </p>\n"
	      		+ "</body>","text/html");

	      msg.setSubject(subject, "UTF-8");

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
    	  Transport.send(msg);  
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	private static int genInt() {
		int min = 000000;
		int max = 999999;
		randomInt = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomInt;
	}
	
	public static Integer genCode() {
		boolean hasGenCode = false;
		int ri = 0;
		
		while(!hasGenCode) {
			ri = genInt();
			if (!(codes.contains(ri))) {
				codes.add(ri);
				hasGenCode = true;
			}
		}
		return ri;
	}
	
	public static void rmCode(int code) {
		codes.remove(codes.indexOf(code));
	}
	
}

