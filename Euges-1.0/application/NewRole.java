/*
 * Created on 18 janv. 2004
 *
 */
package application;

import java.util.Iterator;
import java.util.Vector;

import donnees.eugesSpem.EugesRole;

/**
 * @author Mathieu GAYRAUD
 *
 */

public class NewRole {
	public static Vector roleToVector () {
		int i = 0;

		Vector liste = new Vector();
		
		for (Iterator iter = EugesElements.listeRoles.iterator(); iter.hasNext();) {
			EugesRole e = (EugesRole) iter.next();
			if (e != null)
				liste.add(e.toString());
		}
		
		return liste;
	}
	
	public static boolean supprimerRole(String role) {
		EugesRole r = null;
		for (Iterator iter = EugesElements.listeRoles.iterator(); iter.hasNext();) {
			EugesRole e = (EugesRole) iter.next();
			if (e.toString().equals(role)) {
				r = e;
				break;
			}
		}
		
		if (r != null) {	
			EugesElements.supprimerElement(r);
			// suppression dans les personnes aussi
			for (int i = 0; i < EugesElements._projet._listeIteration.size(); i++) {
				EugesElements._projet.getIteration(i).supprimerRole(r);
			}
			return true;
		}
		else {
			return false;
		}
	}
}
