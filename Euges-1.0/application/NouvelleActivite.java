/*
 * Created on 12 janv. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package application;

import donnees.eugesSpem.EugesActivite;

/**
 * @author Nicolas
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class NouvelleActivite {
			
	public NouvelleActivite() {}
	
//	public static void creerActivite(String nom, String duree, String description) {
	public static void creerActivite(String nom, String description) {
//		int d = (new Integer(duree)).intValue();
		EugesActivite eugesActivite = new EugesActivite(nom);
		EugesElements.ajouterElement(eugesActivite);
//		EugesActRealise actRealisee = new EugesActRealise(eugesActivite);
//		actRealisee.set_chargeEstimee(d);		
//		eugesActivite.ajouterActRealise(actRealisee);
	}
	

//	public static boolean saisieValide(String nom, String duree, String description) {
//		try {
//			int d = (new Integer(duree)).intValue();
//		} catch (Exception ex) {
//			return false;			
//		}
//		if (nom.equals("") && duree.equals(""))
	public static boolean saisieValide(String nom, String description) {
		if (nom.equals(""))
			return false;
		else
			return true;
	}

}
