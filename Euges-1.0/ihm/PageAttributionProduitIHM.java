/*
 * Created on 22 déc. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import utilitaires.GestionImage;
import application.EugesElements;
import configuration.Config;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesVersion;

/**
 * @author Nicolas
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */


public class PageAttributionProduitIHM {
		
		//	gestion multi-langue
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	private List listePersVersion; // liste des personnes associées à cette version du produit
	private List listePersProjet; // liste des personnes associées à l'activité
	private final Shell shell;
	
	public PageAttributionProduitIHM(final Display display, final EugesVersion version) {
			
		Display myDisplay = display;
		shell = new Shell(display, SWT.CLOSE|SWT.APPLICATION_MODAL|SWT.RESIZE);
		shell.setText(message.getString("PageAttributionProduitsIHM.titreHautDePage"));
		shell.setImage(GestionImage._euges);
		
			// Layout pour placer les objets
		 GridLayout gridLayout = new GridLayout();
		 gridLayout.numColumns = 3;
		 shell.setLayout(gridLayout);
		 
		 	
		 
		 	// Création de la page
		 	
		// titre de la page
		Font font = new Font(shell.getDisplay(), "Arial", 15, 15);
		Label titre = new Label(shell, SWT.NONE);
		titre.setFont(font);
		if (version!=null && version.get_produitParent()!=null){
			titre.setText(message.getString("PageAttributionProduitsIHM.titrePage") + version.toString());
		} else {
			titre.setText(message.getString("PageAttributionProduitsIHM.titrePage"));
		}
		 
			// titre de l'arbre des produits
		Label titreListePersonneAssociee = new Label(shell,SWT.WRAP);
		titreListePersonneAssociee.setText(message.getString("PageAttributionProduitsIHM.titreListePersonneAssociee"));
		
			// bouton pour laisser une place vide
		Button boutonNouveau = new Button(shell,SWT.PUSH);
		boutonNouveau.setVisible(false);
		
			// titre de la liste des personnes
		Label titreListePersonne = new Label(shell,SWT.WRAP);
		titreListePersonne.setText(message.getString("titreListePersonne"));

		 	// liste des personnes associées à cette version du produit
		listePersVersion = new List(shell,SWT.SINGLE|SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
		chargementElementList(shell, version);
		
		   // on crée un composite pour mettre les boutons
		Composite compositeBoutons = new Composite(shell, SWT.NONE);
		   // on met un layout dans ce composite
		GridLayout gridLayoutComposite = new GridLayout();
		compositeBoutons.setLayout(gridLayoutComposite);
		
		
		   // bouton d'association d'un responsable à un produit
		Button boutonResponsableProduit = new Button(compositeBoutons,SWT.PUSH);
		boutonResponsableProduit.setText(message.getString("PageAttributionProduitIHM.boutonResponsableProduit.texte"));
		boutonResponsableProduit.setToolTipText(message.getString("PageAttributionProduitIHM.boutonResponsableProduit.infobulle"));
		
		   // contrôle de l'évènement du bouton ajouter un responsable au produit
		boutonResponsableProduit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
			  
				// si pas exactement une personne sélectionnée
				if (listePersProjet.getSelectionCount() != 1){
					boiteMessage(shell, SWT.ICON_ERROR, message.getString("PageAttributionProduitIHM.TropOuPasAssezDePersonneSelectionne.texte"), message.getString("PageAttributionProduitIHM.TropOuPasAssezDePersonneSelectionne.titre"));
				}
				else {
					// une personne sélectionnée
					
					String nomPersonne = listePersProjet.getSelection()[0];
					   // on récupère l'objet de la personne correspondante pour avoir ses info
					EugesPersonne eugesPersonne = EugesElements.getPersonneDansListePersonnes(nomPersonne);
					
					   // on met la variable changerResponsable à faux de manière à tester => si =vrai, l'élément n'a pas de responsable ou il doit être changé 
					int changerResponsable = SWT.NO;
					
					   // si la version a déjà un responsable, on demande s'il faut changer
					if (version.get_responsable() != null){
						   // si le responsable à mettre est le même que celui qui y est déjà
						if (version.get_responsable().toString().equals(eugesPersonne.toString())){
							boiteMessage(shell, SWT.ICON_WARNING, message.getString("PageAttributionProduitIHM.memeResponsable.texte"), message.getString("PageAttributionProduitIHM.memeResponsable.titre"));
						}
						else {   // le responsable est différent de l'actuel
							changerResponsable = boiteMessage(shell, SWT.YES | SWT.NO | SWT.ICON_QUESTION, message.getString("PageAttributionProduitIHM.ResponsableDejaExistant.texte"), message.getString("PageAttributionProduitIHM.ResponsableDejaExistant.titre"));
						}
					}
					else {
						   // il n'y a pas encore de responsable
						   // on doit changer le responsable, mais on teste si il est acteur quand même
						changerResponsable = SWT.YES;
					}
					
					   // si on doit changer le responsable, il faut encore tester si il est pas acteur
					if (changerResponsable == SWT.YES){
						   // si on a au moins un acteur, il faut tester si la personne n'est pas acteur
						if (version.getModifieurCount() != 0){
							   // on cherche dans la liste des acteurs si elle y est
							if (version.get_acteurs().contains(eugesPersonne)) {
								version.get_acteurs().remove(eugesPersonne);
							}
						}
					
						// on met la personne comme responsable du produit
						version.set_responsable(eugesPersonne);
						
						// on rafraichit l'affichage de l'arbre
						chargementElementList(shell, version);
					}
				}
			}	
		});

		// bouton d'association d'un acteur à un produit
		Button boutonActeurProduit = new Button(compositeBoutons,SWT.PUSH);
		boutonActeurProduit.setText(message.getString("PageAttributionProduitIHM.boutonActeurProduit.texte"));
		boutonActeurProduit.setToolTipText(message.getString("PageAttributionProduitIHM.boutonActeurProduit.infobulle"));
		
		// contrôle de l'évènement du bouton ajouter un acteur au produit
		boutonActeurProduit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
			
				// si pas au moins une personne sélectionnée
				if (listePersProjet.getSelectionCount() == 0){
					boiteMessage(shell, SWT.ICON_ERROR, message.getString("PageAttributionProduitIHM.PasDePersonneSelectionne.texte"), message.getString("PageAttributionProduitIHM.PasDePersonneSelectionne.titre"));
				}
				else {
					// au moins une personne sélectionnée
					// tableau des personnes sélectionnées
					String [] tabpersonneSelectionnee = listePersProjet.getSelection();
					  // on parcours le tableau
					for (int i=0; i<tabpersonneSelectionnee.length; i++){
						   // test si la personne n'est pas déjà acteur du produit
						if (!testPersonneDejaActeur(version, tabpersonneSelectionnee[i])){
							   // la personne n'est pas deja acteur, on peut l'inserer
							   // on recupere l'objet EugesPersonne correspondant à la personne sélectionnée
							EugesPersonne personne = null;
							personne = EugesElements.getPersonneDansListePersonnes(tabpersonneSelectionnee[i]);
							
							   // on l'insére dans la liste des acteurs de la version
							version.ajouterModifieur(personne);
						}
						   // on rafraichit l'affichage de l'arbre
						chargementElementList(shell, version);
					}
					  
					
				}
			}
		});

		GridData dataComposite = new GridData(SWT.CENTER);
		boutonResponsableProduit.setLayoutData(dataComposite);
		
		dataComposite = new GridData(SWT.CENTER);
		boutonActeurProduit.setLayoutData(dataComposite);
		
		compositeBoutons.pack();
		
		   // liste des personnes associées au projet
		listePersProjet = new List(shell,SWT.MULTI|SWT.H_SCROLL|SWT.BORDER); 
		String[] listeEugesPersonnes = EugesElements.getTableauListePersonne();
		   // on charge dans la liste des personnes, celles qui travaillent sur l'activité
		chargerListePersonne(version);
		;if (listeEugesPersonnes != null){
			listePersProjet.setItems(listeEugesPersonnes);
		}
		 
		shell.pack();
		 	// placement des éléments
		GridData data = new GridData();
		data.horizontalSpan = 3;
		titre.setLayoutData(data);
		 
		data = new GridData();						
		titreListePersonneAssociee.setLayoutData(data);
		 
		data = new GridData(GridData.CENTER);
		boutonNouveau.setLayoutData(data);
	
		data = new GridData();
		titreListePersonne.setLayoutData(data); 
		 
		data = new GridData(GridData.FILL_BOTH);
		listePersVersion.setLayoutData(data); 
		
		data = new GridData(SWT.CENTER);
		compositeBoutons.setLayoutData(data);
		
		data = new GridData(GridData.FILL_BOTH);
		listePersProjet.setLayoutData(data);
		
		
		shell.pack();
		
		   //ouverture de la page de demarrage
		shell.open();
		
		// Boucle d'évènements
		while (!shell.isDisposed()){
			if (!myDisplay.readAndDispatch())
				myDisplay.sleep();
		}
	}
	
	private int boiteMessage(Shell sh, int typeMessageBox, String message, String titre){
		MessageBox msgErreur = new MessageBox(sh,typeMessageBox);
		msgErreur.setMessage(message);
		msgErreur.setText(titre);
		int reponse = msgErreur.open();
		
		return reponse;
	}
	
	public void chargementElementList(Shell shell, EugesVersion version){
		   // on vide l'arbre
		listePersVersion.removeAll();
			
		if (version.get_responsable() != null){
			   // on écrit le responsable
			listePersVersion.add(version.get_responsable().toString() + " (responsable)");
		}				
		
		   // on parcourt la liste des acteurs
		if (version.get_acteurs() != null){
			
			for (Iterator iterPersonne = version.get_acteurs().iterator(); iterPersonne.hasNext();	) {
				EugesPersonne eugesPersonne = (EugesPersonne) iterPersonne.next();
				if (eugesPersonne != null){	
					   // on écrit les acteurs
					listePersVersion.add(eugesPersonne.toString());
				}
			}
		}
	}
	
	
	/**
	 * test si la personne n'est pas déjà acteur ou responsable de cette version
	 * @param version : la version du produit dans laquelle peut être la personne
	 * @param personne : la personne à tester si déja acteur ou responsable
	 * @return : vrai si la personne est déjà acteur ou responsable
	 */
	private boolean testPersonneDejaActeur(EugesVersion version, String personne){
		boolean dejaActeur = false;
		
		   // si il n'y a pas d'acteur ni de responsable => faux
		if (version.getModifieurCount() != 0 || version.get_responsable() != null){
			   // on récupère la chaine qui identifie le responsable si il y en a un, sinon on a une chaîne vide
			String resp = "";
			if (version.get_responsable() != null){
				resp = version.get_responsable().toString();
			}
			   // si la personne est responsable, dejaActeur passe à vrai
			dejaActeur = (personne.equals(resp));
			
			   // si la personne n'est pas responsable et que la liste des acteur a une longueur>0
			if (!dejaActeur && version.getModifieurCount()!=0) {
					// on recupère la liste des acteurs de la version sélectionnée
				Vector vectorActeurs = version.get_acteurs();
				
				Iterator iter = vectorActeurs.iterator();
				// on parcours la liste des acteurs tant qu'il y en a et tant que la personne n'est pas déjà présente
				while (iter.hasNext() && !dejaActeur){
					EugesPersonne acteur = (EugesPersonne) iter.next();
					dejaActeur = (personne.equals(acteur.toString()));
				}
			}
		}
		return dejaActeur;
	}
	
	private void chargerListePersonne(EugesVersion version){
		Vector listePersonnesPossibles = new Vector();
		   // on parcours la liste des activités pour savoir les personnes possibles sur cette version de produit
			if (EugesElements.listeActivites != null){
				for (Iterator itActivite = EugesElements.listeActivites.iterator(); itActivite.hasNext();) {
					EugesActivite activite = (EugesActivite) itActivite.next();
					
					int nbActRealisées = activite.getActRealiseCount();
					//on parcours la liste des activités réalisées pour savoir les personnes possibles sur cette version de produit
					for (int i=0; i<nbActRealisées; i++) {
						EugesActRealise actRealise = activite.getActRealise(i);
						   // si la liste des versions de cette activité contient la version en cours
						if (actRealise.get_produitsOut() != null) {
							if (actRealise.get_produitsOut().contains(version)){
								   // rajouter les personnes de l'activité réalisée dans la liste si elles n'y sont pas déjà
								if (actRealise.get_personnes() != null) {
									for (Iterator itPersonnes = actRealise.get_personnes().iterator();itPersonnes.hasNext(); ) {
										   // on récupère les personnes
										EugesPersonne personne = (EugesPersonne) itPersonnes.next();
										   // si la personne n'est pas déjà citée, on l'insère
										if (!listePersonnesPossibles.contains(personne)){
											listePersonnesPossibles.add(personne);
										}		
									}
								}
							}
						}
					}
				}
			}
	}
	
}