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

import utilitaires.CopierFichier;
import utilitaires.EugesNavigateur;
import donnees.eugesSpem.EugesPersonne;

/**
 * @author will
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class GenererSite {

	public GenererSite(String chemin,String check){
			File FileIndex = new File (chemin + "/index.htm");
			/*File FileMenu  = new File (chemin+"/menu.htm");
			 File FilePrincipale  = new File (chemin+"/principale.htm");
			 File FileTitre  = new File (chemin+"/titre.htm");*/
			
			//Creation du repertoire au cas ou l'utilisateur 
			//l'aurait supprimé durant l'execution du programme
			File rep = new File(chemin);
			rep.mkdir();
			
			try {
				//Génération de l'index : 
				FileWriter fichierIndex = new FileWriter (FileIndex);
				BufferedWriter index = new BufferedWriter (fichierIndex);
				EugesElements._projet.genereIndex(index);
				EugesElements._projet.genereTitre(index);
				
				genereMenu(index);
				EugesElements._projet.generePrincipale(index);
				index.close();
				
	//			Création du repertoire contenant les produits :
				File repProduits = new File(chemin+"/produits");
				repProduits.mkdir();
				
				//Génération des pages d'itérations : 
				File repIterations = new File(chemin+"/iterations");
				repIterations.mkdir();
				
				EugesElements._projet.genereIterations(chemin);
				
				//Création du repertoire contenant les images :
				File repImages = new File(chemin+"/images");
				repImages.mkdir();
				
				//Copie des images :
				CopierFichier.copyFile("configuration/images/actor.gif",chemin+"/images/actor.gif");
				CopierFichier.copyFile("configuration/images/activite.gif",chemin+"/images/activite.gif");
				CopierFichier.copyFile("configuration/images/role.gif",chemin+"/images/role.gif");
				CopierFichier.copyFile("configuration/images/produit.gif",chemin+"/images/produit.gif");
				CopierFichier.copyFile("configuration/images/it.gif",chemin+"/images/it.gif");
				CopierFichier.copyFile("configuration/images/logo2.png",chemin+"/images/logo2.png");
				CopierFichier.copyFile("configuration/images/iconeAngle.gif",chemin+"/images/iconeAngle.gif");
	
				if (check.equals("1")){
					CopierFichier.copyFile("configuration/images/style_grey.css",chemin+"/style.css");
					CopierFichier.copyFile("configuration/images/style_grey.css",chemin+"/iterations/style.css");
				}
				else
				{
					CopierFichier.copyFile("configuration/images/style_blue.css",chemin+"/style.css");
					CopierFichier.copyFile("configuration/images/style_blue.css",chemin+"/iterations/style.css");
				}
				
				//Ouverture du site dans le navigateur d'Euges :
				new EugesNavigateur(chemin+"/index.htm");
				
			} catch (IOException e) {
				System.out.println (e);
				
			}
		}

	public void genereMenu(BufferedWriter buffer) {
		try {
			buffer.write("<div class='menu'>\n<table>\n<tr>\n<td><img src='images/it.gif'><b>Itérations</b></td>\n</tr>\n");
			
			EugesElements._projet.genereMenuIt(buffer);
			
			/*buffer.write("<tr><td><img src='images/activite.gif'>Activités</b></td>\n</tr>");
			 for (int i = 0; i<listeActivites.size();i++)
			 {
			 ((EugesActivite)listeActivites.get(i)).genereMenu(buffer);
			 }
			 buffer.write("<tr><td><img src='images/produit.gif'>Produits</tr>");
			 for (int i = 0; i<listeProduits.size();i++)
			 {
			 ((EugesProduit)listeProduits.get(i)).genereMenu(buffer);
			 }
			 buffer.write("<tr><td><img src='images/role.gif'>Roles</td></tr>");
			 for (int i = 0; i<listeRoles.size();i++)
			 {
			 ((EugesRole)listeRoles.get(i)).genereMenu(buffer);
			 }*/
			buffer.write("<tr><td><img src='images/actor.gif'>Personnes</td></tr>");
			for (int i = 0; i<EugesElements.listePersonnes.size();i++)
			{
				((EugesPersonne)EugesElements.listePersonnes.get(i)).genereMenu(buffer);
			}
			buffer.write("</table>\n</div>");
			
		} catch (IOException e) {
			System.out.println (e);
			e.printStackTrace();
		}
	}

	public static void genereMenu2(BufferedWriter buffer,String chemin) {
		try {
			buffer.write("<div class='menu'>\n<table>\n<tr>\n<td><img src='../images/it.gif'><b>Itérations</b></td>\n</tr>\n");
			
			EugesElements._projet.genereMenuIt2(buffer);
			
			/*buffer.write("<tr><td><img src='../images/activite.gif'>Activités</b></td>\n</tr>");
			 for (int i = 0; i<listeActivites.size();i++)
			 {
			 ((EugesActivite)listeActivites.get(i)).genereMenu(buffer);
			 }
			 buffer.write("<tr><td><img src='../images/produit.gif'>Produits</tr>");
			 for (int i = 0; i<listeProduits.size();i++)
			 {
			 ((EugesProduit)listeProduits.get(i)).genereMenu(buffer);
			 }
			 buffer.write("<tr><td><img src='../images/role.gif'>Roles</td></tr>");
			 for (int i = 0; i<listeRoles.size();i++)
			 {
			 ((EugesRole)listeRoles.get(i)).genereMenu(buffer);
			 }*/
			buffer.write("<tr><td><img src='../images/actor.gif'>Personnes</td></tr>");
			for (int i = 0; i<EugesElements.listePersonnes.size();i++)
			{
				((EugesPersonne)EugesElements.listePersonnes.get(i)).genereMenu(buffer);
			}
			buffer.write("</table>\n</div>");
			
		} catch (IOException e) {
			System.out.println (e);
			e.printStackTrace();
		}
	}
}
