/*
 * Created on 13 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package application;

import ihm.ArbrePrincipalIHM;
import ihm.vues.planIt.PlanItIHM;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TreeItem;

import utilitaires.GestionImage;
import configuration.Config;
import donnees.Iteration;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesProduit;
import donnees.eugesSpem.EugesRole;
import donnees.eugesSpem.EugesVersion;
import donnees.spem.process.structure.WorkProduct;


/**
 * @author Nicolas Elbeze
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TriArbre {

	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	private int _format;
	
	public TriArbre() {
		_format = 0;
	}
	

	public void actualiser() {
		switch (_format) {
			case 0 :
				trier();
				break;
				
			case 1 :
				trierIterations();
				break;
				
			case 2 :
				trierActivites();
				break;
				
			case 3 :
				trierRoles();
				break;

			case 4 :
				trierPersonnes();
				break;
				
			default :
				break;
		}
	}
	
	
	public void trier() {
		ArbrePrincipalIHM._tree.removeAll();
		_format = 0;
		
		TreeItem itemProjet = new TreeItem(ArbrePrincipalIHM._tree, SWT.NONE);
		TreeItem itemIteration = new TreeItem(itemProjet, SWT.NONE);
		TreeItem itemActivite = new TreeItem(itemProjet, SWT.NONE);
		TreeItem itemProduit = new TreeItem(itemProjet, SWT.NONE);
		TreeItem itemRole = new TreeItem(itemProjet, SWT.NONE);
		TreeItem itemPersonne = new TreeItem(itemProjet, SWT.NONE);
		
		
		// Principaux noeuds
		if (EugesElements._projet == null) {
			itemProjet.setText(message.getString("arbre.projet"));
			itemProjet.setData("projet");
		}
		else {
			itemProjet.setText(EugesElements._projet.get_nomProjet());
			itemProjet.setData(EugesElements._projet);
		}
		
		// Texte affiché sur le noeud
		itemIteration.setText(message.getString("arbre.iterations"));
		itemActivite.setText(message.getString("arbre.activites"));
		itemProduit.setText(message.getString("arbre.produits"));
		itemRole.setText(message.getString("arbre.roles"));
		itemPersonne.setText(message.getString("arbre.personnes"));

		// Texte utilisé pour la fénétre des propriétés
		itemIteration.setData("iterations");
		itemActivite.setData("activites");
		itemProduit.setData("produits");
		itemRole.setData("roles");
		itemPersonne.setData("personnes");
		
		// Les images
		itemIteration.setImage(GestionImage._it);
		itemActivite.setImage(GestionImage._activite);
		itemProduit.setImage(GestionImage._produit);
		itemRole.setImage(GestionImage._role);
		itemPersonne.setImage(GestionImage._actor);
		
		
		// Les iterations
		if (EugesElements._projet == null) {
			System.out.println("NULL");
		}
		else {			
			SortedSet vectIteration = EugesElements._projet.get_listeIteration();
			int nbIteration = vectIteration.size();
			TreeItem itemAuxIteration;
			Iterator it = vectIteration.iterator();
			Iteration auxIteration;
			while (it.hasNext()) {
				auxIteration = (Iteration) it.next();
				final int numIt = auxIteration.get_numIt();
				itemAuxIteration = new TreeItem(itemIteration, SWT.NONE);
				itemAuxIteration.setText(message.getString("arbre.iteration") + " " + numIt);
				itemAuxIteration.setData(auxIteration);
			}
		}
		itemIteration.setExpanded(true);
		
		
		// Les activites
		Vector vectActiv = EugesElements.listeActivites;
		int nbActiv = vectActiv.size();
		TreeItem itemAuxActivites; 
		for (int i=0; i<nbActiv; i++) {
			itemAuxActivites = new TreeItem(itemActivite, SWT.NONE);
			itemAuxActivites.setText(((EugesActivite) vectActiv.elementAt(i)).getName());
			itemAuxActivites.setData((EugesActivite) vectActiv.elementAt(i));
		}
		itemActivite.setExpanded(true);
		
		
		// Les produits
		Vector vectProduits = EugesElements.listeProduits;
		int nbProduits = vectProduits.size();
		TreeItem itemAuxProduit; 
		for (int i=0; i<nbProduits; i++) {
			itemAuxProduit = new TreeItem(itemProduit, SWT.NONE);
			itemAuxProduit.setText(((EugesProduit) vectProduits.elementAt(i)).getName());
			itemAuxProduit.setData((EugesProduit) vectProduits.elementAt(i));
		}
		itemProduit.setExpanded(true);
		
		
		// Les rôles
		Vector vectRôles = EugesElements.listeRoles;
		int nbRôles = vectRôles.size();
		TreeItem itemAuxRôles; 
		for (int i=0; i<nbRôles; i++) {
			itemAuxRôles = new TreeItem(itemRole, SWT.NONE);
			itemAuxRôles.setText(((EugesRole) vectRôles.get(i)).getName());
			itemAuxRôles.setData((EugesRole) vectRôles.get(i));
		}
		itemRole.setExpanded(true);
		
		
		// Les personnes
		Vector vectPersonnes = EugesElements.listePersonnes;
		int nbPersonnes = vectPersonnes.size();
		TreeItem itemAuxPersonnes;
		for (int i=0; i<nbPersonnes; i++) {
			itemAuxPersonnes = new TreeItem(itemPersonne, SWT.NONE);
			itemAuxPersonnes.setText(((EugesPersonne) vectPersonnes.get(i)).getNom());
			itemAuxPersonnes.setData((EugesPersonne) vectPersonnes.get(i));
		}
		itemPersonne.setExpanded(true);
		
		itemProjet.setExpanded(true);
	}

	
	
	public void trierIterations() {
		ArbrePrincipalIHM._tree.removeAll();
		_format = 1;
		
		// Noeud principale
		TreeItem itemProjet = new TreeItem(ArbrePrincipalIHM._tree, SWT.NONE);
		if (EugesElements._projet == null) {
			itemProjet.setText(message.getString("arbre.projet"));
			itemProjet.setData("projet");
		}
		else {
			itemProjet.setText(EugesElements._projet.get_nomProjet());
			itemProjet.setData(EugesElements._projet);
		}

		
		
		// Les iterations
		if (EugesElements._projet == null) {
			System.out.println("NULL");
		}
		else {
			TreeItem itemAuxIteration;
			Iteration auxIteration;
			for (Iterator it = EugesElements._projet.get_listeIteration().iterator(); it.hasNext();) {
				auxIteration = (Iteration) it.next();
				itemAuxIteration = new TreeItem(itemProjet, SWT.NONE);
				itemAuxIteration.setText(message.getString("arbre.iteration") + " " + auxIteration.get_numIt());
				itemAuxIteration.setImage(GestionImage._it);
				itemAuxIteration.setData(auxIteration);
				
				// Recuperation des Activites
				TreeItem itemAuxActivite; 
				EugesActRealise auxActRealise;
				for (int j=0; j<auxIteration.getActiviteCount(); j++) {
					auxActRealise = auxIteration.getActivite(j);
					itemAuxActivite = new TreeItem(itemAuxIteration, SWT.NONE);
					itemAuxActivite.setText(auxActRealise.get_activiteParent().getName());
					itemAuxActivite.setImage(GestionImage._activite);
					itemAuxActivite.setData(auxActRealise);
					
					// Noeuds Fixes
					TreeItem itemProduit = new TreeItem(itemAuxActivite, SWT.NONE);
					TreeItem itemProduitsIn = new TreeItem(itemProduit, SWT.NONE);
					TreeItem itemProduitsOut = new TreeItem(itemProduit, SWT.NONE);
					itemProduit.setText(message.getString("arbre.produits"));
					itemProduitsIn.setText(message.getString("arbre.produits.in"));
					itemProduitsOut.setText(message.getString("arbre.produits.out"));
					itemProduit.setImage(GestionImage._produit);
					itemProduit.setData("produits");
					itemProduitsIn.setData("produitsIn");
					itemProduitsOut.setData("produitsOut");
					
					
					// Recuperation des Produits en entrees de l'activite
					TreeItem itemAuxProduitIn;
					WorkProduct auxProduitIn;
					for (int k=0; k<auxActRealise.get_activiteParent().getInputCount(); k++) {
						auxProduitIn = auxActRealise.get_activiteParent().getInput(k);
						itemAuxProduitIn = new TreeItem(itemProduitsIn, SWT.NONE);
						itemAuxProduitIn.setText(auxProduitIn.getName());
						itemAuxProduitIn.setData(auxProduitIn);
					}
					itemProduitsIn.setExpanded(true);
					
					
					// Recuperation des Produits en sorties de l'activite
					TreeItem itemAuxProduitOut;
					WorkProduct auxProduitOut;
					for (int k=0; k<auxActRealise.get_activiteParent().getOutputCount(); k++) {
						auxProduitOut = auxActRealise.get_activiteParent().getOutput(k);
						itemAuxProduitOut = new TreeItem(itemProduitsOut, SWT.NONE);
						itemAuxProduitOut.setText(auxProduitOut.getName());
						itemAuxProduitOut.setData(auxProduitOut);
					}
					itemProduitsOut.setExpanded(true);
					itemProduit.setExpanded(true);
					
					// Noeud fixe 'Personne'
					TreeItem itemPersonne = new TreeItem(itemAuxActivite, SWT.NONE);
					itemPersonne.setText(message.getString("arbre.personnes"));
					itemPersonne.setImage(GestionImage._actor);
					itemPersonne.setData("personnes");
					
					
					// Recuperation des Personnes
					TreeItem itemAuxPersonne;
					EugesPersonne auxPersonne;
					for (int l=0; l<auxActRealise.getPersonneCount(); l++) {
						auxPersonne = auxActRealise.getPersonne(l);
						itemAuxPersonne = new TreeItem(itemPersonne, SWT.NONE);
						itemAuxPersonne.setText(auxPersonne.getNom());
						itemAuxPersonne.setData(auxPersonne);
					}
					itemPersonne.setExpanded(true);
					itemAuxActivite.setExpanded(true);
				}
				itemAuxIteration.setExpanded(true);	
			}	
		}
		itemProjet.setExpanded(true);
	}

	
	
	public void trierActivites() {
		ArbrePrincipalIHM._tree.removeAll();
		_format = 2;
		
		
		// Noeud principale
		TreeItem itemProjet = new TreeItem(ArbrePrincipalIHM._tree, SWT.NONE);
		if (EugesElements._projet == null) {
			itemProjet.setText(message.getString("arbre.projet"));
			itemProjet.setData("projet");
		}
		else {
			itemProjet.setText(EugesElements._projet.get_nomProjet());
			itemProjet.setData(EugesElements._projet);
		}
		
		
		// Les Activites
		TreeItem itemAuxActivite;
		EugesActivite auxActivite;
		for (int i=0; i<EugesElements.listeActivites.size(); i++) {
			auxActivite = (EugesActivite) EugesElements.listeActivites.elementAt(i);
			itemAuxActivite = new TreeItem(itemProjet, SWT.NONE);
			itemAuxActivite.setText(auxActivite.getName());
			itemAuxActivite.setImage(GestionImage._activite);
			itemAuxActivite.setData(auxActivite);
			
			// Noeud fixe 'Role'
			TreeItem itemRoles = new TreeItem(itemAuxActivite, SWT.NONE);
			itemRoles.setText(message.getString("arbre.roles"));
			itemRoles.setImage(GestionImage._role);
			itemRoles.setData("roles");
			
			// Récupération des rôles pour l'activité
			TreeItem itemAuxRole;
			EugesRole auxRole;
			for (int j=0; j<auxActivite.getRoleCount(); j++) {
				auxRole = auxActivite.getRole(j);
				itemAuxRole = new TreeItem(itemRoles, SWT.NONE);
				itemAuxRole.setText(auxRole.getName());
				itemAuxRole.setData(auxRole);
			}
			itemRoles.setExpanded(true);
			
			
			// Noeuds fixes 'Produits', 'Produits In' et 'Produits Out'
			TreeItem itemProduits = new TreeItem(itemAuxActivite, SWT.NONE);
			TreeItem itemProduitsIn = new TreeItem(itemProduits, SWT.NONE);
			TreeItem itemProduitsOut = new TreeItem(itemProduits, SWT.NONE);
			
			itemProduits.setText(message.getString("arbre.produits"));
			itemProduitsIn.setText(message.getString("arbre.produits.in"));
			itemProduitsOut.setText(message.getString("arbre.produits.out"));
			itemProduits.setData("produits");
			itemProduitsIn.setData("produitsIn");
			itemProduitsOut.setData("produitsOut");
			
			itemProduits.setImage(GestionImage._produit);
			itemProduitsIn.setImage(GestionImage._produit);
			itemProduitsOut.setImage(GestionImage._produit);
			
			
			// Recuperation des produits en entree de l'activite
			TreeItem itemAuxProduitsIn; 
			EugesProduit auxProduitsIn;
			for (int k=0; k<auxActivite.getProduitInCount(); k++) {
				auxProduitsIn = (EugesProduit) auxActivite.getProduitIn(k);
				itemAuxProduitsIn = new TreeItem(itemProduitsIn, SWT.NONE);
				itemAuxProduitsIn.setText(auxProduitsIn.getName());
				itemAuxProduitsIn.setData(auxProduitsIn);
			}					
			itemProduitsIn.setExpanded(true);
			
			
			// Recuperation des produits en sortie de l'activite 
			TreeItem itemAuxProduitsOut; 
			EugesProduit auxProduitsOut;
			for (int k=0; k<auxActivite.getProduitOutCount(); k++) {
				auxProduitsOut = (EugesProduit) auxActivite.getProduitOut(k);
				itemAuxProduitsOut = new TreeItem(itemProduitsOut, SWT.NONE);
				itemAuxProduitsOut.setText(auxProduitsOut.getName());
				itemAuxProduitsOut.setData(auxProduitsOut);
			}
			itemProduitsOut.setExpanded(true);
			itemProduits.setExpanded(true);
			
			itemAuxActivite.setExpanded(true);
		}
		
		itemProjet.setExpanded(true);
	}
	
	
	public void trierRoles() {
		ArbrePrincipalIHM._tree.removeAll();
		_format = 3;
		
		
		// Noeud principale
		TreeItem itemProjet = new TreeItem(ArbrePrincipalIHM._tree, SWT.NONE);
		if (EugesElements._projet == null) {
			itemProjet.setText(message.getString("arbre.projet"));
			itemProjet.setData("projet");
		}
		else {
			itemProjet.setText(EugesElements._projet.get_nomProjet());
			itemProjet.setData(EugesElements._projet);
		}
		
		
		// Les Rôles
		TreeItem itemAuxRole;
		EugesRole auxRole;
		for (int i=0; i<EugesElements.listeRoles.size(); i++) {
			auxRole = (EugesRole) EugesElements.listeRoles.elementAt(i);
			itemAuxRole = new TreeItem(itemProjet, SWT.NONE);
			itemAuxRole.setText(auxRole.getName());
			itemAuxRole.setImage(GestionImage._role);
			itemAuxRole.setData(auxRole);
			
			// Noeud fixe 'Personne'
			TreeItem itemPersonnes = new TreeItem(itemAuxRole, SWT.NONE);
			itemPersonnes.setText(message.getString("arbre.personnes"));
			itemPersonnes.setImage(GestionImage._actor);
			itemPersonnes.setData("personnes");
			
			// Recuperation des Personnes associées au rôle
			EugesPersonne auxPersonne;
			TreeItem itemAuxPersonne;
			boolean appartient = false;
			Iterator it = EugesElements.listePersonnes.iterator();
			while(!appartient && it.hasNext()){
				auxPersonne = (EugesPersonne) it.next();
				
				Iteration auxIteration;
				for (Iterator it2 = EugesElements._projet.get_listeIteration().iterator(); it2.hasNext();) {
					auxIteration = (Iteration) it2.next();
					
					// Recuperation des Roles de l'itération
					EugesRole auxRoleAct;	
					Vector tempRoles = auxIteration.getAssociation(auxPersonne);
					for (Iterator iterator = tempRoles.iterator(); iterator.hasNext();) {
						auxRoleAct = (EugesRole) iterator.next();
						if (auxRoleAct.getName() == auxRole.getName()) {
							appartient = true;
							itemAuxPersonne = new TreeItem(itemPersonnes, SWT.NONE);
							itemAuxPersonne.setText(auxPersonne.getNom());
							itemAuxPersonne.setImage(GestionImage._actor);
							itemAuxPersonne.setData(auxPersonne);
						}
					}	
				}
				appartient = false;
			}
			itemPersonnes.setExpanded(true);
			
			

			// Noeud fixe 'Activités'
			TreeItem itemActivites = new TreeItem(itemAuxRole, SWT.NONE);
			itemActivites.setText(message.getString("arbre.activites"));
			itemActivites.setImage(GestionImage._activite);
			itemActivites.setData("activites");
			
			// Recuperation des Activites
			TreeItem itemAuxActivite;
			EugesActivite auxActivite;
			EugesActRealise auxActRealise;
			boolean appartient2 = false;
			Iterator it2 = EugesElements.listeActivites.iterator();
			while(!appartient2 && it2.hasNext()){
				auxActivite = (EugesActivite) it2.next();
				for (int n=0; n<auxActivite.getRoleCount(); n++) {
					if(auxActivite.getRole(n).getName().equals(auxRole.getName()) && !appartient2) {
						itemAuxActivite= new TreeItem(itemActivites, SWT.NONE);
						itemAuxActivite.setText(auxActivite.getName());
						itemAuxActivite.setImage(GestionImage._activite);
						itemAuxActivite.setData(auxActivite);
						appartient2 = true;
						
						// Ajout des produits en entrées et en sorties de cette activité
						// Noeuds fixes 'Produits', 'Produits In' et 'Produits Out'
						TreeItem itemProduitsIn = new TreeItem(itemAuxActivite, SWT.NONE);
						TreeItem itemProduitsOut = new TreeItem(itemAuxActivite, SWT.NONE);
						
						itemProduitsIn.setText(message.getString("arbre.produits.in"));
						itemProduitsOut.setText(message.getString("arbre.produits.out"));
						itemProduitsIn.setImage(GestionImage._produit);
						itemProduitsOut.setImage(GestionImage._produit);
						itemProduitsIn.setData("produitsIn");
						itemProduitsOut.setData("produitsOut");
						
						
						// Recuperation des produits en entree de l'activite
						TreeItem itemAuxProduitsIn; 
						EugesProduit auxProduitsIn;
						for (int k=0; k<auxActivite.getProduitInCount(); k++) {
							auxProduitsIn = (EugesProduit) auxActivite.getProduitIn(k);
							itemAuxProduitsIn = new TreeItem(itemProduitsIn, SWT.NONE);
							itemAuxProduitsIn.setText(auxProduitsIn.getName());
							itemAuxProduitsIn.setData(auxProduitsIn);
						}					
						itemProduitsIn.setExpanded(true);
						
						// Recuperation des produits en sortie de l'activite
						TreeItem itemAuxProduitsOut; 
						EugesProduit auxProduitsOut;
						for (int k=0; k<auxActivite.getProduitOutCount(); k++) {
							auxProduitsOut = (EugesProduit) auxActivite.getProduitOut(k);
							itemAuxProduitsOut = new TreeItem(itemProduitsOut, SWT.NONE);
							itemAuxProduitsOut.setText(auxProduitsOut.getName());
							itemAuxProduitsOut.setData(auxProduitsOut);
						}	
						itemProduitsOut.setExpanded(true);
						
						itemAuxActivite.setExpanded(true);
					}
				}
				appartient2 = false;
				
			}
			itemActivites.setExpanded(true);
			itemAuxRole.setExpanded(true);
			
		}
		itemProjet.setExpanded(true);
	}

	
	public void trierPersonnes() {
		ArbrePrincipalIHM._tree.removeAll();
		_format = 4;
		
		
		// Noeud principale
		TreeItem itemProjet = new TreeItem(ArbrePrincipalIHM._tree, SWT.NONE);
		if (EugesElements._projet == null) {
			itemProjet.setText(message.getString("arbre.projet"));
			itemProjet.setData("projet");
		}
		else {
			itemProjet.setText(EugesElements._projet.get_nomProjet());
			itemProjet.setData(EugesElements._projet);
		}
		
		
		// Les Personnes
		TreeItem itemAuxPersonne;
		EugesPersonne auxPersonne;
		for (int i=0; i<EugesElements.listePersonnes.size(); i++) {
			auxPersonne = (EugesPersonne) EugesElements.listePersonnes.elementAt(i);
			itemAuxPersonne = new TreeItem(itemProjet, SWT.NONE);
			itemAuxPersonne.setText(auxPersonne.getNom());
			itemAuxPersonne.setImage(GestionImage._actor);
			itemAuxPersonne.setData(auxPersonne);
			
			// un noeud fixe pour chaque itération
			TreeItem itemAuxIteration;
			Iteration auxIteration;
			for (Iterator it = EugesElements._projet.get_listeIteration().iterator(); it.hasNext();) {
				auxIteration = (Iteration) it.next();
				itemAuxIteration = new TreeItem(itemAuxPersonne, SWT.NONE);
				itemAuxIteration.setText(message.getString("arbre.iteration") + " " + auxIteration.get_numIt());
				itemAuxIteration.setImage(GestionImage._it);
				itemAuxIteration.setData(auxIteration);
				
				// Recuperation des Roles de l'itération
				TreeItem itemAuxRole;
				EugesRole auxRole;	
				Vector tempRoles = auxIteration.getAssociation(auxPersonne);
				for (Iterator iterator = tempRoles.iterator(); iterator.hasNext();) {
					auxRole = (EugesRole) iterator.next();
					itemAuxRole = new TreeItem(itemAuxIteration,SWT.NONE);
					itemAuxRole.setImage(GestionImage._role);
					itemAuxRole.setText(auxRole.getName());
					itemAuxRole.setData(auxRole);
				}
				itemAuxIteration.setExpanded(true);
				
			}
			
			
			// Noeud fixe 'Produit'
			TreeItem itemProduit = new TreeItem(itemAuxPersonne, SWT.NONE);
			itemProduit.setText(message.getString("arbre.produits"));
			itemProduit.setImage(GestionImage._produit);
			itemProduit.setData("produits");
			
			
			
			// Recuperation des produits
			TreeItem itemAuxProduits;
			EugesProduit auxProduit;
			EugesVersion auxVersion;
			boolean appartient = false;
			Iterator it = EugesElements.listeProduits.iterator();
			while(it.hasNext()){
				auxProduit = (EugesProduit) it.next();
				for (int n=0; n<auxProduit.getVersionCount(); n++) { 
					auxVersion = auxProduit.getVersionPrecise(n);
					
					// Si auxPersonne est responsable de la version
					if (auxVersion.get_responsable() != null && auxPersonne.getNom() == auxVersion.get_responsable().getNom()) {
						itemAuxProduits = new TreeItem(itemProduit, SWT.NONE);
						itemAuxProduits.setText(auxVersion.get_produitParent().getName() + " " + auxVersion.get_nom());
						itemAuxProduits.setData(auxVersion);
						appartient = true;
					}
					else {
						for (int m=0; m<auxVersion.get_acteurs().size(); m++) {
							if((EugesPersonne) auxVersion.get_acteurs().elementAt(m) == auxPersonne && !appartient) {
								itemAuxProduits = new TreeItem(itemProduit, SWT.NONE);
								itemAuxProduits.setText(auxVersion.get_produitParent().getName() + " " + auxVersion.get_nom());
								itemAuxProduits.setData(auxVersion);
								appartient = true;
							}
						}
					}		
					appartient = false;
				}
				itemProduit.setExpanded(true);
				
			}
			itemAuxPersonne.setExpanded(true);
			
		}
		itemProjet.setExpanded(true);
	}
}
