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
//import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import application.EugesElements;

import java.io.FileOutputStream;

import utilitaires.GestionImage;

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
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public GenIHM(final Shell shell){
		super(shell);
		
		
		//nouveau shell
		shellGen = new Shell(shell, SWT.APPLICATION_MODAL | SWT.CLOSE);
		
		
		shellGen.setText(message.getString("GenIHM.titre"));
		shellGen.setSize(200,150);
		
		shellGen.setImage(GestionImage._euges);
		
		
			
		//Cr�ation des �lements de la fen�tre
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shellGen.setLayout(gridLayout);
		
		//Destinataire
		Label label4 = new Label(shellGen, SWT.NONE);
		label4.setText(message.getString("GenIHM.path"));
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
		
		//bouton ok
		
		Button ok=new Button(shellGen, SWT.PUSH );
		
		ok.setText(message.getString("GenIHM.ok"));
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				saveGen();
				try {
					EugesElements.genereSite(text4.getText());
					shellGen.dispose();
				} catch (Exception e1) {}
				
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
		
		
		
		//Mise en place des �l�ments
		GridData data = new GridData();
		data.widthHint = 60;
		label4.setLayoutData(data);
		GridData data2 = new GridData(GridData.FILL_HORIZONTAL);
		text4.setLayoutData(data2);
		GridData data3 = new GridData();
		data3.horizontalSpan = 2;
		parc.setLayoutData(data3);
		
				
		
		
		
		
		// ouvrir la fen�tre au centre de l'�cran
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
	
}

