/*
 * Created on 15 janv. 2004
 *
 */
package ihm.vues.graphe;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import utilitaires.SashPersoH;


/**
 * @author Mathieu GAYRAUD
 *
 */
public class GrapheIHM extends Composite{
	
	public GrapheIHM(Composite compoShell) {
		super(compoShell, SWT.NONE);
		//Shell compoShell = new Shell(compo.getShell(), SWT.APPLICATION_MODAL | SWT.CLOSE);
		final SashPersoH sash = new SashPersoH(compoShell, 75);
		final GrapheHautIHM haut = new GrapheHautIHM(sash.getTop());
		final GrapheBasIHM bas = new GrapheBasIHM(sash.getBottom());
		
		//redimensionnement des frames haut et bas enb fonction du remendisionnement de la fenetre
		compoShell.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				haut.setBounds(sash.getTop().getClientArea());
				bas.setBounds(sash.getBottom().getClientArea());
			}
		});
	}
}