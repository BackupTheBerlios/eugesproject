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
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import utilitaires.GestionImage;
import application.EugesElements;
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
	private static String check;
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public GenIHM(final Shell shell){
		super(shell);
		
		
		//nouveau shell
		shellGen = new Shell(shell, SWT.APPLICATION_MODAL | SWT.CLOSE);
		
		
		shellGen.setText(message.getString("GenIHM.titre"));
		shellGen.setSize(190,150);
		
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
		
		
		//bouton de style 1
		
		styl1=new Button(shellGen, SWT.RADIO );
		styl1.setSelection(true);
		styl1.setText(message.getString("GenIHM.stylg"));
		
		
		
		//bouton de style 2
		
		styl2=new Button(shellGen, SWT.RADIO );
		styl2.setText(message.getString("GenIHM.stylb"));
		
		
		
		//bouton ok
		
		Button ok=new Button(shellGen, SWT.PUSH );
		
		ok.setText(message.getString("GenIHM.ok"));
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				saveGen();
				getCheck();
				try {
					
					EugesElements.genereSite(text4.getText(),check);
					shellGen.dispose();
				} catch (Exception e1) {}{
				
				}
			
			try {	
				String test = EugesElements._projet.get_description();
			}
			catch (Exception e2)
			
			{
				MessageBox msg = new MessageBox(shell, SWT.ICON_QUESTION|SWT.YES|SWT.NO|SWT.CANCEL);
				msg.setText(message.getString("GenIHM.err"));
				msg.setMessage(message.getString("GenIHM.errgen"));
				msg.open();
			}
		
			}
		});
		
		
		//bouton annuler
		
		Button ann=new Button(shellGen, SWT.PUSH );
		
		ann.setText(message.getString("GenIHM.ann"));
		ann.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shellGen.dispose();
			}
		});
		
		
		
		//Mise en place des éléments
		GridData data2 = new GridData();
		data2.widthHint = 50;
		label4.setLayoutData(data2);
		data2 = new GridData();
		data2.widthHint = 50;
		label5.setLayoutData(data2);
		GridData data = new GridData();
		data.widthHint = 100;
		text4.setLayoutData(data);
		data = new GridData();
		data.widthHint = 25;
		parc.setLayoutData(data);
		
		GridData data3 = new GridData();
		data3.widthHint = 80;
		label6.setLayoutData(data3);
		data3 = new GridData();
		data3.widthHint = 50;
		label7.setLayoutData(data3);
		
		
		/*GridData data2 = new GridData(GridData.FILL_HORIZONTAL);
		text4.setLayoutData(data2);
		GridData data3 = new GridData();
		data3.horizontalSpan = 2;
		parc.setLayoutData(data3);*/
		
				
		
		
		
		
		// ouvrir la fenêtre au centre de l'écran
		Rectangle bounds = shell.getBounds ();
		Rectangle rect = shellGen.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shellGen.setLocation (x, y);
		
		
		
		
		
		
		//ouverture du shell
		shellGen.open();
		
		
		
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
		if (styl1.getSelection())
		{
			
			check = "1";
						
		}
		else{
			check = "0";
		}
	}
	
}

