/*
 * Created on 10 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.vues.grapheCharges;

import ihm.vues.PageVuesIHM;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ScrollBar;

import application.EugesElements;
import configuration.Config;
import donnees.ActiviteGraphique;
import donnees.Iteration;
import donnees.eugesSpem.EugesActRealise;

/**
 * Page d'affichage d'un graphe représentant la variation entre les charges estimées et les charges réelles 
 * @author Nicolas Broueilh
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class GrapheChargesIHM extends PageVuesIHM {
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	private boolean vueSuperposee = false;
	
	private final Canvas canvas;
	
	private final Composite parent;
	
	private final Vector rectActivites = new Vector();
	
	public GrapheChargesIHM(final Composite parent){
		super(parent,SWT.NONE|SWT.H_SCROLL|SWT.V_SCROLL);
		this.parent= parent;
		//this.setLayout(new FillLayout(SWT.VERTICAL));
		this.setSize(parent.getParent().getSize());
		canvas = new Canvas(this,SWT.NONE);
		reglerTaille();
		Menu menu = new Menu(parent.getShell(), SWT.POP_UP);
		MenuItem vueNormale = new MenuItem(menu, SWT.NONE);
		vueNormale.setText(message.getString("vueNormale"));
		vueNormale.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				vueSuperposee = false;
				canvas.redraw();
			}
		});
		
		MenuItem separator = new MenuItem(menu,SWT.SEPARATOR);
		
		MenuItem vueSuper = new MenuItem(menu, SWT.NONE);
		vueSuper.setText(message.getString("vueSuperposee"));
		vueSuper.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				vueSuperposee = true;
				canvas.redraw();
			}
		});

		canvas.setMenu(menu);
		
		final ScrollBar hBar = this.getHorizontalBar ();
		hBar.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				Point location = canvas.getLocation ();
				location.x = -hBar.getSelection ();
				canvas.setLocation (location);
				canvas.redraw();
			}
		});

		final ScrollBar vBar = this.getVerticalBar ();
		vBar.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				Point location = canvas.getLocation ();
				location.y = -vBar.getSelection ();
				canvas.setLocation (location);
				canvas.redraw();
			}
		});
		
		addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				reglerTaille();
				
				Point size = canvas.getSize ();
				Rectangle rect = getClientArea ();
				hBar.setMaximum (size.x);
				hBar.setThumb (Math.min (size.x, rect.width));
				vBar.setMaximum (size.y);
				vBar.setThumb (Math.min (size.y, rect.height));
				int hPage = size.x - rect.width;
				int vPage = size.y - rect.height;
				int hSelection = hBar.getSelection ();
				Point location = canvas.getLocation ();
				if (hSelection >= vPage) {
					if (vPage <= 0) hSelection = 0;
					location.x = -hSelection;
				}
				int vSelection = vBar.getSelection ();
				if (vSelection >= vPage) {
					if (vPage <= 0) vSelection = 0;
					location.y = -vSelection;
				}
				canvas.setLocation (location);
			}
		});
		
		canvas.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				ActiviteGraphique actGraph = getAct(e.x,e.y);
				if (actGraph != null) {
					EugesActRealise act = actGraph.getActiviteRealise();
					System.out.println(act.get_activiteParent().getName());
				}
			}
		
		});
				
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				reglerTaille();
				GC gc = e.gc;
				Point p = canvas.getSize();
				//axe vertical
				gc.drawLine(20,30,20,p.y-10);
				//fleche
				gc.drawLine(15,35,20,30);
				gc.drawLine(20,30,25,35);
				//graduations
				for (int i=0; i<p.y-60; i+=20) {
					gc.drawLine(15,p.y-20-i,25,p.y-20-i);
					gc.drawString(Integer.toString(i/10),5,p.y-20-i-5);
				}
				
				
				//axe horizontal
				gc.drawLine(10,p.y-20,p.x-10,p.y-20);
				//fleche
				gc.drawLine(p.x-10,p.y-20,p.x-15,p.y-25);
				gc.drawLine(p.x-10,p.y-20,p.x-15,p.y-15);
				
				//legende
				Color blue = e.widget.getDisplay().getSystemColor(SWT.COLOR_BLUE);
				Color red = e.widget.getDisplay().getSystemColor(SWT.COLOR_RED);
				Color black = e.widget.getDisplay().getSystemColor(SWT.COLOR_BLACK);
				Color green = e.widget.getDisplay().getSystemColor(SWT.COLOR_GREEN);
				
				gc.setForeground(blue);
				gc.drawString(message.getString("chargesEstimees"),0,0);
				gc.setForeground(red);
				gc.drawString(message.getString("chargesReelles"),0,15);
				gc.setForeground(black);
				gc.drawString(message.getString("activites"),p.x-50,p.y-15);
				if (EugesElements._projet != null) {
					//si la vue superposee n'est pas choisie	
					if (!vueSuperposee) {
						rectActivites.clear();
						//graduations axe horizontal
						for (int i=0; i<p.x-40; i+=100) 
							gc.drawLine(20+i,p.y-15,20+i,p.y-25);
						//affichage des charges
						int nbact = 0;
						for (Iterator it = EugesElements._projet.get_listeIteration().iterator();it.hasNext();) {
							Iteration itTemp = (Iteration)it.next();
							for (int i=0; i < itTemp.getActiviteCount(); i++) {
								EugesActRealise actTemp = itTemp.getActivite(i);
								nbact++;
								int chargeEstimee = actTemp.get_chargeEstimee();
								int chargeReelle = actTemp.get_chargeReelle();
								int [] dessus1 = {((nbact)*100)-30,p.y-20-(chargeEstimee*10),((nbact)*100)-15,p.y-35-(chargeEstimee*10),((nbact)*100)+35,p.y-35-(chargeEstimee*10),((nbact)*100)+20,p.y-20-(chargeEstimee*10)};
								int [] cote1 = {((nbact)*100)+20,p.y-20,((nbact)*100)+20,p.y-20-(chargeEstimee*10),((nbact)*100)+35,p.y-35-(chargeEstimee*10),((nbact)*100)+35,p.y-35};
								int [] dessus2 = {((nbact)*100)+20,p.y-20-(chargeReelle*10),((nbact)*100)+35,p.y-35-(chargeReelle*10),((nbact)*100)+85,p.y-35-(chargeReelle*10),((nbact)*100)+70,p.y-20-(chargeReelle*10)};
								int [] cote2 = {((nbact)*100)+70,p.y-20,((nbact)*100)+70,p.y-20-(chargeReelle*10),((nbact)*100)+85,p.y-35-(chargeReelle*10),((nbact)*100)+85,p.y-35};
								ActiviteGraphique estimee = new ActiviteGraphique(actTemp,new Rectangle(((nbact)*100)-30,p.y-20-(chargeEstimee*10),50,chargeEstimee*10),dessus1,cote1);
								ActiviteGraphique reelle = new ActiviteGraphique(actTemp,new Rectangle(((nbact)*100)+20,p.y-20-(chargeReelle*10),50,chargeReelle*10),dessus2,cote2);
								rectActivites.add(estimee);
								rectActivites.add(reelle);
								//rectangle charge estimée
								gc.setBackground(blue);
								gc.fillRectangle(((nbact)*100)-30,p.y-20-(chargeEstimee*10),50,chargeEstimee*10);
								gc.fillPolygon(dessus1);
								gc.fillPolygon(cote1);
								gc.setForeground(black);
								gc.drawRectangle(((nbact)*100)-30,p.y-20-(chargeEstimee*10),50,chargeEstimee*10);
								gc.drawPolygon(dessus1);
								gc.drawPolygon(cote1);
								gc.drawString(Integer.toString(chargeEstimee),((nbact)*100)-25,p.y-15-(chargeEstimee*10));
								
								
								//rectangle charge réelle
								gc.setBackground(red);
								gc.fillRectangle(((nbact)*100)+20,p.y-20-(chargeReelle*10),50,chargeReelle*10);
								gc.fillPolygon(dessus2);
								gc.fillPolygon(cote2);
								gc.setForeground(black);
								gc.drawRectangle(((nbact)*100)+20,p.y-20-(chargeReelle*10),50,chargeReelle*10);
								gc.drawPolygon(dessus2);
								gc.drawPolygon(cote2);
								gc.drawString(Integer.toString(chargeReelle),((nbact)*100)+25,p.y-15-(chargeReelle*10));
							}
							nbact++;
						}
					}
					else {
						rectActivites.clear();
						//graduations axe horizontal
						for (int i=0; i<p.x-40; i+=50) 
							gc.drawLine(20+i,p.y-15,20+i,p.y-25);
						//affichage des charges
						int nbact = 0;
						for (Iterator it = EugesElements._projet.get_listeIteration().iterator();it.hasNext();) {
							Iteration itTemp = (Iteration)it.next();
							for (int i=0; i < itTemp.getActiviteCount(); i++) {
								EugesActRealise actTemp = itTemp.getActivite(i);
								nbact++;
								int chargeEstimee = actTemp.get_chargeEstimee();
								int chargeReelle = actTemp.get_chargeReelle();
								int [] dessus1 = {((nbact)*50)+20,p.y-20-(chargeEstimee*10),((nbact)*50)+35,p.y-35-(chargeEstimee*10),((nbact)*50)+85,p.y-35-(chargeEstimee*10),((nbact)*50)+70,p.y-20-(chargeEstimee*10)};
								int [] cote1 = {((nbact)*50)+70,p.y-20,((nbact)*50)+70,p.y-20-(chargeEstimee*10),((nbact)*50)+85,p.y-35-(chargeEstimee*10),((nbact)*50)+85,p.y-35};
								
								if (chargeReelle > chargeEstimee) {
									int [] dessus2 = {((nbact)*50)+20,p.y-20-(chargeReelle*10),((nbact)*50)+35,p.y-35-(chargeReelle*10),((nbact)*50)+85,p.y-35-(chargeReelle*10),((nbact)*50)+70,p.y-20-(chargeReelle*10)};
									int [] cote2 = {((nbact)*50)+70,p.y-20,((nbact)*50)+70,p.y-20-(chargeReelle*10),((nbact)*50)+85,p.y-35-(chargeReelle*10),((nbact)*50)+85,p.y-35};
									ActiviteGraphique estimee = new ActiviteGraphique(actTemp,new Rectangle(((nbact)*50)+20,p.y-20-(chargeEstimee*10),50,chargeEstimee*10),dessus1,cote1);
									ActiviteGraphique reelle = new ActiviteGraphique(actTemp,new Rectangle(((nbact)*50)+20,p.y-20-(chargeReelle*10),50,chargeReelle*10),dessus2,cote2);
									rectActivites.add(estimee);
									rectActivites.add(reelle);
									//rectangle charge réelle
									gc.setBackground(red);
									gc.fillRectangle(((nbact)*50)+20,p.y-20-(chargeReelle*10),50,chargeReelle*10);
									gc.fillPolygon(dessus2);
									gc.fillPolygon(dessus1);
									gc.fillPolygon(cote2);
									gc.setForeground(black);
									gc.drawRectangle(((nbact)*50)+20,p.y-20-(chargeReelle*10),50,chargeReelle*10);
									gc.drawPolygon(dessus2);
									gc.drawPolygon(cote2);
									//rectangle charge estimée
									gc.setBackground(blue);
									gc.fillRectangle(((nbact)*50)+20,p.y-20-(chargeEstimee*10),50,chargeEstimee*10);
									gc.fillPolygon(cote1);
									gc.setForeground(black);
									gc.drawRectangle(((nbact)*50)+20,p.y-20-(chargeEstimee*10),50,chargeEstimee*10);
									//gc.drawPolygon(dessus1);
									gc.drawPolygon(cote1);
								}
								else {
									int [] dessus2 = {((nbact)*50)+20,p.y-20-(chargeReelle*10),((nbact)*50)+35,p.y-35-(chargeReelle*10),((nbact)*50)+85,p.y-35-(chargeReelle*10),((nbact)*50)+70,p.y-20-(chargeReelle*10)};
									ActiviteGraphique estimee = new ActiviteGraphique(actTemp,new Rectangle(((nbact)*50)+20,p.y-20-(chargeEstimee*10),50,chargeEstimee*10),dessus1,cote1);
									ActiviteGraphique reelle = new ActiviteGraphique(actTemp,new Rectangle(((nbact)*50)+20,p.y-20-(chargeReelle*10),50,chargeReelle*10),dessus2,cote1);
									rectActivites.add(estimee);
									rectActivites.add(reelle);
									//rectangle charge estimée
									gc.setBackground(blue);
									gc.fillRectangle(((nbact)*50)+20,p.y-20-(chargeEstimee*10),50,chargeEstimee*10);
									gc.fillPolygon(cote1);
									gc.fillPolygon(dessus1);
									gc.setBackground(green);
									gc.fillPolygon(dessus2);
									gc.setForeground(black);
									gc.drawPolygon(dessus2);
									gc.setForeground(black);
									gc.drawRectangle(((nbact)*50)+20,p.y-20-(chargeEstimee*10),50,chargeEstimee*10);
									gc.drawPolygon(dessus1);
									gc.drawPolygon(cote1);
								}
							}
							nbact++;
						}
					}
				}
			}
		});
		
		
		canvas.redraw();
		


	}

	/* (non-Javadoc)
	 * @see ihm.vues.PageVuesIHM#loadData()
	 */
	public void loadData() {
		// TODO Auto-generated method stub
		reglerTaille();
		canvas.redraw();
		
	}
	
	public void reglerTaille() {
		int nombreIt=0;
		int chargeMax=0;
		if (EugesElements.getChargeEstimeeMax()>EugesElements.getChargeReelleMax())
			chargeMax = EugesElements.getChargeEstimeeMax();
		else
			chargeMax = EugesElements.getChargeReelleMax();
		int nombreActivites = EugesElements.getActivitesRealiseesCount();
		if (EugesElements._projet!= null) 
			nombreIt = EugesElements._projet._listeIteration.size();
		if (((nombreActivites*120+nombreIt*100)<parent.getParent().getSize().x)&&(chargeMax*20<parent.getParent().getSize().y))
			canvas.setSize(parent.getParent().getSize().x,parent.getParent().getSize().y-20);
		else if ((nombreActivites*120+nombreIt*100)<parent.getParent().getSize().x)
			canvas.setSize(parent.getParent().getSize().x,chargeMax*20);
		else if (chargeMax*20<parent.getParent().getSize().y)
			canvas.setSize(nombreActivites*120+nombreIt*100,parent.getParent().getSize().y);
		else
			canvas.setSize(nombreActivites*120+nombreIt*100, chargeMax*20);
		
	}
	
	public ActiviteGraphique getAct(int x, int y) {
		for(Iterator it = rectActivites.iterator(); it.hasNext();) {
			ActiviteGraphique act = (ActiviteGraphique)it.next();
			if (act.inRect(x,y))
				return act;
		}
		return null;
	}
}
