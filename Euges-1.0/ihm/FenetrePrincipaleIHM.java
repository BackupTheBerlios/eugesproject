/*
 * Created on 24 nov. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;


import ihm.preferences.PreferencesIHM;
import ihm.vues.VuesIHM;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import utilitaires.EugesAide;
import utilitaires.GestionImage;
import utilitaires.MyDate;
import configuration.Config;


/**
 * @author Nicolas
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FenetrePrincipaleIHM {
		
		private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
//		private Display display = new Display();
		private final Shell shell;
	
			// définition des items de menus
		public static MenuItem menuItemFichier;
		public static MenuItem menuItemFermer;
		public static MenuItem menuItemCreer;
		public static MenuItem menuItemOuvrir;
		public static MenuItem menuItemEnregistrer;
		public static MenuItem menuItemEnregistrerSous;
		public static MenuItem menuItemQuitter;
		public static MenuItem menuItemEdition;
		public static MenuItem menuItemRoles;
		public static MenuItem menuItemActivites;
		public static MenuItem menuItemProduits;
		public static MenuItem menuItemIterations;
		public static MenuItem menuItemEquipe;
		public static MenuItem menuItemAffichage;
		public static MenuItem menuItemBarreOutils;
		public static MenuItem menuItemArbre;
		public static MenuItem menuItemPreferences;
		public static MenuItem menuItemAide;
		public static MenuItem menuItemOuvrirAide;
		public static MenuItem menuItemAPropos;
		
			// définition de items de la barre d'outils
		public static ToolItem itemNouveau;
		public static ToolItem itemOuvrir;	
		public static ToolItem itemFermer;
		public static ToolItem itemEnregistrer;
		public static ToolItem itemMail;
		public static ToolItem itemAide;
		//icones vues
		public static ToolItem itemToolIt;
		public static ToolItem itemToolGraph;
		public static ToolItem itemToolLine;
		
		// l'arbre à gauche
		public static ArbrePrincipalIHM tree;
		
		//liste des vues de l'application
		public static VuesIHM _vues; 
		//date du jour
		public static MyDate dateDuJour;
		
		public FenetrePrincipaleIHM(){
			final Display display = new Display();
			
			/**récuperation de la date du jour*/
			//on crée l'objet en passant en paramétre une chaîne representant le format
			SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy");
			//récupération de la date courante
			Date currentTime = new Date();
			//on crée la chaîne à partir de la date  
			String dateString = formatter.format(currentTime);
			dateDuJour=new MyDate(dateString);
			
			//initialisation de la bibliotheque d'images
			GestionImage images = new GestionImage(display);
			shell = new Shell(display);
			//fenetre de progression
			ProgressStart progressStart = new ProgressStart(display);
			progressStart.progression(10, "Initialisation fenêtre...");
			
			// Mise en place de l'affichage de la fenêtre
				//Titre de la fenêtre
			shell.setText("Euges");
				// Définition de la taille de la fenêtre
			shell.setSize(800, 600);
			shell.setImage(GestionImage._euges);
		
				// Définition des éléments
				// Le menu
			progressStart.progression(20, "Initialisation menu...");
			Menu menu = new Menu(shell, SWT.BAR);
			shell.setMenuBar(menu);
				// Menu fichier
			menuItemFichier = new MenuItem(menu, SWT.CASCADE);
			menuItemFichier.setText(message.getString("menu.fichier"));
				// Sous-menu de fichier
			Menu menuFichier = new Menu(shell, SWT.DROP_DOWN);
			menuItemFichier.setMenu(menuFichier);
		
			menuItemCreer = new MenuItem(menuFichier,SWT.PUSH);
			menuItemCreer.setText(message.getString("menu.fichier.nouveau"));
			menuItemCreer.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					AssistantIHM assistantIHM = new AssistantIHM(shell);
				}
			});
		
			menuItemOuvrir = new MenuItem(menuFichier,SWT.PUSH);
			menuItemOuvrir.setText(message.getString("menu.fichier.ouvrir"));
			menuItemOuvrir.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					ouvrir();
				}
			});
		
			menuItemFermer = new MenuItem(menuFichier,SWT.PUSH);
			menuItemFermer.setText(message.getString("menu.fichier.fermer"));
			menuItemFermer.setEnabled(false);
			menuItemFermer.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					System.out.println("Fermer le projet");
				}
			});
		
			MenuItem menuItemFichierSep1 = new MenuItem(menuFichier,SWT.SEPARATOR);
		
			menuItemEnregistrer = new MenuItem(menuFichier,SWT.PUSH);
			menuItemEnregistrer.setText(message.getString("menu.fichier.enregistrer"));
			menuItemEnregistrer.setEnabled(false);
			menuItemEnregistrer.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					System.out.println("Enregistrer projet en cours");
				}
			});
		
			menuItemEnregistrerSous = new MenuItem(menuFichier,SWT.PUSH);
			menuItemEnregistrerSous.setText(message.getString("menu.fichier.enregistrerSous"));
			menuItemEnregistrerSous.setEnabled(false);
			menuItemEnregistrerSous.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					enregistrerSous();
				}
			});
		
			MenuItem menuItemFichierSep2 = new MenuItem(menuFichier,SWT.SEPARATOR);
		
			menuItemQuitter = new MenuItem(menuFichier,SWT.PUSH);
			menuItemQuitter.setText(message.getString("menu.fichier.quitter"));
			menuItemQuitter.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					System.out.println("Quitter Euges");
					shell.close();
				}
			});	
		
				//	Menu edition
			 menuItemEdition = new MenuItem(menu, SWT.CASCADE);
			 menuItemEdition.setText(message.getString("menu.gestion"));
			 menuItemEdition.setEnabled(false);
				 // Sous-menu de edition
			 Menu menuEdition = new Menu(shell, SWT.DROP_DOWN);
			 menuItemEdition.setMenu(menuEdition);
			 
	
			 menuItemRoles = new MenuItem(menuEdition,SWT.PUSH);
			 menuItemRoles.setText(message.getString("menu.gestion.roles"));
			 menuItemRoles.addListener(SWT.Selection, new Listener(){
				 public void handleEvent(Event e){
					 System.out.println("Modifier rôles");
				 }
			 });
		 
			menuItemActivites = new MenuItem(menuEdition,SWT.PUSH);
			menuItemActivites.setText(message.getString("menu.gestion.activites"));
			menuItemActivites.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					System.out.println("Modifier activités");
				}
			});
				 
			menuItemProduits = new MenuItem(menuEdition,SWT.PUSH);
			menuItemProduits.setText(message.getString("menu.gestion.produits"));
			menuItemProduits.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					System.out.println("Modifier produits");
				}
			});		
		
			menuItemIterations = new MenuItem(menuEdition,SWT.PUSH);
			menuItemIterations.setText(message.getString("menu.gestion.iterations"));
			menuItemIterations.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					System.out.println("Modifier ITs");
				}
			});
		
			menuItemEquipe = new MenuItem(menuEdition,SWT.PUSH);
			menuItemEquipe.setText(message.getString("menu.gestion.equipe"));
			menuItemEquipe.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					System.out.println("Modifier équipe");
				}
			});
		
				//	Menu affichage
			menuItemAffichage = new MenuItem(menu, SWT.CASCADE);
			menuItemAffichage.setText(message.getString("menu.affichage"));
				// Sous-menu de affichage
			Menu menuAffichage = new Menu(shell, SWT.DROP_DOWN);
			menuItemAffichage.setMenu(menuAffichage);

			menuItemBarreOutils = new MenuItem(menuAffichage,SWT.PUSH);
			menuItemBarreOutils.setText(message.getString("menu.affichage.barreOutils"));
			menuItemBarreOutils.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					System.out.println("Barre outils");
				}
			});
		
			menuItemArbre = new MenuItem(menuAffichage,SWT.PUSH);
			menuItemArbre.setText(message.getString("menu.affichage.arbre"));
			menuItemArbre.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					System.out.println("Arbre");
				}
			});
		
			menuItemPreferences = new MenuItem(menuAffichage,SWT.PUSH);
			menuItemPreferences.setText(message.getString("menu.affichage.preferences"));
			menuItemPreferences.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					PreferencesIHM preferences = new PreferencesIHM(shell);
				}
			});
	
				//	Menu aide
			menuItemAide = new MenuItem(menu, SWT.CASCADE);
			menuItemAide.setText(message.getString("menu.aide"));
				// Sous-menu de aide
			Menu menuAide = new Menu(shell, SWT.DROP_DOWN);
			menuItemAide.setMenu(menuAide);
	
			menuItemOuvrirAide = new MenuItem(menuAide,SWT.PUSH);
			menuItemOuvrirAide.setText(message.getString("menu.aide.aide"));
			menuItemOuvrirAide.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					EugesAide eugesAide = new EugesAide("http://euges.free.fr");
				}
			});
		
			menuItemAPropos = new MenuItem(menuAide,SWT.PUSH);
			menuItemAPropos.setText(message.getString("menu.aide.aPropos"));
			menuItemAPropos.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					System.out.println("A propos");
				}
			});
		
				// Fin du menu
			progressStart.progression(30, "Initialisation barre d'outils...");
			
	
				// Barre d'outils
			final CoolBar coolBar = new CoolBar(shell, SWT.FLAT);
			
			CoolItem item1 = new CoolItem(coolBar, SWT.NONE);
			CoolItem item2 = new CoolItem(coolBar, SWT.NONE);
			CoolItem item3 = new CoolItem(coolBar, SWT.NONE);
			CoolItem item4 = new CoolItem(coolBar, SWT.NONE);
			//barre d'outil des differentes vues pour la fenetre principale
			CoolItem item5 = new CoolItem(coolBar, SWT.NONE);
			

			ToolBar toolBar1 = new ToolBar(coolBar, SWT.FLAT);
			Image imageNouveau = new Image(display, Config.config.getProperty("cheminIcone")+"nouveau.ico");
			itemNouveau = new ToolItem(toolBar1, SWT.NONE);
			itemNouveau.setImage(imageNouveau);
			itemNouveau.setToolTipText(message.getString("toolbar.nouveau.tooltiptext"));
			itemNouveau.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					AssistantIHM assistantIHM = new AssistantIHM(shell);
				}
			});
					
			Image imageOuvrir = new Image(display, Config.config.getProperty("cheminIcone")+"ouvrir.ico");
			itemOuvrir = new ToolItem(toolBar1, SWT.NONE);
			itemOuvrir.setImage(imageOuvrir);
			itemOuvrir.setToolTipText(message.getString("toolbar.ouvrir.tooltiptext"));
			itemOuvrir.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					ouvrir();
				}
			});	
		
			Image imageFermer = new Image(display, Config.config.getProperty("cheminIcone")+"fermer.ico");
			itemFermer = new ToolItem(toolBar1, SWT.NONE);
			itemFermer.setEnabled(false);
			itemFermer.setImage(imageFermer);
			itemFermer.setToolTipText(message.getString("toolbar.fermer.tooltiptext"));
			itemFermer.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					System.out.println("Fermer projet");
				}
			});	
			
			toolBar1.pack();

			Point size1 = toolBar1.getSize();
			item1.setControl(toolBar1);
			item1.setSize(toolBar1.computeSize(size1.x, size1.y));
			item1.setMinimumSize(size1);
		
			ToolBar toolBar2 = new ToolBar(coolBar, SWT.FLAT);

			Image imageEnregistrer = new Image(display, Config.config.getProperty("cheminIcone")+"enregistrer.ico");
			itemEnregistrer = new ToolItem(toolBar2, SWT.NONE);
			itemEnregistrer.setImage(imageEnregistrer);
			itemEnregistrer.setToolTipText(message.getString("toolbar.enregistrer.tooltiptext"));
			itemEnregistrer.setEnabled(false);
			itemEnregistrer.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					System.out.println("Enregistrer projet");
				}
			});	
			toolBar2.pack();

			Point size2 = toolBar2.getSize();
			item2.setControl(toolBar2);
			item2.setSize(toolBar2.computeSize(size2.x, size2.y));
			item2.setMinimumSize(size2);

		
			ToolBar toolBar3 = new ToolBar(coolBar, SWT.FLAT);
			Image imageMail = new Image(display, Config.config.getProperty("cheminIcone")+"mail.ico");
			itemMail = new ToolItem(toolBar3, SWT.NONE);
			itemMail.setImage(imageMail);
			itemMail.setToolTipText(message.getString("toolbar.mail.tooltiptext"));
			itemMail.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					MailIHM mailIHM = new MailIHM(shell);
				}
			});	
			toolBar3.pack();

			Point size3 = toolBar3.getSize();
			item3.setControl(toolBar3);
			item3.setSize(toolBar3.computeSize(size3.x, size3.y));
			item3.setMinimumSize(size3);
		

			ToolBar toolBar4 = new ToolBar(coolBar, SWT.FLAT);
			Image imageAide = new Image(display, Config.config.getProperty("cheminIcone")+"aide.ico");
			itemAide = new ToolItem(toolBar4, SWT.NONE);
			itemAide.setImage(imageAide);
			itemAide.setToolTipText(message.getString("toolbar.aide.tooltiptext"));
			itemAide.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					EugesAide eugesAide = new EugesAide("http://euges.free.fr");
				}
			});
			toolBar4.pack();

			Point size4 = toolBar4.getSize();
			item4.setControl(toolBar4);
			item4.setSize(toolBar4.computeSize(size4.x, size4.y));
			item4.setMinimumSize(size4);
//barre d'outil vues
			ToolBar toolBar5 = new ToolBar(coolBar, SWT.FLAT);
			//outil tableau iterations
			itemToolIt = new ToolItem(toolBar5, SWT.RADIO);
			itemToolIt.setImage(GestionImage._toolTabIt);
			itemToolIt.setToolTipText(message.getString("toolbar.itemToolIt.tooltiptext"));
			itemToolIt.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					if (itemToolIt.getSelection()){
						_vues.setVisible(0);
					}
				}
			});
			//outil graphe activites
			itemToolGraph = new ToolItem(toolBar5, SWT.RADIO);
			itemToolGraph.setImage(GestionImage._toolGraphIt);
			itemToolGraph.setToolTipText(message.getString("toolbar.itemToolGraph.tooltiptext"));
			itemToolGraph.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					if (itemToolGraph.getSelection()){
						_vues.setVisible(1);
					}
				}
			});
			//outil graphe effort
			itemToolLine = new ToolItem(toolBar5, SWT.RADIO);
			itemToolLine.setImage(GestionImage._toolGraphLine);
			itemToolLine.setToolTipText(message.getString("toolbar.itemToolLine.tooltiptext"));
			itemToolLine.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e){
					if (itemToolLine.getSelection()){
						_vues.setVisible(2);
					}
				}
			});
			
			//selection du premier element
			itemToolIt.setSelection(true);
			toolBar5.pack();
			Point size5 = toolBar5.getSize();
			item5.setControl(toolBar5);
			item5.setSize(toolBar5.computeSize(size5.x, size5.y));
			item5.setMinimumSize(size5);
			
			progressStart.progression(50, "Initialisation arbre...");
			
			// SahsForm
			final SashForm sashForm = new SashForm(shell, SWT.HORIZONTAL);
			
			// Arbre
			tree = new ArbrePrincipalIHM(sashForm);
			//creation du vecteur de vues
			_vues = new VuesIHM(sashForm);
			
			sashForm.setWeights(new int [] {25,75});		
			
			// redessiner la coolbar et le sashform si la coolbar est redimensionnée
			coolBar.addListener(SWT.Resize, new Listener () {
				public void handleEvent (Event e) {
					Rectangle rectShell = shell.getClientArea();
					Rectangle rectCoolBar = coolBar.getBounds();
					coolBar.setBounds(
							rectShell.x,
							rectShell.y + 3,
							rectShell.width,
							rectCoolBar.height);
					sashForm.setBounds(
							rectShell.x,
							rectShell.y + 3 + rectCoolBar.height + 3,
							rectShell.width,
							rectShell.height - 3 - rectCoolBar.height - 3 - 3);
				}
			});
			
			// redissiner la coolbar et le sashform si la fenetre est redimensionnée
			shell.addListener(SWT.Resize, new Listener () {
				public void handleEvent (Event e) {
					Rectangle rectShell = shell.getClientArea();
					Rectangle rectCoolBar = coolBar.getBounds();
					coolBar.setBounds(
							rectShell.x,
							rectShell.y + 3,
							rectShell.width,
							rectCoolBar.height);
					sashForm.setBounds(
							rectShell.x,
							rectShell.y + 3 + rectCoolBar.height + 3,
							rectShell.width,
							rectShell.height - 3 - rectCoolBar.height - 3 - 3);
				}
			});
			progressStart.progression(80, "Ouverture...");
			
			//ouverture de l'interface
			// ouvrir la fenêtre au centre de l'écran
			Monitor primary = display.getPrimaryMonitor ();
			Rectangle bounds = primary.getBounds ();
			Rectangle rect = shell.getBounds ();
			int x = bounds.x + (bounds.width - rect.width) / 2;
			int y = bounds.y + (bounds.height - rect.height) / 2;
			shell.setLocation (x, y);
			
			shell.open();
			//	ouverture de la page de demarrage
			PageDemarrageIHM pageDemarrage = new PageDemarrageIHM(shell);
			progressStart.progression(100, "Terminé...");
			
			// Boucle d'évènements
				while (!shell.isDisposed()){
					if (!display.readAndDispatch())
					display.sleep();
				}
			// Fermeture de l'environnement
				display.dispose();
		}
		
			// Ouvre une fenêtre qui permet de choisir un fichier à ouvrir
		private String ouvrir(){
			FileDialog fileDialog = new FileDialog(shell);
			fileDialog.setText(message.getString("titreFenetreOuvrir"));
			String [] tab = {"*.txt"};
			fileDialog.setFilterExtensions(tab);
			String file = fileDialog.open();
			
			return file;
		}
		
			// Ouvre une fenêtre qui permet de choisir un fichier de destination de l'enregistrement
		private String enregistrerSous(){
			FileDialog fileDialog = new FileDialog(shell);
			fileDialog.setText(message.getString("titreFenetreEnregistrerSous"));
			String [] tab = {"*.egs"};
			fileDialog.setFilterExtensions(tab);
			String file = fileDialog.open();
			
			return file;
		}
	
	
}
