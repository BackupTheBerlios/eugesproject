/*
 * Created on 17 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.vues;

import ihm.vues.graphe.GrapheIHM;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Composite;

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Vues extends ViewForm{

	//vecteur de vues
	private Vector _vues;
	/**
	 * @param parent
	 * @param nbIt
	 */
	public Vues(final SashForm parent) {
		super(parent, SWT.BORDER);
		//allocation du vecteur de vues
		_vues = new Vector();
		//composite contenant les vues
		//Composite c = new Composite(parent, SWT.BORDER);
		//c.setSize(100,100);
		//_vues.add(new GrapheIHM(c.getShell()));
		
	}
}
