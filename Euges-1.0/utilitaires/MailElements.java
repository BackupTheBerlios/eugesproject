/*
 * Created on 29 janv. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package utilitaires;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author Fils
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MailElements {
	
	private String serveur, message, destinataire, expediteur, sujet;
	
	public MailElements(String srv, String exp, String dst, String msg, String sbj) {
		serveur = srv;
		expediteur = exp;
		destinataire = dst;
		message = msg;
		sujet = sbj;
	}
	
	public void sendMsg() {

		try {

			System.getProperties().put("mail.host", serveur);


			URL u = new URL("mailto:" + destinataire);      
			URLConnection c = u.openConnection(); 
			c.setDoInput(false);                  
			c.setDoOutput(true);                  
			c.connect();                          
			PrintWriter out = new PrintWriter(new OutputStreamWriter(c.getOutputStream()));

			out.println("From: \"" + expediteur + "\" <" + expediteur + ">");
			out.println("To: " + destinataire);
			out.println("Subject: " + sujet);
			out.println();

			out.println(message);

			out.close();

		} catch ( Exception e ) {
		}
	}
	
}	
