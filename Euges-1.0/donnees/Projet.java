/*
 * Created on 29 déc. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package donnees;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import utilitaires.MyDate;

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Projet {
	//attributs de la classe
	
	
	private String _nomProjet;
	private MyDate _dateDebut;
	private MyDate _dateFin;
	private String _repDestination;
	private String _processus;
	private String _cheminProcessus;
	private String _description;
	//liste des itérations du projet
	//fonction d'ordre sur l'ensemble
	private Comparator ordre = new Comparator() {
		public int compare(Object o1, Object o2) {
			return ((Iteration)o1).get_dateDebut().compare(((Iteration)o2).get_dateDebut());
		}
	  };
	public SortedSet _listeIteration = new TreeSet(ordre);
	
	

	/**
	 * 
	 * créer un nouveau projet
	 * 
	 * @param nomProjet
	 * @param dateDebut
	 * @param dateFin
	 * @param repDestination
	 * @param processus
	 * @param description
	 */
	public Projet(String nomProjet, MyDate dateDebut, MyDate dateFin,
				          String repDestination, String processus,	String description) {
		_nomProjet=nomProjet;
		_dateDebut=dateDebut;
		_dateFin=dateFin;
		_repDestination=repDestination;
		_processus=processus;
		_description=description;
		_listeIteration.add(new Iteration(dateDebut, dateFin));
	}

	/**
	 * 
	 * créer un nouveau projet
	 * 
	 * @param nomProjet
	 * @param dateDebut
	 * @param dateFin
	 * @param repDestination
	 * @param processus
	 * @param cheminProcessus
	 * @param description
	 */
	public Projet(String nomProjet, MyDate dateDebut, MyDate dateFin,
				  String repDestination, String processus,	String cheminProcessus, 
				  String description) {
		_nomProjet=nomProjet;
		_dateDebut=dateDebut;
		_dateFin=dateFin;
		_repDestination=repDestination;
		_processus=processus;
		_cheminProcessus = cheminProcessus;
		_description=description;
		_listeIteration.add(new Iteration(dateDebut, dateFin));
	}
	
	/**
	 * 	ajout d'une nouvelle iteration
	 * 
	 * @param itSelection itération à découper
	 * @param dateFin fin de la nouvelle itération
	 */
	public void ajouterIteration(Iteration itSelection, MyDate dateFin){
		Iteration nouvelleIteration = new Iteration(dateFin, itSelection.get_dateFin());
		itSelection.set_dateFin(dateFin);
		_listeIteration.add(nouvelleIteration);
		numeroterIteration();
	}
	/**
	 * numerote les itérations
	 *
	 */
	public void numeroterIteration(){
		int i=0;
		for (Iterator iter = _listeIteration.iterator(); iter.hasNext();) {
			((Iteration) iter.next()).set_numIt(i++);
		}
	}
	/**
	 * supprime une itération en fonction de ses dates de début et fin
	 * 
	 * @param dateDebut
	 * @param dateFin
	 */
	public void supprimerIteration(MyDate dateDebut, MyDate dateFin){
		_listeIteration.remove(new Iteration(dateDebut, dateFin));
	}
	/**
	 * supprime une iteration a partir de son instance
	 * 
	 * @param i
	 */
	public void supprimerIteration(Iteration iteration){
		_listeIteration.remove(iteration);
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
	 * @return Returns the _description.
	 */
	public String get_description() {
		return _description;
	}

	/**
	 * @param _description The _description to set.
	 */
	public void set_description(String _description) {
		this._description = _description;
	}

	/**
	 * @return Returns the _nomProjet.
	 */
	public String get_nomProjet() {
		return _nomProjet;
	}

	/**
	 * @param projet The _nomProjet to set.
	 */
	public void set_nomProjet(String projet) {
		_nomProjet = projet;
	}

	/**
	 * @return Returns the _processus.
	 */
	public String get_processus() {
		return _processus;
	}

	/**
	 * @param _processus The _processus to set.
	 */
	public void set_processus(String _processus) {
		this._processus = _processus;
	}

	/**
	 * @return Returns the _cheminProcessus.
	 */
	public String get_cheminProcessus() {
		return _cheminProcessus;
	}

	/**
	 * @param processus The _cheminProcessus to set.
	 */
	public void set_cheminProcessus(String processus) {
		_cheminProcessus = processus;
	}
	
	
	/**
	 * @return Returns the _repDestination.
	 */
	public String get_repDestination() {
		return _repDestination;
	}

	/**
	 * @param destination The _repDestination to set.
	 */
	public void set_repDestination(String destination) {
		_repDestination = destination;
	}

	/**
	 * @return Returns the _listeIteration.
	 */
	public SortedSet get_listeIteration() {
		return _listeIteration;
	}
	/**
	 * renvoie l'instance de l'iteration demandée
	 * 
	 * @param num numéro de l'itération
	 * @return null si l'instance n'existe pas sinon l'iteration
	 */
	public Iteration getIteration(int num){
		int i=0;
		Iterator iter = _listeIteration.iterator();
		if (num>_listeIteration.size())
			return null;
		while (i!=num && iter.hasNext()) {
			iter.next();
			i++;
		}
		return (Iteration)iter.next();
	}
	/**
	 * permet de supprimer une iteration en fonction de son numero
	 * @param num numero de l'itartion a supprimer
	 */
	public void supprimerIteration(int num){
		if (num!=0 && num!=_listeIteration.size()-1){
			//pour conserver la logique des dates, la date de debut de l'it suivante est egale a la date de fin de l'it precedente
			getIteration(num+1).set_dateDebut(getIteration(num-1).get_dateFin());
			//literation courante est supprimer
			_listeIteration.remove(getIteration(num));
			//on renumerote les iteration
			numeroterIteration();
		}
	}
	/**
	 * supprime toutes les iterations et remet en place l'iteration de debut
	 * correspondant a la date de debut du projet et la date de fin
	 *
	 */
	public void reinitialiserIterations(){
		//recuperation de la date de debut et de la date de fin
		MyDate debut = ((Iteration)_listeIteration.first()).get_dateDebut();
		MyDate fin = ((Iteration)_listeIteration.last()).get_dateFin();
		//suppression de tous les elements
		_listeIteration.clear();
		//ajout de l'iteration de depart
		_listeIteration.add(new Iteration(debut, fin));
	}
	/**
	 * getNombreIteration
	 * @return le nombre d'itération dans la projet
	 */
	public int getNombreIteration(){
		return _listeIteration.size();
	}
	/**
	 * @param ecriture
	 */
	public void sauvegarderAssociations(BufferedWriter ecriture) {
		try {
			//sauvegarde des itérations
			for (Iterator iter = _listeIteration.iterator(); iter.hasNext();)
			{
				ecriture.write("<_association>\n");
				((Iteration)iter.next()).sauvegarderAssociation(ecriture);
				ecriture.write("</_association>\n");
			}
		} catch (IOException e) {
			System.out.println (e);
			e.printStackTrace();
		}
		
		
	}
	
	public void sauvegardePartielle(BufferedWriter ecriture) {
		try {
			ecriture.write("<Projet _nomProjet=\""+this.get_nomProjet()+"\" _dateDebut =\""+this.get_dateDebut()+"\" _dateFin=\""+this.get_dateFin()+"\" _repDestination=\""+this.get_repDestination()+"\" _processus=\""+this.get_processus()+"\" _description=\""+this.get_description()+"\"/>\n");
		} catch (IOException e) {
			System.out.println (e);
			e.printStackTrace();
		}
		
		
	}
	
	public void sauvegardeIterations(BufferedWriter ecriture) {
			//sauvegarde des itérations
			for (Iterator iter = _listeIteration.iterator(); iter.hasNext();)
			{
				((Iteration)iter.next()).sauvegardePartielle(ecriture);
			}		
	}
		
	public void genereIndex(BufferedWriter buffer) {
		try {
			buffer.write("<html>\n<head>\n<title>Euges - " + this.get_nomProjet() + "</title>\n");
			buffer.write("<META HTTP-EQUIV= 'Content-Style-Type' CONTENT='text/css'>\n<LINK rel='stylesheet' type='text/css' href='style.css'>\n</head>");
		} catch (IOException e) {
			System.out.println (e);
			e.printStackTrace();
		}
	}
	
	public void generePrincipale(BufferedWriter buffer) {
		try {
			buffer.write("<div class='common'>\n<body>\n<table>\n<tr>\n");
			buffer.write("<td class='dc'><h2 class='right'><b>Date de début de projet</b> : </h2></td>\n<td><h2 class='left'>"+this.get_dateDebut()+"</h2></td>\n");
			buffer.write("</tr>\n<tr>\n<td class='dc'><h2 class='right'><b>Date de fin de projet</b> : </h2></td>\n<td><h2 class='left'>" + this.get_dateFin()+"</h2></td>\n");
			buffer.write("</tr>\n<tr>\n<td class='dc'><h2 class='right'><b>Description du projet</b> : </h2></td>\n<td><h2 class='left'>"+this.get_description()+"</h2></td></tr>\n</table>");
			buffer.write("</body>\n</div>\n</html>");
			} catch (IOException e) {
			System.out.println (e);
			e.printStackTrace();
		}
	}
	
	
	
	public void genereTitre(BufferedWriter buffer) {
		try {
			
			buffer.write("<div>\n<table>\n<tr>\n<td width='18%'>\n<img src='images/logo2.png'>\n</td>\n");
			buffer.write("<td width='82%'>\n<b><a class='titre' href='index.htm'><center>Euges</center></a></b>\n");
			buffer.write("</td>\n</tr>\n</table>\n</div>\n<br>\n");
			
			/*buffer.write("<html>\n<head>\n</head>\n<body>\n");
			buffer.write("<table border='0' width='100%' height='100%'>\n<tr width='100%' height='100%'>\n<td width='18%' height='100%' >\n<img src='images/logo2.png' width='50%' height='20%'>\n</td>\n<td width='82%' height='100%'>\n<div align='center'><b><a href='principale.htm' target='dyn'><h2>"+this.get_nomProjet()+"</h2></a></b></div>\n</td>\n</tr>");
			buffer.write("</body></html>");*/
			} catch (IOException e) {
			System.out.println (e);
			e.printStackTrace();
		}
	}
	
	public void genereTitre2(BufferedWriter buffer) {
		try {
			
			buffer.write("<div>\n<table>\n<tr>\n<td width='18%'>\n<img src='../images/logo2.png'>\n</td>\n");
			buffer.write("<td width='82%'>\n<b><a class='titre' href='../index.htm'><center>Euges</center></a></b>\n");
			buffer.write("</td>\n</tr>\n</table>\n</div>\n<br>\n");
			
			
		} catch (IOException e) {
			System.out.println (e);
			e.printStackTrace();
		}
	}
	
	public void genereMenuIt(BufferedWriter buffer) {
		
		for (Iterator iter = _listeIteration.iterator(); iter.hasNext();)
		{
			((Iteration)iter.next()).genereLigneMenu(buffer);
		}
	}
	
	public void genereMenuIt2(BufferedWriter buffer) {
		
		for (Iterator iter = _listeIteration.iterator(); iter.hasNext();)
		{
			((Iteration)iter.next()).genereLigneMenu2(buffer);
		}
	}
	
	public void genereIterations(String chemin) {
		for (Iterator iter = _listeIteration.iterator(); iter.hasNext();)
		{
			
			((Iteration)iter.next()).genereIteration(chemin);
		}
	}
	
}
