/*
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailAuth {
	
	private static ArrayList<Integer> codes = new ArrayList<Integer>();
	
	public static void sendEmailAuth(String email, String user) {
		
	}
	
	public static void sendEmail(Session session, String toEmail, String subject, String body){
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setContent("","text/html");

	      msg.setSubject(subject, "UTF-8");

	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	      System.out.println("Message is ready");
    	  Transport.send(msg);  

	      System.out.println("EMail Sent Successfully!!");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	private int genInt() {
		int min = 000000;
		int max = 999999;
		int randomInt = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomInt;
	}
	
	public static int genCode() {
		
		
		
		if (codes.contains(randomInt)) {
			
		}
		return 0;	
	}
}
*/
