/*
 * Created on 13 nov. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

import ihm.vues.planIt.PlanItIHM;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import ihm.preferences.PreferencesIHM;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.MessageBox;


import utilitaires.EugesAide;
import utilitaires.EugesNavigateur;
import utilitaires.GestionImage;
import application.EugesElements;
import configuration.Config;
import utilitaires.MailElements;

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AssistantIHM extends Dialog {
		
		private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
		private Display display;
		
		//boutons de l'application
		Vector _listeBoutons = new Vector();
		
		//page courante
		int pageCourante=0;
		//vecteur contenant la liste des pages de l'assistant
		Vector listePages = new Vector();
		
		/**
		 * constructeur
		 * @param shell
		 */
		public AssistantIHM(Shell shell){
			//creation du dialog
			super(shell);
			//nouveau shell pour la fenetre
			final Shell shellAssistant = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			
			//icone eugesdans la barre de titre
			shellAssistant.setImage(GestionImage._euges);
			
			// récupération du display
			display = Display.getCurrent();
			shellAssistant.setSize(600, 500);
			shellAssistant.setText(message.getString("assistantIHM.titre"));
			
			//bouton site : lien vers le site internet
			Cursor curseurMain = new Cursor(display, SWT.CURSOR_HAND);
			Button boutonSite = new Button(shellAssistant, SWT.PUSH);
			boutonSite.setCursor(curseurMain);
			boutonSite.setBounds(10, 10, 150, 405);
			boutonSite.setToolTipText("www.euges.fr.st");
			boutonSite.setImage(GestionImage._eugesAssistant);
			boutonSite.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									EugesNavigateur fenetre = new EugesNavigateur("http://www.euges.fr.st");
								}
							});
			//bouton aide
			Button boutonAide = new Button(shellAssistant, SWT.PUSH);
			_listeBoutons.add(boutonAide);
			boutonAide.setText(message.getString("assistantIHM.boutonAide"));
			boutonAide.setBounds(10, 430, 100, 30);
			boutonAide.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					EugesAide eugesAide = new EugesAide("http://euges.free.fr");
				}
			});
			
			//bouton precedent
			Button boutonPrecedent = new Button(shellAssistant, SWT.PUSH);
			_listeBoutons.add(boutonPrecedent);
			boutonPrecedent.setText(message.getString("assistantIHM.boutonPrecedent"));
			boutonPrecedent.setBounds(150, 430, 100, 30);
			boutonPrecedent.addSelectionListener(new SelectionAdapter() {
														public void widgetSelected(SelectionEvent e) {
															if (pageCourante!=0){
																pageCourante--;
																((PageAssistantIHM)listePages.elementAt(pageCourante)).setVisible(true);
																((PageAssistantIHM)listePages.elementAt(pageCourante+1)).setVisible(false);
																activeBoutons();
															}
														}
													});
			//bouton suivant
			Button boutonSuivant = new Button(shellAssistant, SWT.PUSH);
			_listeBoutons.add(boutonSuivant);
			boutonSuivant.setText(message.getString("assistantIHM.boutonSuivant"));
			boutonSuivant.setBounds(260, 430, 100, 30);
			boutonSuivant.addSelectionListener(new SelectionAdapter() {
											public void widgetSelected(SelectionEvent e) {
												//on verifie le contenu de la page avant de passer a la suivante
												if(((PageAssistantIHM)(listePages.elementAt(pageCourante))).verifDonnees()){
													//si on est a la page nouveau projet, on charge les pages suivantes
													if (pageCourante==1) chargerPages(shellAssistant);
													//si on est pas à la fin du wizard, on affiche la page suivante
													if (pageCourante<listePages.size()-1){
														pageCourante++;
														((PageAssistantIHM)listePages.elementAt(pageCourante)).setVisible(true);
														((PageAssistantIHM)listePages.elementAt(pageCourante-1)).setVisible(false);
														//activation des bons boutons
														activeBoutons();
													}
												}
											}
										});
			 //bouton terminer
			 Button boutonTerminer = new Button(shellAssistant, SWT.PUSH);
			_listeBoutons.add(boutonTerminer);
			 boutonTerminer.setText(message.getString("assistantIHM.boutonTerminer"));
			 boutonTerminer.setBounds(370, 430, 100, 30);
			 boutonTerminer.addSelectionListener(new SelectionAdapter() {
			 	public void widgetSelected(SelectionEvent e) {
			 		FenetrePrincipaleIHM.menuItemFermer.setEnabled(true);
			 		FenetrePrincipaleIHM.menuItemEnregistrer.setEnabled(true);
			 		FenetrePrincipaleIHM.menuItemEnregistrerSous.setEnabled(true);
			 		FenetrePrincipaleIHM.menuItemEdition.setEnabled(true);
			 		FenetrePrincipaleIHM.itemFermer.setEnabled(true);
			 		FenetrePrincipaleIHM.itemEnregistrer.setEnabled(true);
			 		
					((PlanItIHM)FenetrePrincipaleIHM._vues.elementAt(0)).majIt(EugesElements._projet._listeIteration.size());
					FenetrePrincipaleIHM._vues.elementAt(0).setVisible(true);
					ArbrePrincipalIHM._tri.actualiser();
					EugesElements.processusEnregistre=false;
					
					
					
					
							//envoie du mail de démarrage
					
					if (Config.config.getProperty("start").equals("1")){
							try{	
								String[] TabPers;
								TabPers = EugesElements.getTableauListePersonne();
								String dest="";
								
								
								
								for (int i=0;i<TabPers.length;i++){
								
								dest= dest + (EugesElements.getPersonneDansListePersonnes(TabPers[i]).getMail()+",");
								
								}
								
								if (Config.config.getProperty("login")!="" & Config.config.getProperty("suj")!="" & Config.config.getProperty("msg")!="" & Config.config.getProperty("serv")!=""){
									
										
									MailElements mail = new MailElements(Config.config.getProperty("serv"),Config.config.getProperty("login"),dest,Config.config.getProperty("msg"),Config.config.getProperty("suj"));
										try {
											mail.sendMsg();
										} catch (Exception e1) {}
							 		}
								else {
									while ((Config.config.getProperty("login").equals("") || Config.config.getProperty("suj").equals("") || Config.config.getProperty("msg").equals("") || Config.config.getProperty("serv").equals("")) & Config.config.getProperty("start").equals("1")){
										
										MessageBox msg = new MessageBox(shellAssistant, SWT.ICON_ERROR|SWT.YES);
										msg.setText(message.getString("assistantIHM.probmail"));
										msg.setMessage(message.getString("assistantIHM.msgprobmail"));
										msg.open();
										
										PreferencesIHM preferences = new PreferencesIHM(shellAssistant);
									}
										
									if (Config.config.getProperty("start").equals("1")){
										MailElements mail = new MailElements(Config.config.getProperty("serv"),Config.config.getProperty("login"),dest,Config.config.getProperty("msg"),Config.config.getProperty("suj"));
										try {
											mail.sendMsg();
										} catch (Exception e1) {}
									}
								
									
									
								}
							}
							catch (Exception e1){}
							}
					shellAssistant.dispose();
			 	}
			 });
			 
			 //bouton annuler
			Button boutonAnnuler = new Button(shellAssistant, SWT.PUSH);
			_listeBoutons.add(boutonAnnuler);
			boutonAnnuler.setText(message.getString("assistantIHM.boutonAnnuler"));
			boutonAnnuler.setBounds(480, 430, 100, 30);
			boutonAnnuler.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									shellAssistant.dispose();
								}
							});
			
			
			//chargement des 2 premieres pages de l'assistant
			chargerPagesDebut(shellAssistant);
			
			// ouvrir la fenêtre au centre de l'écran
			Rectangle bounds = shell.getBounds ();
			Rectangle rect = shellAssistant.getBounds ();
			int x = bounds.x + (bounds.width - rect.width) / 2;
			int y = bounds.y + (bounds.height - rect.height) / 2;
			shellAssistant.setLocation (x, y);
			
			shellAssistant.open();
		}
		/**
		 * charge la page d'accueil et la page nouveau projet de l'assistant
		 * 
		 * @param shellAssistant
		 */
		public void chargerPagesDebut(Shell shellAssistant){
			//ouverture des 2 premieres pages
			PageAssistantIHM pageAccueil = new PageAccueilIHM(shellAssistant);
			PageAssistantIHM pageNouveauProjet = new PageNouveauProjetIHM(shellAssistant);
			
			//ajout des pages dans le vecteur 
			listePages.add(pageAccueil);
			listePages.add(pageNouveauProjet);
			
			//toutes les pages de l'assistant sont fixees a la meme taille et masquées
			Iterator itPage = listePages.iterator();
			PageAssistantIHM tmp;
			while (itPage.hasNext()){
				tmp = (PageAssistantIHM)itPage.next();
				tmp.setBounds(170, 10, 410, 410);
				tmp.setVisible(false);
			}
			//on affiche la premiere page des nouvelles pages ajoutées soit la page itérations
			((PageAssistantIHM)listePages.firstElement()).setVisible(true);
			activeBoutons();
		}
		
		/**
		 * chargement des pages suivante en fonctions des champs saisis dans la page nouveau projet
		 * 
		 * @param shellAssistant
		 */
		public void chargerPages(Shell shellAssistant){
			//creation des pages
			PageAssistantIHM pageIteration = new PageIterationIHM(shellAssistant);
			PageAssistantIHM pageAttributionRoles = new PageAttributionRolesIHM(shellAssistant, EugesElements._projet.getIteration(0));
			
			//ajout des pages dans le vecteur 
			listePages.add(pageIteration);
			listePages.add(pageAttributionRoles);
			//toutes les pages de l'assistant sont fixees a la meme taille et masquées
			Iterator itPage = listePages.iterator();
			PageAssistantIHM tmp;
			while (itPage.hasNext()){
				tmp = (PageAssistantIHM)itPage.next();
				tmp.setBounds(170, 10, 410, 410);
				tmp.setVisible(false);
			}
			//on affiche la premiere page
			((PageAssistantIHM)listePages.elementAt(2)).setVisible(true);
			//activation des boutons
			activeBoutons();
		}

		/**
		 * active ou desactive les boutons en fonction de la page en cours
		 */
		public void activeBoutons(){
			boolean[] boutons=((PageAssistantIHM)(listePages.elementAt(pageCourante))).get_activeBoutons();
			Iterator itBoutons = _listeBoutons.iterator();
			int i=0;
			while(itBoutons.hasNext()){
				((Button)itBoutons.next()).setEnabled(boutons[i]);
				i++;
			}
		}
		
		
}
