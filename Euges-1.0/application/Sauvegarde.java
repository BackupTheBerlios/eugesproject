/*
 * Created on 6 avr. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesProduit;
import donnees.eugesSpem.EugesRole;

/**
 * @author will
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sauvegarde {

	/**
	 * @param chemin
	 */
	public static void sauvegarde(String chemin) {
		
		File fichier = new File (chemin);
		try {
			FileWriter fichierEcriture = new FileWriter (fichier);
			BufferedWriter ecriture = new BufferedWriter (fichierEcriture);
			//ecriture des informations sur xml
			ecriture.write("<?xml version =\"1.0\" encoding=\"ISO-8859-1\"?>\n");
			//crétaion de la racine
			ecriture.write("<unProjet>\n");
			//enregistrement des caractéristiques de l'attribut projet
			ecriture.write("<_projet>\n");
			EugesElements._projet.sauvegardePartielle(ecriture);
			//enregistrement des roles
			ecriture.write("<_listeRoles>\n");
			for (int i = 0; i<EugesElements.listeRoles.size();i++)
			{
				((EugesRole)EugesElements.listeRoles.get(i)).sauvegarder(ecriture);
			}
			ecriture.write("</_listeRoles>\n");
			//enregistrement des personnes
			ecriture.write("<_listePersonnes>\n");
			for (int i = 0; i<EugesElements.listePersonnes.size();i++)
			{
				((EugesPersonne)EugesElements.listePersonnes.get(i)).sauvegardePartielle(ecriture);
			}
			ecriture.write("</_listePersonnes>\n");
			//enregistrement des produits
			ecriture.write("<_listeProduits>\n");
			for (int i = 0; i<EugesElements.listeProduits.size();i++)
			{
				((EugesProduit)EugesElements.listeProduits.get(i)).sauvegarder(ecriture);
			}
			ecriture.write("</_listeProduits>\n");
			//enregistrement des activités
			ecriture.write("<_listeActivites>\n");
			for (int i = 0; i<EugesElements.listeActivites.size();i++)
			{
				((EugesActivite)EugesElements.listeActivites.get(i)).sauvegardePartielle(ecriture);
			}
			ecriture.write("</_listeActivites>\n");
			//enregistrement des itérations
			ecriture.write("<_listeIteration>\n");
			EugesElements._projet.sauvegardeIterations(ecriture);
			ecriture.write("</_listeIteration>\n");
			//sauvegarde des associations
			ecriture.write("<_listeAssociations>\n");
			EugesElements._projet.sauvegarderAssociations(ecriture);
			ecriture.write("</_listeAssociations>\n");
			ecriture.write("</_projet>\n");
			ecriture.write("</unProjet>\n");
			ecriture.close();
			EugesElements.processusEnregistre = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//fonction qui sauvegarde
	public static void sauvegarde ()
	{
		sauvegarde (EugesElements._projet.get_repDestination()+"\\"+EugesElements._projet.get_nomProjet()+".egs");		
	}
}
