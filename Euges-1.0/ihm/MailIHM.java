/*
 * Created on 16 janv. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import utilitaires.GestionImage;
import utilitaires.MailElements;
import application.EugesElements;
import configuration.Config;
/**
 * @author Jude
 * 
 *	modif par Fils le 16/02
 *		- ajout des personnes dans la liste
 *		- fonction de mail
 *		
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MailIHM extends Dialog{
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	private Display display=Display.getCurrent();;
	
	private Text text4;
	private Combo combo1;
	
	public MailIHM(final Shell shell){
		super(shell);
		
		//nouveau shell
		final Shell shellMail = new Shell(shell, SWT.APPLICATION_MODAL | SWT.CLOSE);
		
		
		shellMail.setText(message.getString("MailIHM.titre"));
		shellMail.setSize(320,280);
		
		
		//curseur qui apparait lors du survol des boutons
		Cursor curseurMain = new Cursor(display, SWT.CURSOR_HAND);
		shellMail.setImage(GestionImage._euges);
		
		//Création des élements de la fenêtre
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shellMail.setLayout(gridLayout);
		
		//Destinataire
		Label label4 = new Label(shellMail, SWT.NONE);
		label4.setText(message.getString("MailIHM.dest"));
		this.text4 = new Text(shellMail, SWT.BORDER);
		
		
		//Choix
		String[] TabPers;
		TabPers = EugesElements.getTableauListePersonne();
		Label label1 = new Label(shellMail, SWT.NONE);
		label1.setText(message.getString("MailIHM.choix"));
		this.combo1 = new Combo(shellMail,SWT.DROP_DOWN | SWT.READ_ONLY);
		try {
			combo1.setItems(TabPers);
			combo1.select(0);
		} catch (Exception e1) {}
		
		//combo1.setItems(new String[] {"Julien","Cyril","Bruno","Nicolas","Ludovic"});
		
		
		//bouton choix
		//Image imageChoix = new Image(display, Config.config.getProperty("cheminIcone")+"ouvrir.ico");
		Button choix=new Button(shellMail, SWT.PUSH | SWT.FLAT);
		choix.setCursor(curseurMain);
		//choix.setImage(imageChoix);
		choix.setText(message.getString("MailIHM.ajout"));
		choix.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				choose();
			}
		});
		
		//Sujet
		Label label2 = new Label(shellMail, SWT.NONE);
		label2.setText(message.getString("MailIHM.suj"));
		final Text text2 = new Text(shellMail, SWT.BORDER);
		
		//Message
		Label label3 = new Label(shellMail, SWT.NONE);
		label3.setText(message.getString("MailIHM.msg"));
		final Text text3 = new Text(shellMail, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP);
		
		//bouton envoie
		Button envoie=new Button(shellMail, SWT.PUSH | SWT.FLAT);
		envoie.setCursor(curseurMain);
		envoie.setText(message.getString("MailIHM.envoyer"));
		envoie.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean erreur = false;
				MailElements mail = new MailElements(Config.config.getProperty("serv"),Config.config.getProperty("login"),text4.getText(),text3.getText(),text2.getText());
				try {
					mail.sendMsg();
				} catch (Exception e1) {
					MessageBox msg = new MessageBox(shellMail, SWT.ICON_ERROR);
					msg.setText(message.getString("MailIHM.problemeTitre"));
					msg.setMessage(message.getString("MailIHM.problemeCorps"));
					msg.open();
					erreur=true;
					
				}
				if(erreur=false)
					shellMail.dispose();
			}
		});
		//bouton annuler
		Button annuler=new Button(shellMail, SWT.PUSH | SWT.FLAT);
		annuler.setCursor(curseurMain);
		annuler.setText(message.getString("MailIHM.annuler"));
		annuler.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shellMail.dispose();
			}
		});
		
		//Mise en place des éléments
		GridData data = new GridData();
		data.widthHint = 60;
		label4.setLayoutData(data);
		data = new GridData();
		data.widthHint = 60;
		label1.setLayoutData(data);
		data = new GridData();
		data.widthHint = 60;
		label2.setLayoutData(data);
		data = new GridData();
		data.widthHint = 60;
		label3.setLayoutData(data);
		
		GridData data2 = new GridData(GridData.FILL_HORIZONTAL);
		text4.setLayoutData(data2);
		data2 = new GridData();
		combo1.setLayoutData(data2);
		GridData data3 = new GridData();
		data3.horizontalSpan = 2;
		choix.setLayoutData(data3);
		data2 = new GridData(GridData.FILL_HORIZONTAL);
		text2.setLayoutData(data2);
		data2 = new GridData(GridData.FILL_VERTICAL);
		data2.widthHint = 200;
		text3.setLayoutData(data2);
		data3 = new GridData();
		data3.horizontalSpan = 2;
		GridData data4 = new GridData();
		envoie.setLayoutData(data4);
		data4 = new GridData();
		annuler.setLayoutData(data4);
		
		// ouvrir la fenêtre au centre de l'écran
		Rectangle bounds = shell.getBounds ();
		Rectangle rect = shellMail.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shellMail.setLocation (x, y);
		
		//ouverture du shell
		shellMail.open();
	}
	/**
	 * 
	 *
	 */
	public void choose(){
		try {
			this.text4.append(EugesElements.getPersonneDansListePersonnes(this.combo1.getText()).getMail()+",");
		} catch (Exception e) {}
		
		
	}
	
}
