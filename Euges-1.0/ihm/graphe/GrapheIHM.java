/*
 * Created on 15 janv. 2004
 *
 */
package ihm.graphe;


import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
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
public class GrapheIHM extends ViewForm {
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	
	private Table _graphe;
	private Vector _tableColonnes = new Vector();
	
	public GrapheIHM(final Composite parent) {
		super(parent, SWT.BORDER);
		
		final Composite c = new Composite(this, SWT.BORDER|SWT.V_SCROLL);
		
		//scrollbar
		final ScrollBar vBar = c.getVerticalBar ();
		vBar.setIncrement(5);
		vBar.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				Point location = c.getLocation ();
				location.y = -vBar.getSelection ();
				c.setLocation (location);
			}
		});
		
		// titre
		Font font = new Font(parent.getDisplay(), "Arial", 15, 15);
		Label titre = new Label(c, SWT.NONE|SWT.CENTER);
		titre.setFont(font);
		titre.setText(message.getString("grapheIHM.titre"));
		
		//tableau des activités en fonction des iterations
		_graphe=new Table(c, SWT.BORDER);
		_graphe.setHeaderVisible(true);
		
		//chargement des donnees du tableau
		loadData();
		
		//mise en place du layout
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		c.setLayout(layout);
		//layout
		//titre
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint =titre.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		titre.setLayoutData(data);
		//table
		data = new GridData(GridData.FILL_HORIZONTAL|GridData.FILL_VERTICAL);
		_graphe.setLayoutData(data);
		
		//placement du composite en fonction du remendissionnement
		c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				//int height = c.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;

				int height = getClientArea().height/2;
				int width = getClientArea().width;
				c.setSize(width,height);
				
				Point size = c.getSize ();
				Rectangle rect = getClientArea ();
				vBar.setMaximum (size.y);
				vBar.setThumb (Math.min (size.y, rect.height));
				int hPage = size.x - rect.width;
				int vPage = size.y - rect.height;
				int vSelection = vBar.getSelection ();
				Point location = c.getLocation ();
				if (vSelection >= vPage) {
					if (vPage <= 0) vSelection = 0;
					location.y = -vSelection;
				}
				c.setLocation (location);
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
		
		/*a modifier*/
		//recupération du nombre d'itérations
		int nbIteration=4;
		TableColumn tmp;
		//génération des colonnes correspondant au nombre d'itération
		for (int i=0; i<nbIteration; i++){
			tmp = new TableColumn(_graphe, SWT.CENTER);
			tmp.setWidth(150);
			//titre de la colonne
			tmp.setText(message.getString("grapheIHM.iteration")+" "+i);
			_tableColonnes.add(tmp);
		}
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
