/*
 * Created on 17 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.vues.graphe;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Test{

	/**
	 * @param parent
	 */
	public Test() {
		Display d = Display.getCurrent();
		Shell page = new Shell(d);
		GrapheIHM c = new GrapheIHM(page);
		
		page.open();
	}
	
}
