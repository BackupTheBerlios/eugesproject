/*
 * Created on 17 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.vues.grapheActivites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class GrapheBasIHM extends ViewForm{
	//private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	final private Composite composite;
	
	public GrapheBasIHM(final Composite comp) {
		super(comp, SWT.BORDER);
		composite = new Composite(this, SWT.NONE);
		// titre
		Font font = new Font(comp.getDisplay(), "Arial", 15, 15);
		Label titre = new Label(composite, SWT.NONE|SWT.CENTER);
		titre.setFont(font);
		titre.setText("en cours de développement");
		
		//mise en place du layout
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);
		//layout
		//titre
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint =titre.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		titre.setLayoutData(data);
		
		//redimensionnement du composite
		this.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				composite.setBounds(getClientArea());
			}
		});
	}
	/**
	 * @return Returns the composite.
	 */
	public Composite getComposite() {
		return composite;
	}
}
