/*
 * Created on 18 mars 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import utilitaires.GestionImage;

import configuration.Config;

/**
 * @author HUT
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FenetreErreurChargement extends Dialog{
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	private Display _display=Display.getCurrent();
	private Shell _shell;
	private Button boutonCourant;
	
	private Display display=Display.getCurrent();;
	
	public FenetreErreurChargement(final Shell shell){
		//initialisation des attributs (hors combos)
		super(shell);

		_shell = new Shell(shell, SWT.APPLICATION_MODAL | SWT.CLOSE);
		_shell.setText(message.getString("titre"));
		GridLayout gr = new GridLayout (1,true);
		_shell.setLayout(gr);
		
		//définition et initialisation des composites de la fenêtre
		final Composite fenetreErreur = new Composite (_shell, SWT.NONE);
		
		//nouveau shell
		final Shell shellFenetreErreurChargement = new Shell(shell, SWT.APPLICATION_MODAL | SWT.CLOSE);
		shellFenetreErreurChargement.setSize(200, 120);
		//curseur qui apparait lors du survol des boutons
		Cursor curseurMain = new Cursor(display, SWT.CURSOR_HAND);
		shellFenetreErreurChargement.setImage(GestionImage._euges);
		
		// titre de la fenêtre
		shellFenetreErreurChargement.setText(message.getString("titre"));
		
		// message d'erreur
		Label fenetreErreurChargementLabel = new Label(fenetreErreur, SWT.NONE);
		fenetreErreurChargementLabel.setText(message.getString("texte"));
		
		//création du bouton pour fermer le calendrier
		Button fermer = new Button (_shell, SWT.PUSH);
		fermer.setText(message.getString("fermer"));
		fermer.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected  (SelectionEvent e){
				_shell.dispose();
			}
		});
		
		//mise en place du layout
		GridLayout gridLayout = new GridLayout();
		shellFenetreErreurChargement.setLayoutData(gridLayout);
		
		//positionnement des objets sur le layout
		GridData gridData = new GridData();
		fenetreErreurChargementLabel.setLayoutData(gridLayout);
		fermer.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		//shellFenetreErreurChargement.pack();
		
		// ouvrir la fenêtre au centre de l'écran
		Rectangle bounds = shell.getBounds ();
		Rectangle rect = shellFenetreErreurChargement.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shellFenetreErreurChargement.setLocation (x, y);
		
		//ouverture du shell
		shellFenetreErreurChargement.open();
	}
}
