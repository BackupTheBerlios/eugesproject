/*
 * Created on 22 d�c. 2003
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
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import utilitaires.GestionImage;

import configuration.Config;
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesProduit;
import donnees.eugesSpem.EugesVersion;
import application.*;

/**
 * @author Nicolas
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */


public class PageAttributionProduitIHM {
		
		//	gestion multi-langue
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	private Tree arbreProduits;
	private List listePers;
	private final Shell shell;
	
	public PageAttributionProduitIHM(final Display display) {
			
		Display myDisplay = display;
		shell = new Shell(display, SWT.CLOSE|SWT.APPLICATION_MODAL|SWT.RESIZE);
		shell.setText(message.getString("PageAttributionProduitsIHM.titreHautDePage"));
		shell.setImage(GestionImage._euges);
		
			// Layout pour placer les objets
		 GridLayout gridLayout = new GridLayout();
		 gridLayout.numColumns = 3;
		 shell.setLayout(gridLayout);
		 
		 	// El�ments de la page
		 	
		 
		 	// Cr�ation de la page
		 	
		// titre de la page
		Font font = new Font(shell.getDisplay(), "Arial", 15, 15);
		Label titre = new Label(shell, SWT.NONE);
		titre.setFont(font);
		titre.setText(message.getString("PageAttributionProduitsIHM.titrePage"));
		 
			// titre de l'arbre des produits
		Label titreArbreProduit = new Label(shell,SWT.WRAP);
		titreArbreProduit.setText(message.getString("PageAttributionProduitsIHM.titreArbreProduit"));
		
			// bouton pour laisser une place vide
		Button boutonNouveau = new Button(shell,SWT.PUSH);
		boutonNouveau.setVisible(false);
		
			// titre de la liste des personnes
		Label titreListePersonne = new Label(shell,SWT.WRAP);
		titreListePersonne.setText(message.getString("titreListePersonne"));

		 	// arbre des produits
		arbreProduits = new Tree(shell,SWT.SINGLE|SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
		chargementElementArbre(shell);
		
		   // on cr�e un composite pour mettre les boutons
		Composite compositeBoutons = new Composite(shell, SWT.NONE);
		   // on met un layout dans ce composite
		GridLayout gridLayoutComposite = new GridLayout();
		compositeBoutons.setLayout(gridLayoutComposite);
		
			// bouton pour ajouter un nouveau produit
		Button boutonNouveauProduit = new Button(compositeBoutons,SWT.PUSH);
		boutonNouveauProduit.setText("< +");
		boutonNouveauProduit.setToolTipText(message.getString("PageAttributionProduitIHM.boutonNouveauProduit.infobulle"));
		
		// contr�le de l'�v�nement du bouton ajouter un nouveau produit
		boutonNouveauProduit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
					PageNouveauProduitIHM pageNouveauProduit = new PageNouveauProduitIHM(shell);
					chargementElementArbre(shell);
				}
			
		});
		
		   // bouton d'association d'un responsable � un produit
		Button boutonResponsableProduit = new Button(compositeBoutons,SWT.PUSH);
		boutonResponsableProduit.setText(message.getString("PageAttributionProduitIHM.boutonResponsableProduit.texte"));
		boutonResponsableProduit.setToolTipText(message.getString("PageAttributionProduitIHM.boutonResponsableProduit.infobulle"));
		
		   // contr�le de l'�v�nement du bouton ajouter un responsable au produit
		boutonResponsableProduit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				   // si les s�lections dans l'arbre sont correctes 
				if (testSelectionsPourAssociationCorrects()){
					// une version de produit est s�lectionn�
					// si pas exactement une personne s�lectionn�e
					if (listePers.getSelectionCount() != 1){
						boiteMessage(shell, SWT.ICON_ERROR, message.getString("PageAttributionProduitIHM.TropOuPasAssezDePersonneSelectionne.texte"), message.getString("PageAttributionProduitIHM.TropOuPasAssezDePersonneSelectionne.titre"));
					}
					else {
						// une personne s�lectionn�e
						   // on r�cup�re la version de produit s�lectionn�
						EugesVersion produitVersion = ((EugesVersion)(arbreProduits.getSelection()[0].getData()));
						
						   // on r�cup�re la produit s�lectionn�
						String nomPersonne = listePers.getSelection()[0];
						   // on r�cup�re l'objet de la personne correspondante pour avoir ses info
						EugesPersonne eugesPersonne = EugesElements.getPersonneDansListePersonnes(nomPersonne);
						
						   // on met la variable changerResponsable � faux de mani�re � tester => si =vrai, l'�l�ment n'a pas de responsable ou il doit �tre chang� 
						int changerResponsable = SWT.NO;
						
						   // si la version a d�j� un responsable, on demande s'il faut changer
						if (produitVersion.get_responsable() != null){
							   // si le responsable � mettre est le m�me que celui qui y est d�j�
							if (produitVersion.get_responsable().toString().equals(eugesPersonne.toString())){
								boiteMessage(shell, SWT.ICON_WARNING, message.getString("PageAttributionProduitIHM.memeResponsable.texte"), message.getString("PageAttributionProduitIHM.memeResponsable.titre"));
							}
							else {   // le responsable est diff�rent de l'actuel
								changerResponsable = boiteMessage(shell, SWT.YES | SWT.NO | SWT.ICON_QUESTION, message.getString("PageAttributionProduitIHM.ResponsableDejaExistant.texte"), message.getString("PageAttributionProduitIHM.ResponsableDejaExistant.titre"));
							}
						}
						else {
							   // il n'y a pas encore de responsable
							   // on doit changer le responsable, mais on teste si il est acteur quand m�me
							changerResponsable = SWT.YES;
						}
						
						   // si on doit changer le responsable, il faut encore tester si il est pas acteur
						if (changerResponsable == SWT.YES){
							   // si on a au moins un acteur, il faut tester si la personne n'est pas acteur
							if (produitVersion.getModifieurCount() != 0){
								   // on cherche dans la liste des acteurs si elle y est
								if (produitVersion.get_acteurs().contains(eugesPersonne)) {
									produitVersion.get_acteurs().remove(eugesPersonne);
								}
								
							}
						
							// on met la personne comme responsable du produit
							produitVersion.set_responsable(eugesPersonne);
							
							// on rafraichit l'affichage de l'arbre
							chargementElementArbre(shell);
						}
					}
				}	
			}
		});

		// bouton d'association d'un acteur � un produit
		Button boutonActeurProduit = new Button(compositeBoutons,SWT.PUSH);
		boutonActeurProduit.setText(message.getString("PageAttributionProduitIHM.boutonActeurProduit.texte"));
		boutonActeurProduit.setToolTipText(message.getString("PageAttributionProduitIHM.boutonActeurProduit.infobulle"));
		
		// contr�le de l'�v�nement du bouton ajouter un acteur au produit
		boutonActeurProduit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				// si les s�lections dans l'arbre sont correctes 
				if (testSelectionsPourAssociationCorrects()){
					// une version de produit est s�lectionn�
					// si pas au moins une personne s�lectionn�e
					if (listePers.getSelectionCount() == 0){
						boiteMessage(shell, SWT.ICON_ERROR, message.getString("PageAttributionProduitIHM.PasDePersonneSelectionne.texte"), message.getString("PageAttributionProduitIHM.PasDePersonneSelectionne.titre"));
					}
					else {
						// au moins une personne s�lectionn�e
						// on r�cup�re la version de produit s�lectionn�
						EugesVersion version = ((EugesVersion)(arbreProduits.getSelection()[0].getData()));
						
						// tableau des personnes s�lectionn�es
						String [] tabpersonneSelectionnee = listePers.getSelection();
						  // on parcours le tableau
						for (int i=0; i<tabpersonneSelectionnee.length; i++){
							   // test si la personne n'est pas d�j� acteur du produit
							if (!testPersonneDejaActeur(version, tabpersonneSelectionnee[i])){
								   // la personne n'est pas deja acteur, on peut l'inserer
								   // on recupere l'objet EugesPersonne correspondant � la personne s�lectionn�e
								EugesPersonne personne = null;
								personne = EugesElements.getPersonneDansListePersonnes(tabpersonneSelectionnee[i]);
								
								   // on l'ins�re dans la liste des acteurs de la version
								version.ajouterModifieur(personne);
							}
							   // on rafraichit l'affichage de l'arbre
							chargementElementArbre(shell);
						}
						  
						
					}
				}
			}
		});

		GridData dataComposite = new GridData(SWT.CENTER);
		boutonNouveauProduit.setLayoutData(dataComposite);
		
		dataComposite = new GridData(SWT.CENTER);
		boutonResponsableProduit.setLayoutData(dataComposite);
		
		dataComposite = new GridData(SWT.CENTER);
		boutonActeurProduit.setLayoutData(dataComposite);
		
		compositeBoutons.pack();
		
		   // liste des personnes associ�es au projet
		listePers = new List(shell,SWT.MULTI|SWT.H_SCROLL|SWT.BORDER); 
		String[] listeEugesPersonnes = EugesElements.getTableauListePersonne();
		if (listeEugesPersonnes != null){
			listePers.setItems(listeEugesPersonnes);
		}
		 
		shell.pack();
		 	// placement des �l�ments
		GridData data = new GridData();
		data.horizontalSpan = 3;
		titre.setLayoutData(data);
		 
		data = new GridData();						
		titreArbreProduit.setLayoutData(data);
		 
		data = new GridData(GridData.CENTER);
		boutonNouveau.setLayoutData(data);
	
		data = new GridData();
		titreListePersonne.setLayoutData(data); 
		 
		data = new GridData(GridData.FILL_BOTH);
		arbreProduits.setLayoutData(data); 
		
		data = new GridData(SWT.CENTER);
		compositeBoutons.setLayoutData(data);
		
		data = new GridData(GridData.FILL_BOTH);
		listePers.setLayoutData(data);
		
		
		shell.pack();
		
		// ouvrir la fen�tre au centre de l'�cran
	   Monitor primary = display.getPrimaryMonitor ();
	   Rectangle bounds = primary.getBounds ();
	   Rectangle rect = shell.getBounds ();
	   int x = bounds.x + (bounds.width - rect.width) / 2;
	   int y = bounds.y + (bounds.height - rect.height) / 2;
	   shell.setLocation (x, y);
		
		   //ouverture de la page de demarrage
		shell.open();
		
		// Boucle d'�v�nements
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
	
	public void chargementElementArbre(Shell shell){
		   // on vide l'arbre
		arbreProduits.removeAll();
		
		  // on parcours tous les produits
		for (Iterator iter = EugesElements.listeProduits.iterator(); iter.hasNext();) {
			EugesProduit eugesProduit = (EugesProduit) iter.next();
			
			   // on �crit le produit
			TreeItem itemProduit = new TreeItem(arbreProduits, SWT.NONE);
			itemProduit.setExpanded(true);
			   // on met une image � la branche de l'arbre
			itemProduit.setImage(GestionImage._produit);
			   // on met le texte
			itemProduit.setText(eugesProduit.toString());
			
			   // on parcours les versions du produit
			for (Iterator iterator = eugesProduit.get_versions().iterator();	iterator.hasNext();) {
				EugesVersion eugesVersion = (EugesVersion) iterator.next();
				
				   // on �crit la version
				TreeItem itemVersion = new TreeItem(itemProduit, SWT.NONE);
				itemVersion.setExpanded(true);
				itemVersion.setText(eugesVersion.toString());
				   // on met la donn�e
				itemVersion.setData(eugesVersion);
			
				if (eugesVersion.get_responsable() != null){
					   // on �crit le responsable
					TreeItem itemResponsable = new TreeItem(itemVersion, SWT.NONE);
					itemResponsable.setExpanded(true);
					   // on met une image � la branche de l'arbre
					itemResponsable.setImage(GestionImage._actor);
					itemResponsable.setText(eugesVersion.get_responsable().toString() + " (responsable)");
				}				
				
				   // on parcourt la liste des acteurs
				if (eugesVersion.get_acteurs() != null){
					for (Iterator iterPersonne = eugesVersion.get_acteurs().iterator(); iterPersonne.hasNext();	) {
						EugesPersonne eugesPersonne = (EugesPersonne) iterPersonne.next();
						if (eugesPersonne != null){	
							   // on �crit les acteurs
							TreeItem itemPersonne = new TreeItem(itemVersion, SWT.NONE);
							itemPersonne.setExpanded(true);
		 					   // on met une image � la branche de l'arbre
							itemPersonne.setImage(GestionImage._actor);
							itemPersonne.setText(eugesPersonne.toString());
						}
					}
			}
			}
		}
	}
	
	   // fonction pour tester si les elements selectionn�s correspondent � ce qui est attendu
	private boolean testSelectionsPourAssociationCorrects() {
		boolean selectionCorrect = true;
		// si pas d'�l�ment s�lectionn� dans,l'arbre
		if (arbreProduits.getSelectionCount() != 1){
			boiteMessage(shell, SWT.ICON_ERROR, message.getString("PageAttributionProduitIHM.PasDElementSelectionne.texte"), message.getString("PageAttributionProduitIHM.PasDElementSelectionne.titre"));
			selectionCorrect = false;
		}
		    // ce n'est pas une version de produit qui est s�lectionn�
			// test si parent null ou parent non null et parent des parent null car version est au 2�me niveau de l'arbre 
		else if ((arbreProduits.getSelection()[0].getParentItem() == null) ||
					(arbreProduits.getSelection()[0].getParentItem() != null 
							&& arbreProduits.getSelection()[0].getParentItem().getParentItem() != null)){ 
				boiteMessage(shell, SWT.ICON_ERROR, message.getString("PageAttributionProduitIHM.SelectionElementPasVersion.texte"), message.getString("PageAttributionProduitIHM.SelectionElementPasVersion.titre"));
				selectionCorrect = false;
		}
		
		
		return selectionCorrect;
	}
	
	/**
	 * test si la personne n'est pas d�j� acteur ou responsable de cette version
	 * @param version : la version du produit dans laquelle peut �tre la personne
	 * @param personne : la personne � tester si d�ja acteur ou responsable
	 * @return : vrai si la personne est d�j� acteur ou responsable
	 */
	private boolean testPersonneDejaActeur(EugesVersion version, String personne){
		boolean dejaActeur = false;
		
		   // si il n'y a pas d'acteur ni de responsable => faux
		if (version.getModifieurCount() != 0 || version.get_responsable() != null){
			   // on r�cup�re la chaine qui identifie le responsable si il y en a un, sinon on a une cha�ne vide
			String resp = "";
			if (version.get_responsable() != null){
				resp = version.get_responsable().toString();
			}
			   // si la personne est responsable, dejaActeur passe � vrai
			dejaActeur = (personne.equals(resp));
			
			   // si la personne n'est pas responsable et que la liste des acteur a une longueur>0
			if (!dejaActeur && version.getModifieurCount()!=0) {
					// on recup�re la liste des acteurs de la version s�lectionn�e
				Vector vectorActeurs = version.get_acteurs();
				
				Iterator iter = vectorActeurs.iterator();
				// on parcours la liste des acteurs tant qu'il y en a et tant que la personne n'est pas d�j� pr�sente
				while (iter.hasNext() && !dejaActeur){
					EugesPersonne acteur = (EugesPersonne) iter.next();
					dejaActeur = (personne.equals(acteur.toString()));
				}
			}
		}
		return dejaActeur;
	}
	
	
}