package petdb.registration;


import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Emailer {

  	
	private Properties fMailServerConfig = new Properties();
  	
	public Emailer() {

		super();
		fetchConfig();
	}

	
	public void sendEmail( String aFromEmailAddr,
                         String aToEmailAddr,
                         String aCCEmailAddr,
                         String aSubject,
                         String aBody ) throws Exception  
	{

    		Session session = Session.getDefaultInstance( fMailServerConfig, null );

    		MimeMessage message = new MimeMessage( session );

      		message.setFrom( new InternetAddress(aFromEmailAddr) );
      		message.addRecipient(Message.RecipientType.TO,
					 new InternetAddress(aToEmailAddr));
      		message.addRecipient(Message.RecipientType.CC,
					 new InternetAddress(aCCEmailAddr));
      		message.setSubject( aSubject );
      		message.setText( aBody );

      		Transport.send( message );
  	}

  	
	public void refreshConfig() {
    		fMailServerConfig.clear();
    		fetchConfig();
  	}


  	private void fetchConfig() {

        	fMailServerConfig.put("mail.smtp.host", "smtp.ciesin.columbia.edu");

  	}
} 
