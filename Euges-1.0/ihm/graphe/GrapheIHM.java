/*
 * Created on 15 janv. 2004
 *
 */
package ihm.graphe;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import utilitaires.SashPersoH;


/**
 * @author Mathieu GAYRAUD
 *
 */
public class GrapheIHM extends Composite{
	
	public GrapheIHM(Shell compoShell) {
		super(compoShell, SWT.NONE);
		//Shell compoShell = new Shell(compo.getShell(), SWT.APPLICATION_MODAL | SWT.CLOSE);
		SashPersoH sash = new SashPersoH(compoShell, 75, new GrapheHautIHM(compoShell), null);

	}
}