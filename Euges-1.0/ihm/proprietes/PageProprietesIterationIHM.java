/*
 * Created on 14 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.proprietes;


import ihm.PageAssistantIHM;

import java.util.Iterator;
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

import application.EugesElements;


import configuration.Config;
import donnees.Iteration;
import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesVersion;


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
		Label vide;
		
		// Objet GridLayout pour placer les objets
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		this.setLayout(gridLayout);

		// titre de la page
		Font font = new Font(getDisplay(), "Arial", 15, 15);
		Label titre = new Label(this, SWT.NONE);
		titre.setFont(font);
		titre.setText(message.getString("PageProprietesIterationIHM.iteration.titre"));

		// Vide pour la présentation
		CLabel lblVide1 = new CLabel(this,SWT.WRAP);
		lblVide1.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		// Nom de l'itération
		CLabel lblIteration = new CLabel(this,SWT.WRAP);
		lblIteration.setText(message.getString("PageProprietesIterationIHM.iteration.lblNumIteration"));
		Text iteration = new Text(this,SWT.WRAP);
		iteration.setText(it.get_numIt() + "");
		iteration.setEditable(false);
		
		// Date de début de l'itération
		CLabel lblDebut = new CLabel(this,SWT.WRAP);
		lblDebut.setText(message.getString("PageProprietesIterationIHM.iteration.lblDebut"));
		Text debut = new Text(this,SWT.WRAP);
		debut.setText(it.get_dateDebut().toString());
		debut.setEditable(false);
		
		// Date de fin de l'itération
		CLabel lblFin = new CLabel(this,SWT.WRAP);
		lblFin.setText(message.getString("PageProprietesIterationIHM.iteration.lblFin"));
		Text fin = new Text(this,SWT.WRAP);
		fin.setText(it.get_dateFin().toString());
		fin.setEditable(false);
		
		
		// Vide pour la présentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		

		// Affichage d'un séparateur pour la présentation
		Label sep1 = new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
		GridData dataSep = new GridData(GridData.FILL_HORIZONTAL);
		Point pointSep = sep1.computeSize(SWT.DEFAULT,SWT.DEFAULT);
		dataSep.heightHint = pointSep.y;
		dataSep.horizontalSpan = 2;
		sep1.setLayoutData(dataSep);
		
		
		// Vide pour la présentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		
		// Récupération de toutes les activités associées à l'itération
		CLabel lblActivites = new CLabel(this,SWT.WRAP);
		lblActivites.setText(message.getString("PageProprietesIterationIHM.activite.lblActivites"));
		Text auxLabelActivite;
		EugesActivite auxActivite;
		for (int i=0; i<it.getActiviteCount(); i++) {
			auxActivite = it.getActivite(i).get_activiteParent();
			auxLabelActivite = new Text(this, SWT.WRAP);
			auxLabelActivite.setText(auxActivite.getName());
			auxLabelActivite.setEditable(false);
			vide = new Label(this, SWT.NONE);
			vide.setText("");
		}
		
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		

		// Vide pour la présentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		

		// Récupération de toutes les versions associées à l'itération
		CLabel lblProduits = new CLabel(this,SWT.WRAP);
		lblProduits.setText(message.getString("PageProprietesIterationIHM.produit.lblProduits"));
		Text auxLabelProduit;
		for (Iterator iter = EugesElements.getProduitsIteration(it).iterator(); iter.hasNext();) {
			EugesVersion auxVersion = (EugesVersion) iter.next();
			auxLabelProduit = new Text(this, SWT.WRAP);
			auxLabelProduit.setText(auxVersion.get_produitParent().getName() + " " + auxVersion.get_nom());
			auxLabelProduit.setEditable(false);
			vide = new Label(this, SWT.NONE);
			vide.setText("");
		}
		
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		

		// Vide pour la présentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		
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
