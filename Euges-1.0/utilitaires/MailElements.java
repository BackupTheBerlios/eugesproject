/*
 * Created on 29 janv. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package utilitaires;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author Fils
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MailElements {
	
	private String serveur, message, expediteur, sujet;
	private String [] destinataire;
	
	public MailElements(String srv, String exp, String dst, String msg, String sbj) {
		serveur = srv;
		expediteur = exp;
		destinataire = dst.split(";");
		message = msg;
		sujet = sbj;
	}
	
	public MailElements(String srv, String exp, String[] dst, String msg, String sbj) {
		serveur = srv;
		expediteur = exp;
		destinataire = dst;
		message = msg;
		sujet = sbj;
	}
	
	
	public void sendMsg() throws Exception {
		
		System.getProperties().put("mail.host", serveur);

		for (int i =0;i<destinataire.length;i++){
			URL url = new URL("mailto:"+destinataire[i]);
			URLConnection connect = url.openConnection();
			PrintStream out = new PrintStream(connect.getOutputStream(), true); 
			out.print ("From: "+expediteur+"\r\n");
			out.print ("To: "+destinataire[i]+"\r\n");
			out.print ("Subject: "+sujet+"\r\n\n");
			out.print (message+"\r\n");
			out.close();
		}
		
	}
	
	

	
}	
