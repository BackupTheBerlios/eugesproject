/*
 * Created on 29 déc. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package donnees;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import application.EugesElements;

import utilitaires.MyDate;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesProduit;
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
			//sauvegarde des personnes ayant travaillées sur l'itératon avec leurs roles
			Vector pers = new Vector ();
			pers= getPersonnes();
			EugesPersonne persCour = new EugesPersonne();
			Vector role = new Vector ();
			EugesRole roleCour = new EugesRole();
			for (int i =0;i< pers.size();i++)
			{
				persCour = (EugesPersonne)pers.elementAt(i);
				persCour.sauvegardePartielle(ecriture);
				role = getAssociation(persCour);
				for (int j = 0;j<role.size();j++)
				{
					roleCour = (EugesRole)role.elementAt(j);
					roleCour.sauvegarder(ecriture);
				}
				//System.out.println (_personnesRoles.elementAt(i));
			}
			
			//sauvegarde des activités réalisées
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
	
	public void genereLigneMenu(BufferedWriter buffer) {
		try {
			buffer.write("<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='iterations/iteration"+this.get_numIt()+".htm' target='dyn'>It&eacute;ration "+this.get_numIt()+"</td></tr>");
		} catch (IOException e) {
			System.out.println (e);
			e.printStackTrace();
		}	
	}
	
	public void genereIteration(String chemin) {
		File FileIteration  = new File (chemin+"/iterations/iteration"+this.get_numIt()+".htm");
		try {
			
			//Génération de l'index : 
			FileWriter fichierIteration = new FileWriter (FileIteration);
			BufferedWriter iteration = new BufferedWriter (fichierIteration);
			
			iteration.write("<HTML><HEAD></HEAD><BODY bgcolor='lightgrey'><div align='center'><i><h3>Plan d'it&eacute;ration "+this.get_numIt()+"</h3></i></div>");
			iteration.write("<div align='center'>Du "+this.get_dateDebut()+" au "+this.get_dateFin()+"</div><br>");
			
			// 1er tableau : Roles & Personnes :
			iteration.write("<div align='right'><img src='../images/role.gif'><I><B>Roles & Personnes</B></I></div><hr><table border='0' width='100%'>");
			for (int i = 0; i<_activitesRealisees.size();i++)
			{
				((EugesActRealise)_activitesRealisees.get(i)).genereTabPersonne(iteration);
			}
			iteration.write("</table><br>");
			
			// 2eme tableau : Activités :
			iteration.write("<div align='right'><img src='../images/activite.gif'><I><b>Activités</b></I></div><hr><table cellspacing='0'cellpadding='0' border='1' bordercolor='black' width='100%'><tr><td>Activit&eacute;</td><td>Charges pr&eacute;vue (h)</td><td>Charges r&eacute;elle (h)</td></tr>");
			for (int i = 0; i<_activitesRealisees.size();i++)
			{
				((EugesActRealise)_activitesRealisees.get(i)).genereTabActivite(iteration);
			}
			iteration.write("</table><br>");
			
			// 3eme tableau : Produits : 
			iteration.write("<div align='right'><img src='../images/produit.gif'><i><B>Produits</B></i></div><hr><table cellspacing='0'cellpadding='0' border='1' bordercolor='black' width='100%'><tr><td>Produit en sortie</td><td>Version</td><td>Etat / R&eacute;alisation</td></tr>");
			for (int i = 0; i<_activitesRealisees.size();i++)
			{
				((EugesActRealise)_activitesRealisees.get(i)).genereTabProduitOut(iteration);
			}
			iteration.write("</table><br>");
			iteration.write("<table cellspacing='0'cellpadding='0' border='1' bordercolor='black' width='100%'><tr><td>Produit en entr&eacute;e</td></tr>");
			for (int i = 0; i<_activitesRealisees.size();i++)
			{
				((EugesActRealise)_activitesRealisees.get(i)).genereTabProduitIn(iteration);
			}
			iteration.write("</table><br>");
			iteration.close();
		} catch (IOException e) {
			System.out.println (e);
			e.printStackTrace();
		}	
	}
	
}
