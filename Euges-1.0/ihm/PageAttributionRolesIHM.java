/*
 * Created on 25 nov. 2003
 *
 */
package ihm;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import utilitaires.GestionImage;
import application.EugesElements;
import application.NewRole;
import application.PageAttributionRoles;
import configuration.Config;
import donnees.Iteration;
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesRole;
/**
 * @author Mathieu GAYRAUD
 *
 */
public class PageAttributionRolesIHM extends PageAssistantIHM {

	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	private Vector vecteurPersonnes = new Vector (0,1);
	
	private Vector listR = new Vector (0,1);
	
	/**
	 * @param shell fenêtre...
	 * @param listP liste des personnes relatives au projet
	 * @param listR liste des rôles du projet
	 */
	public PageAttributionRolesIHM (final Shell shell, final Iteration it) {
		// Appel au constructeur de l'objet Composite
		super(shell);
		// Objet GridLayout pour placer les objets
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		this.setLayout(gridLayout);

		// titre de la page
		Font font = new Font(getDisplay(), "Arial", 15, 15);
		Label titre = new Label(this, SWT.NONE);
		titre.setFont(font);
		titre.setText(message.getString("PageAttributionRolesIHM.titrePage") + it.get_numIt());

		// titre de l'arbre
		CLabel ssTitre1 = new CLabel(this,SWT.WRAP);
		ssTitre1.setText(message.getString("PageAttributionRolesIHM.ssTitreGauche"));
		ssTitre1.setImage(GestionImage._actor);
		
		// objet vide pour remplir une cellule qui ne sert rien
		Label vide1 = new Label(this,SWT.WRAP);
		vide1.setText("");
		
		// titre de la liste
		CLabel ssTitre2 = new CLabel(this,SWT.WRAP);
		ssTitre2.setText(message.getString("PageAttributionRolesIHM.ssTitreDroit"));
		ssTitre2.setImage(GestionImage._role);
		
		// arbre des personnes
		final Tree personnes = new Tree(this,SWT.SINGLE|SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
		vecteurPersonnes = EugesElements.listePersonnes;
		for (Iterator iter = vecteurPersonnes.iterator(); iter.hasNext();) {
			EugesPersonne elt = (EugesPersonne) iter.next();
			// Identifiant
			TreeItem temp = new TreeItem(personnes,SWT.NONE);
			temp.setImage(GestionImage._actor);
			temp.setText(elt.getId() + " " + elt.getNom() + " " + elt.getPrenom());
			// Roles
			Vector tempRoles = it.getAssociation(elt);
			if (tempRoles != null) {
				for (Iterator iterator = tempRoles.iterator(); iterator.hasNext();) {
					EugesRole e = (EugesRole) iterator.next();
					TreeItem temp2 = new TreeItem(temp,SWT.NONE);
					temp2.setImage(GestionImage._role);
					temp2.setText(e.toString());
				}
			}
		}
		
		// bouton pour ajouter une nouvelle personne
		Button newPers = new Button(this,SWT.PUSH);
		newPers.setText("<+");
		newPers.setToolTipText(message.getString("PageAttributionRolesIHM.toolTipNewPers"));
		
		// contrôle de l'évènement du bouton ajouter une nouvelle personne
		newPers.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				// ouverture d'une nouvelle fenêtre
				NewPeopleIHM np = new NewPeopleIHM(shell);
				np.open();
				personnes.removeAll();
				// on re-rempli l'arbre
				vecteurPersonnes = EugesElements.listePersonnes;
				for (Iterator iter = vecteurPersonnes.iterator(); iter.hasNext();) {
					EugesPersonne elt = (EugesPersonne) iter.next();
					// Identifiant
					TreeItem temp = new TreeItem(personnes,SWT.NONE);
					temp.setImage(GestionImage._actor);
					temp.setText(elt.getId() + " " + elt.getNom() + " " + elt.getPrenom());
					// Roles
					Vector tempRoles = it.getAssociation(elt);
					if (tempRoles != null) {
						for (Iterator iterator = tempRoles.iterator(); iterator.hasNext();) {
							EugesRole elt2 = (EugesRole) iterator.next();
							TreeItem temp2 = new TreeItem(temp,SWT.NONE);
							temp2.setImage(GestionImage._role);
							temp2.setText(elt2.toString());
						}
					}
				}
			}
		});

		// liste des rôles
		final List listRoles = new List(this,SWT.MULTI|SWT.H_SCROLL|SWT.BORDER);
		listR = NewRole.roleToVector();
		String[] temp = new String[listR.size()];
		listR.copyInto(temp);
		listRoles.setItems(temp);

		// bouton pour ajouter un nouveau rôle
		Button newRole = new Button(this,SWT.PUSH);
		newRole.setText("+>");
		newRole.setToolTipText(message.getString("PageAttributionRolesIHM.toolTipNewRole"));

		// contrôle de l'évènement du bouton ajouter un nouveau rôle
		newRole.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				// ouverture d'une nouvelle fenêtre
				NewRoleIHM nr = new NewRoleIHM(shell);
				nr.open();
				listR = NewRole.roleToVector();
				String[] temp = new String[listR.size()];
				listR.copyInto(temp);
				listRoles.setItems(temp);
				// rafraidhissment de l'arbre des personnes
				personnes.removeAll();
				vecteurPersonnes = EugesElements.listePersonnes;
				for (Iterator iter = vecteurPersonnes.iterator(); iter.hasNext();) {
					EugesPersonne elt = (EugesPersonne) iter.next();
					// Identifiant
					TreeItem temp2 = new TreeItem(personnes,SWT.NONE);
					temp2.setImage(GestionImage._actor);
					temp2.setText(elt.getId() + " " + elt.getNom() + " " + elt.getPrenom());
					// Roles
					Vector tempRoles = it.getAssociation(elt);
					if (tempRoles != null) {
						for (Iterator iterator = tempRoles.iterator(); iterator.hasNext();) {
							EugesRole elt2 = (EugesRole) iterator.next();
							TreeItem temp3 = new TreeItem(temp2,SWT.NONE);
							temp3.setImage(GestionImage._role);
							temp3.setText(elt2.toString());
						}
					}
				}
			}
		});

		// bouton pour associer un ou plusieurs rôle à une personne
		Button addRole = new Button(this,SWT.ARROW|SWT.LEFT);
		addRole.setToolTipText(message.getString("PageAttributionRolesIHM.toolTipAddRole"));
		// contrôle de l'évènement de ce bouton de ce bouton
		addRole.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				TreeItem[] persSelected = personnes.getSelection();
				if (persSelected.length == 0) {
					// si aucune personne sélectionnée -> ERREUR
					MessageBox msgErreur = new MessageBox(shell,SWT.ICON_ERROR);
					msgErreur.setMessage(message.getString("PageAttributionRolesIHM.msgbox1Msg"));
					msgErreur.setText(message.getString("PageAttributionRolesIHM.msgbox1Title"));
					msgErreur.open();
				}
				else {
					if (persSelected[0].getParentItem() != null) {
						// si c'est un rôle qui est selectionné dans l'arbre -> ERREUR
						MessageBox msgErreur = new MessageBox(shell,SWT.ICON_ERROR);
						msgErreur.setMessage(message.getString("PageAttributionRolesIHM.msgbox2Msg"));
						msgErreur.setText(message.getString("PageAttributionRolesIHM.msgbox2Title"));
						msgErreur.open();
					}
					else {
						String[] rolesSelected = listRoles.getSelection();
						if (listRoles.getSelectionCount()<1) {
							// si aucun rôle n'a été selectionné -> ERREUR
							MessageBox msgErreur = new MessageBox(shell,SWT.ICON_ERROR);
							msgErreur.setMessage(message.getString("PageAttributionRolesIHM.msgbox3Msg"));
							msgErreur.setText(message.getString("PageAttributionRolesIHM.msgbox3Msg"));
							msgErreur.open();
						}
						else {
							// cas nominal
							EugesPersonne p = PageAttributionRoles.getPersonne(persSelected[0].getText());
							TreeItem newRole;
							for (int i=0;i<rolesSelected.length;i++) {
								// on teste si le rôle n'a pas déjà été affecté à cette personne
								if (!it.contientAssociation(p,PageAttributionRoles.getRole(rolesSelected[i]))) {
									newRole = new TreeItem(persSelected[0],SWT.NONE);
									newRole.setText(rolesSelected[i]);
									newRole.setImage(GestionImage._role);
									it.ajouterAssociation(p,PageAttributionRoles.getRole(rolesSelected[i]));
								}
							}
							//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
							EugesElements.processusEnregistre = true;
							persSelected[0].setExpanded(true);
							listRoles.deselectAll();
						}
					}
				}
			}
		});

		// bouton pour supprimer un rôle d'une personne
		Button delRole = new Button(this,SWT.ARROW|SWT.RIGHT);
		delRole.setToolTipText(message.getString("PageAttributionRolesIHM.toolTipDelRole"));
		// control de l'évènement de ce bouton
		// on efface juste un rôle si celui-ci est selectionné,
		// on efface tous les rôle d'une personne si c'est une personne qui est selectionnée
		delRole.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				TreeItem[] persSelected = personnes.getSelection();
				if (persSelected.length == 0)
					return;
				else {
					if (persSelected[0].getParentItem() == null) {
						EugesPersonne p = PageAttributionRoles.getPersonne(persSelected[0].getText());
						TreeItem[] rolesPers = persSelected[0].getItems();
						for (int i=0;i<rolesPers.length;i++) {
							it.supprimerAssociation(p,PageAttributionRoles.getRole(rolesPers[i].getText()));
							rolesPers[i].dispose();
						}
					}
					else {
						EugesPersonne p = PageAttributionRoles.getPersonne(persSelected[0].getParentItem().getText());
						it.supprimerAssociation(p,PageAttributionRoles.getRole(persSelected[0].getText()));
						persSelected[0].dispose();
					}
					//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
					EugesElements.processusEnregistre = true;
				}
			}
		});
		
		// objet vide pour remplir une cellule qui ne sert rien
		Label vide2 = new Label(this,SWT.WRAP);
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
		point = newPers.computeSize(SWT.DEFAULT,SWT.DEFAULT);
		int widthBut = point.x;
		data.heightHint = point.y;
		data.widthHint = point.x;
		newPers.setLayoutData(data);
		
		data = new GridData();
		data.heightHint = point.y;
		data.widthHint = point.x;
		newRole.setLayoutData(data);
		
		data = new GridData();
		data.heightHint = point.y;
		data.widthHint = point.x;
		addRole.setLayoutData(data);
		
		data = new GridData();
		data.heightHint = point.y;
		data.widthHint = point.x;
		delRole.setLayoutData(data);
		
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
		data.verticalSpan = 5;
		personnes.setLayoutData(data);
		
		data = new GridData(GridData.FILL_BOTH);
		data.widthHint = largeur;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.verticalSpan = 5;
		listRoles.setLayoutData(data);
		
		//activation des boutons
		boolean[] tab={true,true,false,true,true};
		set_activeBoutons(tab);
	}

	/* (non-Javadoc)
	 * @see ihm.PageAssistantIHM#verifDonnees()
	 */
	public boolean verifDonnees() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ihm.PageAssistantIHM#loadData()
	 */
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
}
