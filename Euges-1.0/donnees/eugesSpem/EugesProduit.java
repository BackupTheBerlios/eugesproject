/*
 * Created on 10 janv. 2004
 *
 */
package donnees.eugesSpem;

import java.util.Vector;

import donnees.spem.process.structure.WorkProduct;

/**
 * @author Nicolas
 *
 */
public class EugesProduit extends WorkProduct{
	
	private Vector _versions = new Vector();
	
	public EugesProduit(){
		super();  // appel du constructeur de WorkProduct
	}
	
	public EugesProduit(String name){
		super(name); // appel du constructeur de WorkProduct
	}

	public String toString() {
		return this.getName();
	}
	
	public boolean ajouterVersion(EugesVersion a) {
		if (!contientVersion(a)) {
			_versions.add(a);
			return true;
		}

		return false;
	}

	public boolean supprimerVersion(EugesVersion a) {
		if (contientVersion(a)) {
			_versions.remove(a);
			return true;
		}
		return false;
	}

	public boolean contientVersion(EugesVersion a) {
		return _versions.contains(a);
	}

	   // nb de versions du produit
	public int getVersionCount() {
		return _versions.size();
	}
	
	   // obtenir une version précise
	public EugesVersion getVersionPrecise(int i) {
		return (EugesVersion) _versions.get(i);
	}

	/**
	 * @return Returns the _versions.
	 */
	public Vector get_versions() {
		return _versions;
	}

	/**
	 * @param _versions The _versions to set.
	 */
	public void set_versions(Vector _versions) {
		this._versions = _versions;
	}

}
