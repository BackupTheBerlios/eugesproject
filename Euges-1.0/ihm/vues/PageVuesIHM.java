/*
 * Created on 18 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.vues;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * @author ferreira
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public abstract class PageVuesIHM extends Composite{
	public PageVuesIHM(final ViewForm view, int style) {
		super(view, style);
		view.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				setBounds(view.getClientArea());
			}
		});
	}
	public abstract void loadData();
	
	//public void setVisible(boolean visible);
}
