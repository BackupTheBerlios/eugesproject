/*
 * Created on 26 nov. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.preferences;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import utilitaires.GestionImage;
import configuration.Config;

/**
 * @author Nicolas Broueilh
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CreerTraductionIHM implements SelectionListener {
	/**Le ResourceBundle permettant d'accéder aux fichiers de traduction*/
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);

	/**L'affichage*/
	private Display display;
	
	/**La fenêtre*/
	private Shell shell;
	
	/**Le bouton d'appel de l'aide*/
	private Button aide;
	
	/**Le bouton de validation de la création d'une nouvelle traduction*/
	private Button ok;
	
	/**Le bouton d'annulation de la création d'une nouvelle traduction*/
	private Button annuler;
	
	public CreerTraductionIHM(Shell parent) {
		//Mise en place de l'affichage de la fenêtre
		display = Display.getCurrent();
		shell = new Shell(parent,SWT.APPLICATION_MODAL|SWT.MAX|SWT.MIN|SWT.RESIZE);
		//Titre de la fenêtre
		shell.setText(message.getString("nomFenetre"));
		shell.setSize(350,175);
		// Définition de la taille de la fenêtre
		shell.setImage(GestionImage._euges);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		shell.setLayout(gridLayout);
				
		Label langue = new Label(shell,SWT.NONE);
		langue.setText(message.getString("langue"));
		
		Text textLangue = new Text(shell,SWT.BORDER);
		
		Label pays = new Label(shell,SWT.NONE);
		pays.setText(message.getString("pays"));

		Text textPays = new Text(shell,SWT.BORDER);
		
		
		Label standardLangue = new Label(shell,SWT.NONE);
		standardLangue.setText(message.getString("standardLangue"));

		Text textStandardLangue = new Text(shell,SWT.BORDER);
		textStandardLangue.setTextLimit(2);
		
		Label standardPays = new Label(shell,SWT.NONE);
		standardPays.setText(message.getString("standardPays"));

		Text textStandardPays = new Text(shell,SWT.BORDER);
		textStandardPays.setTextLimit(2);
				
		aide = new Button(shell,SWT.PUSH);
		aide.setText(message.getString("aide"));
		aide.addSelectionListener(this);
		
		ok = new Button(shell,SWT.PUSH);
		ok.setText(message.getString("ok"));
		ok.addSelectionListener(this);
		
		annuler = new Button(shell,SWT.PUSH);
		annuler.setText(message.getString("annuler"));
		annuler.addSelectionListener(this);
		
		
		
		GridData data = new GridData(GridData.FILL_VERTICAL);
		data.horizontalSpan=2;
		langue.setLayoutData(data);
		data = new GridData(GridData.FILL_VERTICAL);
		data.horizontalSpan=2;
		pays.setLayoutData(data);
		data = new GridData(GridData.FILL_VERTICAL);
		data.horizontalSpan=2;
		standardLangue.setLayoutData(data);
		data = new GridData(GridData.FILL_VERTICAL);
		data.horizontalSpan=2;
		standardPays.setLayoutData(data);
		
		GridData data2 = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_BEGINNING);
		data2.horizontalSpan=2;
		textLangue.setLayoutData(data2);
		data2 = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_BEGINNING);
		data2.horizontalSpan=2;
		textPays.setLayoutData(data2);
		data2 = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		data2.widthHint = 20;
		data2.horizontalSpan=1;
		textStandardLangue.setLayoutData(data2);
		data2 = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		data2.widthHint = 20;
		data2.horizontalSpan=1;
		textStandardPays.setLayoutData(data2);
		
		
		GridData data3 = new GridData();
		data3.horizontalSpan=2;
		aide.setLayoutData(data3);
		data3 = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(data3);
		data3 = new GridData(GridData.FILL_HORIZONTAL);
		annuler.setLayoutData(data3);
		
		
		
		
		shell.open();
		while(!shell.isDisposed()){
			if (!display.readAndDispatch()){
				display.sleep();
			}
		}
	}

	/**
	 * Traiter les évènements lors de clics sur les boutons
	 */
	public void widgetSelected(SelectionEvent arg0) {
		//Traitement si clic sur un bouton
		if (arg0.getSource() instanceof Button) {
			Button button = (Button)arg0.getSource();
			//Traitement si clic sur le bouton valider
			if (button == ok) {
				System.out.println("Ok");
			}
			//Traitement si clic sur le bouton annuler
			else if (button == annuler) {
				shell.close();				
			}
			//Traitement si clic sur le bouton aide
			else if (button == aide) {		
				System.out.println("Aide");
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
