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
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesProduit;
import donnees.eugesSpem.EugesRole;


/**
 * @author Nicolas Elbeze
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageProprietesPersonneIHM extends PageAssistantIHM {
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public PageProprietesPersonneIHM(final Shell shell, EugesPersonne pers) {
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
		titre.setText(message.getString("PageProprietesPersonneIHM.personne.titre"));

		// Vide pour la présentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		// Nom de la personne
		CLabel lblNom = new CLabel(this,SWT.WRAP);
		lblNom.setText(message.getString("PageProprietesPersonneIHM.personne.lblNom"));
		Text nom = new Text(this,SWT.WRAP);
		nom.setText(pers.getNom());
		nom.setEditable(false);
		
		// Prénom de la personne
		CLabel lblPrenom = new CLabel(this,SWT.WRAP);
		lblPrenom.setText(message.getString("PageProprietesPersonneIHM.personne.lblPrenom"));
		Text prenom = new Text(this,SWT.WRAP);
		prenom.setText(pers.getPrenom());
		prenom.setEditable(false);
		
		// Mail de la personne
		CLabel lblMail = new CLabel(this,SWT.WRAP);
		lblMail.setText(message.getString("PageProprietesPersonneIHM.personne.lblMail"));
		Text mail = new Text(this,SWT.WRAP);
		mail.setText(pers.getMail());
		mail.setEditable(false);
		
		
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
		
		
		// Tous les rôles associées à la personne
		CLabel lblRoles = new CLabel(this,SWT.WRAP);
		lblRoles.setText(message.getString("PageProprietesPersonneIHM.role.lblRoles"));
		Text auxLabelRole;
		Vector auxRoles = EugesElements.getRolesPersonne(pers);
		for (Iterator it = auxRoles.iterator(); it.hasNext();) {
			EugesRole element = (EugesRole) it.next();
			auxLabelRole = new Text(this, SWT.WRAP);
			auxLabelRole.setText(element.getName());
			auxLabelRole.setEditable(false);
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
		

		// Affichage d'un séparateur pour la présentation
		Label sep2 = new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
		GridData dataSep2 = new GridData(GridData.FILL_HORIZONTAL);
		Point pointSep2 = sep2.computeSize(SWT.DEFAULT,SWT.DEFAULT);
		dataSep2.heightHint = pointSep2.y;
		dataSep2.horizontalSpan = 2;
		sep2.setLayoutData(dataSep2);
		

		// Vide pour la présentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		
		// Tous les produits associées à la personne
		CLabel lblProduits = new CLabel(this,SWT.WRAP);
		lblProduits.setText(message.getString("PageProprietesPersonneIHM.produit.lblProduits"));
		Text auxLabelProduit;
		Vector auxProduits = EugesElements.getProduitsPersonne(pers);
		for (Iterator it = auxProduits.iterator(); it.hasNext();) {
			EugesProduit element = (EugesProduit) it.next();
			auxLabelProduit = new Text(this, SWT.WRAP);
			auxLabelProduit.setText(element.getName());
			auxLabelProduit.setEditable(false);
			vide = new Label(this, SWT.NONE);
			vide.setText("");
		}
		
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
