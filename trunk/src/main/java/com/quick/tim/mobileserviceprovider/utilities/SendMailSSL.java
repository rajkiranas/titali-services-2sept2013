/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.tim.mobileserviceprovider.utilities;

/**
 *
 * @author rajkirans
 */
import com.quick.tim.mobileserviceprovider.global.GlobalConstants;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMailSSL 
{
    private static Properties props;
    private static String fromMailId;
    private static String fromMailPassword;
    static
    {
        props = new Properties();
        props.put("mail.smtp.host", GlobalConstants.getProperty(GlobalConstants.SMTP_HOST));
        props.put("mail.smtp.socketFactory.port", GlobalConstants.getProperty(GlobalConstants.SOCKET_FACTORY_PORT));
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", GlobalConstants.getProperty(GlobalConstants.SMTP_PORT));
        
        fromMailId=GlobalConstants.getProperty(GlobalConstants.FROM_MAIL_ID);
        fromMailPassword=GlobalConstants.getProperty(GlobalConstants.FROM_MAIL_PWD);
    }
	
    public static void sendMail(String toMailIds, String subject, String msgBody)
        {
		
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromMailId,fromMailPassword);
				}
			});
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromMailId));
			message.setRecipients(Message.RecipientType.BCC,
					InternetAddress.parse(toMailIds));
			message.setSubject(subject);
			message.setText(msgBody);
 
			Transport.send(message);
 
			System.out.println("Mail sent");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
