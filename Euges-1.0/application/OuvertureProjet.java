/*
 * Created on 15 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package application;

/**
 * @author HUT
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

import ihm.ArbrePrincipalIHM;
import ihm.FenetrePrincipaleIHM;
import ihm.vues.planIt.PlanItIHM;

import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import utilitaires.MySAXApp;
import configuration.Config;


public class OuvertureProjet {

	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	/**
	 * Contructeur.
	 */
	public OuvertureProjet(String uri) throws SAXException, IOException {
		try{
		XMLReader xr = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
		MySAXApp handler = new MySAXApp();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);


		FileReader r = new FileReader(uri);
		xr.parse(new InputSource(r));
		
		// mise à jour des boutons et de l'arbre
		FenetrePrincipaleIHM.menuItemFermer.setEnabled(true);
		FenetrePrincipaleIHM.menuItemEnregistrer.setEnabled(true);
		FenetrePrincipaleIHM.menuItemEnregistrerSous.setEnabled(true);
		FenetrePrincipaleIHM.menuItemEdition.setEnabled(true);
		FenetrePrincipaleIHM.itemFermer.setEnabled(true);
		FenetrePrincipaleIHM.itemEnregistrer.setEnabled(true);
		((PlanItIHM)FenetrePrincipaleIHM._vues.elementAt(0)).majIt(EugesElements._projet._listeIteration.size());
		FenetrePrincipaleIHM._vues.setVisible(0);
		PlanItIHM.majContenuWidgets();
		ArbrePrincipalIHM._tri.actualiser();
		}
		catch(SAXException e){
			Shell shell = new Shell();
			MessageBox msgErreur = new MessageBox(shell,SWT.ICON_ERROR); 
			msgErreur.setMessage(message.getString("texte")); 
			msgErreur.setText(message.getString("titre")); 
			msgErreur.open();
		}
	}
}
	
