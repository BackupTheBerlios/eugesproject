/*
 * Created on 14 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

import ihm.proprietes.PageProprietesActRealiseIHM;
import ihm.proprietes.PageProprietesActiviteIHM;
import ihm.proprietes.PageProprietesIterationIHM;
import ihm.proprietes.PageProprietesListeActivitesIHM;
import ihm.proprietes.PageProprietesListeIterationsIHM;
import ihm.proprietes.PageProprietesListePersonnesIHM;
import ihm.proprietes.PageProprietesListeProduitsIHM;
import ihm.proprietes.PageProprietesListeProduitsInIHM;
import ihm.proprietes.PageProprietesListeProduitsOutIHM;
import ihm.proprietes.PageProprietesListeRolesIHM;
import ihm.proprietes.PageProprietesPersonneIHM;
import ihm.proprietes.PageProprietesProduitIHM;
import ihm.proprietes.PageProprietesProjetIHM;
import ihm.proprietes.PageProprietesProjetVideIHM;
import ihm.proprietes.PageProprietesRoleIHM;
import ihm.proprietes.PageProprietesVersionIHM;

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
import donnees.Projet;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesProduit;
import donnees.eugesSpem.EugesRole;
import donnees.eugesSpem.EugesVersion;

/**
 * @author Nicolas Elbeze
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FenetreProprietesIHM {
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	private Shell _shell;
	private Display _display;
	
	public FenetreProprietesIHM(Shell shell) {
		_display = shell.getDisplay();
		_shell = new Shell(shell,SWT.CLOSE|SWT.APPLICATION_MODAL);
		_shell.setText(message.getString("titreShell"));
		_shell.setImage(GestionImage._euges);
	}
	
	
	public void open(int format, Object donnee) {
		// GridLayout pour l'alignement
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		_shell.setLayout(gridLayout);
		
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		
		switch(format) {
			case 0 :	PageProprietesProjetVideIHM pageProprietesProjetVideIHM = new PageProprietesProjetVideIHM(_shell);
						pageProprietesProjetVideIHM.pack();
						pageProprietesProjetVideIHM.setLayoutData(data);
						break;
				
			case 1 :	PageProprietesProjetIHM pageProprietesProjetIHM = new PageProprietesProjetIHM(_shell, (Projet) donnee);
						pageProprietesProjetIHM.pack();
						pageProprietesProjetIHM.setLayoutData(data);
						break;
						
			case 2 :	PageProprietesListeIterationsIHM pageProprietesListeIterationIHM = new PageProprietesListeIterationsIHM(_shell);
						pageProprietesListeIterationIHM.pack();
						pageProprietesListeIterationIHM.setLayoutData(data);
						break;
						
			case 3 :	PageProprietesIterationIHM pageProprietesIterationIHM = new PageProprietesIterationIHM(_shell, (Iteration) donnee);
						pageProprietesIterationIHM.pack();
						pageProprietesIterationIHM.setLayoutData(data);
						break;
						
			case 4 :	PageProprietesListeActivitesIHM pageProprietesListeActivitesIHM = new PageProprietesListeActivitesIHM(_shell);
						pageProprietesListeActivitesIHM.pack();
						pageProprietesListeActivitesIHM.setLayoutData(data);
						break;
						
						
			case 5 :	PageProprietesActiviteIHM pageProprietesActiviteIHM = new PageProprietesActiviteIHM(_shell, (EugesActivite) donnee);
						pageProprietesActiviteIHM.pack();
						pageProprietesActiviteIHM.setLayoutData(data);
						break;

			case 6 :	PageProprietesActRealiseIHM pageProprietesActRealiseIHM = new PageProprietesActRealiseIHM(_shell, (EugesActRealise) donnee);
						pageProprietesActRealiseIHM.pack();
						pageProprietesActRealiseIHM.setLayoutData(data);
						break;
						
			case 7 :	PageProprietesListeProduitsIHM pageProprietesListeProduitsIHM = new PageProprietesListeProduitsIHM(_shell);
						pageProprietesListeProduitsIHM.pack();
						pageProprietesListeProduitsIHM.setLayoutData(data);
						break;
						
			case 8 :	PageProprietesListeProduitsInIHM pageProprietesListeProduitsInIHM = new PageProprietesListeProduitsInIHM(_shell);
						pageProprietesListeProduitsInIHM.pack();
						pageProprietesListeProduitsInIHM.setLayoutData(data);
						break;
						
						
			case 9 :	PageProprietesListeProduitsOutIHM pageProprietesListeProduitsOutIHM = new PageProprietesListeProduitsOutIHM(_shell);
						pageProprietesListeProduitsOutIHM.pack();
						pageProprietesListeProduitsOutIHM.setLayoutData(data);
						break;

			case 10 :	PageProprietesProduitIHM pageProprietesProduitIHM = new PageProprietesProduitIHM(_shell, (EugesProduit) donnee);
						pageProprietesProduitIHM.pack();
						pageProprietesProduitIHM.setLayoutData(data);
						break;
						
						
			case 11 :	PageProprietesVersionIHM pageProprietesVersionIHM = new PageProprietesVersionIHM(_shell, (EugesVersion) donnee);
						pageProprietesVersionIHM.pack();
						pageProprietesVersionIHM.setLayoutData(data);
						break;
						
			case 12 :	PageProprietesListeRolesIHM pageProprietesListeRolesIHM = new PageProprietesListeRolesIHM(_shell);
						pageProprietesListeRolesIHM.pack();
						pageProprietesListeRolesIHM.setLayoutData(data);
						break;
						
						
			case 13 :	PageProprietesRoleIHM pageProprietesRoleIHM = new PageProprietesRoleIHM(_shell, (EugesRole) donnee);
						pageProprietesRoleIHM.pack();
						pageProprietesRoleIHM.setLayoutData(data);
						break;

			case 14 :	PageProprietesListePersonnesIHM pageProprietesListePersonnesIHM = new PageProprietesListePersonnesIHM(_shell);
						pageProprietesListePersonnesIHM.pack();
						pageProprietesListePersonnesIHM.setLayoutData(data);
						break;

			case 15 :	PageProprietesPersonneIHM pageProprietesPersonneIHM = new PageProprietesPersonneIHM(_shell, (EugesPersonne) donnee);
						pageProprietesPersonneIHM.pack();
						pageProprietesPersonneIHM.setLayoutData(data);
						break;
		}
			
		
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
		fermerBut.setText("Fermer");
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
