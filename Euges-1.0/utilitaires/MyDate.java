/*
 * Created on 3 déc. 2003
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package utilitaires;


/**
 * @author will
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class MyDate {
	private int _jour;
	private int _mois;
	private int _annee;
	/**
	 * constructeur
	 * 
	 * @param jour
	 * @param mois
	 * @param annee
	 */
	public MyDate(int jour, int mois, int annee) {
		_jour = jour;
		_mois = mois;
		_annee = annee;
	}
	/**
	 * constructeur de classe construit un objet MyDate a partir d'une chaine
	 * de caractères
	 * 
	 * @param date
	 */
	public MyDate(String date) {
		_jour = Integer.parseInt(date.substring(0, 2));
		_mois = Integer.parseInt(date.substring(3, 5));
		_annee = Integer.parseInt(date.substring(6, date.length()));
	}
	/**
	 * retourne si une date est valide
	 */
	public boolean estValide(){
		if (_jour <= 0 || _jour>=32 || _mois<=0 || _mois >= 13)
		{
			return false;
		}	
		if (_jour==31 && (_mois==2 || _mois==4 || _mois== 6 || _mois==9 || _mois==11))
		{
			return false;
		}
		if (_jour==30 && (_mois==2))
		{	
			return false;
		}
		
		if (_jour== 29 && _mois == 2 && !estBissextile())
		{
			return false;
		}
		
		return true;
	}
	/**
	 * dit si une date est bissextile ou pas
	 * 
	 * @return boolean
	 */
	public boolean estBissextile() {
		if (_annee % 400 == 0) {
			return true;
		} else {
			if (_annee % 100 == 0) {
				return false;
			} else {
				if (_annee % 4 == 0) {
					return true;
				} else {
					return false;
				}
			}
		}

	}
	/**
	 * donne le jour de la semaine pour une date donnée
	 * @return boolean
	 */
	public int donneJour(){
//		int m, a, j, s, JD, JS;
//		
//		j = _jour;
//		m = _mois;
//		a = _annee;
//		if(m<3) m += 12;
//		else a -= 1;
//		s = a / 100;
//		JD = (int) (1720996.5 - s + s / 4 + (365.25*a) + (30.6001*(m+1)) + j);
//		JD = JD - (JD/7)*7;
//		JS = JD %7;
//		JS += 2;
//		if(JS == 8) JS= 1;

		int[] nbJoursMois = {0,31,59,90,120,151,181,212,243,273,304,334};
		int[] nbJoursMoisBissex = {0,31,60,91,121,152,182,213,244,274,305,335};
		
		int a = _annee - 2000;
		int b = a / 4;
		int c = a / 100;
		
		int d = mod((a + b - c), 7);
		int e;

		if ((((_annee % 4) == 0)&&((_annee % 100) != 0)) ||
			(((_annee % 400) == 0)&&((_annee % 100) == 0))){
			e = mod((nbJoursMoisBissex[_mois - 1] + _jour - 1), 7);
		}
		else {
			e = mod((nbJoursMois[_mois - 1] + _jour), 7);
		}
		
		
		int res = mod(d + e - 2, 7);
		
		return res;
	}
	
	public static int mod(int val, int m) {
		int retour = val % m;
		
		if (retour < 0)
			retour = m + retour;
		
		return retour;
	}
	
	/**
	 * compare 2 dates
	 * 
	 * @param date
	 * @return 0 si les 2 dates sont égales,
	 *         <0 si la premiere est inférieure sinon >0
	 */
	public int compare(MyDate date) {
		//test des années
		if (get_annee() < date.get_annee())
			return -1;
		else if (get_annee() > date.get_annee())
			return +1;
		else {
			//test des mois
			if (get_mois() < date.get_mois())
				return -1;
			else if (get_mois() > date.get_mois())
				return +1;
			else {
				//test des jours
				if (get_jour() < date.get_jour())
					return -1;
				else if (get_jour() > date.get_jour())
					return +1;
			}
		}
		return 0;
	}
	public String toString() {
		String jour;
		String mois;
		//mise au format standard du jour
		if (_jour < 10)
			jour = "0" + (new Integer(_jour).toString());
		else
			jour = (new Integer(_jour).toString());
		//mise au format standard du mois
		if (_mois < 10)
			mois = "0" + (new Integer(_mois).toString());
		else
			mois = (new Integer(_mois).toString());

		return jour + "/" + mois + "/" + (new Integer(_annee).toString());
	}

	/**
	 * @return Returns the _annee.
	 */
	public int get_annee() {
		return _annee;
	}

	/**
	 * @return Returns the _jour.
	 */
	public int get_jour() {
		return _jour;
	}

	/**
	 * @return Returns the _mois.
	 */
	public int get_mois() {
		return _mois;
	}

}
