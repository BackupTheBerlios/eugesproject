/*
 * Created on 21 nov. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.preferences;

//import java.util.ResourceBundle;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import utilitaires.CopierFichier;
import utilitaires.GestionImage;
import application.EugesElements;
import application.preferences.IconesAPP;
import configuration.Config;

/**
 * Classe permettznt de créer la fenêtre préférences
 * @author Nicolas Broueilh
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PreferencesIHM implements SelectionListener {
	
	private static boolean modifie = false; 
	
	/**Le ResourceBundle permettant d'accéder aux fichiers de traduction*/
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	/**L'affichage*/
	private Display display;
	
	/**La fenêtre*/
	private Shell shell;
	
	/**Le bouton d'appel de l'aide*/
	private Button aide;

	/**Le bouton d'annulation des préférences*/
	private Button annuler;

	/**Le bouton de validation des préférences*/
	private Button valider;
	
	/**Le bouton permettant de restaurer les préférences par défaut*/
	private Button restaurerDefauts;
	
	/** Instance de la classe IconesIHM qui va permettre de changer les icônes d'Euges*/
	private IconesIHM iconesIHM;
	
	/** Instance de la classe LanguesIHM qui va permettre de changer la langue d'Euges*/
	private LanguesIHM langueIHM;

	/** Instance de la classe AutoMailIHM qui va permettre de modifier les options de mail*/
	private AutoMailIHM automailIHM;
	
	public PreferencesIHM(Shell parent) {
		// Mise en place de l'affichage de la fenêtre
		display = Display.getCurrent();
		shell = new Shell(parent,SWT.APPLICATION_MODAL|SWT.MIN);
		//Titre de la fenêtre
		shell.setText(message.getString("nomFenetre"));
		shell.setSize(690,450);//550,420);
		// Définition de la taille de la fenêtre
		shell.setImage(GestionImage._euges);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns=7;
		shell.setLayout(gridLayout);

		Cursor curseurMain = new Cursor(display, SWT.CURSOR_HAND);
		Button imageButton = new Button(shell,SWT.PUSH);
		imageButton.setCursor(curseurMain);
		imageButton.setBounds(10, 10, 150, 405);
		imageButton.setToolTipText("www.euges.fr.st");
		imageButton.setImage(GestionImage._eugesAssistant);
		

		TabFolder tabFolder = new TabFolder(shell,SWT.NONE);
		
		TabItem itemIcones = new TabItem(tabFolder,SWT.NONE);
		itemIcones.setText(message.getString("icones"));
		iconesIHM = new IconesIHM(tabFolder,SWT.NONE,shell);
		itemIcones.setControl(iconesIHM);
		
		TabItem itemLangues = new TabItem(tabFolder,SWT.NONE);
		itemLangues.setText(message.getString("langues"));
		langueIHM = new LanguesIHM(tabFolder,SWT.NONE,shell);
		itemLangues.setControl(langueIHM);
		
		TabItem itemAutoMail = new TabItem(tabFolder,SWT.NONE);
		itemAutoMail.setText(message.getString("mail"));
		automailIHM = new AutoMailIHM(tabFolder,SWT.NONE,shell);
		itemAutoMail.setControl(automailIHM);
		
		aide = new Button(shell,SWT.PUSH);
		aide.setText(message.getString("aide"));
		aide.addSelectionListener(this);
		
		restaurerDefauts = new Button(shell,SWT.PUSH);
		restaurerDefauts.setText(message.getString("defauts"));
		restaurerDefauts.addSelectionListener(this);
		
		valider = new Button(shell,SWT.PUSH);
		valider.setText(message.getString("ok"));
		valider.addSelectionListener(this);
		
		annuler = new Button(shell,SWT.PUSH);
		annuler.setText(message.getString("annuler"));
		annuler.addSelectionListener(this);
		
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan=2;
		imageButton.setLayoutData(data);
		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan=5;
		tabFolder.setLayoutData(data);
		
		GridData data1 = new GridData();
		data1.horizontalSpan=2;
		aide.setLayoutData(data1);
		data1 = new GridData();
		data1.horizontalSpan=3;
		restaurerDefauts.setLayoutData(data1);
		data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalSpan=1;
		valider.setLayoutData(data1);
		data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalSpan=1;
		annuler.setLayoutData(data1);
		
		
		
		shell.open();
		while(!shell.isDisposed()){
			if (!display.readAndDispatch()){
				display.sleep();
			}
		}
	}
	
	/**
	 * Methode permettant de récupérer le bouton aide
	 * @return le bouton aide
	 */
	public Button getAide() {
		return aide;
	}

	/**
	 * Methode permettant de récupérer le bouton annuler
	 * @return le bouton annuler
	 */
	public Button getAnnuler() {
		return annuler;
	}

	
	/**
	 * Methode permettant de récupérer le bouton valider
	 * @return le bouton valider
	 */
	public Button getValider() {
		return valider;
	}

	
	/**
	 * Traitement des évènements lors de clics sur les boutons
	 */
	public void widgetSelected(SelectionEvent arg0) {
		//Traitement si clic sur un bouton
		if (arg0.getSource() instanceof Button) {
			Button button = (Button)arg0.getSource();
			//Traitement si clic sur le bouton valider
			if (button == valider) {
				AutoMailIHM.saveMail();
				if (modifie || langueIHM.getListe().getSelectionIndex() != -1){
					EugesElements.processusEnregistre = false;
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
					messageBox.setMessage(message.getString("messageBox.message"));
					messageBox.setText(message.getString("messageBox.text"));
					messageBox.open();
					
					if (langueIHM.getListe().getSelectionIndex() != -1){
						langueIHM.getLangue().appliquerChangements(langueIHM.getListe().getItem(langueIHM.getListe().getSelectionIndex()));
					}
				
					new IconesAPP().sauvegarde(iconesIHM.getTabNouveauxChemins(), iconesIHM.getTabIcones());
				}
				shell.close();
				
			}
			//Traitement si clic sur le bouton annuler
			else if (button == annuler) {
				shell.close();				
			}
			//Traitement si clic sur le bouton aide
			else if (button == aide) {		
			}
			//Traitement si clic sur le bouton restaurer défauts
			else if (button == restaurerDefauts) {
				MessageBox questionBox = new MessageBox(shell,SWT.ICON_QUESTION|SWT.YES|SWT.NO);
				questionBox.setMessage(message.getString("questionBox.message"));
				questionBox.setText(message.getString("questionBox.text"));
				int reponse = questionBox.open();
				if (reponse == SWT.YES) {
					Config.fichierConfig.delete();
					CopierFichier.copyFile(Config.fichierConfigDefaut,Config.fichierConfig);
				
					File repertoireDest = new File(Config.config.getProperty("cheminIcone"));
					repertoireDest.mkdir();
					
					   // répertoire ou se trouve les icônes par défaut
					File cheminIcone = new File(Config.config.getProperty("cheminIconeDefaut"));
					   // liste des fichiers dans le répertoire de sauvegarde des icônes
					File[] tabCheminIcone = cheminIcone.listFiles(new FilenameFilter() {
						public boolean accept(File dir, String name) {
							File file = new File(dir + "\\" + name);
							boolean accept = false;
							String extension = name.substring(name.lastIndexOf('.')+1);
							accept = extension.equals("jpg") || extension.equals("ico") || extension.equals("rpm") || extension.equals("png");
							return file.isFile() && accept;
						}
					});
					// on copie tous les anciens icônes dans le répertoire xxxOld
					for (int i=0; i<tabCheminIcone.length; i++){
						File fileDest = new File(repertoireDest + "\\" + tabCheminIcone[i].getName());
						
						CopierFichier.copyFile(tabCheminIcone[i], fileDest);
						
					}
					
					
//					CopierFichier.copyDirectory(new File(Config.config.getProperty("cheminIconeDefaut")),
//							new File(Config.config.getProperty("cheminIcone")));
				}
				modifie = true;
			}
		}		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public static boolean isModifie() {
		return modifie;
	}

	public static void setModifie(boolean modifie) {
		PreferencesIHM.modifie = modifie;
	}

}
