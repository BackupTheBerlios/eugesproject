/*
 * Created on 4 mars 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

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

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageGestionIterationIHM extends Dialog{
	
	public PageGestionIterationIHM(Shell shell){
		super(shell);
		final Shell shellVide = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		
		GridLayout layout = new GridLayout(2, false);
		shellVide.setLayout(layout);
		
		PageIterationIHM itCmp = new PageIterationIHM(shellVide);
		
//		separateur1
		Label sep1 = new Label(shellVide, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		Button fermer = new Button(shellVide, SWT.PUSH);
		fermer.setText("Ok");
		fermer.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shellVide.dispose();
			}
		});
		
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan=2;
		itCmp.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan=2;
		sep1.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan=2;
		fermer.setLayoutData(data);
		
//		 ouvrir la fenêtre au centre de l'écran
		Rectangle bounds = shell.getBounds ();
		Rectangle rect = shellVide.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shellVide.setLocation (x, y);
		
		shellVide.pack();
		shellVide.open();
//		 Boucle d'évènements
		while (!shellVide.isDisposed()){
			if (!Display.getCurrent().readAndDispatch())
				Display.getCurrent().sleep();
		}
	}
}