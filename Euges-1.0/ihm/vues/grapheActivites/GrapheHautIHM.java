/*
 * Created on 15 janv. 2004
 *
 */
package ihm.vues.grapheActivites;


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

import application.EugesElements;
import configuration.Config;
import donnees.eugesSpem.EugesActivite;


/**
 * @author Mathieu GAYRAUD
 *
 */
public class GrapheHautIHM extends Composite {
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	private Table _graphe;
	private Vector _tableColonnes = new Vector();
	
	public GrapheHautIHM(final Composite comp) {
		super(comp, SWT.BORDER);
		// titre
		Font font = new Font(comp.getDisplay(), "Arial", 15, 15);
		Label titre = new Label(this, SWT.NONE|SWT.CENTER);
		titre.setFont(font);
		titre.setText(message.getString("grapheIHM.titre"));
		
		//tableau des activités en fonction des iterations
		_graphe=new Table(this, SWT.BORDER);
		_graphe.setHeaderVisible(true);
		//chargement des donnees du tableau
		loadData();
		
		//mise en place du layout
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		setLayout(layout);
		//layout
		//titre
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint =titre.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		titre.setLayoutData(data);
		//table
		data = new GridData(GridData.FILL_BOTH);
		_graphe.setLayoutData(data);
		
		//redimensionnement des colonnes pdt le redimensionnment des fenetres
		addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				int largeur = _graphe.getClientArea().width;
				TableColumn[] cols = _graphe.getColumns();
				//la taille minimale d'une colonne est de 150 pixels
				if (largeur>cols.length*150){
					for (int i=0; i<cols.length; i++)
						cols[i].setWidth(largeur / cols.length);
				}
			}
		});
		
	}
	/**
	 * chargement des donnees du tableau
	 *
	 */
	public void loadData(){
		//suppression de tous les elements
		_graphe.removeAll();
		//Déclaration des couleurs
		Color activitesFond = new Color(Display.getCurrent(), 255, 255, 204);
		Color activitesTexte = new Color(Display.getCurrent(), 0,0,0);
		
		//si le projet a été créé
		if (EugesElements._projet!=null){
			//recupération du nombre d'itérations
			int nbIteration=EugesElements._projet.getNombreIteration();
			System.out.println("->>>"+nbIteration);
			TableColumn tmp;
			//génération des colonnes correspondant au nombre d'itération
			for (int i=0; i<nbIteration; i++){
				tmp = new TableColumn(_graphe, SWT.CENTER);
				//titre de la colonne
				tmp.setText(message.getString("grapheIHM.iteration")+" "+i);
				_tableColonnes.add(tmp);
			}
			
			/*/	int largeur = _graphe.getClientArea().width;
			 TableColumn[] cols = _graphe.getColumns();
			 for (int i=; i<cols.length; i++)
			 cols[i].setWidth(largeur / 4);*/
			
			//création des activites
			//recuperation du vecteur d'activités
			Vector vecteurTmp = EugesElements.listeActivites;
			
			//remplissage des lignes
			TableItem item;
			String[] texte;	//texte a afficher
			int dateDebut, dateFin; //dates de debut et fin de l'activite 
			for (Iterator iter = vecteurTmp.iterator(); iter.hasNext();) {
				EugesActivite element = (EugesActivite) iter.next();
				//creation d'un nouvel item
				item = new TableItem(_graphe, SWT.NONE);
				//creation des lignes avec ecriture du texte et mise en forme(couleur fond et texte)
				texte=new String[nbIteration];
				for (int i=0; i<nbIteration; i++){
					if (i>=element.getIterationDebut() && i<=element.getIterationFin()){
						item.setForeground(i, activitesTexte);
						item.setBackground(i, activitesFond);
						texte[i]=element.getName();
					}else{
						texte[i]="";
					}
				}
				item.setText(texte);
			}
		}
	}
}
