/*
 * Created on 29 déc. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package donnees;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import utilitaires.MyDate;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesRole;

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Iteration {
	//attributs de la classe
	private int _numIt=0;
	private MyDate _dateDebut;
	private MyDate _dateFin;
	private Vector _activitesRealisees = new Vector();
	private Vector _personnesRoles = new Vector();
	
	/**
	 * cré une nouvelle iteration
	 * @param dateDebut
	 * @param dateFin
	 */
	public Iteration(MyDate dateDebut, MyDate dateFin){
		_dateDebut=dateDebut;
		_dateFin=dateFin;
	}
	/**
	 * constructeur par défaut
	 *
	 */
	public Iteration(){
		
	}
	/**
	 * 
	 * @return retourne une tableau de string content le numero de l'it, la date de debut et la date de fin
	 */
	public String[] toTableString(){
		String [] tableString = new String[3];
		tableString[0]=new Integer(_numIt).toString();
		tableString[1]=_dateDebut.toString();
		tableString[2]=_dateFin.toString();
		return tableString;
	}
	/**
	 * @return Returns the _dateDebut.
	 */
	public MyDate get_dateDebut() {
		return _dateDebut;
	}

	/**
	 * @param debut The _dateDebut to set.
	 */
	public void set_dateDebut(MyDate debut) {
		_dateDebut = debut;
	}

	/**
	 * @return Returns the _dateFin.
	 */
	public MyDate get_dateFin() {
		return _dateFin;
	}

	/**
	 * @param fin The _dateFin to set.
	 */
	public void set_dateFin(MyDate fin) {
		_dateFin = fin;
	}

	/**
	 * @return Returns the _numIt.
	 */
	public int get_numIt() {
		return _numIt;
	}

	/**
	 * @param it The _numIt to set.
	 */
	public void set_numIt(int it) {
		_numIt = it;
	}
	public boolean ajouterActivite(EugesActRealise a) {
		if (!contientActivite(a)) {
			_activitesRealisees.add(a);
			return true;
		}

		return false;
	}

	public boolean supprimerActivite(EugesActRealise a) {
		if (contientActivite(a)) {
			_activitesRealisees.remove(a);
			return true;
		}
		return false;
	}

	public boolean contientActivite(EugesActRealise a) {
		return _activitesRealisees.contains(a);
	}

	public int getActiviteCount() {
		return _activitesRealisees.size();
	}

	public EugesActRealise getActivite(int i) {
		return (EugesActRealise) _activitesRealisees.get(i);
	}
	
	public void ajouterAssociation(EugesPersonne p, EugesRole r) {
		int index;
		if (!contientAssociation(p,r)) {
			if ((index = contientPersonne(p)) == -1) {
				Vector temp = new Vector();
				Vector tempRoles = new Vector();
				temp.add(p);
				tempRoles.add(r);
				temp.add(tempRoles);
				_personnesRoles.add(temp);
			}
			else {
				Vector temp = (Vector) _personnesRoles.elementAt(index);
				Vector tempRoles = (Vector) temp.elementAt(1);
				tempRoles.add(r);
			}
		}
	}
	
	public void supprimerAssociation(EugesPersonne p, EugesRole r) {
		if (!contientAssociation(p,r)) {
			int index;
			if ((index = contientPersonne(p)) != -1) {
				Vector temp = (Vector) _personnesRoles.elementAt(index);
				Vector tempRoles = (Vector) temp.elementAt(1);
				tempRoles.remove(r);
				if (tempRoles.size() == 0) {
					temp.clear();
					_personnesRoles.remove(temp);
				}
			}
		}
	}
	
	public boolean contientAssociation(EugesPersonne p, EugesRole r) {
		int index;
		if ((index = contientPersonne(p)) != -1) {
			Vector temp = (Vector) _personnesRoles.elementAt(index);
			Vector tempRoles = (Vector) temp.elementAt(1);
			return tempRoles.contains(r);
		}
		return false;
	}
	
	public int contientPersonne(EugesPersonne p) {
		for (Iterator iter = _personnesRoles.iterator(); iter.hasNext();) {
			Vector e = (Vector) iter.next();
			if (e.contains(p))
				return _personnesRoles.indexOf(e);
		}
		return -1;
	}
	
	public void supprimerRole(EugesRole r) {
		for (Iterator iter = _personnesRoles.iterator(); iter.hasNext();) {
			Vector e = (Vector) iter.next();
			Vector tempRoles = (Vector) e.elementAt(1);
			tempRoles.remove(r);
			if (tempRoles.size() == 0) {
				e.clear();
				_personnesRoles.remove(e);
			}
		}
	}

	public void supprimerPersonne(EugesPersonne p) {
		int index;
		if ((index = contientPersonne(p)) != -1) {
			_personnesRoles.removeElementAt(index);
		}
	}
	
	public Vector getAssociation(EugesPersonne p) {
		int index;
		if ((index = contientPersonne(p)) != -1) {
			Vector temp = (Vector) _personnesRoles.elementAt(index);
			Vector tempRoles = (Vector) temp.elementAt(1);
			return tempRoles;
		}
		return (new Vector());
	}
	
	public Vector getAssociation(EugesRole r) {
		Vector v = new Vector();
		for (Iterator iter = _personnesRoles.iterator(); iter.hasNext();) {
			Vector e = (Vector) iter.next();
			Vector tempRoles = (Vector) e.elementAt(1);
			if (tempRoles.contains(r)) {
				v.add((EugesPersonne) e.elementAt(0));
			}
		}
		return v;
	}
	
	public Vector getPersonnes() {
		Vector v = new Vector();
		for (Iterator iter = _personnesRoles.iterator(); iter.hasNext();) {
			Vector e = (Vector) iter.next();
			v.add((EugesPersonne)e.elementAt(0));
		}
		return v;
	}

	public Vector getRoles() {
		Vector v = new Vector();
		for (Iterator iter = _personnesRoles.iterator(); iter.hasNext();) {
			Vector e = (Vector) iter.next();
			Vector tempRoles = (Vector) e.elementAt(1);
			for (Iterator iterator = tempRoles.iterator();iterator.hasNext();) {
				EugesRole element = (EugesRole) iterator.next();
				if (!v.contains(element))
					v.add(element);
			}
		}
		return v;
	}
	/**
	 * @param ecriture
	 */
		public void sauvegarderAssociation(BufferedWriter ecriture) {
		try {
			ecriture.write("<Iteration _numIt=\""+this.get_numIt()+"\"/>\n");
			for (int i = 0; i<_activitesRealisees.size();i++)
			{
				((EugesActRealise)_activitesRealisees.get(i)).sauvegarder(ecriture);
			}
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
	
	
	public void sauvegardePartielle(BufferedWriter ecriture) {
		try {
			ecriture.write("<Iteration _numIt=\""+this.get_numIt()+"\" _dateDebut=\""+this.get_dateDebut()+"\" _dateFin=\""+this.get_dateFin()+"\"/>\n");
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
}
