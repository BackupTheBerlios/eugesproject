/*
 * Created on 21 janv. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import utilitaires.ChampDate;
import utilitaires.GestionImage;
import utilitaires.MyDate;
import application.EugesElements;
import configuration.Config;

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ModifierIterationIHM extends Dialog {
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	private ChampDate champDebut;
	private ChampDate champFin;
	
	private MyDate ancienDebut;
	private MyDate ancienFin;
	
	private Display display;
	
	//shell de la fenetre
	private Shell shellModifierIt;
	public ModifierIterationIHM(Shell shell, int numIt){
		super(shell);
		
		//nouveau shell pour la fenetre
		shellModifierIt = new Shell(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		
		// récupération du display
		display = Display.getCurrent();
		shellModifierIt.setSize(600, 500);
		shellModifierIt.setText(message.getString("modifierIterationIHM.titre"));
		
		//icone euges dans la barre de titre
		shellModifierIt.setImage(GestionImage._euges);
		// titre de la page
		Font font = new Font(Display.getCurrent(), "Arial", 15, 15);
		Label titre = new Label(shellModifierIt, SWT.NONE);
		titre.setFont(font);
		titre.setText(message.getString("modifierIterationIHM.titre"));
		
		Label dateDebut = new Label(shellModifierIt, SWT.NONE);
		dateDebut.setText(message.getString("modifierIterationIHM.dateDebut"));
		
		Label dateFin = new Label(shellModifierIt, SWT.NONE);
		dateFin.setText(message.getString("modifierIterationIHM.dateFin"));
		//creation des champs de dates
		champDebut= new ChampDate(shellModifierIt);		
		champFin= new ChampDate(shellModifierIt);		
		//affectation des valeurs
		ancienDebut=EugesElements._projet.getIteration(numIt).get_dateDebut();
		ancienFin=EugesElements._projet.getIteration(numIt).get_dateFin();

		champDebut.setChamps(ancienDebut);
		champFin.setChamps(ancienFin);
		//si l'iteration est l'it 0, on grise la date de début qui ne peut etre modifiee (car date de debut du projet)
		if (numIt==0)
			champDebut.setEnabled(false);
		//si l'iteration est la derniere it, on grise la date de fin qui ne peut etre modifiee (car date de fin du projet)
		if (numIt==EugesElements._projet.get_listeIteration().size()-1)
			champFin.setEnabled(false);
		//insertion d'une ligne vide
		Label vide = new Label(shellModifierIt, SWT.NONE);
		vide.setText("");
		
		//mise en place du layout
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns=3;
		
		shellModifierIt.setLayout(gridLayout);
		//positionnement des objets sur le layout
		//titre
		GridData gridData = new GridData();
		gridData.horizontalSpan=3;
		titre.setLayoutData(gridData);
		//dates de debut et fin
		gridData = new GridData();
		gridData.horizontalSpan=2;
		dateDebut.setLayoutData(gridData);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		dateFin.setLayoutData(gridData);
		//champs date de debut et fin
		gridData = new GridData();
		champDebut.setLayoutData(gridData);
		gridData.horizontalSpan=2;
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		champFin.setLayoutData(gridData);
		//ligne vide
		gridData = new GridData();
		gridData.horizontalSpan=3;
		vide.setLayoutData(gridData);
		//ajout des boutons
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gridData.horizontalSpan=3;
		creerBoutons(numIt).setLayoutData(gridData);
		
		shellModifierIt.pack();
		
		//ouvrir la fenêtre au centre de l'écran
		Rectangle bounds = shell.getBounds ();
		Rectangle rect = shellModifierIt.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shellModifierIt.setLocation (x, y);
	}
	
	public Composite creerBoutons(final int numIt){
		Composite boutons = new Composite(shellModifierIt, SWT.NONE);
		//bouton Valider
		Button valider = new Button(boutons, SWT.PUSH);
		valider.setText(message.getString("modifierIterationIHM.valider"));
		//bouton Annuler
		Button annuler = new Button(boutons, SWT.PUSH);
		annuler.setText(message.getString("modifierIterationIHM.annuler"));
		//bouton reset
		Button reset = new Button(boutons, SWT.PUSH);
		reset.setText(message.getString("modifierIterationIHM.reset"));
		
		//mise en place du layout
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns=3;
		boutons.setLayout(gridLayout);
		//positionnement des objets sur le layout
		//boutons valider et supprimer
		GridData gridData = new GridData();
		valider.setLayoutData(gridData);
		gridData = new GridData();
		annuler.setLayoutData(gridData);
		gridData = new GridData();
		reset.setLayoutData(gridData);
		
		//ajout des listener
		//bouton valider
		valider.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//test si la date de debut est inferieure a la date de fin
				if (champDebut.toMyDate().compare(champFin.toMyDate())>0){
					MessageBox msg = new MessageBox(shellModifierIt, SWT.ICON_ERROR);
					msg.setText(message.getString("modifierIterationIHM.erreur"));
					msg.setMessage(message.getString("modifierIterationIHM.dateInferieur"));
					msg.open();
				}
				//teste si la date de debut de l'it n'est pas inferieure a la date de debut de l'it precedent
				else if(numIt!=0 && champDebut.toMyDate().compare(EugesElements._projet.getIteration(numIt-1).get_dateDebut())<=0){
					MessageBox msg = new MessageBox(shellModifierIt, SWT.ICON_ERROR);
					msg.setText(message.getString("modifierIterationIHM.erreur"));
					msg.setMessage(message.getString("modifierIterationIHM.dateInferieurPrecedente"));
					msg.open();
				}
				//teste si la date de fin de l'it n'est pas superieure a la date de fin de l'it suivant
				else if (numIt!=EugesElements._projet.get_listeIteration().size()-1 && champFin.toMyDate().compare(EugesElements._projet.getIteration(numIt+1).get_dateFin())>=0){
					MessageBox msg = new MessageBox(shellModifierIt, SWT.ICON_ERROR);
					msg.setText(message.getString("modifierIterationIHM.erreur"));
					msg.setMessage(message.getString("modifierIterationIHM.dateSuperieurSuivant"));
					msg.open();
				}else{
					
					//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
					EugesElements.processusEnregistre = false;
					//si le champ de debut a ete modifie, on modifie l'iteration ainsi que l'iteration precedente si l'it courante n'est pas la 0
					if (champDebut.toMyDate().compare(ancienDebut)!=0){
						EugesElements._projet.getIteration(numIt).set_dateDebut(champDebut.toMyDate());
						if (numIt!=0)
							EugesElements._projet.getIteration(numIt-1).set_dateFin(champDebut.toMyDate());
					}
					//si le champ de fin a ete modifie, on modifie l'iteration ainsi que l'iteration suivante si l'it courante n'est pas la derniere
					if (champFin.toMyDate().compare(ancienFin)!=0){
						EugesElements._projet.getIteration(numIt).set_dateFin(champFin.toMyDate());
						if (numIt!=EugesElements._projet.get_listeIteration().size()-1)
							EugesElements._projet.getIteration(numIt+1).set_dateDebut(champFin.toMyDate());
					}
					//fermeture de la fenetre
					shellModifierIt.dispose();
				}
			}
		});
		
		//bouton annuler
		annuler.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shellModifierIt.dispose();
			}
		});
		//bouton reset : si la date a ete modifiée ce bouton permet de revenir au date de debut
		reset.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				 champDebut.setChamps(ancienDebut);
				 champFin.setChamps(ancienFin);
			}
		});
		//retour du composite créé
		return boutons;
	}
	/**
	 * ouvre la fenetre de modification de l'iteration
	 * cette methode attend la fermeture de la fenetre pour rendre la main a la fenetre mere
	 */
	public void open(){
		//ouverture de la fenetre
		shellModifierIt.open();
		//attente que la fenetre se ferme
		while (!shellModifierIt.isDisposed()){
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
}
