/*
 * Created on 11 janv. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import utilitaires.GestionImage;
import application.EugesElements;
import application.NouvelleActivite;
import configuration.Config;

/**
 * @author Nicolas
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class NouvelleActiviteIHM implements SelectionListener {
	/**Le ResourceBundle permettant d'accéder aux fichiers de traduction*/
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);

	/**L'affichage*/
	private Display display;
	
	/**La fenêtre*/
	private Shell shell;
	
	/**Le bouton d'appel de l'aide*/
	private Button aide;
	
	/**Le bouton de validation de la création d'une nouvelle activité*/
	private Button ok;
	
	/**Le bouton d'annulation de la création d'une nouvelle activité*/
	private Button annuler;
	
	/**Le champ de saisie du nom de l'activité*/
	private Text textNom;
	
	/**Le champ de saisie de la duree de l'activite*/
	private Text textChargeEst;
	
	/**La zone de saisie de la description de l'activite*/
	private Text textDesc;
	
	public NouvelleActiviteIHM(Shell parent) {
		//Mise en place de l'affichage de la fenêtre
		display = Display.getCurrent();
		shell = new Shell(parent,SWT.APPLICATION_MODAL|SWT.MAX|SWT.MIN|SWT.RESIZE);
		//Titre de la fenêtre
		shell.setText(message.getString("nomFenetre"));
		shell.setSize(350,300);
		// Définition de la taille de la fenêtre
		shell.setImage(GestionImage._euges);
		

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		shell.setLayout(gridLayout);
		
		Label nom = new Label(shell,SWT.NONE);
		nom.setText(message.getString("nom"));
		
		textNom = new Text(shell,SWT.BORDER);
		
//		Label chargeEst = new Label(shell,SWT.NONE);
//		chargeEst.setText(message.getString("chargeEst"));
//
//		textChargeEst = new Text(shell,SWT.BORDER);
//		
//		Label heure = new Label(shell,SWT.NONE);
//		heure.setText(message.getString("heure"));
		
		Label desc = new Label(shell,SWT.NONE);
		desc.setText(message.getString("description"));
		
		textDesc= new Text(shell, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		
		aide = new Button(shell,SWT.PUSH);
		aide.setText(message.getString("aide"));
		aide.addSelectionListener(this);
		
		ok = new Button(shell,SWT.PUSH);
		ok.setText(message.getString("ok"));
		ok.addSelectionListener(this);
		
		annuler = new Button(shell,SWT.PUSH);
		annuler.setText(message.getString("annuler"));
		annuler.addSelectionListener(this);
		

		GridData data = new GridData();
		data.horizontalSpan=2;
		nom.setLayoutData(data);
//		data = new GridData();
//		data.horizontalSpan=2;
//		chargeEst.setLayoutData(data);
		data = new GridData();
		data.horizontalSpan=4;
		desc.setLayoutData(data);
		
		GridData data2 = new GridData(GridData.FILL_HORIZONTAL);
		data2.horizontalSpan=2;
		textNom.setLayoutData(data2);
//		data2 = new GridData(GridData.FILL_HORIZONTAL);
//		data2.horizontalSpan=1;
//		textChargeEst.setLayoutData(data2);
//		data2 = new GridData(GridData.FILL_HORIZONTAL);
//		data2.horizontalSpan=1;
//		heure.setLayoutData(data2);
		data2 = new GridData(GridData.FILL_BOTH);
		data2.horizontalSpan=4;
		textDesc.setLayoutData(data2);
		

		GridData data3 = new GridData();
		data3.horizontalSpan=2;
		aide.setLayoutData(data3);
		data3 = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(data3);
		data3 = new GridData(GridData.FILL_HORIZONTAL);
		annuler.setLayoutData(data3);
		
		//ouverture de la page au centre de l'appli
		Rectangle bounds = parent.getBounds ();
		Rectangle rect = shell.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation (x, y);
		
		shell.open();
		while(!shell.isDisposed()){
			if (!display.readAndDispatch()){
				display.sleep();
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent arg0) {
		//Traitement si clic sur un bouton
		if (arg0.getSource() instanceof Button) {
			Button button = (Button)arg0.getSource();
			//Traitement si clic sur le bouton nouvelle activite
			if (button == ok) {
				//tester les infos saisies par l'utilisateur
//				if (NouvelleActivite.saisieValide(textNom.getText(),textChargeEst.getText(),textDesc.getText())) {
				if (NouvelleActivite.saisieValide(textNom.getText(),textDesc.getText())) {
					//Creer la nouvelle activite
//					NouvelleActivite.creerActivite(textNom.getText(),textChargeEst.getText(),textDesc.getText());
					NouvelleActivite.creerActivite(textNom.getText(),textDesc.getText());
					//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
					EugesElements.processusEnregistre = true;
					shell.close();
				}
				else {
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_ERROR|SWT.OK);
					messageBox.setMessage(message.getString("messageBox.message"));
					messageBox.setText(message.getString("messageBox.text"));
					messageBox.open();
				}				
			}
			else if (button == annuler) {
				shell.close();
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
