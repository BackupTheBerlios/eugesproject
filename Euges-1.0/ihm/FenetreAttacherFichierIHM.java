/*
 * Created on 6 mars 2004
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import configuration.Config;
import donnees.eugesSpem.EugesVersion;

/**
 * @author Mathieu GAYRAUD
 *
 */
public class FenetreAttacherFichierIHM {
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	private Shell _shell;
	private EugesVersion _v;
	private String _oldFile = "";
	private Display _display;
	
	public FenetreAttacherFichierIHM (Shell shell, EugesVersion v) {
		_shell = new Shell(shell, SWT.CLOSE|SWT.APPLICATION_MODAL|SWT.RESIZE);
		_v = v;
		_oldFile = _v.getFile();
		_display = shell.getDisplay();
		_shell.setText(message.getString("titreFenetre"));
	}
	
	public void open() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 6;
		layout.makeColumnsEqualWidth = true;
		_shell.setLayout(layout);
		GridData data;
		
		Label label = new Label(_shell, SWT.NONE);
		label.setText(message.getString("label"));
		data = new GridData(GridData.FILL_BOTH);
		label.setLayoutData(data);
		
		final Text fichier = new Text(_shell, SWT.READ_ONLY|SWT.BORDER);
		fichier.setText(_oldFile);
		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 3;
		fichier.setLayoutData(data);

		Button choose = new Button(_shell, SWT.PUSH | SWT.FLAT);
		choose.setText(message.getString("parcourir"));
		choose.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(_shell);
				fileDialog.setText(message.getString("titreFenetreOuvrir"));
				String chemin = fileDialog.open();
				if (chemin!=null)
				{
					// ecrire le nom du fichier dans le champ texte
					fichier.setText(chemin);
					_v.setFile(chemin);
					_oldFile = chemin;
				}
			}
		});
		data = new GridData(GridData.FILL_BOTH);
		choose.setLayoutData(data);

		Button clear = new Button(_shell, SWT.PUSH | SWT.FLAT);
		clear.setText(message.getString("effacer"));
		clear.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fichier.setText("");
				_v.setFile("");
				_oldFile = "";
			}
		});
		data = new GridData(GridData.FILL_BOTH);
		clear.setLayoutData(data);

		// bas de la page
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
		fermerBut.setText(message.getString("fermer"));
		fermerBut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
					_shell.dispose();
			}
		});

		c.pack();
		data = new GridData(GridData.HORIZONTAL_ALIGN_END);
		c.setLayoutData(data);

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 6;
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
