/*
 * Created on 9 févr. 2004
 *
 */
package application;

import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesProduit;


/**
 * @author Mathieu GAYRAUD
 *
 */
public class FenetreGestionProduits {
	public static void supprimerProd(EugesActivite a) {
		for (int i = 0; i < a.getProduitInCount(); i++) {
			a.supprimerProduitIn(a.getProduitIn(i));
		}
		for (int i = 0; i < a.getProduitOutCount(); i++) {
			a.supprimerProduitOut(a.getProduitOut(i));
		}
	}
	
	public static void supprimerProd(EugesActivite a, String io) {
		if (io.equals("IN"))
			for (int i = 0; i < a.getProduitInCount(); i++)
				a.supprimerProduitIn(a.getProduitIn(i));
		else
			for (int i = 0; i < a.getProduitOutCount(); i++)
				a.supprimerProduitOut(a.getProduitOut(i));
	}

	public static void supprimerProd(EugesProduit p, EugesActivite a, String io) {
		if (io.equals("IN"))
			a.supprimerProduitIn(p);
		else
			a.supprimerProduitOut(p);
	}
	
	public static EugesProduit ajouterProdIn(String prod, EugesActivite a) {
		EugesProduit p = EugesElements.getProduitDansListeProduits(prod);
		if (!a.contientProduitIn(p)) {
			a.ajouterProduitIn(p);
			return p;
		}
		return null;
	}

	public static EugesProduit ajouterProdOut(String prod, EugesActivite a) {
		EugesProduit p = EugesElements.getProduitDansListeProduits(prod);
		if (!a.contientProduitOut(p)) {
			a.ajouterProduitOut(p);
			return p;
		}
		return null;
	}
}
