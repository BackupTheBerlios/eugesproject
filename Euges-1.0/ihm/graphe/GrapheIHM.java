/*
 * Created on 30 janv. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.graphe;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class GrapheIHM extends Composite{
	
	//tableau représentant le graphe
	private Table _graphe;
	
	
	public GrapheIHM(final Composite parent){
		super(parent, SWT.NONE|SWT.V_SCROLL);
		_graphe = new Table(parent, SWT.NONE);
		
	}
}
