/*
 * Created on 4 d�c. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package application.preferences;

import java.io.File;
import java.io.FilenameFilter;

import utilitaires.CopierFichier;
import configuration.Config;

/**
 * @author Nicolas
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class IconesAPP {

	/**
	 * 
	 */
	public IconesAPP() {
	}
	
	// fonction pour sauvegarder les nouveaux ic�nes
	public void sauvegarde(String [] tabNouveauxChemins, String [] tabIcones){
		
			// r�pertoire o� sont les ic�nes
		File cheminIcone = new File(Config.config.getProperty("cheminIcone"));
		
			// on r�cup�re le chemin du nouveau r�pertoire servant � m�moriser les anciens ic�nes 
		File repertoireSauvegardeIcones = new File(cheminIcone.getAbsolutePath() + "Old\\");
				
			// on cr�e ce r�pertoire
		repertoireSauvegardeIcones.mkdir();
		
		   // liste des fichiers dans le r�pertoire actuel des ic�nes
		File[] tabCheminIcone = cheminIcone.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				File file = new File(dir + "\\" + name);
				boolean accept = false;
				String extension = name.substring(name.lastIndexOf('.')+1);
				accept = extension.equals("jpg") || extension.equals("ico") || extension.equals("rpm") || extension.equals("png");
				return (file.isFile() && accept);
			}
		});
		   // on copie tous les anciens ic�nes dans le r�pertoire xxxOld
		for (int i=0; i<tabCheminIcone.length; i++){
			File fileDest = new File(repertoireSauvegardeIcones + "\\" + tabCheminIcone[i].getName());
			
			CopierFichier.copyFile(tabCheminIcone[i], fileDest);
		
		}
		
			// on parcours la liste des ic�nes
		for (int i=0; i<tabNouveauxChemins.length; i++){
				// si dans le tableau tabNouveauIcones il y a un chemin, c'est qu'on veut changer l'ic�ne
			if (tabNouveauxChemins[i] != null){
					// fichier nouvel ic�ne
				File nouveauIcone = new File(tabNouveauxChemins[i]);
				
					// fichier ancien ic�ne
				File ancienIcone = new File(Config.config.getProperty("cheminIcone") + tabIcones[i] + ".ico");
				
					// Les changements
				
					//si le nouvel ic�ne est diff�rent de l'ancien
				if (!(nouveauIcone.getAbsolutePath().equals(ancienIcone.getAbsolutePath()))){
						// si le nouvel ic�ne se trouve dans le r�pertoire des ic�nes d'Euges
					if (cheminIcone.getAbsolutePath().equals(nouveauIcone.getParent())){
							 // on copie l'ic�ne depuis le r�pertoire de sauvegarde
						CopierFichier.copyFile(repertoireSauvegardeIcones + "//" + nouveauIcone.getName(),
								Config.config.getProperty("cheminIcone") + tabIcones[i] + ".ico");
					}
					else { 
							// sinon copie du fichier du nouvel ic�ne dans le r�pertoire des ic�nes
						CopierFichier.copyFile(tabNouveauxChemins[i],
								Config.config.getProperty("cheminIcone") + tabIcones[i] + ".ico");
					}
				}
			}
		}
			// tableau des ic�nes contenus dans le r�pertoire iconesOld
		File [] tabIconesRepertoiresOld = repertoireSauvegardeIcones.listFiles();
			// suppression des fichiers du r�pertoire
		for (int i=0; i < tabIconesRepertoiresOld.length; i++){
			tabIconesRepertoiresOld[i].delete();
		}
			// suppression du r�pertoire ou on a copi� les ic�nes
		repertoireSauvegardeIcones.delete();
	}

}
