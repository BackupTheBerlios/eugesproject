/*
 * Created on 17 janv. 2004
 *
 */
package ihm;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import utilitaires.GestionImage;
import application.EugesElements;
import configuration.Config;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesRole;

/**
 * @author Mathieu GAYRAUD
 *
 */
public class NouveauParticipantIHM extends Dialog {

	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	private Display _display;
	private Shell _shell;
	private List _persDispo;
	private List _participants;
	private Vector _vPersDispo = new Vector();
	private Vector _vParticipants = new Vector();
	
	public NouveauParticipantIHM (Shell shell) {
		super(shell);
		_display = shell.getDisplay();
		_shell = new Shell(shell,SWT.CLOSE|SWT.APPLICATION_MODAL);
		_shell.setText(message.getString("NouveauParticipantIHM.titreShell"));
		_shell.setImage(GestionImage._euges);
	}
	
	public void open (final EugesActRealise act, int numIt) {
		// Objet GridLayout pour placer les objets
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		_shell.setLayout(gridLayout);

		// titre de la page
		Font font = new Font(_display, "Arial", 15, 15);
		Label titre = new Label(_shell, SWT.NONE);
		titre.setFont(font);
		titre.setText(message.getString("titrePage") + " \n" + act.toString());

		// titre de l'arbre
		Label ssTitre1 = new Label(_shell,SWT.WRAP);
		ssTitre1.setText(message.getString("ssTitreGauche"));
		
		// objet vide pour remplir une cellule qui ne sert rien
		Label vide1 = new Label(_shell,SWT.WRAP);
		vide1.setText("");
		
		// titre de la liste
		Label ssTitre2 = new Label(_shell,SWT.WRAP);
		ssTitre2.setText(message.getString("ssTitreDroit"));
		
		// liste des personnes
		_participants = new List(_shell,SWT.MULTI|SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
		for (int i=0; i<act.getPersonneCount(); i++) {
			String s = act.getPersonne(i).toString();
			_vParticipants.add(s);
			_participants.add(s);
		}
		
		// bouton pour déterminer un ou plusieurs participants
		Button ajouterPart = new Button(_shell,SWT.ARROW|SWT.LEFT);
		ajouterPart.setToolTipText(message.getString("toolTipAddPart"));
		// contrôle de l'évènement de ce bouton de ce bouton
		ajouterPart.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				String[] persSelected = _persDispo.getSelection();
				if (persSelected.length == 0) {
					// si aucune personne sélectionnée -> ERREUR
					MessageBox msgErreur = new MessageBox(_shell,SWT.ICON_ERROR);
					msgErreur.setMessage(message.getString("msgbox1Msg"));
					msgErreur.setText(message.getString("msgbox1Title"));
					msgErreur.open();
				}
				else {
					// cas nominal
					for (int i=0; i<persSelected.length; i++) {
						if (!_vParticipants.contains(persSelected[i])) {
							_participants.add(persSelected[i]);
							_vParticipants.add(persSelected[i]);
							act.ajouterPersonne(EugesElements.getPersonneDansListePersonnes(persSelected[i]));
							_persDispo.remove(persSelected[i]);
							_vPersDispo.remove(persSelected[i]);
						}
					}
					//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
					EugesElements.processusEnregistre = false;
					_persDispo.deselectAll();
				}
			}
		});

		// liste des personnes disponibles
		_persDispo = new List(_shell,SWT.MULTI|SWT.H_SCROLL|SWT.BORDER);
		// on rempli la liste avec seulement les personne qui peuvent intervenir pour cette activité
		EugesRole r = act.get_activiteParent().getRole(0);
		for (Iterator iter = EugesElements._projet.getIteration(numIt).getAssociation(r).iterator(); iter.hasNext();) {
			EugesPersonne e = (EugesPersonne) iter.next();
			String s = e.toString();
			if (!_vParticipants.contains(s)) {
				_vPersDispo.add(s);
				_persDispo.add(s);
			}
		}

		// bouton pour supprimer un ou plusieurs participants
		Button supprPart = new Button(_shell,SWT.ARROW|SWT.RIGHT);
		supprPart.setToolTipText(message.getString("toolTipDelPart"));
		// control de l'évènement de ce bouton
		supprPart.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				String[] persSelected = _participants.getSelection();
				if (persSelected.length == 0)
					return;
				else {
					// Cas nominal
					for (int i=0; i<persSelected.length; i++) {
						if (!_vPersDispo.contains(persSelected[i])) {
							_persDispo.add(persSelected[i]);
							_vPersDispo.add(persSelected[i]);
							act.supprimerPersonne(EugesElements.getPersonneDansListePersonnes(persSelected[i]));
							_participants.remove(persSelected[i]);
							_vParticipants.remove(persSelected[i]);
						}
					}
					//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
					EugesElements.processusEnregistre = false;
					_participants.deselectAll();
				}
			}
		});
		
		// objet vide pour remplir une cellule qui ne sert rien
		Label vide2 = new Label(_shell,SWT.WRAP);
		vide2.setText("");

		// mise en place des caractéristiques du GridLayout (hauteur, largeur, remplissage, span, ...)
		GridData data = new GridData();
		Point point = titre.computeSize(SWT.DEFAULT,SWT.DEFAULT);
		data.heightHint = point.y;
		data.horizontalSpan = 3;
		titre.setLayoutData(data);

		data = new GridData();
		point = ssTitre1.computeSize(SWT.DEFAULT,SWT.DEFAULT);
		int largeurSsTitre1 = point.x;
		data.heightHint = point.y;
		data.verticalAlignment = GridData.CENTER;
		ssTitre1.setLayoutData(data);

		data = new GridData();
		data.heightHint = point.y;
		vide1.setLayoutData(data);

		data = new GridData();
		data.heightHint = point.y;
		data.verticalAlignment = GridData.CENTER;
		point = ssTitre2.computeSize(SWT.DEFAULT,SWT.DEFAULT);
		int largeurSsTitre2 = point.x;
		ssTitre2.setLayoutData(data);
		
		data = new GridData();
		point = ajouterPart.computeSize(SWT.DEFAULT,SWT.DEFAULT);
		data.heightHint = point.y;
		data.widthHint = point.x;
		ajouterPart.setLayoutData(data);
		
		data = new GridData();
		data.heightHint = point.y;
		data.widthHint = point.x;
		supprPart.setLayoutData(data);
		
		int largeur;
		if (largeurSsTitre1 > largeurSsTitre2)
			largeur = largeurSsTitre1;
		else
			largeur = largeurSsTitre2;
		data = new GridData(GridData.FILL_BOTH);
		data.widthHint = largeur;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.verticalSpan = 3;
		_participants.setLayoutData(data);
		
		data = new GridData(GridData.FILL_BOTH);
		data.widthHint = largeur;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.verticalSpan = 3;
		_persDispo.setLayoutData(data);

		// bas de page
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
		fermerBut.setText(message.getString("Fermer"));
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
		// fin bas de page
		
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
