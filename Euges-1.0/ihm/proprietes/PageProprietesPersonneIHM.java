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
import donnees.eugesSpem.EugesPersonne;


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
		
		// Objet GridLayout pour placer les objets
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		this.setLayout(gridLayout);

		// titre de la page
		Font font = new Font(getDisplay(), "Arial", 15, 15);
		Label titre = new Label(this, SWT.NONE);
		titre.setFont(font);
		titre.setText(message.getString("PageProprietesIHM.personne.titre"));

		// Vide pour la présentation
		CLabel lblVide1 = new CLabel(this,SWT.WRAP);
		lblVide1.setText("");
		Label vide1 = new Label(this,SWT.WRAP);
		vide1.setText("");
		
		// Nom de la personne
		CLabel lblNom = new CLabel(this,SWT.WRAP);
		lblNom.setText(message.getString("PageProprietesIHM.personne.lblNom"));
		Text nom = new Text(this,SWT.WRAP);
		nom.setText(pers.getNom());
		nom.setEditable(false);
		
		// Prénom de la personne
		CLabel lblPrenom = new CLabel(this,SWT.WRAP);
		lblPrenom.setText(message.getString("PageProprietesIHM.personne.lblPrenom"));
		Text prenom = new Text(this,SWT.WRAP);
		prenom.setText(pers.getPrenom());
		prenom.setEditable(false);
		
		// Mail de la personne
		CLabel lblMail = new CLabel(this,SWT.WRAP);
		lblMail.setText(message.getString("PageProprietesIHM.personne.lblMail"));
		Text mail = new Text(this,SWT.WRAP);
		mail.setText(pers.getMail());
		mail.setEditable(false);
		
		// Vide pour la présentation
		CLabel lblVide2 = new CLabel(this,SWT.WRAP);
		lblVide2.setText("");
		Label vide2 = new Label(this,SWT.WRAP);
		vide2.setText("");
		

//		// Tous les rôles associées à la personne
//		CLabel lblRoles = new CLabel(this,SWT.WRAP);
//		lblRoles.setText(message.getString("PageProprietesIHM.role.lblRoles"));
//		Label auxLabelRole;
//		EugesRole auxRole;
//		Label auxVide;
//		for (int i=0; i<pers.getRoleCount(); i++) {
//			auxRole = pers.getRole(i);
//			auxLabelRole = new Label(this, SWT.WRAP);
//			auxLabelRole.setText(auxRole.getName());
//			auxVide = new Label(this,SWT.WRAP);
//			auxVide.setText("");
//		}
//		
//		auxVide = new Label(this,SWT.WRAP);
//		auxVide.setText("");
//		
		
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
