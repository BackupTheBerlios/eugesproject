/*
 * Created on 17 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.vues;

import ihm.vues.grapheActivites.GrapheHautIHM;
import ihm.vues.grapheCharges.GrapheChargesIHM;
import ihm.vues.planIt.PlanItIHM;

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
	private final Composite c;
	/**
	 * @param parent
	 * @param nbIt
	 */
	public VuesIHM(final SashForm parent) {
		super(parent, SWT.BORDER);
		//allocation du vecteur de vues
		_vues = new Vector();
		//composite contenant les vues
		c = new Composite(this, SWT.NONE);
		//ajout des vues dans la liste des vues
		_vues.add(new PlanItIHM(c, 0));
		_vues.add(new GrapheHautIHM(c));
		_vues.add(new GrapheChargesIHM(c));
		
		((PageVuesIHM)_vues.elementAt(0)).setVisible(true);
		((PageVuesIHM)_vues.elementAt(1)).setVisible(false);
		((PageVuesIHM)_vues.elementAt(2)).setVisible(false);
		
		addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				c.setBounds(getClientArea());
				for (int i=0; i<_vues.size(); i++)
					((PageVuesIHM)_vues.elementAt(i)).setBounds(c.getClientArea());
			}
		});
	}
	/**
	 * affiche la page dont le numero est passé en parametre
	 * @param numPage numero de la page a afficher
	 */
	public void setVisible(int numPage){		
		if (((PageVuesIHM)_vues.elementAt(numPage)) instanceof GrapheChargesIHM) {
			((GrapheChargesIHM)_vues.elementAt(numPage)).dispose();
			_vues.setElementAt(new GrapheChargesIHM(c),numPage);
		}
		((PageVuesIHM)_vues.elementAt(numPage)).setVisible(true);
		((PageVuesIHM)_vues.elementAt(numPage)).loadData();
		if (pageCourante!=numPage){
			((PageVuesIHM)_vues.elementAt(pageCourante)).setVisible(false);
			pageCourante=numPage;
		}
	}
	/**
	 * @param i indice du composite a retourner
	 * @return composite voulu
	 */
	public  PageVuesIHM elementAt(int i){
		return (PageVuesIHM)_vues.elementAt(i);
	}
}