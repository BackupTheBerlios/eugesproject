/*
 * Created on 15 janv. 2004
 *
 */
package ihm.vues.grapheActivites;


import ihm.FenetrePrincipaleIHM;
import ihm.vues.PageVuesIHM;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import utilitaires.MyDate;
import application.EugesElements;
import configuration.Config;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesActivite;


/**
 * @author Mathieu GAYRAUD
 *
 */
public class GrapheHautIHM extends PageVuesIHM {
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	private Table _graphe;
	private Vector _tableColonnes = new Vector();
	
	public GrapheHautIHM(final Composite comp) {
		super(comp, SWT.NONE);
		// titre
		Font font = new Font(comp.getDisplay(), "Arial", 15, 15);
		Label titre = new Label(this, SWT.NONE|SWT.CENTER);
		titre.setFont(font);
		titre.setText(message.getString("grapheHautIHM.titre"));
		
		//tableau des activités en fonction des iterations
		_graphe=new Table(this, SWT.BORDER);
		_graphe.setHeaderVisible(true);
		//chargement des donnees du tableau
		loadData();
		
		//mise en place du layout
		GridLayout layout = new GridLayout(3, true);
		layout.numColumns = 1;
		setLayout(layout);
		//layout
		//titre
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan=3;
		data.heightHint =titre.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		titre.setLayoutData(data);
		//table
		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan=3;
		_graphe.setLayoutData(data);
	}
	/**
	 * chargement des donnees du tableau
	 *
	 */
	public void loadData(){
		//suppression de tous les elements
		_graphe.removeAll();
		//Déclaration des couleurs
		Color activitesFondPasse = new Color(Display.getCurrent(), 255, 160, 160);
		Color activitesFondEnCours = new Color(Display.getCurrent(), 160, 160, 255);
		Color activitesFondFutur = new Color(Display.getCurrent(), 160, 255, 160);
		Color activitesTexte = new Color(Display.getCurrent(), 0,0,0);
		
		//si le projet a été créé
		if (EugesElements._projet!=null){
			//suppression des colonnes du tableau si elle existent
			for (Iterator iter = _tableColonnes.iterator(); iter.hasNext();) {
				((TableColumn) iter.next()).dispose();
			}
			_tableColonnes.removeAllElements();
			//recupération du nombre d'itérations
			int nbIteration=EugesElements._projet.getNombreIteration();
			TableColumn tmp;
			//génération des colonnes correspondant au nombre d'itération
			for (int i=0; i<nbIteration+1; i++){
				tmp = new TableColumn(_graphe, SWT.CENTER);
				tmp.setWidth(getTailleColonne());
				//titre de la colonne
				if (i==0)
					tmp.setText(message.getString("grapheHautIHM.activite"));
				else
					tmp.setText(message.getString("grapheHautIHM.iteration")+" "+i);
				_tableColonnes.add(tmp);
			}
			
			
			//création des activites
			//recuperation du vecteur d'activités
			Vector vecteurTmp = EugesElements.listeActivites;
			if (vecteurTmp.size()!=0){
				//tri du vecteur
				ordonnerListeActivites(vecteurTmp);
				
				//remplissage des lignes
				TableItem item;
				String[] texte;	//texte a afficher
				int dateDebut, dateFin; //dates de debut et fin de l'activite 
				for (Iterator iter = vecteurTmp.iterator(); iter.hasNext();) {
					//recuperation de l'activite a traiter
					EugesActivite element = (EugesActivite) iter.next();
					//recuperation du vecteur d'activite realise correspondant a l'activité en cours et ordonnancement du vecteur
					Vector listeActReal = element.get_activitesRealisees();
					ordonnerListeActivitesRealisees(listeActReal);
					
					//creation d'un nouvel item
					item = new TableItem(_graphe, SWT.NONE);
					//creation des lignes avec ecriture du texte et mise en forme(couleur fond et texte)
					texte=new String[nbIteration+1];
					texte[0]=element.getName();
					for (int i=0; i<nbIteration; i++){
						boolean ok=false;
						int j=0;
						while(!ok && j<listeActReal.size()){
							if (i==((EugesActRealise)listeActReal.elementAt(j)).getIt()){
								ok=true;
								MyDate itdatedebut = EugesElements._projet.getIteration(i).get_dateDebut();
								MyDate itdatefin = EugesElements._projet.getIteration(i).get_dateFin();
								MyDate dateDuJour = FenetrePrincipaleIHM.dateDuJour;
								
								if (itdatefin.compare(dateDuJour)<0)
									item.setBackground(i+1, activitesFondPasse);
								else if (itdatedebut.compare(dateDuJour)>0)
									item.setBackground(i+1, activitesFondFutur);
								else
									item.setBackground(i+1, activitesFondEnCours);
								texte[i+1]="";
							}else{
								texte[i+1]="";
							}
							j++;
						}
					}
					item.setText(texte);
				}
			}
		}
//		redimensionnement des colonnes pdt le redimensionnment des fenetres
		_graphe.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				int bonneTaille = getTailleColonne();
				TableColumn[] cols = _graphe.getColumns();
				for (int i=0; i<cols.length; i++)
					cols[i].setWidth(bonneTaille);
			}
		});
	}
	/**
	 * permet de trier un vectuer d'activites
	 * @param activiteEuges vecteur d'EugesActivites a trier
	 */
	private void ordonnerListeActivites(Vector activiteEuges){
		for (int i = 0; i < activiteEuges.size(); i++) {
			for (int j = i; j < activiteEuges.size(); j++) {
				if (((EugesActivite) activiteEuges.elementAt(i)).getIterationDebut() > ((EugesActivite) activiteEuges.elementAt(j)).getIterationDebut()) {
					EugesActivite tmp = (EugesActivite) activiteEuges.elementAt(i);
					activiteEuges.setElementAt((EugesActivite) activiteEuges.elementAt(j),i);
					activiteEuges.setElementAt(tmp, j);
				}
				
			}
		}
	}
	/**
	 * permet de trier un vectuer d'activites
	 * @param activiteEuges vecteur d'EugesActivites a trier
	 */
	private void ordonnerListeActivitesRealisees(Vector activiteRealiseEuges){
		for (int i = 0; i < activiteRealiseEuges.size(); i++) {
			for (int j = i; j < activiteRealiseEuges.size(); j++) {
				if (((EugesActRealise) activiteRealiseEuges.elementAt(i)).getIt() > ((EugesActRealise) activiteRealiseEuges.elementAt(j)).getIt()) {
					EugesActRealise tmp = (EugesActRealise) activiteRealiseEuges.elementAt(i);
					activiteRealiseEuges.setElementAt((EugesActRealise) activiteRealiseEuges.elementAt(j),i);
					activiteRealiseEuges.setElementAt(tmp, j);
				}
				
			}
		}
	}
	/**
	 * 
	 * @return la taille appropriée pour les colonnes
	 */
	private int getTailleColonne(){
		int largeur = _graphe.getClientArea().width;
		TableColumn[] cols = _graphe.getColumns();
		//la taille minimale d'une colonne est de 150 pixels
		if (largeur>cols.length*150){
			for (int i=0; i<cols.length; i++)
				return (largeur / cols.length);
		}
		return 150;
	}
}
