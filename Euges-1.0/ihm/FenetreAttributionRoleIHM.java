/*
 * Created on 8 févr. 2004
 *
 */
package ihm;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import utilitaires.GestionImage;
import configuration.Config;
import donnees.Iteration;

/**
 * @author Mathieu GAYRAUD
 *
 */
public class FenetreAttributionRoleIHM {
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);

	private Shell _shell;
	private Display _display;
	private Iteration _it;

	public FenetreAttributionRoleIHM (Shell shell, Iteration it) {
		_it = it;
		_display = shell.getDisplay();
		_shell = new Shell(shell,SWT.CLOSE|SWT.APPLICATION_MODAL);
		_shell.setText(message.getString("titreShell"));
		_shell.setImage(GestionImage._euges);
	}
	
	public void open() {
		// GridLayout pour l'alignement
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		_shell.setLayout(gridLayout);
		
		PageAttributionRolesIHM pageAttributionRolesIHM = new PageAttributionRolesIHM(_shell,_it);
		pageAttributionRolesIHM.pack();
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		pageAttributionRolesIHM.setLayoutData(data);

		Composite basDePage = new Composite(_shell,SWT.NONE);
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		basDePage.setLayout(gridLayout2);
		Label sep = new Label(basDePage,SWT.SEPARATOR|SWT.HORIZONTAL);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		sep.setLayoutData(data);
		
		Label copyright = new Label(basDePage,SWT.NONE);
		copyright.setText("www.euges.fr.st");
		copyright.setEnabled(false);
		copyright.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING|GridData.HORIZONTAL_ALIGN_BEGINNING));

		Composite c = new Composite(basDePage,SWT.NONE);
		RowLayout rowLayout = new RowLayout();
		rowLayout.wrap = false;
		rowLayout.pack = false;
		rowLayout.justify = false;
		rowLayout.type = SWT.HORIZONTAL;
		rowLayout.marginLeft = 5;
		rowLayout.marginTop = 5;
		rowLayout.marginRight = 5;
		rowLayout.marginBottom = 5;
		rowLayout.spacing = 5;
		c.setLayout(rowLayout);
		Button fermerBut = new Button(c,SWT.PUSH);
		fermerBut.setText(message.getString("Fermer"));
		fermerBut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
					_shell.dispose();
			}
		});

		c.pack();
		data = new GridData(GridData.HORIZONTAL_ALIGN_END);
		c.setLayoutData(data);

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		basDePage.setLayoutData(data);

		_shell.pack();
		// ouvrir la fenêtre au centre de l'écran
		Rectangle bounds = _shell.getParent().getBounds ();
		Rectangle rect = _shell.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		_shell.setLocation (x, y);
		_shell.open();
		
		while(!_shell.isDisposed()){
		if(!_shell.getDisplay().readAndDispatch())
			_shell.getDisplay().sleep();
		}
	}
}
