/*
 * Created on 29 nov. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

import java.util.Iterator;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import utilitaires.ChampDate;
import utilitaires.MyDate;
import application.EugesElements;
import configuration.Config;
import donnees.Iteration;

/**
 * @author Will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageIterationIHM extends PageAssistantIHM{
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	//definition des boutons activés pour l'assistant
	private final boolean[] tab={true,false,true,true,true};
	
	//Tableau contenant l'ensemble des iterations
	private Table _tableauIteration;
	//champ representant la fin de la nouvelle itération
	private ChampDate dateFin;
	//bouton de modification d'une iteration
	private Button boutonModifier;
	//bouton de suppression d'une iteration
	private Button boutonSupprimer;
	//bouton pour reinitialiser les iterations
	private Button boutonReinitialiser;
	//menu contextuel
	private MenuItem itemModifier;
	private MenuItem itemSupprimer;
	
	//Shell de la page courante
	private Shell shellCourant;
	
	public PageIterationIHM(final Shell shell){
		//appel du constructeur de la classe composite
		super(shell);
		shellCourant=shell;
		//titre		
		Font font = new Font(getDisplay(), "Arial", 15, 15);
		Label titre = new Label(this, SWT.NONE);
		titre.setFont(font);
		titre.setText(message.getString("pageIterationIHM.titre"));
		titre.pack();
		
		//label nouvelle iteration
		Label nouvelleIteration = new Label(this, SWT.NONE);
		nouvelleIteration.setText(message.getString("pageIterationIHM.ajoutNouvelleIteration"));
		
		//Label date de fin d'iteration
		Label dateFinIteration = new Label(this, SWT.NONE);
		dateFinIteration.setText(message.getString("pageIterationIHM.dateFinNouvelleIteration"));
		
		//date de fin de la nouvelle iteration
		dateFin=new ChampDate(this);
		
		//bouton ajouter itération
		Button ajouter = new Button(this, SWT.PUSH);
		ajouter.setText(message.getString("pageIterationIHM.ajouter"));
		ajouter.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (dateFin.isEmpty()){
					MessageBox msg = new MessageBox(shellCourant, SWT.ICON_ERROR);
					msg.setText(message.getString("pageIterationIHM.erreur"));
					msg.setMessage(message.getString("pageIterationIHM.erreurChampDateVide"));
					msg.open();
				}else{
					int selection = _tableauIteration.getSelectionIndex();
					//récupération de la nouvelle date
					MyDate nouvelleDate = new MyDate(dateFin.toString());
					//récupération des dates de début et fin
					MyDate debutDate = new MyDate(_tableauIteration.getItem(selection).getText(1));
					MyDate finDate = new MyDate(_tableauIteration.getItem(selection).getText(2));
					//vérification que la nouvelle date soit comprise entre la date de début et la date de fin 
					if (nouvelleDate.compare(debutDate)>0 && nouvelleDate.compare(finDate)<0) {
						//ajout dela nouvelle it
						EugesElements._projet.ajouterIteration((Iteration)_tableauIteration.getItem(selection).getData(), nouvelleDate);
						//rechargement des données
						loadData();
						//effacement des champs
						dateFin.clear();
						//activation du bouton reinitialiser
						boutonReinitialiser.setEnabled(true);
						//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
						EugesElements.processusEnregistre = false;
					}else{
						MessageBox msg = new MessageBox(shellCourant, SWT.ICON_ERROR);
						msg.setText(message.getString("pageIterationIHM.erreur"));
						msg.setMessage(message.getString("pageIterationIHM.erreurAjout"));
						msg.open();
					}
				}
												}
											});
		//separateur1
		Label sep1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);

		//titre du tableau des iterations
		Label titreTableauIteration = new Label(this, SWT.NONE);
		titreTableauIteration.setText(message.getString("pageIterationIHM.titreTableauIteration"));
		titreTableauIteration.pack();
		
		//tableau des iterations
		_tableauIteration = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		_tableauIteration.setLinesVisible(true);
		_tableauIteration.setHeaderVisible(true);
		//colonnes du tableau
		TableColumn colonne1 = new TableColumn(_tableauIteration, SWT.LEFT);
		TableColumn colonne2 = new TableColumn(_tableauIteration, SWT.LEFT);
		TableColumn colonne3 = new TableColumn(_tableauIteration, SWT.LEFT);
		
		colonne1.setWidth(100);
		colonne2.setWidth(100);
		colonne3.setWidth(100);
		
		colonne1.setText(message.getString("pageIterationIHM.iteration"));
		colonne2.setText(message.getString("pageIterationIHM.dateDebut"));
		colonne3.setText(message.getString("pageIterationIHM.dateFin"));
		
		//bouton modifier itération
		boutonModifier = new Button(this, SWT.PUSH);
		boutonModifier.setText(message.getString("pageIterationIHM.modifier"));
		boutonModifier.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				modifierIteration(_tableauIteration.getSelectionIndex());
				//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
				EugesElements.processusEnregistre = false;
				}
		});
		//bouton supprimer itération
		boutonSupprimer = new Button(this, SWT.PUSH);
		boutonSupprimer.setText(message.getString("pageIterationIHM.supprimer"));
		boutonSupprimer.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				supprimerIteration(_tableauIteration.getSelectionIndex());
				//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
				EugesElements.processusEnregistre = false;
			}
		});
		//bouton supprimer reinitialiser
		boutonReinitialiser = new Button(this, SWT.PUSH);
		boutonReinitialiser.setText(message.getString("pageIterationIHM.reinitialiser"));
		boutonReinitialiser.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//ouverture boite de dialogue pour confirmation
				MessageBox msg = new MessageBox(shell, SWT.YES|SWT.NO|SWT.ICON_ERROR);
				msg.setText(message.getString("pageIterationIHM.reinitialiser"));
				msg.setMessage(message.getString("pageIterationIHM.reinitialiserTout"));
				//si la reponse est oui, on supprime toute les iterations et on remet l'iteration precedente
				if (msg.open()==SWT.YES){
					//reinitialisation des iterations
					EugesElements._projet.reinitialiserIterations();
					//chargement de données
					loadData();
					//desactivation du bouton reinitialiser
					boutonReinitialiser.setEnabled(false);
					//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
					EugesElements.processusEnregistre = false;
				}
			}
		});
		//listener sur le tableau permettant d'activer ou de descativer le bouton supprimer
		_tableauIteration.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int selection = _tableauIteration.getSelectionIndex(); 
				if (selection==0 || selection==_tableauIteration.getItemCount()-1){
					boutonSupprimer.setEnabled(false);
					itemSupprimer.setEnabled(false);
				}else{
					boutonSupprimer.setEnabled(true);
					itemSupprimer.setEnabled(true);
				}
			}
		});
		/*
		 * mise en place du menu contextuel sur le tableau des iterations
		 */
		Menu menuContext = new Menu(shell, SWT.NONE);
		_tableauIteration.setMenu(menuContext);
		itemModifier = new MenuItem(menuContext, SWT.NONE);
		itemModifier.setText(message.getString("pageIterationIHM.modifier"));
		itemModifier.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				modifierIteration(_tableauIteration.getSelectionIndex());
			}
		});
		new MenuItem(menuContext, SWT.SEPARATOR);
		itemSupprimer = new MenuItem(menuContext, SWT.NONE);
		itemSupprimer.setText(message.getString("pageIterationIHM.supprimer"));
		itemSupprimer.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				supprimerIteration(_tableauIteration.getSelectionIndex());
			}
		});
		
		/*
		 * mise en place du layout du composite
		 * 
		 * */
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns=3;
		this.setLayout(gridLayout);
				
		//titre
		GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=3;
		titre.setLayoutData(gridData1);
		//label nouvelleIteration
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=3;
		nouvelleIteration.setLayoutData(gridData1);
		//date de fin d'iteration
		gridData1 = new GridData();
		dateFinIteration.setLayoutData(gridData1);
		//compositeDateFin
		gridData1 = new GridData();
		dateFin.setLayoutData(gridData1);
		//bouton ajouter
		gridData1 = new GridData();
		ajouter.setLayoutData(gridData1);
		
		//separateur
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=3;
		sep1.setLayoutData(gridData1);
		
		//titre tableau iterations
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=3;
		titreTableauIteration.setLayoutData(gridData1);
		
		//mise en place du tableau des iterations sur le layout
		gridData1 = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		gridData1.horizontalSpan=3;
		_tableauIteration.setLayoutData(gridData1);
		
		//bouton modifier
		gridData1 = new GridData(GridData.HORIZONTAL_ALIGN_END);
		//gridData1.horizontalSpan=2;
		boutonModifier.setLayoutData(gridData1);
		//bouton supprimer
		gridData1 = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		boutonSupprimer.setLayoutData(gridData1);
		//bouton reinitialiser
		gridData1 = new GridData(GridData.HORIZONTAL_ALIGN_END);
		boutonReinitialiser.setLayoutData(gridData1);
		
		//activation des boutons de l'assistant
		set_activeBoutons(tab);
		
		//chargement des données
		loadData();
		//desactivation du bouton reinitialiser
		boutonReinitialiser.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see ihm.PageAssistantIHM#verifDonnees()
	 */
	public boolean verifDonnees() {
		return true;
	}

	/* (non-Javadoc)
	 * @see ihm.PageAssistantIHM#loadData()
	 */
	public void loadData() {
		int i=0;
		Iterator itIteration = EugesElements._projet.get_listeIteration().iterator();
		//effacement de la liste
		_tableauIteration.removeAll();
		//insertion des éléments
		while (itIteration.hasNext()){
			TableItem item = new TableItem(_tableauIteration, SWT.NONE);
			Iteration itTmp = (Iteration)itIteration.next();
			item.setData(itTmp);
			item.setText(itTmp.toTableString());
			i++;
		}
		//desactivation du bouton supprimer
		boutonSupprimer.setEnabled(false);
		itemSupprimer.setEnabled(false);
		//si le nbre d'it est superieur a 1 alors on active le bouton modifier
		if (i>1){
			boutonModifier.setEnabled(true);
			itemModifier.setEnabled(true);
		}else{
			boutonModifier.setEnabled(false);
			itemModifier.setEnabled(false);
		}
		
		//selection du premier element
		_tableauIteration.setSelection(0);
	}
	/**
	 * supprime une iteration dans le tableau des iterations
	 *
	 */
	public void supprimerIteration(int it){
		MessageBox msg = new MessageBox(shellCourant, SWT.YES |SWT.NO|SWT.ICON_WARNING);
		msg.setMessage(message.getString("pageIterationIHM.messageSuppression"));
		//ouverture de la boite de message et recuperation de la reponse
		//si oui alors on supprime l'it
		if (msg.open()==SWT.YES) {
			EugesElements._projet.supprimerIteration(it);
			loadData();
		}
	}
	/**
	 * modifie une iteration dans le tableau des iterations
	 *
	 */
	public void modifierIteration(int it){
		//création de la fenetre
		ModifierIterationIHM modifIt = new ModifierIterationIHM(shellCourant, it);
		//ouverture de la page
		modifIt.open();
		//chargement des données
		loadData();
	}
}
