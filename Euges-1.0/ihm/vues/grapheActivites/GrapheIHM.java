/*
 * Created on 15 janv. 2004
 *
 */
package ihm.vues.grapheActivites;


import ihm.vues.PageVuesIHM;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;


/**
 * @author Will
 *
 */
public class GrapheIHM extends PageVuesIHM{
	
	final SashForm _sashForm;
	final GrapheHautIHM _haut;
	final GrapheBasIHM _bas;
	
	public GrapheIHM(final ViewForm parent) {
		super(parent, SWT.NONE);
		_sashForm = new SashForm(this, SWT.VERTICAL);
		_haut = new GrapheHautIHM(_sashForm);
		_bas = new GrapheBasIHM(_sashForm);
		_sashForm.setWeights(new int [] {75,25});

		_sashForm.setBounds(parent.getClientArea());
		//redimensionnement du sashForm
		parent.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				_sashForm.setBounds(parent.getClientArea());
			}
		});
	}

	/* (non-Javadoc)
	 * @see ihm.vues.PageVuesIHM#loadData()
	 */
	public void loadData() {
		_haut.loadData();
	}

	/* (non-Javadoc)
	 * @see ihm.vues.PageVuesIHM#setVisible(boolean)
	 */
	public void setVisible(boolean visible) {
		_sashForm.setVisible(visible);
	}
}