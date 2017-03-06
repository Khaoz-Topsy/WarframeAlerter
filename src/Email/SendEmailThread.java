package Email;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;


public class SendEmailThread implements Runnable
{
	private String EmailAddress;
	private String Subject;
	private String Text;
	
    public SendEmailThread(String sEmailAddress, String sSubject, String sText)
    {
    	EmailAddress = sEmailAddress;
    	Subject = sSubject;
    	Text = sText;
    }

	@Override
	public void run()
	{
		final String username = "TODO put in gmail address";
		final String password = "TODO put in gmail password";
	
	    Properties props = new Properties();
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
	
	    Session session = Session.getInstance(props, new javax.mail.Authenticator() 
	    {
	        protected PasswordAuthentication getPasswordAuthentication() 
	        {
	            return new PasswordAuthentication(username, password);
	        }
		});
	
	    try {
	
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(username));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EmailAddress));
	        message.setSubject(Subject);
	        message.setText(Text);
	
	        Transport.send(message);
	        
	//		JOptionPane.showMessageDialog(null, "Email has been sent", "Email Sent", JOptionPane.INFORMATION_MESSAGE);
	
			System.out.println("Email sent to: " + EmailAddress);
			System.out.println("Message: " + Text);
	
	    } 
	    catch (MessagingException e) 
	    {
	        throw new RuntimeException(e);
	    }
	    System.out.println("Done");
	}
}
