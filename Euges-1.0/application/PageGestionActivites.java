/*
 * Created on 11 janv. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package application;

import ihm.NouvelleActiviteIHM;

import java.util.Vector;

import org.eclipse.swt.widgets.Shell;

import donnees.Iteration;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesRole;

/**
 * Classe "control" de la classe PageGestionActivitesIHM.
 * Permet d'effectuer les traitements li�s � l'ihm.
 * 
 * @author Nicolas Broueilh
 */
public class PageGestionActivites {
	
	public PageGestionActivites() {}
	
	/**
	 * Ouverture de la fen�tre de cr�ation d'une nouvelle activit�
	 * @param parent le shell parent de la fen�tre � ouvrir
	 */
	public static void nouvelleActivite(Shell parent) {
		//ouverture de la fen�tre de saisie des informations de la nouvelle activit�
		new NouvelleActiviteIHM(parent);		
	}
	
	
	
	/**
	 * Ajoute le lien entre l'activite et le role dans le spem
	 * @param act l'activite
	 * @param role le role
	 * @return 1 si le lien a �t� ajout�, 0 s'il existait d�j�
	 */
	public static int ajoutLienActiviteRole(EugesActivite act, EugesRole role) {
		if (act.getRole()==null) { 
			act.setRole(role);
			return 1;
		}
		else
			return 0;
	}
	
	/**
	 * Supprime le lien entre le r�le et l'activit�
	 * @param act
	 * @param role
	 */
	public static void supprLienActiviteRole(EugesActivite act, EugesRole role) {
		act.setRole(null);
	}
	
	/**
	 * Supprime l'activit�
	 * @param act
	 * @param vectRoles le vecteur contenant les r�les associ�s � l'activit�
	 */
	public static void supprActivite(EugesActivite act, Vector vectRoles) {
		for (int i = 0; i<EugesElements._projet._listeIteration.size();i++) {
			Iteration it = EugesElements._projet.getIteration(i);
			for (int j=0; j< it.getActiviteCount(); j++)
				it.supprimerActivite((EugesActRealise)it.getActivite(i));
		}			
		EugesElements.listeActivites.remove(act);
	}

}
