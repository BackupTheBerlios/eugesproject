/*
 * Created on 11 janv. 2004
 *
 */
package donnees.eugesSpem;

import java.util.Vector;


/**
 * @author Nicolas
 *
 */
public class EugesPersonne {

	private int _id;
	private String _nom;
	private String _prenom;
	private String _mail;
	private Vector _roles = new Vector();
	
	public EugesPersonne() {
	}
	
	public EugesPersonne(int id, String nom, String prenom, String mail) {
		setId(id);
		setNom(nom);
		setPrenom(prenom);
		setMail(mail);
	}

	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return _id;
	}	public String toString() {
		return (_id + " " + _nom + " " + _prenom);
	}
	


	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this._id = id;
	}



	/**
	 * @return Returns the mail.
	 */
	public String getMail() {
		return _mail;
	}



	/**
	 * @param mail The mail to set.
	 */
	public void setMail(String mail) {
		this._mail = mail;
	}


	/**
	 * @return Returns the nom.
	 */
	public String getNom() {
		return _nom;
	}

	/**
	 * @param nom The nom to set.
	 */
	public void setNom(String nom) {
		this._nom = nom;
	}	public boolean ajouterRole(EugesRole a) {
		if (!contientRole(a)) {
			_roles.add(a);
			return true;
		}

		return false;
	}

	/**
	 * @return Returns the prenom.
	 */
	public String getPrenom() {
		return _prenom;
	}	public boolean supprimerRole(EugesRole a) {
		if (contientRole(a)) {
			_roles.remove(a);
			return true;
		}
		return false;
	}

	/**
	 * @param prenom The prenom to set.
	 */
	public void setPrenom(String prenom) {
		this._prenom = prenom;
	}	public boolean contientRole(EugesRole a) {
		return _roles.contains(a);
	}

	public int getRoleCount() {
		return _roles.size();
	}

	public EugesRole getRole(int i) {
		return (EugesRole) _roles.get(i);
	}

}
