/*
 * Created on 28 janv. 2004
 *
 */
package application;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.swt.custom.TableTreeItem;

import donnees.Iteration;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesProduit;
import donnees.eugesSpem.EugesVersion;

/**
 * @author Mathieu GAYRAUD
 *
 */
public class It {

	public static EugesActRealise ajouterActRealise(String a, int it) {
		EugesActRealise act = new EugesActRealise(EugesElements.getActivite(a));
		act.setIt(it);
		EugesElements._projet.getIteration(it).ajouterActivite(act);
		EugesElements.getActivite(a).ajouterActRealise(act);
		return act;
	}
	
	public static void supprimerActRealise(EugesActRealise a, int it) {
		EugesElements._projet.getIteration(it).supprimerActivite(a);
		a.get_activiteParent().supprimerActRealise(a);
	}
	
	public static Vector getProdIn(TableTreeItem[] items) {
		Vector retour = new Vector();
		for (int i = 0; i < items.length; i++) {
			EugesActRealise a = (EugesActRealise) items[i].getData();
			EugesActivite act = a.get_activiteParent();
			for (int j = 0; j < act.getProduitInCount(); j++) {
				if (!retour.contains(act.getProduitIn(j)))
					retour.add(act.getProduitIn(j));
			}
		}
		return retour;
	}

	public static Vector getProdOut(TableTreeItem[] items) {
		Vector retour = new Vector();
		for (int i = 0; i < items.length; i++) {
			EugesActRealise a = (EugesActRealise) items[i].getData();
			EugesActivite act = a.get_activiteParent();
			for (int j = 0; j < act.getProduitOutCount(); j++) {
				if (!retour.contains(act.getProduitOut(j)))
					retour.add(act.getProduitOut(j));
			}
		}
		return retour;
	}
	
	public static Vector getVerOut(Iteration it) {
		Vector retour = new Vector();
		for (int i = 0; i < it.getActiviteCount(); i++) {
			EugesActRealise a = (EugesActRealise) it.getActivite(i);
			for (int j = 0; j < a.getProduitOutCount(); j++) {
				if (!retour.contains(a.getProduitOut(j)))
					retour.add(a.getProduitOut(j));
			}
		}
		return retour;
	}

	public static String getInAct(EugesProduit p) {
		String s = "(Act: ";
		boolean boucle = false;
		for (Iterator iter = EugesElements.listeActivites.iterator(); iter.hasNext();) {
			EugesActivite e = (EugesActivite) iter.next();
			if (e.contientProduitIn(p)) {
				s = s + e.toString() + ", ";
				boucle = true;
			}
		}
		if (boucle)
			s = s.substring(0, s.length() - 2);
		
		s = s + ")";
		return s;
	}
	
	public static String getOutAct(EugesProduit p) {
		String s = "(Act: ";
		boolean boucle = false;
		for (Iterator iter = EugesElements.listeActivites.iterator(); iter.hasNext();) {
			EugesActivite e = (EugesActivite) iter.next();
			if (e.contientProduitOut(p)) {
				s = s + e.toString() + ", ";
				boucle = true;
			}
		}
		if (boucle)
			s = s.substring(0, s.length() - 2);
		
		s = s + ")";
		return s;
	}

	public static EugesVersion creerVersion(String nomProd, Iteration it) {
		EugesProduit p = EugesElements.getProduitDansListeProduits(nomProd);
		EugesVersion v = new EugesVersion(p);
		p.ajouterVersion(v);
		
		EugesActRealise ar;
		EugesActivite a;
		for (int i = 0; i < it.getActiviteCount(); i++) {
			ar = (EugesActRealise)it.getActivite(i);
			a = ar.get_activiteParent();
			if (a.contientProduitOut(p)) {
				ar.ajouterProduitOut(v);
			}
		}
		
		return v;
	}
	
	public static void supprimerVersion (EugesVersion v, Iteration it) {
		v.get_produitParent().supprimerVersion(v);

		EugesActRealise ar;
		for (int i = 0; i < it.getActiviteCount(); i++) {
			ar = (EugesActRealise)it.getActivite(i);
			if (ar.contientProduitOut(v)) {
				ar.supprimerProduitOut(v);
			}
		}
	}
}
