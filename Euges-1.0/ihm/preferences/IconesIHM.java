/*
 * Created on 30 nov. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.preferences;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import configuration.Config;


/**
 * @author Nicolas
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class IconesIHM extends Composite implements SelectionListener {

	/**Le ResourceBundle permettant d'accéder aux fichiers de traduction*/
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	private final Shell shell;
	
		//Tableau dans lequel on stocke le nom des icônes actuelles
	final String tabIcones[];
	
	private final String tabNouveauxChemins[];
	
	public IconesIHM(Composite parent, int style, Shell sh) {
			// appel du constructeur de la classe mère
		super(parent, style);
		
		File cheminIcones = new File(Config.config.getProperty("cheminIcone"));
		tabIcones = cheminIcones.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				File file = new File(dir + "\\" + name);
				boolean accept = false;
				String extension = name.substring(name.lastIndexOf('.')+1);
				accept = extension.equals("jpg") || extension.equals("ico") || extension.equals("xpm") || extension.equals("png") || extension.equals("gif");
				return file.isFile() && accept;
			}
		});
		
		shell = sh;
			// le layout
		setLayout(new GridLayout());
		
			// zone explicative du principe de changement d'icône
		Label labelInstruction = new Label(this,SWT.NONE);
		labelInstruction.setText(message.getString("labelInstruction"));
		
			// la barre d'outils
		ToolBar toolIcones = new ToolBar(this,SWT.NONE);
		
			// les items de la barre d'outils
		final ToolItem tabToolItem[] = new ToolItem[tabIcones.length];
			// mise en place du tableau des nouveaux chemins
		tabNouveauxChemins = new String [tabIcones.length];
			// affichage des icônes actuelles
		for (int cpt=0; cpt<tabIcones.length; cpt++){
			tabToolItem[cpt] = new ToolItem(toolIcones,SWT.NONE);
			Image imageIcone = new Image(shell.getDisplay(), Config.config.getProperty("cheminIcone") + tabIcones[cpt]);
			tabToolItem[cpt].setImage(imageIcone);
			tabToolItem[cpt].setToolTipText(message.getString("toolItemIcone." + tabIcones[cpt].substring(0, tabIcones[cpt].lastIndexOf('.')) + ".infobulle"));
			
				// sert à savoir sur quelle icône on est en train de travailler
			final int numIcone = cpt;
			
				// si on clique sur une icône -> fenêtre de choix d'une nouvelle icône
			tabToolItem[cpt].addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					String nouveauChemin = ouvrir(message.getString("toolItemIcone." + tabIcones[numIcone].substring(0, tabIcones[numIcone].lastIndexOf('.')) + ".changer"));
					if (nouveauChemin != null){
						tabNouveauxChemins[numIcone] = nouveauChemin;
						tabToolItem[numIcone].setImage(new Image(shell.getDisplay(),nouveauChemin));
						PreferencesIHM.setModifie(true);
					}
				}
			});
			
			if (cpt < tabIcones.length-1){
				new ToolItem(toolIcones,SWT.SEPARATOR);
			}
		}
		
		
		toolIcones.pack();
		
		
		GridData gridDataLabel = new GridData();
		labelInstruction.setLayoutData(gridDataLabel);
		GridData gridDataIcones = new GridData();
		toolIcones.setLayoutData(gridDataIcones);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

		//fonction qui ouvre ue boîte de dialog pour choisir sa nouvelle icône
	public String ouvrir(String text){
		FileDialog fileDialog = new FileDialog(shell);
		fileDialog.setText(text);
		String [] tab = {"*.ico", "*.gif", "*.xpm", "*.jpg", "*.png"};
		fileDialog.setFilterExtensions(tab);
		return fileDialog.open();
	}
	
	public String[] getTabNouveauxChemins(){
		return tabNouveauxChemins;
	}
	
	public String[] getTabIcones(){
		return tabIcones;
	}
	
	
	
}
