/*
 * Created on 10 janv. 2004
 *
 */
package donnees.eugesSpem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Vector;

import donnees.spem.process.structure.WorkProduct;

/**
 * @author Nicolas
 *
 */
public class EugesProduit extends WorkProduct{
	
	private double _versionSuivante = 1.0;
	public Vector _etats = new Vector();
	private Vector _versions = new Vector();
	private String _cheminProduit;
	
	
	public EugesProduit(){
		super();  // appel du constructeur de WorkProduct
	}
	
	public EugesProduit(String name){
		super(name); // appel du constructeur de WorkProduct
	}

	public EugesProduit(String name, String chemin, Vector etats) {
		super(name); // appel du constructeur de WorkProduct
		_cheminProduit = chemin;
		_etats = etats;
	}
	
	public String toString() {
		return this.getName();
	}
	
	
	/**
	 * @return Returns the _cheminProduit.
	 */
	public String get_cheminProduit() {
		return _cheminProduit;
	}

	/**
	 * @param produit The _cheminProduit to set.
	 */
	public void set_cheminProduit(String produit) {
		_cheminProduit = produit;
	}

	
	/**
	 * @return Returns the _etats.
	 */
	public Vector get_etats() {
		return _etats;
	}

	/**
	 * @param _etats The _etats to set.
	 */
	public void set_etats(Vector _etats) {
		this._etats = _etats;
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

	public String getVersion() {
		String s = (new Float(_versionSuivante)).toString();
		_versionSuivante += 0.1;
		return s;
	}
	/**
	 * @return Returns the _versionSuivante.
	 */
	public double get_versionSuivante() {
		return _versionSuivante;
	}

	/**
	 * @param suivante The _versionSuivante to set.
	 */
	public void set_versionSuivante(double suivante) {
		_versionSuivante = suivante;
	}
	/**
	 * @param ecriture
	 */
	public void sauvegarder(BufferedWriter ecriture) {
		try {
			ecriture.write("<EugesProduit name=\""+this.getName()+"\"/>\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void genereMenu(BufferedWriter buffer) {
		try {
			buffer.write("<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+this.getName()+"  "+this.get_cheminProduit()+"  "+this.getVersion()+"</td></tr>");
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
}
