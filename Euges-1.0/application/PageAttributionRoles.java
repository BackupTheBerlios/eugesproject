/*
 * Created on 18 janv. 2004
 *
 */
package application;

import java.util.Iterator;

import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesRole;

/**
 * @author Mathieu GAYRAUD
 *
 */
public class PageAttributionRoles {
	
	public static EugesPersonne getPersonne(String pers) {
		String temp = new String();
		for (Iterator iter = EugesElements.listePersonnes.iterator(); iter.hasNext();) {
			EugesPersonne e = (EugesPersonne) iter.next();
			temp = e.getId() + " " + e.getNom() + " " + e.getPrenom();
			if (temp.equals(pers)) {
				return e;
			}
		}
		return null;
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
}
