/*$Id:*/
package petdb.data;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.servlet.*;

import javax.servlet.http.*;



/* The class is used for download tag along data. 
*/

public class ReferenceFeedback extends HttpServlet 
{
	
	public void doPost(HttpServletRequest request, HttpServletResponse  response)
			throws java.io.IOException, ServletException 
	{

	final String username = "feedback@ldeo-mgg.org";
	final String password = "baihao";
    String errorMsg = null; //"The reference feedback has been sent successfully!";

    String from = "feedback@ldeo-mgg.org";
    String to = "info@petdb.org";	
    String cc = request.getParameter("email");  //client email address
    String refNum = request.getParameter("refNum");
    String subject = "(PetDB Ref="+refNum+") "+request.getParameter("subject");
    String comment = "PetDB Reference: "+refNum+"\n"+"From: "+cc+"\n\n"+request.getParameter("comment");

	Properties props = new Properties();
//	props.put("mail.smtp.auth", "true");
//	props.put("mail.smtp.starttls.enable", "true");
//	props.put("mail.smtp.host", "mail.ldeo-mgg.org");
	props.put("mail.smtp.host", "localhost");
	props.put("mail.smtp.port", "25");
    
//	Session session = Session.getInstance(props,
//	  new javax.mail.Authenticator() {
//		protected PasswordAuthentication getPasswordAuthentication() {
//			return new PasswordAuthentication(username, password);
//		}
//	  });
	
	Session session = Session.getInstance(props,null);
	try {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));   
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));    
        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));    
		message.setSubject(subject);
		message.setText(comment);
		Transport.send(message);
	} catch (MessagingException e) {
		System.err.println(e.getMessage());
        errorMsg = "Sending failed. Please contact us at info@petdb.org.";
	} catch (Exception e) {
        errorMsg = "Sending failed. Please contact us at info@petdb.org.";
        System.err.println(e.getMessage());
    }
  
    if(errorMsg != null) {
        request.getRequestDispatcher("ref_feedback.jsp?errorMsg="+errorMsg+"&refNum="+refNum).forward(request,response);
    } else {
        request.getRequestDispatcher("ref_feedback_result.jsp?feedbackMsg=The reference feedback has been sent successfully!").forward(request,response);
    }
  
}

}
