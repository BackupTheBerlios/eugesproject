/*
 * Created on 4 mars 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import utilitaires.GestionImage;
import configuration.Config;

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageGestionIterationIHM extends Dialog{
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public PageGestionIterationIHM(Shell shell){
		super(shell);
		final Shell shellPage = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shellPage.setSize(400,500);
		//icone eugesdans la barre de titre
		shellPage.setImage(GestionImage._euges);
		shellPage.setText(message.getString("pageGestionIterationIHM.titre"));
		
		GridLayout layout = new GridLayout(2, false);
		shellPage.setLayout(layout);
		
		PageIterationIHM itCmp = new PageIterationIHM(shellPage);
		
		//separateur
		Label sep1 = new Label(shellPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		//Informations
		Label information = new Label(shellPage, SWT.BORDER|SWT.ICON_WARNING);
		information.setText(message.getString("pageGestionIterationIHM.information"));
		
		//separateur
		Label sep2 = new Label(shellPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		Button fermer = new Button(shellPage, SWT.PUSH);
		fermer.setText(message.getString("pageGestionIterationIHM.fermer"));
		fermer.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shellPage.dispose();
			}
		});
		
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan=2;
		itCmp.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan=2;
		sep1.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan=2;
		information.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan=2;
		sep2.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan=2;
		fermer.setLayoutData(data);
		
//		 ouvrir la fenêtre au centre de l'écran
		Rectangle bounds = shell.getBounds ();
		Rectangle rect = shellPage.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shellPage.setLocation (x, y);
		
		//shellPage.pack();
		shellPage.open();
//		 Boucle d'évènements
		while (!shellPage.isDisposed()){
			if (!Display.getCurrent().readAndDispatch())
				Display.getCurrent().sleep();
		}
	}
}