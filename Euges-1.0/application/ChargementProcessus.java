/*
 * Created on 7 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package application;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import configuration.Config;
import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesProduit;
import donnees.eugesSpem.EugesRole;

/**
 * @author Nicolas Elbeze
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ChargementProcessus {

	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public ChargementProcessus(String file){
		try {
			// Parser XML
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbfactory.newDocumentBuilder();
			Document document = builder.parse(file);
			
			Element root = document.getDocumentElement();
			
			HashMap roleHashMap = new HashMap();
			HashMap activiteHashMap = new HashMap();
			HashMap produitHashMap = new HashMap();
			
			// MAJ de _cheminProcessus dans la classe projet
			EugesElements._projet.set_cheminProcessus(root.getChildNodes().item(1).getChildNodes().item(0).toString());
			
			
			// Chargement des roles
			Node nodeRole = root.getChildNodes().item(3);
			Node auxNodeRole;
			EugesRole auxRole;
			for(int i=1; i<nodeRole.getChildNodes().getLength(); i=i+2) {
				auxNodeRole = nodeRole.getChildNodes().item(i);
				String name = auxNodeRole.getChildNodes().item(3).getChildNodes().item(0).toString();
				String chemin = auxNodeRole.getChildNodes().item(9).getChildNodes().item(0).toString();
				auxRole = new EugesRole(name, chemin);
				EugesElements.listeRoles.addElement(auxRole);
			
				// ajout dans la HashMap des rôles
				roleHashMap.put(auxNodeRole.getChildNodes().item(1).getChildNodes().item(0).toString(), auxRole);
			}
			
			
			// Chargement des activités
			Node nodeActivite = root.getChildNodes().item(5);
			Node auxNodeActivite;
			EugesActivite auxActivite;
			for(int i=1; i<nodeActivite.getChildNodes().getLength(); i=i+2) {
				auxNodeActivite = nodeActivite.getChildNodes().item(i);
				String nom = auxNodeActivite.getChildNodes().item(3).getChildNodes().item(0).toString();
				String chemin = auxNodeActivite.getChildNodes().item(11).getChildNodes().item(0).toString();
				auxActivite = new EugesActivite(nom, chemin);
						
				// ajout dans la HashMap des activités
				activiteHashMap.put(auxNodeActivite.getChildNodes().item(1).getChildNodes().item(0).toString(), auxActivite);

				// associer le rôle à l'activité
				auxRole = (EugesRole) roleHashMap.get(auxNodeActivite.getChildNodes().item(5).getChildNodes().item(0).toString());
				if (auxActivite.getRole()==null)
					auxActivite.setRole(auxRole);
				
				EugesElements.listeActivites.addElement(auxActivite);
			}
			
			
			
			// Chargement des produits
			Node nodeProduit = root.getChildNodes().item(7);
			Node auxNodeProduit;
			EugesProduit auxProduit;
			
			for(int i=1; i<nodeProduit.getChildNodes().getLength(); i=i+2) {
				auxNodeProduit = nodeProduit.getChildNodes().item(i);
				String nom = auxNodeProduit.getChildNodes().item(3).getChildNodes().item(0).toString();
				String chemin = auxNodeProduit.getChildNodes().item(13).getChildNodes().item(0).toString();
				// Chargement des etats du produit courrant
				Vector etats = new Vector();
				for (int j = 1; j < auxNodeProduit.getChildNodes().item(11).getChildNodes().getLength(); j=j+2) {
					String auxEtat = auxNodeProduit.getChildNodes().item(11).getChildNodes().item(j).getChildNodes().item(0).toString();
					etats.add(auxEtat);
				}
				
				auxProduit = new EugesProduit(nom, chemin, etats);
				EugesElements.listeProduits.addElement(auxProduit);
				
				// ajout dans la HashMap des produits
				produitHashMap.put(auxNodeProduit.getChildNodes().item(1).getChildNodes().item(0).toString(), auxProduit);
				
				
				// ajout du produit en entrées des activités 
				EugesActivite auxInputAct;
				Node auxNodeActIn;
				for(int j=1; j<auxNodeProduit.getChildNodes().item(7).getChildNodes().getLength() ; j=j+2) {
					auxNodeActIn = auxNodeProduit.getChildNodes().item(7).getChildNodes().item(j);
					auxInputAct = (EugesActivite) activiteHashMap.get(auxNodeActIn.getChildNodes().item(0).toString());
					auxInputAct.ajouterProduitIn(auxProduit);
				}
				
				
				// ajout du produit en sorties des activités 
				EugesActivite auxOutputAct;
				Node auxNodeActOut;
				for(int j=1; j<auxNodeProduit.getChildNodes().item(9).getChildNodes().getLength() ; j=j+2) {
					auxNodeActOut = auxNodeProduit.getChildNodes().item(9).getChildNodes().item(j);
					auxOutputAct = (EugesActivite) activiteHashMap.get(auxNodeActOut.getChildNodes().item(0).toString());
					auxOutputAct.ajouterProduitOut(auxProduit);
				}
				
			}
		}
		catch (Exception e) {
				MessageBox msg = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_ERROR);
				msg.setText(message.getString("ChargementProcessus.erreur"));
				msg.setMessage(message.getString("ChargementProcessus.erreurChamps"));
				msg.open();
				System.out.println("Erreur");
		}
	}
}
