/*
 * Created on 25 nov. 2003
 *
 */
package ihm;

import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import utilitaires.GestionImage;

import application.EugesElements;
import application.NewRole;
import configuration.Config;
import donnees.eugesSpem.EugesRole;
/**
 * @author Mathieu GAYRAUD
 *
 */
public class NewRoleIHM {
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	private Shell _shell;
	private Display _display;
	private Vector _liste;
	
	public NewRoleIHM(Shell shell) {
		_display = shell.getDisplay();
		_shell = new Shell(shell,SWT.CLOSE|SWT.APPLICATION_MODAL);
		_shell.setText(message.getString("NewRoleIHM.titreShell"));
		_shell.setImage(GestionImage._role);
	}

	public void open() {
		// GridLayout pour l'alignement
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		_shell.setLayout(gridLayout);

		_liste = EugesElements.listeRoles;
		
		Label legende = new Label(_shell,SWT.WRAP);
		legende.setText(message.getString("NewRoleIHM.nomRole"));
		
		final Text newRole = new Text(_shell,SWT.BORDER);
		newRole.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		
		Button ajoutBut = new Button(_shell,SWT.FLAT);
		ajoutBut.setText(message.getString("NewRoleIHM.ADD"));
		ajoutBut.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		
		Label legende2 = new Label(_shell,SWT.WRAP);
		legende2.setText(message.getString("NewRoleIHM.listRole"));
		
		int largeur;
		if (legende.computeSize(SWT.DEFAULT,SWT.DEFAULT).x > legende2.computeSize(SWT.DEFAULT,SWT.DEFAULT).x)
			largeur = legende.computeSize(SWT.DEFAULT,SWT.DEFAULT).x;
		else
			largeur = legende2.computeSize(SWT.DEFAULT,SWT.DEFAULT).x;
		
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		data.widthHint = largeur;
		legende.setLayoutData(data);
		data = new GridData(GridData.VERTICAL_ALIGN_BEGINNING|GridData.HORIZONTAL_ALIGN_BEGINNING);
		data.widthHint = largeur;
		legende2.setLayoutData(data);
		
		final List listR = new List(_shell,SWT.BORDER|SWT.V_SCROLL|SWT.MULTI);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.widthHint = 170;
		data.heightHint = 100;
		listR.setLayoutData(data);
		_liste = NewRole.roleToVector();
		String[] temp = new String[_liste.size()];
		_liste.copyInto(temp);
		listR.setItems(temp);
		
		newRole.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				String string = "";
				switch (e.character) {
					case SWT.CR:
						if (!newRole.getText().equals("")) {
							if (!_liste.contains(newRole.getText())) {
								EugesRole role = new EugesRole(newRole.getText());
								EugesElements.ajouterElement(role);
								_liste = NewRole.roleToVector();
								String[] temp = new String[_liste.size()];
								_liste.copyInto(temp);
								listR.setItems(temp);
								newRole.setText("");
								newRole.setFocus();
							}
							else
								newRole.setFocus();
						}
						else {
							MessageBox msgErreur = new MessageBox(_shell,SWT.ICON_ERROR);
							msgErreur.setMessage(message.getString("NewRoleIHM.messageErreur"));
							msgErreur.setText(message.getString("NewRoleIHM.titreErreur"));
							msgErreur.open();
						}
						break;
					default:
						break;
				}
			}
			public void keyReleased(KeyEvent arg0) {
				
			}
		});
		
		ajoutBut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				if (!newRole.getText().equals("")) {
					if (!_liste.contains(newRole.getText())) {
						EugesRole role = new EugesRole(newRole.getText());
						EugesElements.ajouterElement(role);
						_liste = NewRole.roleToVector();
						String[] temp = new String[_liste.size()];
						_liste.copyInto(temp);
						listR.setItems(temp);
						newRole.setText("");
						newRole.setFocus();
					}
					else
						newRole.setFocus();
				}
				else {
					MessageBox msgErreur = new MessageBox(_shell,SWT.ICON_ERROR);
					msgErreur.setMessage(message.getString("NewRoleIHM.messageErreur"));
					msgErreur.setText(message.getString("NewRoleIHM.titreErreur"));
					msgErreur.open();
				}
			}
		});


		Button supprBut = new Button(_shell,SWT.FLAT);
		supprBut.setText(message.getString("NewRoleIHM.DEL"));
		supprBut.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING|GridData.HORIZONTAL_ALIGN_FILL));
		supprBut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				String[] temp = listR.getSelection();
				for (int i = 0; i < temp.length; i++) {
					NewRole.supprimerRole(temp[i]);
				}
				_liste = NewRole.roleToVector();
				temp = new String[_liste.size()];
				_liste.copyInto(temp);
				listR.setItems(temp);
				newRole.setText("");
				newRole.setFocus();
			}
		});
		
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
		fermerBut.setText(message.getString("NewRoleIHM.Fermer"));
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
		if(!_display.readAndDispatch())
			_display.sleep();
		}
	}
}
