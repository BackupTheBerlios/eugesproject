/*
 * Created on 16 janv. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

import java.io.FileOutputStream;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import utilitaires.GestionImage;
import application.GenererSite;
import configuration.Config;

/**
 * @author Jude
 * 
 *		
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class GenIHM extends Dialog{
	
	
	
	private Display display=Display.getCurrent();;
	private final Shell shellGen;
	private static Text text4;
	private static Button styl1;
	private static Button styl2;
	private static Button styl3;
	private static Button styl4;
	
	private static String check;
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public GenIHM(final Shell shell){
		super(shell);
		
		
		//nouveau shell
		shellGen = new Shell(shell, SWT.APPLICATION_MODAL | SWT.CLOSE);
		//titre		
		Font font = new Font(shell.getDisplay(), "Arial", 15, 15);
		Label titre = new Label(shellGen, SWT.NONE);
		titre.setFont(font);
		titre.setText(message.getString("GenIHM.titre"));
		titre.pack();
		  
		
		shellGen.setText(message.getString("GenIHM.titre"));
		shellGen.setImage(GestionImage._euges);
		
		//Création des élements de la fenêtre
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shellGen.setLayout(gridLayout);
		
		//Destinataire
		Label label4 = new Label(shellGen, SWT.NONE);
		label4.setText(message.getString("GenIHM.path"));
		Label label5 = new Label(shellGen, SWT.NONE);
		label5.setText("");
		text4 = new Text(shellGen, SWT.BORDER);
		text4.setText(Config.config.getProperty("gen"));
		text4.setEditable(false);
		
		
		//bouton parcourir
		Button parc=new Button(shellGen, SWT.PUSH );
		parc.setText(message.getString("GenIHM.parc"));
		parc.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ouvrir();
			}
		});
		
		Label label6 = new Label(shellGen, SWT.NONE);
		label6.setText(message.getString("GenIHM.styl"));
		Label label7 = new Label(shellGen, SWT.NONE);
		label7.setText("");
		
		//Mise en place des éléments
		GridData data2 = new GridData(GridData.FILL_HORIZONTAL);
		data2.horizontalSpan=2;
		titre.setLayoutData(data2);
		
		data2 = new GridData();
		label4.setLayoutData(data2);
		data2 = new GridData();
		label5.setLayoutData(data2);
		GridData data = new GridData();
		text4.setLayoutData(data);
		data = new GridData();
		parc.setLayoutData(data);
		
		GridData data3 = new GridData();
		label6.setLayoutData(data3);
		data3 = new GridData();
		label7.setLayoutData(data3);
		//style de generartion
		data3 = new GridData(GridData.FILL_HORIZONTAL);
		data3.horizontalSpan=2;
		genererStyles().setLayoutData(data3);
		//boutons
		data3 = new GridData(GridData.HORIZONTAL_ALIGN_END);
		data3.horizontalSpan=2;
		genererBoutons(shellGen).setLayoutData(data3);
		
		//ouvrir la fenêtre au centre de l'écran
		Rectangle bounds = shell.getBounds ();
		Rectangle rect = shellGen.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shellGen.setLocation (x, y);
		
		shellGen.pack();
		//ouverture du shell
		shellGen.open();
	}
	
	private Group genererStyles(){
		//groupe de selection du style
		Group styles = new Group(shellGen, SWT.NONE);
		styles.setText(message.getString("GenIHM.styles"));

		//bouton de style 1 (html gris)
		styl1=new Button(styles, SWT.RADIO );
		styl1.setSelection(true);
		styl1.setText(message.getString("GenIHM.htmlGris"));
		//bouton de style 2 (html bleu)
		styl2=new Button(styles, SWT.RADIO );
		styl2.setText(message.getString("GenIHM.htmlBleu"));
		//bouton de style 2 (html bleu)
		styl3=new Button(styles, SWT.RADIO );
		styl3.setText(message.getString("GenIHM.xml"));
		//bouton de style 2 (html bleu)
		styl4=new Button(styles, SWT.RADIO );
		styl4.setText(message.getString("GenIHM.flash"));
		
		//Création des élements de la fenêtre
		GridLayout gridLayout = new GridLayout(2, true);
		styles.setLayout(gridLayout);
		//placement des elements
		GridData gridData = new GridData();
		styl1.setLayoutData(gridData);
		gridData = new GridData();
		styl2.setLayoutData(gridData);
		gridData = new GridData();
		styl3.setLayoutData(gridData);
		gridData = new GridData();
		styl4.setLayoutData(gridData);
		
		return styles;
	}
	
	private Composite genererBoutons(final Shell s){
		Composite boutons = new Composite(s, SWT.NONE);
		//bouton ok
		Button ok=new Button(boutons, SWT.PUSH );
		ok.setText(message.getString("GenIHM.ok"));
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if 	(!text4.getText().equals("")){
					saveGen();
					getCheck();
					try {
						new GenererSite(text4.getText(),check);
					} catch (Exception e1) {}{
					}
					
					try {
						shellGen.dispose();
					}catch (Exception e2){
						MessageBox msg = new MessageBox(s, SWT.ICON_ERROR|SWT.YES);
						msg.setText(message.getString("GenIHM.err"));
						msg.setMessage(message.getString("GenIHM.errgen"));
						msg.open();
					}
					
				}
			}
		});
		
		
		//bouton annuler
		Button ann=new Button(boutons, SWT.PUSH );
		ann.setText(message.getString("GenIHM.ann"));
		ann.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shellGen.dispose();
			}
		});
		//Création des élements de la fenêtre
		GridLayout gridLayout = new GridLayout(2, true);
		boutons.setLayout(gridLayout);
		//placement des elements
		GridData gridData = new GridData();
		ok.setLayoutData(gridData);
		gridData = new GridData();
		ann.setLayoutData(gridData);
		
		return boutons;
	}
	private String ouvrir(){
		DirectoryDialog directoryDialog = new DirectoryDialog(shellGen);
		directoryDialog.setText(message.getString("GenIHM.parc"));
		String dir = directoryDialog.open();
		try {	
		text4.setText(dir);
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		return dir;
	}
	
	public static void saveGen() {
		try {
			Config.config.setProperty("gen",text4.getText());
			Config.config.store(new FileOutputStream(Config.fichierConfig),"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getCheck(){
		if (styl1.getSelection()){
			check = "1";
		}else{
			check = "0";
		}
	}
	
}

