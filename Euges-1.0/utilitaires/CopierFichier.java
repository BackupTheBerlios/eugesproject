/*
 * Created on 30 nov. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package utilitaires;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Nicolas Broueilh
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CopierFichier {
	/**
	 * Permet de copier un répertoire.
	 */
	public static void copyDirectory(File srcDir, File dstDir) {
		dstDir.mkdirs();
		File[] files = srcDir.listFiles();
		for (int i=0; i < files.length; i++) {
			// on ne copie pas le fichier description.xml
			if (!files[i].getName().equals("description.xml")) {
				copyFile(files[i], new File(dstDir.getPath() + "/" + files[i].getName()));
			}
		}
		
		// si le répertoire de destination est vide
		// on le supprime
		if (dstDir.list().length == 0) {
			dstDir.delete();
		}
	}
	
	/**
	 * Permet de copier un fichier.
	 */
	public static void copyFile(String srcFile, String dstFile) {
		copyFile(new File(srcFile), new File(dstFile));
	}
	
	/**
	 * Permet de copier un fichier.
	 */
	public static void copyFile(File srcFile, File dstFile) {
		try {
			BufferedInputStream in =
			new BufferedInputStream(new FileInputStream(srcFile));
			BufferedOutputStream out =
			new BufferedOutputStream(new FileOutputStream(dstFile));
			byte buffer[] = new byte[1024*8];
			int count;
			
			while ((count = in.read(buffer)) != -1) {
				out.write(buffer, 0, count);
			}
			
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
