/*
 * Created on 7 f�vr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package application;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesProduit;
import donnees.eugesSpem.EugesRole;

/**
 * @author Nicolas
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ChargementProcessus {

	
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
			
			
			// Chargement des roles
			Node nodeRole = root.getChildNodes().item(1);
			Node auxNodeRole;
			EugesRole auxRole;
			for(int i=1; i<nodeRole.getChildNodes().getLength(); i=i+2) {
				auxNodeRole = nodeRole.getChildNodes().item(i);
				auxRole = new EugesRole(auxNodeRole.getChildNodes().item(3).getChildNodes().item(0).toString());
				//System.out.println("Ajout du role : " + auxNodeRole.getChildNodes().item(3).getChildNodes().item(0));
				EugesElements.listeRoles.addElement(auxRole);
			
				// ajout dans la HashMap des r�les
				roleHashMap.put(auxNodeRole.getChildNodes().item(1).getChildNodes().item(0).toString(), auxRole);
			}
			
			
			// Chargement des activit�s
			Node nodeActivite = root.getChildNodes().item(3);
			Node auxNodeActivite;
			EugesActivite auxActivite;
			for(int i=1; i<nodeActivite.getChildNodes().getLength(); i=i+2) {
				auxNodeActivite = nodeActivite.getChildNodes().item(i);
				auxActivite = new EugesActivite(auxNodeActivite.getChildNodes().item(3).getChildNodes().item(0).toString());
				//System.out.println("Ajout d'une activit� : " + auxNodeActivite.getChildNodes().item(3).getChildNodes().item(0));
						
				// ajout dans la HashMap des activit�s
				activiteHashMap.put(auxNodeActivite.getChildNodes().item(1).getChildNodes().item(0).toString(), auxActivite);

				// associer le r�le � l'activit�
				auxRole = (EugesRole) roleHashMap.get(auxNodeActivite.getChildNodes().item(5).getChildNodes().item(0).toString());
				if (!auxActivite.contientRole(auxRole))
					auxActivite.ajouterRole(auxRole);
				
				//System.out.println("Association de l'activit� au role : " + auxRole.getName());
				
				EugesElements.listeActivites.addElement(auxActivite);
			}
			
			
			
			// Chargement des produits
			Node nodeProduit = root.getChildNodes().item(5);
			Node auxNodeProduit;
			EugesProduit auxProduit;
			
			for(int i=1; i<nodeProduit.getChildNodes().getLength(); i=i+2) {
				auxNodeProduit = nodeProduit.getChildNodes().item(i);
				auxProduit = new EugesProduit(auxNodeProduit.getChildNodes().item(3).getChildNodes().item(0).toString());
				//System.out.println("Ajout du produit : " + auxNodeProduit.getChildNodes().item(3).getChildNodes().item(0));
				EugesElements.listeProduits.addElement(auxProduit);
				//EugesElements.ajouteEugesProduit(auxProduit.getName(), " 1.0");
				
				// ajout dans la HashMap des produits
				produitHashMap.put(auxNodeProduit.getChildNodes().item(1).getChildNodes().item(0).toString(), auxProduit);
				
				
				// ajout du produit en entr�es des activit�s 
				EugesActivite auxInputAct;
				Node auxNodeActIn;
				for(int j=1; j<auxNodeProduit.getChildNodes().item(7).getChildNodes().getLength() ; j=j+2) {
					auxNodeActIn = auxNodeProduit.getChildNodes().item(7).getChildNodes().item(j);
					auxInputAct = (EugesActivite) activiteHashMap.get(auxNodeActIn.getChildNodes().item(0).toString());
					auxInputAct.ajouterProduitIn(auxProduit);
//					System.out.println("Association : " + auxProduit.getName() + "-" + auxInputAct.getName() );
				}
				
				
				// ajout du produit en sorties des activit�s 
				EugesActivite auxOutputAct;
				Node auxNodeActOut;
				for(int j=1; j<auxNodeProduit.getChildNodes().item(9).getChildNodes().getLength() ; j=j+2) {
					auxNodeActOut = auxNodeProduit.getChildNodes().item(9).getChildNodes().item(j);
					auxOutputAct = (EugesActivite) activiteHashMap.get(auxNodeActOut.getChildNodes().item(0).toString());
					auxOutputAct.ajouterProduitOut(auxProduit);
//					System.out.println("Association : " + auxProduit.getName() + "-" + auxOutputAct.getName() );
				}
				
			}
		}
		catch (Exception e) {
				System.out.println("Erreur");
		}
	}
}