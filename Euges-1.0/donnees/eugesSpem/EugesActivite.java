/*
 * Created on 1 janv. 2004
 *
 */
package donnees.eugesSpem;

import java.util.Vector;

import donnees.spem.process.structure.Activity;

/**
 * Cette classe représente définit une activité telle qu'elle sera représentée sous le logiciel Euges
 * C'est à dire une activité définie par le SPEM avec des informations supplémentaires
 * @author Nicolas Broueilh
 */
public class EugesActivite extends Activity{	

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
	 * @return Returns the _produitsIn.
	 */
	public Vector get_produitsIn() {
		return _produitsIn;
	}

	/**
	 * @param in The _produitsIn to set.
	 */
	public void set_produitsIn(Vector in) {
		_produitsIn = in;
	}

	/**
	 * @return Returns the _produitsOut.
	 */
	public Vector get_produitsOut() {
		return _produitsOut;
	}

	/**
	 * @param out The _produitsOut to set.
	 */
	public void set_produitsOut(Vector out) {
		_produitsOut = out;
	}

}
