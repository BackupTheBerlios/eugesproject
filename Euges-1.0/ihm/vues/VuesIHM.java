/*
 * Created on 17 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.vues;

import ihm.vues.grapheActivites.GrapheIHM;
import ihm.vues.grapheCharges.*;
import ihm.vues.planIt.*;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class VuesIHM extends ViewForm{

	//vecteur de vues
	private Vector _vues;
	private int pageCourante=0;
	/**
	 * @param parent
	 * @param nbIt
	 */
	public VuesIHM(final SashForm parent) {
		super(parent, SWT.BORDER);
		//allocation du vecteur de vues
		_vues = new Vector();
		//composite contenant les vues
		final Composite c = new Composite(this, SWT.NONE);
		//ajout des vues dans la liste des vues
		_vues.add(new PlanItIHM(c, 0));
		_vues.add(new GrapheChargesIHM(c));
		_vues.add(new GrapheIHM(c));
		
		((PageVuesIHM)_vues.elementAt(0)).setVisible(true);
		((PageVuesIHM)_vues.elementAt(1)).setVisible(false);
		((PageVuesIHM)_vues.elementAt(2)).setVisible(false);
		
		addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				c.setBounds(getClientArea());
				((PageVuesIHM)_vues.elementAt(0)).setBounds(c.getClientArea());
				((PageVuesIHM)_vues.elementAt(1)).setBounds(c.getClientArea());
				((PageVuesIHM)_vues.elementAt(2)).setBounds(c.getClientArea());
			}
		});
	}
	/**
	 * affiche la page dont le numero est passé en parametre
	 * @param numPage numero de la page a afficher
	 */
	public void setVisible(int numPage){
		System.out.println("page courante="+pageCourante);
		System.out.println("nouvelle page="+numPage);
		
		((PageVuesIHM)_vues.elementAt(numPage)).setVisible(true);
		((PageVuesIHM)_vues.elementAt(numPage)).loadData();
		((PageVuesIHM)_vues.elementAt(pageCourante)).setVisible(false);
		
		pageCourante=numPage;
	}
	/**
	 * @param i indice du composite a retourner
	 * @return composite voulu
	 */
	public Composite elementAt(int i){
		return (Composite)_vues.elementAt(i);
	}
}