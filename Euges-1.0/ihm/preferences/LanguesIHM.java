/*
 * Created on 23 nov. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.preferences;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import application.preferences.Langues;
import configuration.Config;

/**
 * Cette classe permet de créer le contenu de l'objet de type TabItem correspondant à l'onglet de gestion du choix des langues du logiciel
 *
 *  @author Nicolas Broueilh
 *
 */
public class LanguesIHM extends Composite implements SelectionListener {
	/**Le ResourceBundle permettant d'accéder aux fichiers de traduction*/
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	/**La liste contenant les traductions existantes*/
	private List liste;
	
	/**Le bouton permettant de créer une nouvelle traduction*/
	private Button creer;
	
	/**Le bouton permettant de supprimer la traduction sélectionnée*/
	private Button supprimer;
	
	/**La classe contenant les controles sur l'ihm*/
	private Langues langues = new Langues();
	
	private Shell _shell;
	
	
	
	public LanguesIHM(Composite arg0, int arg1, Shell shell) {
		super(arg0, arg1);
		_shell=shell;
		//Le layout du composite
		setLayout(new FormLayout());
		
		
		// Le label du haut
		Label haut = new Label(this,SWT.NONE);
		haut.setText(message.getString("labelHaut"));
		
		// La liste des traduction existantes
		liste = new List(this,SWT.BORDER);
		
		Composite comp = new Composite(this,SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		comp.setLayout(gridLayout);
		
		// Bouton permettant de créer une traduction
		creer = new Button(comp,SWT.PUSH);
		creer.setText(message.getString("creer"));
		creer.addSelectionListener(this);
		
		// Bouton permettant de supprimer une traduction
		supprimer = new Button(comp,SWT.PUSH);
		supprimer.setText(message.getString("supprimer"));
		supprimer.addSelectionListener(this);

		
		GridData data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalSpan=1;
		creer.setLayoutData(data1);
		data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalSpan=1;
		supprimer.setLayoutData(data1);
				
		FormData formLabel = new FormData();
		formLabel.top = new FormAttachment(0, 0);
		formLabel.left = new FormAttachment(0, 0);
		formLabel.bottom = new FormAttachment(liste, 0);
		formLabel.right = new FormAttachment(100,0);
		haut.setLayoutData(formLabel);
		
		FormData formListe = new FormData();
		formListe.top = new FormAttachment(haut, 0);
		formListe.left = new FormAttachment(0, 0);
		formListe.bottom = new FormAttachment(90, 0);
		formListe.right = new FormAttachment(75,0);
		liste.setLayoutData(formListe);
		

		FormData formComp = new FormData();
		formComp.top = new FormAttachment(haut, 0);
		formComp.left = new FormAttachment(liste, 0);
		formComp.bottom = new FormAttachment(90, 0);
		formComp.right = new FormAttachment(100,0);
		comp.setLayoutData(formComp);
		
		
		
		//remplir la liste des traductions
		langues.remplirListeTraduction(liste);
		
	}

	
	/**
	 * Permet de récupérer le bouton créer
	 * @return le bouton creer
	 */
	public Button getCreer() {
		return creer;
	}

	/**
	 * Permet de récupérer la liste contenant les traductions
	 * @return la liste
	 */
	public List getListe() {
		return liste;
	}


	/**
	 * Permet de récupérer le bouton supprimer
	 * @return le bouton supprimer
	 */
	public Button getSupprimer() {
		return supprimer;
	}
	
	/**
	 * Permet de récupérer l'instance langue effectuant le "control" de langueIHM
	 * @return l'instance Langue
	 */
	public Langues getLangue() {
		return langues;
	}



	/**
	 * Traitement des évènements lors de clics sur les boutons
	 */
	public void widgetSelected(SelectionEvent arg0) {
		// Traitement si clic sur un bouton
		if (arg0.getSource() instanceof Button) {
			Button button = (Button)arg0.getSource();
			//Traitement si clic sur le bouton créer
			if (button == creer) {
				CreerTraductionIHM creerTraduction = new CreerTraductionIHM(_shell);				
			}
			//Traitement si clic sur le bouton supprimer
			else if (button == supprimer) {
				System.out.println("Supprimer traduction");				
			}
		}
		
	}


	public void widgetDefaultSelected(SelectionEvent arg0) {
		
	}

}
