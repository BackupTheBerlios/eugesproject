/*
 * Created on 30 nov. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public abstract class PageAssistantIHM extends Composite{

	private boolean[] _activeBoutons = new boolean[5];
	/**
	 * constructeur de l'asistant
	 */
	public PageAssistantIHM(Shell shell) {
		super(shell, SWT.NONE);
	}
	/**
	 * fonction abstraite de vérifictaion des données des pages
	 * 
	 * @return true si la page ne comporte aucune erreur, sinon false
	 */
	public abstract boolean verifDonnees();
	/**
	 * charge le données du modèle pour chaqu'une des pages
	 *
	 */
	public abstract void loadData();
	/**
	 * @return Returns the _activeBoutons.
	 */
	public boolean[] get_activeBoutons() {
		return _activeBoutons;
	}

	/**
	 * @param boutons The _activeBoutons to set.
	 */
	public void set_activeBoutons(boolean[] boutons) {
		_activeBoutons = boutons;
	}

}
