/*
 * Created on 20 nov. 2003
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import utilitaires.ChampDate;
import application.ChargementProcessus;
import application.EugesElements;
import configuration.Config;

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageNouveauProjetIHM extends PageAssistantIHM{
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	//definition des boutons activés pour l'assistant
	final private boolean[] tab={true,true,true,false,true};
	
	//infos relatives à un projet
	final private Text _nomProjet;
	final private Text _cheminSauvegarde;
	final private Text _cheminProcessus;
	//date de debut
	final private ChampDate _dateDebut;
	//date de fin
	final private ChampDate _dateFin;
	//description du projet
	final private Text _description;
	
	final private Shell shellProjet;
	
	public PageNouveauProjetIHM(final Shell shell){
		//appel du constructeur de la classe composite
		super(shell);
		//initialisation du shell
		shellProjet=shell;
		//titre		
		Font font = new Font(getDisplay(), "Arial", 15, 15);
		Label titre = new Label(this, SWT.NONE);
		titre.setFont(font);
		titre.setText(message.getString("pageNouveauProjetIHM.titre"));
		titre.pack();
			
		 ///nom projet
		Label projet = new Label(this, SWT.NONE);
		projet.setText(message.getString("pageNouveauProjetIHM.nomProjet"));
		projet.pack();
		_nomProjet = new Text(this, SWT.SINGLE | SWT.BORDER);
		
		//date debut
		Label dateDebut = new Label(this, SWT.NONE);
		dateDebut.setText(message.getString("pageNouveauProjetIHM.dateDebut"));
		dateDebut.pack();
		//jour fin
		Label dateFin = new Label(this, SWT.NONE);
		dateFin.setText(message.getString("pageNouveauProjetIHM.dateFin"));
		dateFin.pack();
		
		//date de debut
		_dateDebut=new ChampDate(this);
		
		//date de fin
		_dateFin=new ChampDate(this);
		
		//separateur1
		Label sep1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);

		//chemin de sauvegarde
		Label sauvegarde = new Label(this, SWT.NONE);
		sauvegarde.setText(message.getString("pageNouveauProjetIHM.repDestination"));
		sauvegarde.pack();
		_cheminSauvegarde = new Text(this, SWT.SINGLE | SWT.BORDER );
		_cheminSauvegarde.setEditable(false);
		Button parcourirSauvegarde = new Button(this, SWT.PUSH);
		parcourirSauvegarde.setText(message.getString("pageNouveauProjetIHM.parcourir"));
		parcourirSauvegarde.pack();

		parcourirSauvegarde.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(SelectionEvent e) {
											DirectoryDialog repertoire = new DirectoryDialog(shell);
											repertoire.setText(message.getString("pageNouveauProjetIHM.repDestination"));
											String rep;
											if ((rep=repertoire.open())!=null)
												_cheminSauvegarde.setText(rep);
										}
									});
		//separateur2
		Label sep2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		sep2.setBounds(10,220,390,20);
		
		//processus en entree
		Label processus = new Label(this, SWT.NONE);
		processus.setText(message.getString("pageNouveauProjetIHM.processus"));
		processus.pack();

		_cheminProcessus = new Text(this, SWT.SINGLE | SWT.BORDER);

		Button parcourirProcessus = new Button(this, SWT.PUSH);
		parcourirProcessus.setText(message.getString("pageNouveauProjetIHM.parcourir"));
		parcourirProcessus.pack();

		parcourirProcessus.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(SelectionEvent e) {
													FileDialog fichier = new FileDialog(shell);
													fichier.setText(message.getString("pageNouveauProjetIHM.processus"));
													String fich;
													if ((fich=fichier.open())!=null)
														_cheminProcessus.setText(fich);
												}
											});
		//separateur3
		Label sep3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		sep3.setBounds(10,290,390,20);
		
		//champ de description
		Label description = new Label(this, SWT.NONE);
		description.setText(message.getString("pageNouveauProjetIHM.description"));
		description.pack();
		_description = new Text(this, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP);

		
		//mise en place du layout du composite
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns=4;
		this.setLayout(gridLayout);
		//titre
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan=4;
		titre.setLayoutData(gridData1);
		//projet
		gridData1 = new GridData();
		gridData1.horizontalSpan=4;
		projet.setLayoutData(gridData1);
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=3;
		_nomProjet.setLayoutData(gridData1);
		
		//label date debut
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=2;
		dateDebut.setLayoutData(gridData1);
		//label date fin
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=2;
		dateFin.setLayoutData(gridData1);
		//champ date debut
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=2;
		_dateDebut.setLayoutData(gridData1);
		//champ date fin
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=2;
		_dateFin.setLayoutData(gridData1);

		//separateur 1
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=4;
		sep1.setLayoutData(gridData1);

		//sauvegarde
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=4;
		sauvegarde.setLayoutData(gridData1);
		//chemin sauvegarde
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=3;
		_cheminSauvegarde.setLayoutData(gridData1);
		//bouton parcourir sauvegarde
		gridData1 = new GridData();
		gridData1.horizontalSpan=1;
		parcourirSauvegarde.setLayoutData(gridData1);

		//separateur 2
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=4;
		sep2.setLayoutData(gridData1);

		//label processus
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=4;
		processus.setLayoutData(gridData1);
		//chemin processus
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=3;
		_cheminProcessus.setLayoutData(gridData1);
		//bouton parcourir processus
		gridData1 = new GridData();
		gridData1.horizontalSpan=1;
		parcourirProcessus.setLayoutData(gridData1);

		//separateur 3
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=4;
		sep3.setLayoutData(gridData1);

		//label description
		gridData1 = new GridData(GridData.FILL_HORIZONTAL);
		gridData1.horizontalSpan=4;
		description.setLayoutData(gridData1);
		//champ description
		gridData1 = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		gridData1.horizontalSpan=4;
		_description.setLayoutData(gridData1);
		
		//activation des boutons
		set_activeBoutons(tab);
	}

	/* (non-Javadoc)
	 * @see ihm.PageAssistantIHM#verifDonnees()
	 */
	public boolean verifDonnees() {
		//on verifie que tous les attributs
		//de la classe ne soient pas vide sauf le champ description
		if (_nomProjet.getText().length()==0 || 
			_cheminSauvegarde.getText().length()==0 ||
			_cheminProcessus.getText().length()==0 ||
			_dateDebut.isEmpty() ||
			_dateFin.isEmpty()) {
			MessageBox msg = new MessageBox(shellProjet, SWT.ICON_ERROR);
			msg.setText(message.getString("pageNouveauProjetIHM.erreur"));
			msg.setMessage(message.getString("pageNouveauProjetIHM.erreurChamps"));
			msg.open();
			return false;	
		}else if (_dateDebut.toMyDate().compare(_dateFin.toMyDate())>=0){
			MessageBox msg = new MessageBox(shellProjet, SWT.ICON_ERROR);
			msg.setText(message.getString("pageNouveauProjetIHM.erreur"));
			msg.setMessage(message.getString("pageNouveauProjetIHM.erreurDate"));
			msg.open();
			return false;
		}else {
			//si toutes les données ont été vérifiées avec succées alors le projet est créé
			new EugesElements(_nomProjet.getText(), _dateDebut.toMyDate(), _dateFin.toMyDate(), _cheminSauvegarde.getText(), _cheminProcessus.getText(), _description.getText());
			new ChargementProcessus(_cheminProcessus.getText());
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see ihm.PageAssistantIHM#loadData()
	 */
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
}
