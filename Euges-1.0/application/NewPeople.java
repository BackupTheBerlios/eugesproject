/*
 * Created on 18 janv. 2004
 *
 */
package application;

import java.util.Iterator;

import donnees.eugesSpem.EugesPersonne;

/**
 * @author Mathieu GAYRAUD
 *
 */
public class NewPeople {
	
	public static boolean supprimerPersonne (String id) {
		EugesPersonne p = null;
		String temp;
		for (Iterator iter = EugesElements.listePersonnes.iterator(); iter.hasNext();) {
			EugesPersonne e = (EugesPersonne) iter.next();
			temp = id.substring(0, id.lastIndexOf(" ")) + " " + e.getId();
			if (temp.equals(id)) {
				p = e;
				break;
			}
		}
		
		if (p != null) {	
			EugesElements.supprimerElement(p);
			for (int i=0; i < EugesElements._projet._listeIteration.size(); i++) {
				EugesElements._projet.getIteration(i).supprimerPersonne(p);
			}
			return true;
		}
		else {
			return false;
		}
	}
}
