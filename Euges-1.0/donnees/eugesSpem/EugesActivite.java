 /*
 * Created on 1 janv. 2004
 *
 */
package donnees.eugesSpem;

import java.util.Iterator;
import java.util.Vector;

import donnees.spem.process.structure.Activity;

/**
 * Cette classe représente définit une activité telle qu'elle sera représentée sous le logiciel Euges
 * C'est à dire une activité définie par le SPEM avec des informations supplémentaires
 * @author Nicolas Broueilh
 */
public class EugesActivite extends Activity{	

	//vecteur d'activites realisees 
	private Vector _activites = new Vector();
	private Vector _roles = new Vector();
	private Vector _produitsIn = new Vector();
	private Vector _produitsOut = new Vector();

	public EugesActivite () {
		super();
	}
	
	public EugesActivite (String name) {
		super(name);
	}

	public String toString() {
		return getName();
	}
	
	public boolean ajouterActRealise(EugesActRealise a) {
		if (!contientActRealise(a)) {
			_activites.add(a);
			return true;
		}

		return false;
	}

	public boolean supprimerActRealise(EugesActRealise a) {
		if (contientActRealise(a)) {
			_activites.remove(a);
			return true;
		}
		return false;
	}

	public boolean contientActRealise(EugesActRealise a) {
		return _activites.contains(a);
	}
	
	public int getActRealiseCount() {
		return _activites.size();
	}
	/**
	 * getIterationDebut()
	 * @return le numero de l'iteration pendant laquelle commence l'activité
	 */
	public int getIterationDebut(){
		if(getActRealiseCount()!=0){
			int debut=((EugesActRealise)_activites.elementAt(0)).getIt();
			for (Iterator iter = _activites.iterator(); iter.hasNext();) {
				EugesActRealise element = (EugesActRealise) iter.next();
				if(element.getIt()<debut)
					debut=element.getIt();
			}
			return debut;
		}
		return -1;
	}
	/**
	 * getIterationFin()
	 * @return le numero de l'iteration pendant laquelle se termine l'activité
	 */
	public int getIterationFin(){
		if(getActRealiseCount()!=0){
			int fin=((EugesActRealise)_activites.elementAt(0)).getIt();
			for (Iterator iter = _activites.iterator(); iter.hasNext();) {
				EugesActRealise element = (EugesActRealise) iter.next();
				if(element.getIt()>fin)
					fin=element.getIt();
			}
			return fin;
		}
		return -1;
	}
	
	public EugesActRealise getActRealise(int i) {
		return (EugesActRealise) _activites.get(i);
	}
	

	public boolean ajouterRole(EugesRole r) {
		if (!contientRole(r)) {
			_roles.add(r);
			return true;
		}

		return false;
	}

	public boolean supprimerRole(EugesRole r) {
		if (contientRole(r)) {
			_activites.remove(r);
			return true;
		}
		return false;
	}
	
	public boolean contientRole(EugesRole r) {
		return _roles.contains(r);
	}

	
	public int getRoleCount() {
			return _roles.size();
		}

	public EugesRole getRole(int i) {
		return (EugesRole) _roles.get(i);
	}
	
	public boolean ajouterProduitIn(EugesProduit a) {
		if (!contientProduitIn(a)) {
			_produitsIn.add(a);
			return true;
		}

		return false;
	}

	public boolean supprimerProduitIn(EugesProduit a) {
		if (contientProduitIn(a)) {
			_produitsIn.remove(a);
			return true;
		}
		return false;
	}

	public boolean contientProduitIn(EugesProduit a) {
		return _produitsIn.contains(a);
	}

	public int getProduitInCount() {
		return _produitsIn.size();
	}

	public EugesProduit getProduitIn(int i) {
		return (EugesProduit) _produitsIn.get(i);
	}

	public boolean ajouterProduitOut(EugesProduit a) {
		if (!contientProduitOut(a)) {
			_produitsOut.add(a);
			return true;
		}

		return false;
	}

	public boolean supprimerProduitOut(EugesProduit a) {
		if (contientProduitOut(a)) {
			_produitsOut.remove(a);
			return true;
		}
		return false;
	}

	public boolean contientProduitOut(EugesProduit a) {
		return _produitsOut.contains(a);
	}

	public int getProduitOutCount() {
		return _produitsOut.size();
	}

	public EugesProduit getProduitOut(int i) {
		return (EugesProduit) _produitsOut.get(i);
	}
	/**
	 * @return Returns the _activites.
	 */
	public Vector get_activitesRealisees() {
		return _activites;
	}
}
