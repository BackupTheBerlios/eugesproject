/*
 * Created on 4 déc. 2003
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
	
	// fonction pour sauvegarder les nouveaux icônes
	public void sauvegarde(String [] tabNouveauxChemins, String [] tabIcones){
		
			// répertoire où sont les icônes
		File cheminIcone = new File(Config.config.getProperty("cheminIcone"));
		
			// on récupère le chemin du nouveau répertoire servant à mémoriser les anciens icônes 
		File repertoireSauvegardeIcones = new File(cheminIcone.getAbsolutePath() + "Old\\");
				
			// on crée ce répertoire
		repertoireSauvegardeIcones.mkdir();
		
		   // liste des fichiers dans le répertoire actuel des icônes
		File[] tabCheminIcone = cheminIcone.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				File file = new File(dir + "\\" + name);
				boolean accept = false;
				String extension = name.substring(name.lastIndexOf('.')+1);
				accept = extension.equals("jpg") || extension.equals("ico") || extension.equals("rpm") || extension.equals("png");
				return (file.isFile() && accept);
			}
		});
		   // on copie tous les anciens icônes dans le répertoire xxxOld
		for (int i=0; i<tabCheminIcone.length; i++){
			File fileDest = new File(repertoireSauvegardeIcones + "\\" + tabCheminIcone[i].getName());
			
			CopierFichier.copyFile(tabCheminIcone[i], fileDest);
		
		}
		
			// on parcours la liste des icônes
		for (int i=0; i<tabNouveauxChemins.length; i++){
				// si dans le tableau tabNouveauIcones il y a un chemin, c'est qu'on veut changer l'icône
			if (tabNouveauxChemins[i] != null){
					// fichier nouvel icône
				File nouveauIcone = new File(tabNouveauxChemins[i]);
				
					// fichier ancien icône
				File ancienIcone = new File(Config.config.getProperty("cheminIcone") + tabIcones[i] + ".ico");
				
					// Les changements
				
					//si le nouvel icône est différent de l'ancien
				if (!(nouveauIcone.getAbsolutePath().equals(ancienIcone.getAbsolutePath()))){
						// si le nouvel icône se trouve dans le répertoire des icônes d'Euges
					if (cheminIcone.getAbsolutePath().equals(nouveauIcone.getParent())){
							 // on copie l'icône depuis le répertoire de sauvegarde
						CopierFichier.copyFile(repertoireSauvegardeIcones + "//" + nouveauIcone.getName(),
								Config.config.getProperty("cheminIcone") + tabIcones[i] + ".ico");
					}
					else { 
							// sinon copie du fichier du nouvel icône dans le répertoire des icônes
						CopierFichier.copyFile(tabNouveauxChemins[i],
								Config.config.getProperty("cheminIcone") + tabIcones[i] + ".ico");
					}
				}
			}
		}
			// tableau des icônes contenus dans le répertoire iconesOld
		File [] tabIconesRepertoiresOld = repertoireSauvegardeIcones.listFiles();
			// suppression des fichiers du répertoire
		for (int i=0; i < tabIconesRepertoiresOld.length; i++){
			tabIconesRepertoiresOld[i].delete();
		}
			// suppression du répertoire ou on a copié les icônes
		repertoireSauvegardeIcones.delete();
	}

}
