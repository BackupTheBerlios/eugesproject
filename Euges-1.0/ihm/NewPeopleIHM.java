/*
 * Created on 8 déc. 2003
 *
 */

package ihm;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import utilitaires.GestionImage;
import application.EugesElements;
import application.NewPeople;
import configuration.Config;
import donnees.eugesSpem.EugesPersonne;

/**
 * @author Mathieu GAYRAUD
 *
 */
public class NewPeopleIHM {

	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	private Shell _shell;
	private Display _display;
	private Vector _personnes;
	private int _nextID = 0;
	
	public NewPeopleIHM(Shell shell) {
		_display = shell.getDisplay();
		_shell = new Shell(shell,SWT.CLOSE|SWT.APPLICATION_MODAL);
		_shell.setText(message.getString("NewPeopleIHM.titreShell"));
		_shell.setImage(GestionImage._actor);
	}
	
	public void open() {
		// GridLayout pour l'alignement
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		_shell.setLayout(gridLayout);

		_personnes = EugesElements.listePersonnes;
		
		Label titre1 = new Label(_shell,SWT.NONE);
		titre1.setText(message.getString("NewPeopleIHM.titre1"));
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		data.horizontalSpan = 2;
		titre1.setLayoutData(data);

		Label titre2 = new Label(_shell,SWT.NONE);
		titre2.setText(message.getString("NewPeopleIHM.titre2"));
		titre2.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		
		Label ssTitre1 = new Label(_shell,SWT.NONE);
		ssTitre1.setText(message.getString("NewPeopleIHM.ssTitre1"));
		ssTitre1.pack();
		
		final Text nom = new Text(_shell,SWT.BORDER);
		nom.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		
		final Tree arbreNew = new Tree (_shell,SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL|SWT.SINGLE);
		data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 145;
		data.heightHint = 145;
		data.verticalSpan = 5;
		data.grabExcessVerticalSpace = true;
		data.grabExcessHorizontalSpace = true;
		arbreNew.setLayoutData(data);
		// on rempli l'arbre
		for (Iterator iter = _personnes.iterator(); iter.hasNext();) {
			EugesPersonne e = (EugesPersonne) iter.next();
			int IDPers = e.getId();
			// Identifiant
			TreeItem temp = new TreeItem(arbreNew,SWT.NONE);
			temp.setText("Personne " + IDPers);
			if (IDPers > _nextID)
				_nextID = IDPers;
			// Nom Prenom Email
			TreeItem temp2 = new TreeItem(temp,SWT.NONE);
			temp2.setText(e.getNom());
			temp2 = new TreeItem(temp,SWT.NONE);
			temp2.setText(e.getPrenom());
			temp2 = new TreeItem(temp,SWT.NONE);
			temp2.setText(e.getMail());
		}
		_nextID++;
		
		Label ssTitre2 = new Label(_shell,SWT.NONE);
		ssTitre2.setText(message.getString("NewPeopleIHM.ssTitre2"));
		ssTitre2.pack();
		
		final Text prenom = new Text(_shell,SWT.BORDER);
		prenom.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		
		Label ssTitre3 = new Label(_shell,SWT.NONE);
		ssTitre3.setText(message.getString("NewPeopleIHM.ssTitre3"));
		ssTitre3.pack();

		int largeur;
		if (ssTitre1.computeSize(SWT.DEFAULT,SWT.DEFAULT).x > ssTitre2.computeSize(SWT.DEFAULT,SWT.DEFAULT).x)
			if (ssTitre1.computeSize(SWT.DEFAULT,SWT.DEFAULT).x > ssTitre3.computeSize(SWT.DEFAULT,SWT.DEFAULT).x)
				largeur = ssTitre1.computeSize(SWT.DEFAULT,SWT.DEFAULT).x;
			else
				largeur = ssTitre3.computeSize(SWT.DEFAULT,SWT.DEFAULT).x;
		else
			if (ssTitre2.computeSize(SWT.DEFAULT,SWT.DEFAULT).x > ssTitre3.computeSize(SWT.DEFAULT,SWT.DEFAULT).x)
				largeur = ssTitre2.computeSize(SWT.DEFAULT,SWT.DEFAULT).x;
			else
				largeur = ssTitre3.computeSize(SWT.DEFAULT,SWT.DEFAULT).x;

		data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		data.widthHint = largeur;
		ssTitre1.setLayoutData(data);
		data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		data.widthHint = largeur;
		ssTitre2.setLayoutData(data);
		data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		data.widthHint = largeur;
		ssTitre3.setLayoutData(data);

		final Text email = new Text(_shell,SWT.BORDER);
		email.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		
		Button addBut = new Button (_shell,SWT.FLAT);
		addBut.setText(message.getString("NewPeopleIHM.addBut"));
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = addBut.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		data.horizontalSpan = 2;
		addBut.setLayoutData(data);
		
		Button delBut = new Button (_shell,SWT.FLAT);
		delBut.setText(message.getString("NewPeopleIHM.delBut"));
		data = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_BEGINNING);
		data.horizontalSpan = 2;
		delBut.setLayoutData(data);

		addBut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				if ((nom.getText().length()!=0)&&(prenom.getText().length()!=0)) {
					// initialisation de l'objet à insérer
					EugesPersonne p = new EugesPersonne(_nextID,nom.getText(),prenom.getText(),email.getText());
					EugesElements.ajouterElement(p);
					
					//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
					EugesElements.processusEnregistre = true;
					// Identifiant
					TreeItem temp = new TreeItem(arbreNew,SWT.NONE);
					temp.setText(message.getString("NewPeopleIHM.personne") + " " + _nextID);
					_nextID++;
					// nom
					TreeItem temp2 = new TreeItem(temp,SWT.NONE);
					temp2.setText(nom.getText());
					// prenom
					temp2 = new TreeItem(temp,SWT.NONE);
					temp2.setText(prenom.getText());
					// email
					temp2 = new TreeItem(temp,SWT.NONE);
					temp2.setText(email.getText());
					// on efface les champs
					nom.setText("");
					prenom.setText("");
					email.setText("");
					nom.setFocus();
				} else {
					MessageBox msgErreur = new MessageBox(_shell,SWT.ICON_ERROR);
					msgErreur.setMessage(message.getString("NewPeopleIHM.messageErreur"));
					msgErreur.setText(message.getString("NewPeopleIHM.titreErreur"));
					msgErreur.open();
				}
			}
		});
		
		delBut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				TreeItem[] persSelected = arbreNew.getSelection();
				TreeItem persCur;
				if (persSelected.length == 0)
					return ;
				else {
					//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
					EugesElements.processusEnregistre = true;
					
					persCur = persSelected[0];
					if (persSelected[0].getParentItem() != null) {
						persCur = persSelected[0].getParentItem();
					}
					NewPeople.supprimerPersonne(persCur.getText());
					persCur.dispose();
				}
			}
		});
		
		Composite basDePage = new Composite(_shell,SWT.NONE);
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
		fermerBut.setText(message.getString("NewPeopleIHM.Fermer"));
		fermerBut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				_shell.dispose();
			}
		});

		c.pack();
		data = new GridData(GridData.HORIZONTAL_ALIGN_END);
		c.setLayoutData(data);

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		basDePage.setLayoutData(data);
		
		_shell.pack();

		// ouvrir la fenêtre au centre de l'écran
		Rectangle bounds = _shell.getParent().getBounds ();
		Rectangle rect = _shell.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		_shell.setLocation (x, y);
		
		_shell.open();

		while(!_shell.isDisposed()){
		if(!_display.readAndDispatch())
			_display.sleep();
		}
	}
}
