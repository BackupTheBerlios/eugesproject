/*
 * Created on 15 janv. 2004
 *
 */
package ihm.vues.grapheActivites;


import ihm.vues.PageVuesIHM;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;


/**
 * @author Mathieu GAYRAUD
 *
 */
public class GrapheIHM extends PageVuesIHM{
	
	final SashForm _sashForm;
	final GrapheHautIHM _haut;
	final GrapheBasIHM _bas;
	
	public GrapheIHM(Composite compo) {
		super(compo, SWT.NONE);
		_sashForm = new SashForm(compo, SWT.VERTICAL);
		_haut = new GrapheHautIHM(_sashForm);
		_bas = new GrapheBasIHM(_sashForm);
		_sashForm.setWeights(new int [] {75,25});

		//redimensionnement du sashForm
		compo.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				_sashForm.setBounds(getClientArea());
			}
		});
	}

	/* (non-Javadoc)
	 * @see ihm.vues.PageVuesIHM#loadData()
	 */
	public void loadData() {
		// TODO Auto-generated method stub
		_haut.loadData();
	}
}