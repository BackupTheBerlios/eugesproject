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
import donnees.eugesSpem.EugesVersion;


/**
 * @author Nicolas Elbeze
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageProprietesVersionIHM extends PageAssistantIHM {
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public PageProprietesVersionIHM(final Shell shell, EugesVersion vers) {
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
		titre.setText(message.getString("PageProprietesVersionIHM.version.titre"));

		// Vide pour la pr�sentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		// Nom du produit
		CLabel lblVersion = new CLabel(this,SWT.WRAP);
		lblVersion.setText(message.getString("PageProprietesVersionIHM.version.lblProduit"));
		Text version = new Text(this,SWT.WRAP);
		version.setText(vers.get_produitParent().getName());
		version.setEditable(false);
		
		// Produit associ� � l'it�ration : 
		CLabel lblIteration = new CLabel(this,SWT.WRAP);
		lblIteration.setText(message.getString("PageProprietesVersionIHM.version.lblIteration"));
		Text iteration = new Text(this,SWT.WRAP);
		iteration.setText(vers.get_realisation() + "");
		iteration.setEditable(false);
		
		// Etat du produit
		CLabel lblEtat = new CLabel(this,SWT.WRAP);
		lblEtat.setText(message.getString("PageProprietesVersionIHM.version.lblEtat"));
		Text etat = new Text(this,SWT.WRAP);
		etat.setText(vers.get_etat());
		etat.setEditable(false);
		

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
		lblActivitesIn.setText(message.getString("PageProprietesVersionIHM.activite.lblActivitesIn"));
		Text auxLabelActiviteIn;
		Vector activitesIn = EugesElements.getActivitesProduitIn(vers.get_produitParent());
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
		lblActivitesOut.setText(message.getString("PageProprietesVersionIHM.activite.lblActivitesOut"));
		Text auxLabelActiviteOut;
		Vector activitesOut = EugesElements.getActivitesProduitOut(vers.get_produitParent());
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
