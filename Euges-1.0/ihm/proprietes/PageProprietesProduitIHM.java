/*
 * Created on 14 f�vr. 2004
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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import utilitaires.EugesNavigateur;
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
	
	public PageProprietesProduitIHM(final Shell shell, final EugesProduit prod) {
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

		// Vide pour la pr�sentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		// Nom du produit
		CLabel lblProduit = new CLabel(this,SWT.WRAP);
		lblProduit.setText(message.getString("PageProprietesProduitIHM.produit.lblProduit"));
		final Label produit = new Label(this,SWT.WRAP);
		produit.setText(prod.getName());
		//produit.setEditable(false);
		
		// Lien vers le site de l'outil publication
		if ((prod.get_cheminProduit() != null) && (!prod.get_cheminProduit().equals(""))) {
			produit.setForeground(new Color(getDisplay(), 0, 0, 255));
			produit.setCursor( new Cursor(getDisplay(), SWT.CURSOR_HAND));
			produit.addListener(SWT.MouseUp, new Listener(){
				public void handleEvent(Event e){
					new EugesNavigateur(EugesElements._projet.get_cheminProcessus() + prod.get_cheminProduit());
				}
			});
		}
		
		
		// Vide pour la pr�sentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		
		// Affichage d'un s�parateur pour la pr�sentation
		Label sep1 = new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
		GridData dataSep = new GridData(GridData.FILL_HORIZONTAL);
		Point pointSep = sep1.computeSize(SWT.DEFAULT,SWT.DEFAULT);
		dataSep.heightHint = pointSep.y;
		dataSep.horizontalSpan = 2;
		sep1.setLayoutData(dataSep);


		// Vide pour la pr�sentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		
		// Toutes les activit�s dont le produit est entr�e
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
		
		

		// Toutes les activit�s dont le produit est sortie
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
		
		
		// Vide pour la pr�sentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		
		// mise en place des caract�ristiques du GridLayout (hauteur, largeur, remplissage, span, ...)
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
