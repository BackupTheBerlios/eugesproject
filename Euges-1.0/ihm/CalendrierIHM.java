/**
 * 
 *
 * 
 * @author lasticot69
 */
package ihm;

import java.util.Date;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import utilitaires.GestionImage;
import utilitaires.MyDate;


/**
 * @author nico
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CalendrierIHM extends Dialog{
	
	
	private Display display=Display.getCurrent();
	private Combo comboMois ;
	private Combo comboAnnee;
	private Shell shell;
	private String jour;
	private ChampDate d;
	private Vector tabButton;
	
	public CalendrierIHM (final Shell shellPrincipal,final ChampDate d)
	{
		
		super (shellPrincipal);
		this.jour = "1";
		//shell de la fenêtre
		shell = new Shell(shellPrincipal, SWT.APPLICATION_MODAL | SWT.CLOSE);
		this.d = d;
		tabButton = new Vector ();
		shell.setText("Calendrier");
		// Définition de la taille de la fenêtre
		//shell.setSize(350, 400);
		
		shell.setImage(GestionImage._euges);
			
		//définition du layout de la fenêtre
		GridLayout gr = new GridLayout (1,true);
		shell.setLayout(gr);
		
		//panneau enTete
		Composite enTete = new Composite (shell, SWT.NONE);
		final Composite piedDePage = new Composite (shell, SWT.BORDER);
		piedDePage.setRedraw(true);
		//final Composite tabJour = new Composite (shell, SWT.BORDER);
		//création des labels du panneau
		Label mois = new Label(enTete,SWT.NONE);
		mois.setText("Mois");
		Label annee = new Label(enTete,SWT.NONE);
		annee.setText("Annee");
		
		//création du combo de choix de mois
		String [] listeMois = {"Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Aout","Septembre","Octobre","Novembre","Décembre"};
		comboMois = new Combo (enTete, SWT.READ_ONLY); 
		comboMois.setItems(listeMois);
		comboMois.select(0);
		comboMois.addSelectionListener (new SelectionAdapter ()
				{
			public void widgetSelected  (SelectionEvent e)
			{
				masquerJour();
			}
		});

		//création du combo de choix d'année
		comboAnnee = new Combo (enTete, SWT.READ_ONLY);
		comboAnnee.addSelectionListener (new SelectionAdapter ()
											{
			public void widgetSelected  (SelectionEvent e)
			{
				masquerJour();
			}
											});
		Date today = new Date ();

		//récupération de l'année courante sous la forme 20XX
		int anneeCourante = today.getYear()+1900;
		//remplissage du combo année
		
		for (int j =0, i = anneeCourante - 10; i< anneeCourante +11 ;i++, j++)
		{
			Integer an = new Integer (i);
			comboAnnee.add(an.toString(),j);
		}
		comboAnnee.select(10);
		
		//définition du layout de enTete
		GridLayout layoutEnTete = new GridLayout (2,true);
		
		enTete.setLayout (layoutEnTete);
		
		//ajout des éléments ds enTete
		GridData gridEnTete = new GridData (GridData.HORIZONTAL_ALIGN_CENTER);
		gridEnTete.widthHint=60;
		mois.setData(gridEnTete);
		gridEnTete = new GridData (GridData.HORIZONTAL_ALIGN_CENTER);
		gridEnTete.widthHint=60;
		annee.setData(gridEnTete);
		gridEnTete = new GridData (GridData.HORIZONTAL_ALIGN_CENTER);
		gridEnTete.widthHint=60;
		comboMois.setData(gridEnTete);
		gridEnTete = new GridData (GridData.HORIZONTAL_ALIGN_CENTER);
		gridEnTete.widthHint=60;
		comboAnnee.setData(gridEnTete);
		
		
		//affichage de enTete dans la fenêtre
		gridEnTete = new GridData (GridData.HORIZONTAL_ALIGN_CENTER);
		shell.setData(gridEnTete);
		
		//définition du layout de piedDePage
		GridLayout layoutPiedDePage = new GridLayout (7,false);
		layoutPiedDePage.horizontalSpacing =10;
		layoutPiedDePage.marginHeight=10;
		layoutPiedDePage.marginWidth=10;
		layoutPiedDePage.verticalSpacing=10;
		piedDePage.setLayout (layoutPiedDePage);
		
		//affichage de piedDePage dans la fenêtre
		GridData gridPiedDePage = new GridData (GridData.HORIZONTAL_ALIGN_CENTER);
		shell.setData(gridPiedDePage);
		
		// affichage des jours
		afficheGrillejours(piedDePage);
		
		//création du bouton pour valider la saisie de la date
		Button valider = new Button (shell, SWT.PUSH);
		valider.setText("valider");
		valider.addSelectionListener(new SelectionAdapter()
				{
			public void widgetSelected  (SelectionEvent e)
			{
				valider (CalendrierIHM.this.d);
			}
		}
		);
		shell.pack();
		
		//ouvrir la fenêtre au centre de l'écran
		Rectangle bounds = shellPrincipal.getBounds ();
		Rectangle rect = shell.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation (x, y);
		
		//affichage du shell
		shell.open(); 


		
	}
	public ChampDate getDate ()
	{
		return this.d;
	}
	public void valider (ChampDate d)
	{
		//on récupère le mois sous forme numérique
		String mois = new String ("");
		int valMois = comboMois.getSelectionIndex()+1;
		if (valMois<10)
		{
			mois = "0"+valMois;
		}
		else
		{
			mois = ""+valMois;
		}
		//on récupère le mois au bon format
		
		if (Integer.parseInt(jour)<10)
		{
			this.jour ="0"+this.jour;
		}
		
		//on demande à la fenêtre père de mettre à jour son champ
		this.d.setText(this.jour,mois,comboAnnee.getItem(comboAnnee.getSelectionIndex()));
		shell.dispose ();
	}
	
	public void choisirJour (Button src)
	{
		//on sauvegarde le jour choisi dans l'attribut jour
		this.jour = src.getText();
	}
	
	public void afficheGrillejours(Composite tabJour)
	{	

		GridData gridPiedDePage;
		// affichage des boutons des jours
		for(int i=1; i<=49; i++)
		{	
			Button numJour = new Button(tabJour, SWT.PUSH | SWT.FLAT);
			tabButton.add(numJour);
			Integer I = new Integer(i);
			numJour.setText(I.toString());
			numJour.addSelectionListener(new SelectionAdapter()
					{
						public void widgetSelected  (SelectionEvent e)
						{
							Button src = (Button)e.getSource();
							choisirJour (src);
						}
					}
				);
			
			gridPiedDePage = new GridData (GridData.HORIZONTAL_ALIGN_CENTER);
			gridPiedDePage.widthHint=60;
			numJour.setData(gridPiedDePage);
		}
		masquerJour();
	}
	public void masquerJour()
	{
		for (int i = 1; i<=49;i++)
		{
			Button b =(Button)tabButton.elementAt(i-1);
			b.setVisible(true); 
		}
		for (int i = 1; i<=49;i++)
		{
			String str = new String (comboAnnee.getItem(comboAnnee.getSelectionIndex()));
			
			MyDate dateCourante = new MyDate(i,comboMois.getSelectionIndex()+1,new Integer(str).intValue());

			if (!dateCourante.estValide())
			{
				Button b =(Button)tabButton.elementAt(i-1);
				b.setVisible(false);
			}
		}
	}

}
