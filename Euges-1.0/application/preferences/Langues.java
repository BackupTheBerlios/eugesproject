/*
 * Created on 23 nov. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package application.preferences;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

import org.eclipse.swt.widgets.List;

import configuration.Config;
import donnees.preferences.Traduction;


/**
 * Classe Control de la classe Langues
 * Permet d'effectuer les traitements liés à l'ihm de l'onglet langues des préférences
 * @author Nicolas Broueilh *
 */
public class Langues {
	
	private Vector traductions = new Vector();
		
	public Langues() {
	}
	
	/**
	 * Methode permettant de remplir la liste des traductions lors de l'ouverture de l'onglet langue ou après ajout d'une nouvelle traduction
	 * @param liste la liste
	 */
	public void remplirListeTraduction(List liste) {
		//Le futur contenu de la liste
		String [] contenuListe;
		remplirVecteurTraduction();
		//Remplir le contenu de la liste à partir des traductions contenu dans le vecteur traduction
		contenuListe = new String[traductions.size()];
		for (int i=0;i<traductions.size();i++) {
			contenuListe[i]=((Traduction)traductions.get(i)).getLangue()+ " - "+((Traduction)traductions.get(i)).getPays();
		}
		liste.setItems(contenuListe);
		
	}
	/**
	 * Remplir le vecteur traduction à partir des répertoires contenus dans le répertoire des traductions
	 *
	 */
	public void remplirVecteurTraduction() {
		File file = new File(Config.config.getProperty("cheminTraduction"));
		//Les répertoires contenus dans le répertoire des traductions
		File [] repertoiresTraductions = file.listFiles(new filtreRepertoireTraduction());
		for (int i=0;i<repertoiresTraductions.length;i++) {
			//Permet de récupérer le fichier de description de la traduction Description.conf
			File [] description = repertoiresTraductions[i].listFiles(new filtreDescription());
			if (description.length>0) {
				Properties prop = new Properties();
				if (description[0].exists()){
					try {
						prop.load(new FileInputStream(description[0]));
						Traduction traduction = new Traduction(prop.getProperty("langue"),prop.getProperty("pays"),prop.getProperty("standardLangue"),prop.getProperty("standardPays"),repertoiresTraductions[i].getPath());
						traductions.add(traduction);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * Permet de changer la langue lorsqu'une nouvelle a été sélectionnée
	 * @param s :la chaine de caractere contenue dans la liste
	 */
	public void appliquerChangements(String s) {
		Traduction traduction = getTraduction(s);
		File file = new File(traduction.getRepertoire());
		//Permet de récupérer le fichier de description de la traduction Description.conf
		File [] description = file.listFiles(new filtreDescription());
		if (description.length>0) {
			Properties prop = new Properties();
			if (description[0].exists()){
				try {
					prop.load(new FileInputStream(description[0]));
					Config.config.setProperty("langue",prop.getProperty("standardLangue"));
					Config.config.setProperty("pays",prop.getProperty("standardPays"));
					Config.config.store(new FileOutputStream(Config.fichierConfig),"");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Permet de récupérer la traduction grâce à la chaîne sélectionnée dans la liste
	 * @param s : la chaine selectionnee dans la liste
	 * @return la traduction
	 */
	public Traduction getTraduction(String s) {
		String langue = s.substring(0,s.indexOf("-")-1);
		String pays = s.substring(s.indexOf("-")+2);
		for(int i=0;i<traductions.size();i++) {
			if (((Traduction)traductions.get(i)).getLangue().equals(langue) &&((Traduction)traductions.get(i)).getPays().equals(pays))
				return (Traduction)traductions.get(i);
		}
		return null;
	}
	
	
	public class filtreRepertoireTraduction implements FileFilter {
		public boolean accept(File pathname) {
			return (pathname.getName().length() == 2)&&(pathname.isDirectory());
		}
	}
	

	public class filtreDescription implements FileFilter {
		public boolean accept(File pathname) {
			return pathname.getName().equals("Description.conf");
		}
	}

	
	/**
	 * Permet de récupérer le vecteur contenant les traductions
	 * @return le vecteur contenant les traductions
	 */
	public Vector getTraductions() {
		return traductions;
	}

}
