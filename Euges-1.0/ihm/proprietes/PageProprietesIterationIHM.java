/*
 * Created on 14 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.proprietes;


import ihm.PageAssistantIHM;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


import configuration.Config;
import donnees.Iteration;


/**
 * @author Nicolas Elbeze
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageProprietesIterationIHM extends PageAssistantIHM {
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public PageProprietesIterationIHM(final Shell shell, Iteration it) {
		// Appel au constructeur de l'objet Composite
		super(shell);
		
		// Objet GridLayout pour placer les objets
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		this.setLayout(gridLayout);

		// titre de la page
		Font font = new Font(getDisplay(), "Arial", 15, 15);
		Label titre = new Label(this, SWT.NONE);
		titre.setFont(font);
		titre.setText(message.getString("PageProprietesIHM.iteration.titre"));

		// Vide pour la présentation
		CLabel lblVide1 = new CLabel(this,SWT.WRAP);
		lblVide1.setText("");
		Label vide1 = new Label(this,SWT.WRAP);
		vide1.setText("");
		
		// Nom de l'itération
		CLabel lblIteration = new CLabel(this,SWT.WRAP);
		lblIteration.setText(message.getString("PageProprietesIHM.iteration.lblNumIteration"));
		Text iteration = new Text(this,SWT.WRAP);
		iteration.setText(it.get_numIt() + "");
		iteration.setEditable(false);
		
		// Date de début de l'itération
		CLabel lblDebut = new CLabel(this,SWT.WRAP);
		lblDebut.setText(message.getString("PageProprietesIHM.iteration.lblDebut"));
		Text debut = new Text(this,SWT.WRAP);
		debut.setText(it.get_dateDebut().toString());
		debut.setEditable(false);
		
		// Date de fin de l'itération
		CLabel lblFin = new CLabel(this,SWT.WRAP);
		lblFin.setText(message.getString("PageProprietesIHM.iteration.lblFin"));
		Text fin = new Text(this,SWT.WRAP);
		fin.setText(it.get_dateFin().toString());
		fin.setEditable(false);
		
		// Vide pour la présentation
		CLabel lblVide2 = new CLabel(this,SWT.WRAP);
		lblVide2.setText("");
		Label vide2 = new Label(this,SWT.WRAP);
		vide2.setText("");
		
		
		// mise en place des caractéristiques du GridLayout (hauteur, largeur, remplissage, span, ...)
		GridData data = new GridData();
		Point point = titre.computeSize(SWT.DEFAULT,SWT.DEFAULT);
		data.heightHint = point.y;
		data.horizontalSpan = 2;
		titre.setLayoutData(data);	
	}
	
		
	
	/* (non-Javadoc)
	 * @see ihm.PageAssistantIHM#verifDonnees()
	 */
	public boolean verifDonnees() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see ihm.PageAssistantIHM#loadData()
	 */
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
	
}
