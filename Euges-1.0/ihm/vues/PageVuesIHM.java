/*
 * Created on 18 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.vues;

import org.eclipse.swt.widgets.Composite;

/**
 * @author ferreira
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public abstract class PageVuesIHM extends Composite{
	public PageVuesIHM(final Composite comp, int style) {
		super(comp, style);
	}
	public abstract void loadData();
}
