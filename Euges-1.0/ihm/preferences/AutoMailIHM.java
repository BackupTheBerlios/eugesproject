/*
 * Created on 28 janv. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.preferences;
import java.io.FileOutputStream;
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
	
	
	private static Text servs;
	
	private static Text mess;
	
	private static Text login;
	
	private static Text sujet;
	
	private static Label suj;
	
	private static Button button;
	
	private Composite comp;
	
	
	
	
	
	public AutoMailIHM(Composite arg0, int arg1, Shell shell) {
		super(arg0, arg1);
		_shell=shell;
		
		
		//Le layout du composite
		setLayout(new FormLayout());
		
		
		
		comp = new Composite(this,SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		comp.setLayout(gridLayout);
		
		
		
		
		// Serveur de courrier Sortant
		Label bas = new Label(comp,SWT.NONE);
		bas.setText(message.getString("AutoMailIHM.serv"));
		servs = new Text(comp, SWT.BORDER);
		servs.setText(Config.config.getProperty("serv"));
		
		// Label Vierge
		Label vierge = new Label(comp,SWT.NONE);
		vierge.setText("");
		
		// Label Vierge2
		Label vierge2 = new Label(comp,SWT.NONE);
		vierge2.setText("");
		
		// Login
		Label log = new Label(comp,SWT.NONE);
		log.setText(message.getString("AutoMailIHM.log"));
		login = new Text(comp, SWT.BORDER);
		login.setText(Config.config.getProperty("login"));
			
		
		// Sujet
		suj = new Label(comp,SWT.NONE);
		suj.setText(message.getString("AutoMailIHM.suj"));
		sujet = new Text(comp, SWT.BORDER);
		sujet.setText(Config.config.getProperty("suj"));
		
		//Message
		Label msg = new Label(comp, SWT.NONE);
		msg.setText(message.getString("AutoMailIHM.msg"));
		mess = new Text(comp, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP);
		mess.setText(Config.config.getProperty("msg"));
		
		//Envoie au demarrage du projet
		button = new Button(comp, SWT.CHECK);
		button.setText(message.getString("AutoMailIHM.button"));
		button.setSelection(setCheck());
		
		
		
		
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
	
	public static String getCheck(){
		if (button.getSelection())
		{
			return "1";
		}
		else{
			return "0";
		}
	}
	
	public static boolean setCheck(){
		if (Config.config.getProperty("start")=="1"){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public static void saveMail() {
		
		try {
			Config.config.setProperty("serv",servs.getText());
			Config.config.setProperty("login",login.getText());
			Config.config.setProperty("suj",sujet.getText());
			Config.config.setProperty("msg",mess.getText());
			Config.config.setProperty("start",getCheck());
			Config.config.store(new FileOutputStream(Config.fichierConfig),"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
}

