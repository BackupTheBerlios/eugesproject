/*
 * Created on 7 déc. 2003
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import utilitaires.GestionImage;

import configuration.Config;
/**
 * @author Will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageDemarrageIHM extends Dialog{
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	private Display display=Display.getCurrent();;
	
	public PageDemarrageIHM(final Shell shell){
		super(shell);
		
		//nouveau shell
		final Shell shellDemarrage = new Shell(shell, SWT.APPLICATION_MODAL | SWT.CLOSE);
		//curseur qui apparait lors du survol des boutons
		Cursor curseurMain = new Cursor(display, SWT.CURSOR_HAND);
		shellDemarrage.setImage(GestionImage._euges);
		
		shellDemarrage.setText(message.getString("PageDemarrageIHM.titre"));
		//bouton nouveau projet
		Image imageNouveau = new Image(display, Config.config.getProperty("cheminIcone")+"nouveau.ico");
		Button nouveauProjet=new Button(shellDemarrage, SWT.PUSH | SWT.FLAT);
		nouveauProjet.setCursor(curseurMain);
		nouveauProjet.setImage(imageNouveau);
		nouveauProjet.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(SelectionEvent e) {
											shellDemarrage.dispose();
											AssistantIHM assistantIHM = new AssistantIHM(shell);
										}
									});
		//label nouveau projet
		Label nouveauProjetLabel= new Label(shellDemarrage, SWT.NONE);
		nouveauProjetLabel.setText(message.getString("PageDemarrageIHM.nouveauProjet"));
		
		//separateur1
		Label sep1 = new Label(shellDemarrage, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		//bouton projet existant
		Image imageOuvrir = new Image(Display.getCurrent(), Config.config.getProperty("cheminIcone")+"ouvrir.ico");
		Button projetExistant=new Button(shellDemarrage, SWT.PUSH | SWT.FLAT);
		projetExistant.setCursor(curseurMain);
		projetExistant.setImage(imageOuvrir);
		projetExistant.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(SelectionEvent e) {
													FileDialog fileDialog = new FileDialog(shellDemarrage);
													String chemin;
													if ((chemin=fileDialog.open())!=null)
														shellDemarrage.dispose();
												}
											});
		//label ouvrir projet
		Label projetExistantLabel= new Label(shellDemarrage, SWT.NONE);
		projetExistantLabel.setText(message.getString("PageDemarrageIHM.projetExistant"));
		
		//bouton annuler
		Button annuler = new Button(shellDemarrage, SWT.PUSH | SWT.CANCEL);
		annuler.setFocus();
		annuler.setText(message.getString("PageDemarrageIHM.annuler"));
		annuler.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(SelectionEvent e) {
													shellDemarrage.dispose();
												}
											});
		//mise en place du layout
		GridLayout gridLayout = new GridLayout(2, false);
		shellDemarrage.setLayout(gridLayout);
		//positionnement des objets sur le layout
		GridData gridData = new GridData();
		nouveauProjet.setLayoutData(gridData);
		gridData = new GridData();
		nouveauProjetLabel.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan=2;
		sep1.setLayoutData(gridData);
		
		gridData = new GridData();
		projetExistant.setLayoutData(gridData);
		gridData = new GridData();
		projetExistantLabel.setLayoutData(gridData);
		
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gridData.horizontalSpan=2;
		annuler.setLayoutData(gridData);
		
		shellDemarrage.pack();
		
		// ouvrir la fenêtre au centre de l'écran
		Rectangle bounds = shell.getBounds ();
		Rectangle rect = shellDemarrage.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shellDemarrage.setLocation (x, y);
		
		//ouverture du shell
		shellDemarrage.open();
	}
}
