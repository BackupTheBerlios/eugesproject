/*
 * Created on 25 nov. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package donnees.preferences;

/**
 * Classe représenatnt une traduction du logiciel
 * @author Nicolas Broueilh
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Traduction {
	
	/**La langue de la traduction*/
	public String _langue;
	
	/**Le pays de la traduction*/
	public String _pays;
	
	/**Le standard de la langue*/
	public String _standardLangue;

	/**Le standard de la langue*/
	public String _standardPays;
	
	/**Le répertoire contenant la traduction*/
	public String _repertoire;
	
	public Traduction(String langue, String pays,String standardLangue, String standardPays, String repertoire){
		_langue=langue;
		_pays=pays;
		_standardLangue = standardLangue;
		_standardPays = standardPays;
		_repertoire=repertoire;
	}
	
	/**
	 * Permet de récupérer la langue de la traduction
	 * @return la langue
	 */
	public String getLangue() {
		return _langue;
	}

	/**
	 * Permet de récupérer le pays de la traduction
	 * @return le pays
	 */	
	public String getPays() {
		return _pays;
	}

	/**
	 * Permet de récupérer le répertoire de la traduction
	 * @return le répertoire
	 */
	public String getRepertoire() {
		return _repertoire;
	}

}
