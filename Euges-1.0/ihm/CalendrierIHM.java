package ihm;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import utilitaires.ChampDate;
import utilitaires.MyDate;
import configuration.Config;

/**
 * @author nico
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CalendrierIHM extends Dialog{
	
	
	private Display _display=Display.getCurrent();
	private Combo _comboMois ;
	private Combo _comboAnnee;
	private Shell _shell;
	private String _jour;
	private ChampDate _date;
	private Vector _vecteurButtonJour;
	private Button boutonCourant;
	private ResourceBundle _message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	

	/**
	 * Cette méthode permet de créer un CalendrierIHM à partir 
	 * de la fenêtre appelante et du ChampDate à remplir
	 * 
	 * @return CalendrierIHM
	 *
	 * @param Shell shell de la fenêtre appelante
	 * 
	 * @param ChampDate ChampDate devant être rempli
	 * 
	 * @see #CalendrierIHM(Shell, ChampDate)
	 *
	 */
	
	public CalendrierIHM (final Shell shellPere,final ChampDate d)
	{
		
		//initialisation des attributs (hors combos)
		super (shellPere);
		_shell = new Shell(shellPere, SWT.APPLICATION_MODAL | SWT.CLOSE);
		_shell.setText(_message.getString("CalendrierIHM.titre"));
		Image eugesIcone = new Image(_display, "configuration/images/euges.ico");
		_shell.setImage(eugesIcone);
		GridLayout gr = new GridLayout (1,true);
		_shell.setLayout(gr);
		
		this._jour = "1";
		
		this._date = d;
		
		_vecteurButtonJour = new Vector ();
				
		//définition et initialisation des composites de la fenêtre
		final Composite enTete = new Composite (_shell, SWT.NONE);
		final Composite piedDePage = new Composite (_shell, SWT.BORDER);
		
		
		//Remplissage du composite enTete
		Label mois = new Label(enTete,SWT.NONE);
		mois.setText(_message.getString("CalendrierIHM.mois"));
		Label annee = new Label(enTete,SWT.NONE);
		annee.setText(_message.getString("CalendrierIHM.annee"));
		
		//création du combo de choix de mois
		String [] listeMois = {_message.getString("CalendrierIHM.mois1"),_message.getString("CalendrierIHM.mois2"),_message.getString("CalendrierIHM.mois3"),_message.getString("CalendrierIHM.mois4"),_message.getString("CalendrierIHM.mois5"),_message.getString("CalendrierIHM.mois6"),_message.getString("CalendrierIHM.mois7"),_message.getString("CalendrierIHM.mois8"),_message.getString("CalendrierIHM.mois9"),_message.getString("CalendrierIHM.mois10"),_message.getString("CalendrierIHM.mois11"),_message.getString("CalendrierIHM.mois12")};
		_comboMois = new Combo (enTete, SWT.READ_ONLY); 
		_comboMois.setItems(listeMois);
		_comboMois.select(0);
		_comboMois.addSelectionListener (new SelectionAdapter ()
				{
			public void widgetSelected  (SelectionEvent e)
			{
				CalendrierIHM.this._jour = "1";
				masquerJour();
			}
		});

		//création du combo de choix d'année
		_comboAnnee = new Combo (enTete, SWT.READ_ONLY);
		_comboAnnee.addSelectionListener (new SelectionAdapter ()
											{
			public void widgetSelected  (SelectionEvent e)
			{
				CalendrierIHM.this._jour = "1";
				masquerJour();
			}
											});
		//récupération de l'année courante sous la forme 20XX
		Date today = new Date ();
		int anneeCourante = today.getYear()+1900;
		
		//remplissage du combo année
		for (int j =0, i = anneeCourante; i< anneeCourante +20 ;i++, j++)
		{
			Integer an = new Integer (i);
			_comboAnnee.add(an.toString(),j);
		}
		_comboAnnee.select(0);
		//définition du layout de enTete
		enTete.setLayout (new GridLayout (2,true));
		
		
		//Remplissage du composite piedDePage
		
		//en-têtes des colonnes
		Label lundi = new Label(piedDePage,SWT.NONE);
		lundi.setText(_message.getString("CalendrierIHM.jour1"));
		Label mardi = new Label(piedDePage,SWT.NONE);
		mardi.setText(_message.getString("CalendrierIHM.jour2"));
		Label mercredi = new Label(piedDePage,SWT.NONE);
		mercredi.setText(_message.getString("CalendrierIHM.jour3"));
		Label jeudi = new Label(piedDePage,SWT.NONE);
		jeudi.setText(_message.getString("CalendrierIHM.jour4"));
		Label vendredi = new Label(piedDePage,SWT.NONE);
		vendredi.setText(_message.getString("CalendrierIHM.jour5"));
		Label samedi = new Label(piedDePage,SWT.NONE);
		samedi.setText(_message.getString("CalendrierIHM.jour6"));
		Label dimanche = new Label(piedDePage,SWT.NONE);
		dimanche.setText(_message.getString("CalendrierIHM.jour7"));
		
		// affichage des boutons représentant les jours
		afficheGrillejours(piedDePage);
		
		//définition du layout de piedDePage
		GridLayout layoutPiedDePage = new GridLayout (7,false);
		layoutPiedDePage.horizontalSpacing =10;
		layoutPiedDePage.marginHeight=10;
		layoutPiedDePage.marginWidth=10;
		layoutPiedDePage.verticalSpacing=10;
		piedDePage.pack();
		piedDePage.setLayout(layoutPiedDePage);
				
		
		
		//création du bouton pour valider la saisie de la date
		Button valider = new Button (_shell, SWT.PUSH);
		valider.setText(_message.getString("CalendrierIHM.valider"));
		valider.addSelectionListener(new SelectionAdapter()
				{
			public void widgetSelected  (SelectionEvent e)
			{
				valider ();
			}
		}
		);
		
		//affichage de la fenêtre
		_shell.pack();
//		 ouvrir la fenêtre au centre de l'écran
		Rectangle bounds = shellPere.getBounds ();
		Rectangle rect = _shell.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		_shell.setLocation (x, y);
		
		_shell.open(); 


		
	}
	
	
	/**
	 * Cette méthode permet de récupérer la date choisie par l'utilisateur,
	 * de mettre à jour l'attribut Date puis de fermer la fenêtre.
	 * Elle est invoquée lors d'un clic sur le bouton "valider"
	 * 
	 * @return void
	 *
	 * @see #valider()
	 *
	 */
	public void valider ()
	{
		//on récupère le mois et on le met en String sous la forme XX
		String mois = new String ("");
		int valeurMois = _comboMois.getSelectionIndex()+1;
		if (valeurMois<10)
		{
			mois = "0"+valeurMois;
		}
		else
		{
			mois = ""+valeurMois;
		}
		
		//on récupère le jour et on le met sous la forme XX
		if (Integer.parseInt(_jour)<10)
		{
			this._jour ="0"+this._jour;
		}
		
		//mise à jour de l'attribut MyDate
		this._date.setText(this._jour,mois,_comboAnnee.getItem(_comboAnnee.getSelectionIndex()));
		//fermeture de la fenêtre
		_shell.dispose ();
	}
	
	/**
	 * Cette méthode permet de sauvegarder le dernier jour sur
	 * lequel l'utilisateur a cliqué. Elle sera invoquée lors d'un 
	 * clic sur un bouton représentant un jour
	 * 
	 * @return void
	 *
	 * @param Button bouton sur lequel on a cliqué 
	 * 
	 * @see #choisirJour(Button)
	 *
	 */
	public void choisirJour (Button source)
	{
		//on sauvegarde le jour choisi dans l'attribut jour
		this.setJour (source.getText()) ;
		
		if (boutonCourant != null) {
			boutonCourant.setFont(new Font(_shell.getDisplay(),"Arial",9,SWT.NORMAL));
		}
		source.setFont(new Font(_shell.getDisplay(),"Arial",9,SWT.BOLD));
		boutonCourant = source;
	}
	
	/**
	 * Créée un vecteur de 42 boutons qui seront associés à un jour ou cachés
	 * 
	 * @return void
	 *
	 * @param Composite conteneur des boutons 
	 * 
	 * @see #afficheGrillejours(Composite)
	 *
	 */
	public void afficheGrillejours(Composite tabJour)
	{	

		GridData gridPiedDePage;
		// création des boutons des jours
		for(int i=1; i<=42; i++)
		{	
			//création du bouton
			final Button numeroJour = new Button(tabJour, SWT.PUSH|SWT.FLAT);
			numeroJour.setFont(new Font(_shell.getDisplay(),"Arial",9,SWT.NORMAL));
			numeroJour.setLayoutData(new GridData(GridData.FILL_BOTH));
			//ajout du bouton dans le vecteur contenant tous les boutons
			_vecteurButtonJour.add(numeroJour);
			//Integer I = new Integer(i);
			//numeroJour.setText(I.toString());
			//mise sous écoute du bouton
			numeroJour.addSelectionListener(new SelectionAdapter()
					{
						public void widgetSelected  (SelectionEvent e)
						{
							Button source = (Button)e.getSource();
							choisirJour (source);
						}
					}
				);
		}
		//on met le texte des boutons et masque ceux qui ne sont pas en bonne place
		masquerJour();
	}
	
	/**
	 * Cache les boutons n'étant pas à une bonne place pour le mois et l'année choisie
	 * et affiche les autres en mettant le numéro du jour correspondant
	 *  
	 * @return void
	 * 
	 * @see #masquerJour()
	 *
	 */
	public void masquerJour()
	{
		//on met tous les boutons visibles
		for (int i = 1; i<=42;i++)
		{
			Button b =(Button)_vecteurButtonJour.elementAt(i-1);
			b.setVisible(true); 
		}
		String chaineAnnee = new String (_comboAnnee.getItem(_comboAnnee.getSelectionIndex()));
		MyDate dateCourante = new MyDate(1,_comboMois.getSelectionIndex()+1,new Integer(chaineAnnee).intValue());
		//valeur qui sera affichée sur le boutons
		int nombreAAfficher = 1;
		
		/*on rend invisible tous les boutons de la première ligne
		  pour aligner le premier
		  jour du mois sous le libellé correspondant
		*/
		for (int i = 1; i<=dateCourante.donneJour();i++)
		{
			Button b =(Button)_vecteurButtonJour.elementAt(i-1);
			b.setVisible(false); 
		}
		//on affiche le nombre de jour du mois puis on caches les autres boutons
		for (int i = dateCourante.donneJour()+1; i<=42;i++)
		{
			
			dateCourante = new MyDate (nombreAAfficher,_comboMois.getSelectionIndex()+1,new Integer(chaineAnnee).intValue());
			if (!dateCourante.estValide())
			{
				Button b =(Button)_vecteurButtonJour.elementAt(i-1);
				b.setVisible(false);
			}
			else
			{
				Button b =(Button)_vecteurButtonJour.elementAt(i-1);
				Integer I = new Integer(nombreAAfficher);
				b.setText(I.toString());
				nombreAAfficher ++;
			}
		}
	}
	
	/**
	 * Cette méthode permet d'accéder à l'attribut _date du CalendrierIHM
	 * 
	 * @return ChampDate
	 * 
	 * @see #getDate()
	 *
	 */
	public ChampDate getDate ()
	{
		return this._date;
	}
	
	/**
	 * Cette méthode permet de modifier l'attribut _jour du CalendrierIHM
	 * 
	 * @param String nouvelle valeur du jour
	 * 
	 * @return void
	 * 
	 * @see #setJour(String)
	 *
	 */
	public void setJour (String chaine)
	{
		this._jour = chaine;
	}

}
