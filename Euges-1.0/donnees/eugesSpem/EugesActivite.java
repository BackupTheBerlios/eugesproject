 /*
 * Created on 1 janv. 2004
 *
 */
package donnees.eugesSpem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import donnees.spem.process.structure.Activity;

/**
 * Cette classe représente définit une activité telle qu'elle sera représentée sous le logiciel Euges
 * C'est à dire une activité définie par le SPEM avec des informations supplémentaires
 * @author Nicolas Broueilh
 */
public class EugesActivite extends Activity{	

	private String _cheminActivite;
	
	//vecteur d'activites realisees 
	private Vector _activites = new Vector();
	private EugesRole _role;
	private Vector _produitsIn = new Vector();
	private Vector _produitsOut = new Vector();

	public EugesActivite () {
		super();
	}
	
	public EugesActivite (String name) {
		super(name);
	}

	public EugesActivite (String name, String chemin) {
		super(name);
		_cheminActivite = chemin;
	}
	
	public String toString() {
		return getName();
	}
	

	/**
	 * @return Returns the _cheminActivite.
	 */
	public String get_cheminActivite() {
		return _cheminActivite;
	}

	/**
	 * @param activite The _cheminActivite to set.
	 */
	public void set_cheminActivite(String activite) {
		_cheminActivite = activite;
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
	

	public void setRole(EugesRole r) {
			_role=r;
	}
	public EugesRole getRole() {
		return _role;
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
	/**
	 * @param ecriture
	 */
	public void sauvegarder(BufferedWriter ecriture) {
		try {
			ecriture.write("<EugesActivite>\n");
			//sauvegarde de _activites
			for (int i = 0; i<_activites.size();i++)
			{
				((EugesActRealise)_activites.get(i)).sauvegarder(ecriture);
			}
			//sauvegarde _de roles
			_role.sauvegarder(ecriture);
			ecriture.write("</EugesActivite>\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sauvegardePartielle(BufferedWriter ecriture) {
		try {
			ecriture.write("<EugesActivite name=\""+this.getName()+"\"/>\n");
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
	
	public void genereMenu(BufferedWriter buffer) {
		try {
			buffer.write("<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+this.getName()+"</td></tr>");
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
}
