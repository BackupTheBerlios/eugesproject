/*
 * Created on 15 janv. 2004
 *
 */
package ihm.graphe;


import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import configuration.Config;


/**
 * @author Mathieu GAYRAUD
 *
 */
public class GrapheIHM extends ViewForm {
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	
	private Table _graphe;
	private CTabItem[] _tabItems;

	public GrapheIHM(final Composite parent) {
		super(parent, SWT.BORDER|SWT.V_SCROLL);
		
		final Composite c = new Composite(this, SWT.BORDER);
		c.setSize(200,200);
		// titre
		Font font = new Font(parent.getDisplay(), "Arial", 15, 15);
		Label titre = new Label(this, SWT.NONE|SWT.CENTER);
		titre.setFont(font);
		titre.setText(message.getString("grapheIHM.titre"));
		titre.pack();
		
		//graphe des données
		_graphe = new Table(c, SWT.NONE);
		TableColumn colonne1 = new TableColumn(_graphe, SWT.LEFT);
		TableColumn colonne2 = new TableColumn(_graphe, SWT.LEFT);
		TableColumn colonne3 = new TableColumn(_graphe, SWT.LEFT);
		colonne1.setWidth(100);
		colonne2.setWidth(100);
		colonne3.setWidth(100);
		
		colonne1.setText("0");
		colonne2.setText("1");
		colonne3.setText("2");
		
		//mise en place du layout
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		c.setLayout(layout);
		
		GridData data = new GridData();
		titre.setLayoutData(data);
		
		data = new GridData();
		_graphe.setLayoutData(data);
		
	}
}
