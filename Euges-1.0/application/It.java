/*
 * Created on 28 janv. 2004
 *
 */
package application;

import donnees.eugesSpem.EugesActRealise;

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
}
