/*
 * Created on 23 déc. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import utilitaires.GestionImage;
import application.EugesElements;
import application.PageGestionActivites;
import configuration.Config;
import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesRole;

/**
 * Classe permettant de créer la page de gestion des activites
 * Cette page (redimensionnable) peut être insérée dans le wizard ou tout autre fenêtre
 * 
 * @author Nicolas Broueilh
 *
 */
public class PageGestionActivitesIHM implements SelectionListener {
	
	/**Le message permettant de récupérer les traductions du logiciel*/
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);

	/**L'arbre contenant les activites du projet*/
	private Tree activites;
		
	/**Bouton d'ajout d'un rôle à l'activité sélectionnée*/
	private Button addRole;
	
	/**Bouton de suppression d'un rôle à l'activité sélectionnée*/
	private Button delRole;
	
	/**Bouton de création d'une nouvelle activité*/
	private Button newActivite;

	/**Bouton de suppression d'une activité*/
	private Button removeActivite;
	
	/**Le bouton d'appel de l'aide*/
	private Button aide;
		
	/**Le bouton de fermeture de la gestion des activités*/
	private Button fermer;
	
	/**Le shell de la fenêtre*/
	private Shell shell;
	
	/**Le display de la fenêtre*/
	private Display display;
	
	/**La liste contenant les rôles du projet*/
	private List listRoles;

	public PageGestionActivitesIHM(Shell parent) {
		//Mise en place de l'affichage de la fenêtre
		display = Display.getCurrent();
		shell = new Shell(parent,SWT.APPLICATION_MODAL|SWT.MAX|SWT.MIN|SWT.RESIZE);
		//Titre de la fenêtre
		shell.setText(message.getString("nomFenetre"));
		shell.setSize(350,300);
		// Définition de la taille de la fenêtre
		shell.setImage(GestionImage._euges);
		// Objet GridLayout pour placer les objets
		GridLayout layoutPrincipal = new GridLayout();
		layoutPrincipal.numColumns = 3;
		shell.setLayout(layoutPrincipal);

		// titre de l'arbre
	    Label ssTitre1 = new Label(shell,SWT.WRAP);
		ssTitre1.setText(message.getString("ssTitreCentre"));
		
		// objet vide pour remplir une cellule qui ne sert rien
		Label vide1 = new Label(shell,SWT.WRAP);
		vide1.setText("");

		// titre de la liste des roles
		Label ssTitre3 = new Label(shell,SWT.WRAP);
		ssTitre3.setText(message.getString("ssTitreGauche"));

		
		// arbre des activites
		activites = new Tree(shell,SWT.SINGLE|SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
		activites.pack();
		
		//Les boutons		
		Composite boutons = new Composite(shell,SWT.NONE);
		GridLayout roleLayout = new GridLayout();
		roleLayout.numColumns = 1;
		boutons.setLayout(roleLayout);
		

		// Bouton d'ajout d'une activite
		newActivite = new Button(boutons,SWT.PUSH);
		newActivite.setText("< +");
		newActivite.setToolTipText(message.getString("toolTipNewActivite"));
		newActivite.addSelectionListener(this);
		
		removeActivite = new Button(boutons,SWT.PUSH);
		removeActivite.setText("- >");
		removeActivite.setToolTipText(message.getString("toolTipRemoveActivite"));
		removeActivite.addSelectionListener(this);
		
		addRole = new Button(boutons,SWT.ARROW|SWT.LEFT);
		addRole.setToolTipText(message.getString("toolTipAddRole"));
		addRole.addSelectionListener(this);

		delRole = new Button(boutons,SWT.ARROW|SWT.RIGHT);
		delRole.setToolTipText(message.getString("toolTipDelRole"));
		delRole.addSelectionListener(this);
		

		GridData dataRole = new GridData(GridData.FILL_HORIZONTAL);
		newActivite.setLayoutData(dataRole);
		dataRole = new GridData(GridData.FILL_HORIZONTAL);
		removeActivite.setLayoutData(dataRole);
		dataRole = new GridData(GridData.FILL_HORIZONTAL);
		addRole.setLayoutData(dataRole);
		dataRole = new GridData(GridData.FILL_HORIZONTAL);
		delRole.setLayoutData(dataRole);

		// liste des rôles
		listRoles = new List(shell,SWT.MULTI|SWT.H_SCROLL|SWT.BORDER);


		aide = new Button(shell,SWT.PUSH);
		aide.setText(message.getString("aide"));
		aide.addSelectionListener(this);
		
		
		fermer = new Button(shell,SWT.PUSH);
		fermer.setText(message.getString("fermer"));
		fermer.addSelectionListener(this);

		//remplissage de l'arbre représentant les activités
		if (!EugesElements.listeActivites.isEmpty()) {
			for (Iterator it = EugesElements.listeActivites.iterator();it.hasNext();) {
				EugesActivite temp = (EugesActivite)it.next();
				if (temp!=null) {
					TreeItem item = new TreeItem(activites,SWT.NONE);	
					item.setData(temp);
					item.setImage(GestionImage._activite);
					item.setText(temp.toString());
					//ajout dans l'arbre des liens entre l'activite courante et ses roles associés
					for (int i=0; i < temp.getRoleCount(); i++) {
						EugesRole role = (EugesRole)temp.getRole(i);
						if (role != null) {
							TreeItem itemRole = new TreeItem(item,SWT.NONE);
							itemRole.setData(role);
							itemRole.setImage(GestionImage._role);
							itemRole.setText(role.toString());
						}
					}
				}
			}
		}
		
		//remplissage de la liste des rôles
		if (!EugesElements.listeRoles.isEmpty()) {
			for (Iterator it = EugesElements.listeRoles.iterator();it.hasNext();) {
				EugesRole temp = (EugesRole)it.next();
				if (temp!=null) {
					listRoles.add(temp.toString());
				}
			}
		}	
		
		//positionnement principal
		GridData dataPrincipal = new GridData();
		ssTitre1.setLayoutData(dataPrincipal);
		dataPrincipal = new GridData();
		vide1.setLayoutData(dataPrincipal);
		dataPrincipal = new GridData();
		ssTitre3.setLayoutData(dataPrincipal);
		
		dataPrincipal = new GridData(GridData.FILL_BOTH);
		dataPrincipal.widthHint = 45;
		activites.setLayoutData(dataPrincipal);
		dataPrincipal = new GridData();
		boutons.setLayoutData(dataPrincipal);
		dataPrincipal = new GridData(GridData.FILL_BOTH);
		dataPrincipal.widthHint = 45;
		listRoles.setLayoutData(dataPrincipal);
		
		

		GridData data3 = new GridData();
		data3.horizontalSpan=2;
		aide.setLayoutData(data3);
		data3 = new GridData(GridData.FILL_HORIZONTAL);
		fermer.setLayoutData(data3);
		
		
		
		//	Boucle d'évènements
		shell.open();
		while (!shell.isDisposed()){
			if (!shell.getDisplay().readAndDispatch())
				shell.getDisplay().sleep();
		}
		
	}

	/* (non-Javadoc)
	 * @see ihm.PageAssistantIHM#verifDonnees()
	 */
	public boolean verifDonnees() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent arg0) {
		//Traitement si clic sur un bouton
		if (arg0.getSource() instanceof Button) {
			Button button = (Button)arg0.getSource();
			//Traitement si clic sur le bouton nouvelle activite
			if (button == newActivite) {
				//ouvrir la fenêtre nouvelle activite qui va créer la nouvelle instance ActiviteEuges
				PageGestionActivites.nouvelleActivite(shell);
				//redessiner l'arbre avec la nouvelle activite
				activites.removeAll();
				for(Iterator it = EugesElements.listeActivites.iterator();it.hasNext();) {
					EugesActivite temp = (EugesActivite)it.next();
					if (temp!=null) {
						TreeItem item = new TreeItem(activites,SWT.NONE);	
						item.setData(temp);
						item.setImage(GestionImage._activite);
						item.setText(temp.toString());
						//ajout dans l'arbre des liens entre l'activite courante et ses roles associés
						for (int i=0; i < temp.getRoleCount(); i++) {
							EugesRole role = (EugesRole)temp.getRole(i);
							if (role != null) {
								TreeItem itemRole = new TreeItem(item,SWT.NONE);
								itemRole.setData(role);
								itemRole.setImage(GestionImage._role);
								itemRole.setText(role.toString());
							}
						}
					}
				}
				
			}
			//ajout d'un lien entre un role et une activite
			else if (button == addRole) {
				//tester si un role et une activite sont selectionnes
				TreeItem[] actSelected = activites.getSelection();
				String[] rolesSelected = listRoles.getSelection();
				if ((actSelected.length == 0)||(rolesSelected.length == 0)) {
					// si aucune activite et aucun role sélectionné -> ERREUR
					MessageBox msgErreur = new MessageBox(shell,SWT.ICON_ERROR);
					msgErreur.setMessage(message.getString("msgbox1Msg"));
					msgErreur.setText(message.getString("msgbox1Title"));
					msgErreur.open();
				}
				else if (actSelected[0].getParentItem() != null) {
					//si c un role qui est selectionne dans l'arbre -> erreur
					MessageBox msgErreur = new MessageBox(shell,SWT.ICON_ERROR);
					msgErreur.setMessage(message.getString("msgbox2Msg"));
					msgErreur.setText(message.getString("msgbox1Title"));
					msgErreur.open();
				}
				else {
					//cas normal
					//ajouter le lien dans le spem s'il n'existe pas deja
					EugesActivite eugesActivite = (EugesActivite)actSelected[0].getData();
					EugesRole role = EugesElements.getRole(rolesSelected[0]);
					if (PageGestionActivites.ajoutLienActiviteRole(eugesActivite,role) == 1) {
						TreeItem item = new TreeItem(actSelected[0],SWT.NONE);
						item.setData(role);
						item.setImage(GestionImage._role);
						item.setText(role.toString());
					}
					else {
						MessageBox msgErreur = new MessageBox(shell,SWT.ICON_ERROR);
						msgErreur.setMessage(message.getString("msgbox3Msg"));
						msgErreur.setText(message.getString("msgbox1Title"));
						msgErreur.open();
					}
				}
			}
			//Suppression d'un lien entre un role et une activite
			else if (button == delRole) {
				//tester si un role est selectionne dans l'arbre
				TreeItem[] roleSelected = activites.getSelection();
				if (roleSelected.length == 0) {
					// si aucun role n'est selectionne
					MessageBox msgErreur = new MessageBox(shell,SWT.ICON_ERROR);
					msgErreur.setMessage(message.getString("msgbox4Msg"));
					msgErreur.setText(message.getString("msgbox1Title"));
					msgErreur.open();
				}
				else if (!(roleSelected[0].getData() instanceof EugesRole)) {
					//si ce n'est pas un role qui est selectionne dans l'arbre -> erreur
					MessageBox msgErreur = new MessageBox(shell,SWT.ICON_ERROR);
					msgErreur.setMessage(message.getString("msgbox4Msg"));
					msgErreur.setText(message.getString("msgbox1Title"));
					msgErreur.open();
				}
				else {
					PageGestionActivites.supprLienActiviteRole((EugesActivite)roleSelected[0].getParentItem().getData(),(EugesRole)roleSelected[0].getData());
					//redessiner l'arbre
					activites.removeAll();
					for(Iterator it = EugesElements.listeActivites.iterator();it.hasNext();) {
						EugesActivite temp = (EugesActivite)it.next();
						if (temp!=null) {
							TreeItem item = new TreeItem(activites,SWT.NONE);	
							item.setData(temp);
							item.setImage(GestionImage._activite);
							item.setText(temp.toString());
							//ajout dans l'arbre des liens entre l'activite courante et ses roles associés
							for (int i=0; i < temp.getRoleCount(); i++) {
							EugesRole role = (EugesRole)temp.getRole(i);
							if (role != null) {
									TreeItem itemRole = new TreeItem(item,SWT.NONE);
									itemRole.setData(role);
									itemRole.setImage(GestionImage._role);
									itemRole.setText(role.toString());
								}
							}
						}
					}
				
				}
			}
			else if (button == removeActivite) {
				//tester si une activite est selectionne dans l'arbre
				TreeItem[] actSelected = activites.getSelection();
				if (actSelected.length == 0) {
					// si aucune activite n'est selectionne
					MessageBox msgErreur = new MessageBox(shell,SWT.ICON_ERROR);
					msgErreur.setMessage(message.getString("msgbox5Msg"));
					msgErreur.setText(message.getString("msgbox1Title"));
					msgErreur.open();
				}
				else if (!(actSelected[0].getData() instanceof EugesActivite)) {
					//si ce n'est pas une activite qui est selectionnee dans l'arbre -> erreur
					MessageBox msgErreur = new MessageBox(shell,SWT.ICON_ERROR);
					msgErreur.setMessage(message.getString("msgbox5Msg"));
					msgErreur.setText(message.getString("msgbox1Title"));
					msgErreur.open();
				}
				else {
					TreeItem[] roles = actSelected[0].getItems();
					Vector vectRoles = new Vector();
					for (int i=0; i<roles.length; i++) {
						vectRoles.add((EugesRole)roles[0].getData());
					}
					PageGestionActivites.supprActivite((EugesActivite)actSelected[0].getData(),vectRoles);
					//redessiner l'arbre
					activites.removeAll();
					for(Iterator it = EugesElements.listeActivites.iterator();it.hasNext();) {
						EugesActivite temp = (EugesActivite)it.next();
						if (temp!=null) {
							TreeItem item = new TreeItem(activites,SWT.NONE);	
							item.setData(temp);
							item.setImage(GestionImage._activite);
							item.setText(temp.toString());
							//ajout dans l'arbre des liens entre l'activite courante et ses roles associés
							for (int i=0; i < temp.getRoleCount(); i++) {
							EugesRole role = (EugesRole)temp.getRole(i);
								if (role != null) {
									TreeItem itemRole = new TreeItem(item,SWT.NONE);
									itemRole.setData(role);
									itemRole.setImage(GestionImage._role);
									itemRole.setText(role.toString());
								}
							}
						}
					}
					
				}
			}
			else if (button == fermer) {
				shell.close();
			}
			//idem pour les produits
		}
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

	
	/* (non-Javadoc)
	 * @see ihm.PageAssistantIHM#loadData()
	 */
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
	
}
