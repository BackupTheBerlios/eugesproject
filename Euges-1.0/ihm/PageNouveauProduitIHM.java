/*
 * Created on 25 déc. 2003
 *
 */
package ihm;

import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import utilitaires.GestionImage;
import application.EugesElements;
import configuration.Config;

/**
 * @author Nicolas
 *
 */


public class PageNouveauProduitIHM {
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	private final Shell shell;
	private Display myDisplay;
	private boolean annuler = false;
	private Vector initialProduit = new Vector (0,1); //sauvegarde la liste de départ si la personne annule 
	final Text newProduit;
	private List listP;
	
	public PageNouveauProduitIHM(Shell shel) {
		shell = new Shell(shel,SWT.CLOSE|SWT.APPLICATION_MODAL|SWT.RESIZE);
//		shell.setSize(365,240);
		shell.setText(message.getString("PageNouveauProduitIHM.titre"));
		shell.setImage(GestionImage._euges);
		
			//layout
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		shell.setLayout(layout);
		
		Label legende = new Label(shell,SWT.WRAP);
		legende.setText(message.getString("nomProduit"));
		
		newProduit = new Text(shell,SWT.BORDER);
		
		Button ajoutBut = new Button(shell,SWT.FLAT);
		ajoutBut.setText(message.getString("boutonAjoutProduit"));
		
		Label legende2 = new Label(shell,SWT.WRAP);
		legende2.setText(message.getString("titreListProduit"));
	
		listP = new List(shell,SWT.BORDER|SWT.V_SCROLL|SWT.MULTI);
		   // on rempli la liste es produits
		chargerListP();
		
		newProduit.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				switch (e.character) {
					case SWT.CR:
						ajoutProduit();
				
						break;
					default:
						break;
				}
			}
			public void keyReleased(KeyEvent arg0) {
				
			}
		});
		
		ajoutBut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				ajoutProduit();
			}
		});
		
		Button supprBut = new Button(shell,SWT.FLAT);
		supprBut.setText(message.getString("boutonSupprProduit"));
		supprBut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				String[] temp = listP.getSelection();
				for (int i = 0; i < temp.length; i++) {
					EugesElements.supprimeProduit(temp[i]);
				}
				//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
				EugesElements.processusEnregistre = false;
				chargerListP();
			}
		});
		
		
			// placer les éléments
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		legende.setLayoutData(data);
		
		newProduit.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		
		ajoutBut.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		
		int largeur;
		if (legende.computeSize(SWT.DEFAULT,SWT.DEFAULT).x > legende2.computeSize(SWT.DEFAULT,SWT.DEFAULT).x)
			largeur = legende.computeSize(SWT.DEFAULT,SWT.DEFAULT).x;
		else
			largeur = legende2.computeSize(SWT.DEFAULT,SWT.DEFAULT).x;
		
		data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		data.widthHint = largeur;
		legende.setLayoutData(data);
		data = new GridData(GridData.VERTICAL_ALIGN_BEGINNING|GridData.HORIZONTAL_ALIGN_BEGINNING);
		data.widthHint = largeur;
		legende2.setLayoutData(data);
		
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.widthHint = 170;
		data.heightHint = 100;
		listP.setLayoutData(data);
		
		supprBut.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING|GridData.HORIZONTAL_ALIGN_FILL));
		
		// bas de la page
		Composite basDePage = new Composite(shell,SWT.NONE);
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		basDePage.setLayout(gridLayout2);
		Label sep = new Label(basDePage,SWT.SEPARATOR|SWT.HORIZONTAL);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		sep.setLayoutData(data);
		
		Label copyright = new Label(basDePage,SWT.NONE);
		copyright.setText("www.euges.fr.st");
		copyright.setEnabled(false);
		copyright.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING|GridData.HORIZONTAL_ALIGN_BEGINNING));

		Composite c = new Composite(basDePage,SWT.NONE);
		RowLayout rowLayout = new RowLayout();
		rowLayout.wrap = false;
		rowLayout.pack = false;
		rowLayout.justify = false;
		rowLayout.type = SWT.HORIZONTAL;
		rowLayout.marginLeft = 5;
		rowLayout.marginTop = 5;
		rowLayout.marginRight = 5;
		rowLayout.marginBottom = 5;
		rowLayout.spacing = 5;
		c.setLayout(rowLayout);
		Button fermerBut = new Button(c,SWT.PUSH);
		fermerBut.setText(message.getString("fermer"));
		fermerBut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
					shell.dispose();
			}
		});

		c.pack();
		data = new GridData(GridData.HORIZONTAL_ALIGN_END);
		c.setLayoutData(data);

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		basDePage.setLayoutData(data);

		shell.pack();
		   // ouvrir la fenêtre au centre de l'écran
		Monitor primary = shell.getDisplay().getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = shell.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation (x, y);
		
		shell.open();
		
		while(!shell.isDisposed()){
		if(!shell.getDisplay().readAndDispatch())
			shell.getDisplay().sleep();
		}
		
		if (annuler) {
			EugesElements.listeProduits = initialProduit;
		}
		
	}
	
	private void ajoutProduit(){
		   // si zone de texte non vide
		if (!EugesElements.testTexteVide(newProduit.getText())) {
			   // si élément non déjà existant
			if (EugesElements.testObjetExistant(newProduit.getText())) {
				MessageBox msgErreur = new MessageBox(shell,SWT.ICON_ERROR);
				msgErreur.setMessage(message.getString("PageNouveauProduitIHM.messageErreurProduitExistant"));
				msgErreur.setText(message.getString("PageNouveauProduitIHM.titreErreurProduitExistant"));
				msgErreur.open();
				   // on remet le curseur dans la zone de texte
				newProduit.setFocus();
			}
			else{
				   // on peut ajouter l'élément
				EugesElements.ajouteEugesProduit(newProduit.getText(), " 1.0");
				//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
				EugesElements.processusEnregistre = false;
				   // on rempli la liste es produits
				chargerListP();
				
				   // on réinitialise la zone de texte à vide et on lui donne le focus
				newProduit.setText("");
				newProduit.setFocus();
			}
		}
		else {
			// Si la personne n'a rien écrit dans le champ de texte
			MessageBox msgErreur = new MessageBox(shell,SWT.ICON_ERROR);
			msgErreur.setMessage(message.getString("PageNouveauProduitIHM.messageErreurTexteVide"));
			msgErreur.setText(message.getString("PageNouveauProduitIHM.titreErreurTexteVide"));
			msgErreur.open();
		}
	}
	
	   // charger les éléments dans listP avec la liste globale des produits
	public void chargerListP(){
		// déf d'un tableau pour ensuite le mettre dans la liste de l'ihm
		String[] tabEugesProduit;
		// on appelle la fonction qui remplie ce tableau
		tabEugesProduit = EugesElements.getTableauListeProduit();

		// On associe le tableau à la liste de l'ihm
		listP.removeAll();
		if (tabEugesProduit != null){
			listP.setItems(tabEugesProduit);
		}
	}
	
	
	
}