package chimpPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.testng.annotations.AfterSuite;

public class SendEmail {
	private static final Logger LOGGER = Logger.getAnonymousLogger();
//	private static final String SERVER_SMTP;// = "smtp.gmail.com";
//    private static final int PORT_SERVER_SMTP;// = 587;
//    private static final String USERNAME;// = "sanu.menon@chimpfund.com"; // Modify this to a common email id
//    private static final String PASSWORD;// = "Sanu123#"; // Modify this to a common email id
//    private static final String FROMEMAIL;// ="sanu.menon@chimpfund.com"; // Modify this to a common email id
    
    private static  String SERVER_SMTP;// = "smtp.gmail.com";
    private static  int PORT_SERVER_SMTP;// = 587;
    private static  String USERNAME;// = "sanu.menon@chimpfund.com"; // Modify this to a common email id
    private static  String PASSWORD;// = "Sanu123#"; // Modify this to a common email id
    private static  String FROMEMAIL;// ="sanu.menon@chimpfund.com"; // Modify this to a common email id
    
    
	private String subject = "Chimp Automation Updates";
	private String strEmailList;
	InputStream input;
    InternetAddress[] address;
    StringBuilder sb;
    
    public SendEmail(){
    	try {
    		getPropertyValues();
    		//System.out.println(SERVER_SMTP +"  "+ PORT_SERVER_SMTP +"  "+ USERNAME +"  "+ PASSWORD +"  "+ FROMEMAIL); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @AfterSuite
    public void intiateEmail() throws IOException{
    	List<String> lstEmaillist=getEmailList();
    	sendMail(lstEmaillist);
    	
    }
	public void sendMail(List <String> strSendemailgroup )
	{
        
        Session session = Session.getInstance(getEmailProperties(), getAuthenticator());

        try 
        {
        	for( String emialGroup : strSendemailgroup)
        	{
        		address = new InternetAddress[1];
        		address[0] = new InternetAddress( emialGroup) ;
        	    final Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(FROMEMAIL));
            	message.setRecipients( Message.RecipientType.BCC, address ) ;
            	message.setSubject(subject);
	            BodyPart messageBodyPart = new MimeBodyPart();
	            MimeMultipart multipart = new MimeMultipart();
	            messageBodyPart = new MimeBodyPart();
	            sb = new StringBuilder();
		       	sb.append("<html>");
				sb.append("<body>");
				sb.append("<p>");
				sb.append("Hello, ");
				sb.append("<p>");
				sb.append("This email is being send to inform you that the automation has completed the execution successfully . Please verify the attached file for results");
				sb.append("<p>");
				sb.append("<b>");
				sb.append("Open the results in system viewer or in any browser");
				sb.append("</b>");
				sb.append("<p>");
				sb.append("Thank You");
				sb.append("<p>");
				sb.append("Chimp UI Automation Team");
				sb.append("</body>");
	    		sb.append("</html>");	
	    		messageBodyPart.setContent(sb.toString(), "text/html; charset=utf-8");
	    		multipart.addBodyPart(messageBodyPart);
	    		
    		// Part two is attachment
	             messageBodyPart = new MimeBodyPart();
	             //messageBodyPart.setContent(messageBodyPart, "text/html; charset=utf-8");
	             String strEmailFileName="emailable-report.html";
	             String strPathfile=System.getProperty("user.dir") +File.separator+"test-output/";
	             String filename = strPathfile+strEmailFileName;
	             DataSource source = new FileDataSource(filename);
	             messageBodyPart.setDataHandler(new DataHandler(source));
	             messageBodyPart.setFileName("Execution-Results.html");
	             
	             multipart.addBodyPart(messageBodyPart);
	    		 message.setContent(multipart);
	           
	    		
	    		message.setSentDate(new Date());
	            
	            
	            System.out.println("Sending mail to " + address[0].toString() );
	            Transport.send(message);
	            System.out.println("Send mail completed for " + address[0].toString());
        	}   
        	
        } 
        catch (final MessagingException ex) {
            LOGGER.log(Level.WARNING, "Error Message " + ex.getMessage(), ex);
        }
	}
	
	public Properties getEmailProperties() {
		//Set Email properties
		Properties props = new Properties();
        props.put("mail.smtp.host", SERVER_SMTP); //SMTP Host
        props.put("mail.smtp.port", PORT_SERVER_SMTP); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        return props;
    }
	
	public Authenticator getAuthenticator(){
		//create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };
        return auth;
	}
	
	
	public List<String> getEmailList() throws IOException
	{
		List<String> lstEmailgroup;
		Properties prop = new Properties();
		String propFileName = "SendEmail.properties";
		input = getClass().getClassLoader().getResourceAsStream(propFileName);
		if (input != null) {
			prop.load(input);
			strEmailList = prop.getProperty("strEmailListconf");
			lstEmailgroup = new ArrayList<>(Arrays.asList(strEmailList.split(",")));
		}
		else 
		{
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		return lstEmailgroup;
	}
	
	public void getPropertyValues() throws IOException
	{
		Properties prop = new Properties();
		String propFileName = "SendEmail.properties";
		input = getClass().getClassLoader().getResourceAsStream(propFileName);
		if (input != null) {
			prop.load(input);
			SERVER_SMTP=prop.getProperty("SERVER_SMTPCONF");
			PORT_SERVER_SMTP=Integer.parseInt(prop.getProperty("PORT_SERVER_SMTPCONF"));
			USERNAME=prop.getProperty("USERNAMECONF");
			PASSWORD=prop.getProperty("PASSWORDCONF");
			FROMEMAIL=prop.getProperty("FROMEMAILCONF");
			subject=prop.getProperty("subjectconf");
			
		}
		else 
		{
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		
	}
}
