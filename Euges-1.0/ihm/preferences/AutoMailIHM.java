/*
 * Created on 28 janv. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.preferences;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import configuration.Config;


/**
 * @author Jude
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AutoMailIHM extends Composite{
	
	/**Le ResourceBundle permettant d'accéder aux fichiers de traduction*/
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	private Shell _shell;
	
	private Text serve;
	
	private Text servs;
	
	private Text mess;
	
	private Text login;
	
	private Text passwd;
	
	public AutoMailIHM(Composite arg0, int arg1, Shell shell) {
		super(arg0, arg1);
		_shell=shell;
		//Le layout du composite
		setLayout(new FormLayout());
		
		
		
		Composite comp = new Composite(this,SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		comp.setLayout(gridLayout);
		
			
		
		
		// Serveur de courrier Sortant
		Label bas = new Label(comp,SWT.NONE);
		bas.setText(message.getString("AutoMailIHM.serv"));
		Text servs = new Text(comp, SWT.BORDER);
		
		// Label Vierge
		Label vierge = new Label(comp,SWT.NONE);
		vierge.setText("");
		
		// Label Vierge2
		Label vierge2 = new Label(comp,SWT.NONE);
		vierge2.setText("");
		
		// Login
		Label log = new Label(comp,SWT.NONE);
		log.setText(message.getString("AutoMailIHM.log"));
		Text login = new Text(comp, SWT.BORDER);
		
		// Mot de passe
		Label pass = new Label(comp,SWT.NONE);
		pass.setText(message.getString("AutoMailIHM.pass"));
		Text passwd = new Text(comp, SWT.BORDER);
		passwd.setEchoChar('*');
		
		
		
		
		// Sujet
		Label suj = new Label(comp,SWT.NONE);
		suj.setText(message.getString("AutoMailIHM.suj"));
		Text sujet = new Text(comp, SWT.BORDER);
		
		//Message
		Label msg = new Label(comp, SWT.NONE);
		msg.setText(message.getString("AutoMailIHM.msg"));
		Text mess = new Text(comp, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP);
		
		//Envoie au demarrage du projet
		Button button = new Button(comp, SWT.CHECK);
		button.setText(message.getString("AutoMailIHM.button"));
		
		
		
		//placement des éléments
		GridData data1 = new GridData();
		bas.setLayoutData(data1);
		data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalSpan=1;
		servs.setLayoutData(data1);
		data1 = new GridData(GridData.FILL_HORIZONTAL);
		vierge.setLayoutData(data1);
		data1 = new GridData(GridData.FILL_HORIZONTAL);
		vierge2.setLayoutData(data1);
		data1 = new GridData();
		log.setLayoutData(data1);
		data1 = new GridData();
		data1.widthHint = 60;
		login.setLayoutData(data1);
		data1 = new GridData();
		pass.setLayoutData(data1);
		data1 = new GridData();
		data1.widthHint = 60;
		passwd.setLayoutData(data1);
		data1 = new GridData(GridData.FILL_HORIZONTAL);
		suj.setLayoutData(data1);
		data1 = new GridData();
		data1.widthHint = 60;
		sujet.setLayoutData(data1);
		data1 = new GridData(GridData.FILL_HORIZONTAL);
		msg.setLayoutData(data1);
		data1 = new GridData();
		data1.widthHint = 200;
		data1.heightHint = 100;
		mess.setLayoutData(data1);
		data1 = new GridData();
		button.setLayoutData(data1);
		
		
		
	}
	
	
	
	
}
