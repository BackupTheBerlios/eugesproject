/*
 * Created on 17 janv. 2004
 *
 */
package donnees.eugesSpem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Vector;

/**
 * @author Mathieu GAYRAUD
 *
 */
public class EugesVersion {
	private String _nom;
	private String _etat = "";
	private int _realisation = 0;
	private EugesPersonne _responsable;
	private EugesProduit _produitParent;
	private Vector _acteurs = new Vector();

	public EugesVersion () {
	
	}

	public EugesVersion (EugesProduit p) {
		set_produitParent(p);
		set_nom(p.getVersion());
	}
	public EugesVersion (String nom) {
		set_nom(nom);
	}
	
	public EugesVersion(String nom, String etat, int realisation,	EugesPersonne responsable, EugesProduit produitParent){
		set_nom(nom);
		set_etat(etat);
		set_realisation(realisation);
		set_responsable(responsable);
		
		set_produitParent(produitParent);
	}
		
	public String toString() {
		return _produitParent.getName() + get_nom();
	}
	
	private void setVersion(double v) {
		_produitParent.set_versionSuivante(v);
	}
	
	public boolean ajouterModifieur(EugesPersonne a) {
		if (!contientModifieur(a)) {
			_acteurs.add(a);
			return true;
		}

		return false;
	}

	public boolean supprimerModifieur(EugesPersonne a) {
		if (contientModifieur(a)) {
			_acteurs.remove(a);
			return true;
		}
		return false;
	}

	public boolean contientModifieur(EugesPersonne a) {
		if (getModifieurCount() == 0){
			return false;
		}
		else {
			return _acteurs.contains(a);
		}
	}

	public EugesPersonne getModifieur(int i) {
		
		return (EugesPersonne)_acteurs.get(i);
	}

	public int getModifieurCount() {
		
		return _acteurs.size();
	}

	/**
	 * @return Returns the _etat.
	 */
	public String get_etat() {
		return _etat;
	}

	/**
	 * @param _etat The _etat to set.
	 */
	public void set_etat(String _etat) {
		this._etat = _etat;
	}

	/**
	 * @return Returns the _nom.
	 */
	public String get_nom() {
		return _nom;
	}

	/**
	 * @param _nom The _nom to set.
	 */
	public void set_nom(String _nom) {
		//setVersion((new Float(_nom)).doubleValue());
		this._nom = _nom;
	}

	/**
	 * @return Returns the _produitParent.
	 */
	public EugesProduit get_produitParent() {
		return _produitParent;
	}

	/**
	 * @param parent The _produitParent to set.
	 */
	public void set_produitParent(EugesProduit parent) {
		_produitParent = parent;
	}

	/**
	 * @return Returns the _realisation.
	 */
	public int get_realisation() {
		return _realisation;
	}

	/**
	 * @param _realisation The _realisation to set.
	 */
	public void set_realisation(int _realisation) {
		this._realisation = _realisation;
	}

	/**
	 * @return Returns the _responsable.
	 */
	public EugesPersonne get_responsable() {
		return _responsable;
	}

	/**
	 * @param _responsable The _responsable to set.
	 */
	public void set_responsable(EugesPersonne _responsable) {
		this._responsable = _responsable;
	}

	/**
	 * @return Returns the _acteurs.
	 */
	public Vector get_acteurs() {
		return _acteurs;
	}

	/**
	 * @param _acteurs The _acteurs to set.
	 */
	public void set_acteurs(Vector _acteurs) {
		this._acteurs = _acteurs;
	}
	/**
	 * @param ecriture
	 */
	public void sauvegarderProduitIn(BufferedWriter ecriture) {
		try {
			ecriture.write("<ProduitsIn name=\""+this.get_nom()+"\"/>\n");
			//sauvegarde de l'état
			ecriture.write("<_version _nom=\""+this.get_nom()+"\" _etat=\""+this.get_etat()+"\" _realisation=\""+this.get_realisation()+"\" _responsable=\""+this.get_responsable()+"\" _produitParent=\""+this.get_produitParent()+"\"/>\n");
			//sauvegarde des acteurs
			for (int i = 0; i<_acteurs.size();i++)
			{
				((EugesPersonne)_acteurs.get(i)).sauvegardeActeur(ecriture);
			}
			
		} catch (IOException e) {
			System.out.println (e);
			e.printStackTrace();
		}
		
	}
	public void sauvegarderProduitOut(BufferedWriter ecriture) {
		try {
			ecriture.write("<ProduitsOut name=\""+this.get_nom()+"\"/>\n");
			//sauvegarde de l'état
			ecriture.write("<_version _nom=\""+this.get_nom()+"\" _etat=\""+this.get_etat()+"\" _realisation=\""+this.get_realisation()+"\" _responsable=\""+this.get_responsable()+"\" _produitParent=\""+this.get_produitParent()+"\"/>\n");
			//sauvegarde des acteurs
			for (int i = 0; i<_acteurs.size();i++)
			{
				((EugesPersonne)_acteurs.get(i)).sauvegardeActeur(ecriture);
			}
			
		} catch (IOException e) {
			System.out.println (e);
			e.printStackTrace();
		}
		
	}
}
