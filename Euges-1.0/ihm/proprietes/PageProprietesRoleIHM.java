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
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesRole;



/**
 * @author Nicolas Elbeze
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageProprietesRoleIHM extends PageAssistantIHM {
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public PageProprietesRoleIHM(final Shell shell, EugesRole r) {
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
		titre.setText(message.getString("PageProprietesRoleIHM.role.titre"));

		// Vide pour la présentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		// Nom du rôle
		CLabel lblRole = new CLabel(this,SWT.WRAP);
		lblRole.setText(message.getString("PageProprietesRoleIHM.role.lblRole"));
		Text role = new Text(this,SWT.WRAP);
		role.setText(r.getName());
		role.setEditable(false);
		
		
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
		
		
		// Toutes les activites associées au rôle
		CLabel lblActivites = new CLabel(this,SWT.WRAP);
		lblActivites.setText(message.getString("PageProprietesRoleIHM.activite.lblActivites"));
		Text auxLabelActivite;
		Vector activites = EugesElements.getActivitesRole(r);
		for (Iterator it = activites.iterator(); it.hasNext();) {
			EugesActivite auxActivite = (EugesActivite) it.next();
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
		
		
		// Toutes les personnes associées au rôle
		CLabel lblPersonnes = new CLabel(this,SWT.WRAP);
		lblPersonnes.setText(message.getString("PageProprietesRoleIHM.personne.lblPersonnes"));
		Text auxLabelPersonne;
		Vector personnes = EugesElements.getPersonnesRole(r);
		for (Iterator it = personnes.iterator(); it.hasNext();) {
			EugesPersonne auxPersonnes = (EugesPersonne) it.next();
			auxLabelPersonne = new Text(this, SWT.WRAP);
			auxLabelPersonne.setText(auxPersonnes.getNom());
			auxLabelPersonne.setEditable(false);
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
