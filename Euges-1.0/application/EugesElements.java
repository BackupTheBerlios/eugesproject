/*
 * Created on 29 d�c. 2003
 *
 * Projet Euges
 * 
 */
package application;

import java.util.Iterator;
import java.util.Vector;

import utilitaires.MyDate;
import donnees.Iteration;
import donnees.Projet;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesProduit;
import donnees.eugesSpem.EugesRole;
import donnees.eugesSpem.EugesVersion;





/**
 * @author will
 *
 */
public class EugesElements {
	
	public static Projet _projet;
	
	//Listes globales utilis�es dans tout le projet Euges
	public static Vector listeActivites = new Vector(); // liste des activit�s
	public static Vector listeRoles = new Vector(); // liste des r�les 
	public static Vector listeProduits = new Vector(); // liste des produits
	public static Vector listePersonnes = new Vector(); // liste des personnes
	//booleen qui permet de savoir si le processus est enregistr�
	public static boolean processusEnregistre = true;
	
	
	/**
	 * constructeur nouveau projet en cours
	 * @param nomProjet
	 * @param dateDebut
	 * @param dateFin
	 * @param repDestination
	 * @param processus
	 * @param description
	 */
	public EugesElements(String nomProjet, MyDate dateDebut, MyDate dateFin,
			String repDestination, String processus,	String description){
		_projet=new Projet(nomProjet, dateDebut, dateFin, repDestination, processus, description);
	}
	public EugesElements(){
	}
	
	// fonction qui teste si la chaine de caract�res est vide
	public static boolean testTexteVide(String string){
		return string.equals("");
	}
	
	// fonction qui teste si l'objet en param�tres est dans la liste globale associ�e
	public static boolean contientElement(Object o){
		if (o instanceof EugesProduit){
			return listeProduits.contains(o);
		}
		else if (o instanceof EugesActivite){
			return listeActivites.contains(o);
		}
		else if (o instanceof EugesRole){
			return listeRoles.contains(o);
		}
		else {
			return listePersonnes.contains(o);
		}
	}
	
	// fonction qui ajoute l'objet en param�tres dans la liste globale associ�e
	public static boolean ajouterElement(Object o){
		if (o instanceof EugesProduit) {
			return listeProduits.add(o);
		}
		else if (o instanceof EugesActivite){
			return listeActivites.add(o);
		}
		else if (o instanceof EugesRole){
			return listeRoles.add(o);
		}
		else {
			return listePersonnes.add(o);
		}
	}
	
	// fonction qui supprime l'objet en param�tres de la liste globale associ�e
	public static boolean supprimerElement(Object o){
		if (o instanceof EugesProduit) {
			return listeProduits.remove(o);
		}
		else if (o instanceof EugesActivite){
			return listeActivites.remove(o);
		}
		else if (o instanceof EugesRole){
			return listeRoles.remove(o);
		}
		else {
			return listePersonnes.remove(o);
		}
	}
	// fonction qui teste si l'objet en param�tres est dans la liste globale associ�e
	public static boolean testObjetExistant(Object o){
		if (o instanceof EugesProduit){
			return listeProduits.contains(o);
		}
		else if (o instanceof EugesActivite){
			return listeActivites.contains(o);
		}
		else if (o instanceof EugesRole){
			return listeRoles.contains(o);
		}
		else {
			return listePersonnes.contains(o);
		}
	}	/*fonction d'ajout d'un produit dans la liste globale des produits*/
	public static void ajouteEugesProduit(String name){	
		EugesProduit produit = new EugesProduit(name);
		EugesElements.listeProduits.add(produit);
	}
	
	/*fonction de suppression d'un produit dans la liste globale des produits*/
	public static void supprimeEugesProduit(EugesProduit produit){		
		if (EugesElements.listeProduits.contains(produit)){
			EugesElements.listeProduits.remove(produit);
		}
	}
	
	public static void supprimeProduit(String name){
		for (Iterator iter = listeProduits.iterator(); iter.hasNext();) {
			EugesProduit e = (EugesProduit) iter.next();
			if (e.toString().equals(name)) {
				// on supprime les versions
				for (int i = 0; i < e.getVersionCount(); i++) {
					supprimerVersion(e.getVersionPrecise(i));
				}
				// on supprime les produits des activites
				for (Iterator it = listeActivites.iterator(); it.hasNext();) {
					EugesActivite elt = (EugesActivite) it.next();
					elt.supprimerProduitIn(e);
					elt.supprimerProduitOut(e);
				}				
				supprimerElement(e);
				break;
			}
		}
	}
	
	public static void supprimerVersion(EugesVersion v) {
		for (Iterator iter = listeActivites.iterator(); iter.hasNext();) {
			EugesActivite e = (EugesActivite) iter.next();
			for (int i = 0; i < e.getActRealiseCount(); i++) {
				e.getActRealise(i).supprimerProduitIn(v);
				e.getActRealise(i).supprimerProduitOut(v);
			}
		}
		v.get_produitParent().supprimerVersion(v);
	}
	
	/*fonction d'ajout d'un nouveau produit avec une version dans la liste globale des produits */
	public static void ajouteEugesProduit(String name, String version){
		
		// on cr�e le produit sans sa version
		EugesProduit produit = new EugesProduit(name);
		
		// vecteur avec la version du produit
//		Vector vers = new Vector();
		
		
		// on ajoute dans le vecteur la version du produit
//		vers.add(new EugesVersion(version, "en cours", 20, null, produit ));
		
		// on ajoute cette version dans le produit
//		produit.set_versions(vers);
		
		// on ajoute le produit dans la liste globale des produits
		EugesElements.listeProduits.add(produit);
	}
	
	// fonction qui renvoie un tableau de String des EugesProduit de la liste globale listeProduits
	public static String[] getTableauListeProduit(){
		// si la liste est non vide
		if (listeProduits.size() != 0){
			// tableau contenant les noms des produits
			String [] tabEugesProduit = new String[listeProduits.size()];
			
			// indice en cours dans le tableau
			int i = 0;
			
			for (Iterator iter = listeProduits.iterator(); iter.hasNext();) {
				tabEugesProduit[i] = ((EugesProduit) iter.next()).toString();
				i++;
			}
			
			return tabEugesProduit;
		}
		else {
			return null;
		}
	}
	
//	fonction qui renvoie un tableau de String des EugesProduit de la liste globale listeProduits
	public static String[] getTableauListePersonne(){
		// si la liste est non vide
		if (listePersonnes.size() != 0){
			// tableau contenant les noms des produits
			String [] tabEugesPersonne = new String[listePersonnes.size()];
			
			// indice en cours dans le tableau
			int i = 0;
			
			for (Iterator iter = listePersonnes.iterator(); iter.hasNext();) {
				tabEugesPersonne[i] = ((EugesPersonne) iter.next()).toString();
				i++;
			}
			
			return tabEugesPersonne;
		}
		else {
			return null;
		}
	}
	
	
	/**
	 * fonction qui renvoie l'objet EugesPersonne qui est pass� en param�tres
	 * @param personne : nom de la personne � chercher
	 * @return l'objet de la personne trouv� 
	 */
	public static EugesPersonne getPersonneDansListePersonnes(String personne){
		boolean trouve = false;
		// variable personne � retourner
		EugesPersonne eugesPersonne = null;
		// iterator pour le parcours de la liste
		Iterator iter = listePersonnes.iterator();
		
		while (iter.hasNext() && trouve == false){
			EugesPersonne pers = (EugesPersonne)iter.next();
			if (pers.toString().equals(personne)) {
				eugesPersonne = pers;
				trouve = true;
			}
		}
		
		return eugesPersonne;
	}
	
	
	/**
	 * fonction qui renvoie l'objet EugesProduit qui est pass� en param�tres
	 * @param produit : nom du produit � chercher
	 * @return l'objet du produit trouv� 
	 */
	public static EugesProduit getProduitDansListeProduits(String produit){
		boolean trouve = false;
		// variable produit � retourner
		EugesProduit eugesProduit = null;
		// iterator pour le parcours de la liste
		Iterator iter = listeProduits.iterator();
		
		while (iter.hasNext() && trouve == false){
			EugesProduit prod = (EugesProduit)iter.next();
			if (prod.toString().equals(produit)) {
				eugesProduit = prod;
				trouve = true;
			}
		}
		
		return eugesProduit;
		
	}
	
	public static EugesRole getRole (String role) {
		for (Iterator iter = EugesElements.listeRoles.iterator(); iter.hasNext();) {
			EugesRole e = (EugesRole) iter.next();
			if (e.toString().equals(role)) {
				return e;
			}
		}
		return null;
	}
	
	public static EugesActivite getActivite (String act) {
		for (Iterator iter = listeActivites.iterator(); iter.hasNext();) {
			EugesActivite e = (EugesActivite) iter.next();
			if (e.getName().equals(act)) {
				return e;
			}
		}
		return null;
	}
	public static int getActivitesCount() {
		return listeActivites.size();
	}
	/**
	 * Fonction qui renvoi la charge maximum des charges estimees des activit�s
	 * M�thode utilis� pour la cr�ation des graphes
	 * @return la charge max
	 */
	public static int getChargeEstimeeMax() {
		int max = 0;
		for (Iterator it = listeActivites.iterator(); it.hasNext();) {
			EugesActivite actTemp = (EugesActivite)it.next();
			for (int i = 0 ; i < actTemp.getActRealiseCount(); i++) {
				if (actTemp.getActRealise(i).get_chargeEstimee() > max)
					max = actTemp.getActRealise(i).get_chargeEstimee();
			}
		}
		return max;
	}
	
	
	/**
	 * Fonction qui renvoi la charge maximum des charges reelles des activit�s
	 * M�thode utilis� pour la cr�ation des graphes
	 * @return la charge max
	 */
	public static int getChargeReelleMax() {
		int max = 0;
		for (Iterator it = listeActivites.iterator(); it.hasNext();) {
			EugesActivite actTemp = (EugesActivite)it.next();
			for (int i = 0 ; i < actTemp.getActRealiseCount(); i++) {
				if (actTemp.getActRealise(i).get_chargeReelle() > max)
					max = actTemp.getActRealise(i).get_chargeReelle();
			}
		}
		return max;
	}
	
	public static int getActivitesRealiseesCount() {
		int nb=0;
		for (Iterator it = listeActivites.iterator(); it.hasNext();) {
			EugesActivite actTemp = (EugesActivite)it.next();
			nb += actTemp.getActRealiseCount();
		}
		return nb;
	}
	// Retourne tous les r�les de la personne : pers
	public static Vector getRolesPersonne(EugesPersonne pers) {
		Vector resultat = new Vector();
		for (Iterator iter = EugesElements.listeRoles.iterator(); iter.hasNext();) {
			EugesRole auxRole = (EugesRole) iter.next();
			Vector auxPersonnes = getPersonnesRole(auxRole);
			if (auxPersonnes.contains(pers)) {
				resultat.add(auxRole);
			}
		}
		return resultat;
	}
	
	
	// Retourne toutes les personnes associ�es au role : r
	public static Vector getPersonnesRole(EugesRole r) {
		Vector resultat = new Vector();
		boolean appartient = false;
		EugesPersonne auxPersonne;
		Iterator it = EugesElements.listePersonnes.iterator();
		
		while(!appartient && it.hasNext()){
			auxPersonne = (EugesPersonne) it.next();
			Iteration auxIteration;
			for (Iterator it2 = EugesElements._projet.get_listeIteration().iterator(); it2.hasNext();) {
				auxIteration = (Iteration) it2.next();
				
				// Recuperation des Roles de l'it�ration
				EugesRole auxRoleAct;	
				Vector tempRoles = auxIteration.getAssociation(auxPersonne);
				for (Iterator iterator = tempRoles.iterator(); iterator.hasNext();) {
					auxRoleAct = (EugesRole) iterator.next();
					if (auxRoleAct.getName().equals(r.getName()) && !resultat.contains(auxPersonne)) {
						appartient = true;
						resultat.add(auxPersonne);	
					}
				}	
			}
			appartient = false;
		}
		
		return resultat;
	}
	
	
	// Retourne toutes les activites associ�es au role : r
	public static Vector getActivitesRole(EugesRole r) {
		Vector resultat = new Vector();
		EugesActivite auxActivite;
		boolean appartient = false;
		Iterator it2 = EugesElements.listeActivites.iterator();
		while(!appartient && it2.hasNext()){
			auxActivite = (EugesActivite) it2.next();
			if (auxActivite.getRole()!=null){
				if(auxActivite.getRole().getName().equals(r.getName()) && !appartient) {
					appartient = true;
					resultat.add(auxActivite);	
				}				
			}
			appartient = false;
		}
		
		return resultat;
	}
	
	
	// Retourne tous les produits associ�es � la personne : pers
	public static Vector getProduitsPersonne(EugesPersonne pers) {
		Vector resultat = new Vector();
		EugesProduit auxProduit;
		EugesVersion auxVersion;
		boolean appartient = false;
		Iterator it = EugesElements.listeProduits.iterator();
		while(it.hasNext()){
			auxProduit = (EugesProduit) it.next();
			for (int n=0; n<auxProduit.getVersionCount(); n++) { 
				auxVersion = auxProduit.getVersionPrecise(n);
				
				// Si auxPersonne est responsable de la version
				if (auxVersion.get_responsable() != null && pers.getNom().equals(auxVersion.get_responsable().getNom()) && !appartient) {
					resultat.add(auxVersion.get_produitParent());
					appartient = true;
				}
				else {
					for (int m=0; m<auxVersion.get_acteurs().size(); m++) {
						if((EugesPersonne) auxVersion.get_acteurs().elementAt(m) == pers && !appartient) {
							resultat.add(auxVersion.get_produitParent());
							appartient = true;
						}
					}
				}		
			}
			appartient = false;
		}
		return resultat;
	}
	
	
	// Retourne toutes les activit�s qui ont en entr�e le produit : prod
	public static Vector getActivitesProduitIn(EugesProduit prod) {
		Vector resultat= new Vector();
		Vector activites = EugesElements.listeActivites;
		for (Iterator it = activites.iterator(); it.hasNext();) {
			EugesActivite auxActivite = (EugesActivite) it.next();
			if (auxActivite.contientProduitIn(prod)) {
				resultat.add(auxActivite);
			}
		}
		return resultat;
	}
	
	
	// Retourne toutes les activit�s qui ont en sortie le produit : prod
	/**
	 * 
	 */
	public static Vector getActivitesProduitOut(EugesProduit prod) {
		Vector resultat = new Vector();
		Vector activites = EugesElements.listeActivites;
		for (Iterator it = activites.iterator(); it.hasNext();) {
			EugesActivite auxActivite = (EugesActivite) it.next();
			if (auxActivite.contientProduitOut(prod)) {
				resultat.add(auxActivite);
			}
		}
		return resultat;
	}
	
	
	// Retourne toutes les versions associ�es � une it�ration
	public static Vector getProduitsIteration(Iteration it) {
		Vector resultat = new Vector();
		
		for (int i=0; i<it.getActiviteCount(); i++) {
			EugesActRealise auxActRealise = it.getActivite(i);
			for (int k=0; k<auxActRealise.getProduitInCount(); k++) {
				EugesVersion auxVersion = auxActRealise.getProduitIn(k); 
				if (!resultat.contains(auxVersion)) {
					resultat.add(auxVersion);
				}
			}
			
			for (int l=0; l<auxActRealise.getProduitOutCount(); l++) {
				EugesVersion auxVersion = auxActRealise.getProduitOut(l); 
				if (!resultat.contains(auxVersion)) {
					resultat.add(auxVersion);
				}
			}
		}
		
		return resultat;
	}
	public static void clearAllElements(){
		//le pointeur vers le projet est mis a null
		//le garbage collector se charge de supprimer tous les elements
		//qui ne st plus referenc�s
		_projet=null;
		
		//suppression de tous les elements dans les listes globales
		listeActivites.removeAllElements();
		listeRoles.removeAllElements();
		listeProduits.removeAllElements();
		listePersonnes.removeAllElements();
	}
	
}