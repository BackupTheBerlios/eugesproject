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

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import utilitaires.MySAXApp;


public class OuvertureProjet {

	/**
	 * Contructeur.
	 */
	public OuvertureProjet(String uri) throws SAXException, IOException {
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
		PlanItIHM.majContenuWidgets();
		ArbrePrincipalIHM._tri.actualiser();
	}
}
	
