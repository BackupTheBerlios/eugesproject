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
import java.util.Vector;

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
import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesProduit;


/**
 * @author Nicolas Elbeze
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageProprietesProduitIHM extends PageAssistantIHM {
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public PageProprietesProduitIHM(final Shell shell, EugesProduit prod) {
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
		titre.setText(message.getString("PageProprietesProduitIHM.produit.titre"));

		// Vide pour la présentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		// Nom du produit
		CLabel lblProduit = new CLabel(this,SWT.WRAP);
		lblProduit.setText(message.getString("PageProprietesProduitIHM.produit.lblProduit"));
		Text produit = new Text(this,SWT.WRAP);
		produit.setText(prod.getName());
		produit.setEditable(false);
		

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
		
		
		// Toutes les activités dont le produit est entrée
		CLabel lblActivitesIn = new CLabel(this,SWT.WRAP);
		lblActivitesIn.setText(message.getString("PageProprietesProduitIHM.activite.lblActivitesIn"));
		Text auxLabelActiviteIn;
		Vector activitesIn = EugesElements.getActivitesProduitIn(prod);
		for (Iterator it = activitesIn.iterator(); it.hasNext();) {
			EugesActivite auxActiviteIn = (EugesActivite) it.next();
			auxLabelActiviteIn = new Text(this, SWT.WRAP);
			auxLabelActiviteIn.setText(auxActiviteIn.getName());
			auxLabelActiviteIn.setEditable(false);
			vide = new Label(this, SWT.NONE);
			vide.setText("");
		}
		
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		

		// Toutes les activités dont le produit est sortie
		CLabel lblActivitesOut = new CLabel(this,SWT.WRAP);
		lblActivitesOut.setText(message.getString("PageProprietesProduitIHM.activite.lblActivitesOut"));
		Text auxLabelActiviteOut;
		Vector activitesOut = EugesElements.getActivitesProduitOut(prod);
		for (Iterator it = activitesOut.iterator(); it.hasNext();) {
			EugesActivite auxActiviteOut = (EugesActivite) it.next();
			auxLabelActiviteOut = new Text(this, SWT.WRAP);
			auxLabelActiviteOut.setText(auxActiviteOut.getName());
			auxLabelActiviteOut.setEditable(false);
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
