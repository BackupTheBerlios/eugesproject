/*
 * Created on 29 déc. 2003
 *
 * Projet Euges
 * 
 */
package application;

import java.util.Iterator;
import java.util.Vector;

import utilitaires.MyDate;
import donnees.Projet;
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
	
	   //Listes globales utilisées dans tout le projet Euges
	public static Vector listeActivites = new Vector(); // liste des activités
	public static Vector listeRoles = new Vector(); // liste des rôles 
	public static Vector listeProduits = new Vector(); // liste des produits
	public static Vector listePersonnes = new Vector(); // liste des personnes
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
	
	   // fonction qui teste si la chaine de caractères est vide
	public static boolean testTexteVide(String string){
		return string.equals("");
	}
	
	// fonction qui teste si l'objet en paramètres est dans la liste globale associée
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
	
	// fonction qui ajoute l'objet en paramètres dans la liste globale associée
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
	
	// fonction qui supprime l'objet en paramètres de la liste globale associée
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
	   // fonction qui teste si l'objet en paramètres est dans la liste globale associée
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

		// on crée le produit sans sa version
		EugesProduit produit = new EugesProduit(name);
		
		// vecteur avec la version du produit
		Vector vers = new Vector();
		
		
		// on ajoute dans le vecteur la version du produit
		vers.add(new EugesVersion(version, "en cours", 20, null, produit ));

		// on ajoute cette version dans le produit
		produit.set_versions(vers);
		
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
	
// fonction qui renvoie un tableau de String des EugesProduit de la liste globale listeProduits
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
	 * fonction qui renvoie l'objet EugesPersonne qui est passé en paramètres
	 * @param personne : nom de la personne à chercher
	 * @return l'objet de la personne trouvé 
	 */
	public static EugesPersonne getPersonneDansListePersonnes(String personne){
		boolean trouve = false;
		// variable personne à retourner
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
	 * fonction qui renvoie l'objet EugesProduit qui est passé en paramètres
	 * @param produit : nom du produit à chercher
	 * @return l'objet du produit trouvé 
	 */
	public static EugesProduit getProduitDansListeProduits(String produit){
		boolean trouve = false;
		// variable produit à retourner
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
		EugesRole r = null;
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

}
