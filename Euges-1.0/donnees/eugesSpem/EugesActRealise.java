/*
 * Created on 17 janv. 2004
 *
 */
package donnees.eugesSpem;

import java.util.Vector;

/**
 * @author Mathieu GAYRAUD
 *
 */
public class EugesActRealise {
	
	private int _chargeEstimee = 0;
	private int _chargeReelle = 0;
	private Vector _personnes = new Vector();
	private Vector _produitsIn = new Vector();
	private Vector _produitsOut = new Vector();
	private EugesActivite _activiteParent;
	private int it;
	
	public EugesActRealise() {
	
	}

	public EugesActRealise(EugesActivite a) {
		set_activiteParent(a);
	}
	
	public String toString() {
		return _activiteParent.toString();
	}
	
	/**
	 * @return Returns the _activiteParent.
	 */
	public EugesActivite get_activiteParent() {
		return _activiteParent;
	}

	/**
	 * @param parent The _activiteParent to set.
	 */
	public void set_activiteParent(EugesActivite parent) {
		_activiteParent = parent;
	}

	/**
	 * @return Returns the _chargeEstimee.
	 */
	public int get_chargeEstimee() {
		return _chargeEstimee;
	}

	/**
	 * @param estimee The _chargeEstimee to set.
	 */
	public void set_chargeEstimee(int estimee) {
		_chargeEstimee = estimee;
	}

	/**
	 * @return Returns the _chargeReelle.
	 */
	public int get_chargeReelle() {
		return _chargeReelle;
	}

	/**
	 * @param reelle The _chargeReelle to set.
	 */
	public void set_chargeReelle(int reelle) {
		_chargeReelle = reelle;
	}

	public boolean ajouterPersonne(EugesPersonne a) {
		if (!contientPersonne(a)) {
			_personnes.add(a);
			return true;
		}

		return false;
	}

	public boolean supprimerPersonne(EugesPersonne a) {
		if (contientPersonne(a)) {
			_personnes.remove(a);
			return true;
		}
		return false;
	}

	public boolean contientPersonne(EugesPersonne a) {
		return _personnes.contains(a);
	}

	public int getPersonneCount() {
		return _personnes.size();
	}

	public EugesPersonne getPersonne(int i) {
		return (EugesPersonne) _personnes.get(i);
	}

	public boolean ajouterProduitIn(EugesVersion a) {
		if (!contientProduitIn(a)) {
			_produitsIn.add(a);
			return true;
		}

		return false;
	}

	public boolean supprimerProduitIn(EugesVersion a) {
		if (contientProduitIn(a)) {
			_produitsIn.remove(a);
			return true;
		}
		return false;
	}

	public boolean contientProduitIn(EugesVersion a) {
		return _produitsIn.contains(a);
	}

	public int getProduitInCount() {
		return _produitsIn.size();
	}

	public EugesVersion getProduitIn(int i) {
		return (EugesVersion) _produitsIn.get(i);
	}

	public boolean ajouterProduitOut(EugesVersion a) {
		if (!contientProduitOut(a)) {
			_produitsOut.add(a);
			return true;
		}

		return false;
	}

	public boolean supprimerProduitOut(EugesVersion a) {
		if (contientProduitOut(a)) {
			_produitsOut.remove(a);
			return true;
		}
		return false;
	}

	public boolean contientProduitOut(EugesVersion a) {
		return _produitsOut.contains(a);
	}

	public int getProduitOutCount() {
		return _produitsOut.size();
	}

	public EugesVersion getProduitOut(int i) {
		return (EugesVersion) _produitsOut.get(i);
	}
	/**
	 * @return Returns the it.
	 */
	public int getIt() {
		return it;
	}

	/**
	 * @param it The it to set.
	 */
	public void setIt(int it) {
		this.it = it;
	}

}
