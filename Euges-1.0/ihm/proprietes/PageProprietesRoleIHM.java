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
	
	public PageProprietesRoleIHM(final Shell shell, final EugesRole r) {
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

		// Vide pour la pr�sentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		// Nom du r�le
		CLabel lblRole = new CLabel(this,SWT.WRAP);
		lblRole.setText(message.getString("PageProprietesRoleIHM.role.lblRole"));
		final Label role = new Label(this,SWT.WRAP);
		role.setText(r.getName());
		//role.setEditable(false);
		
		// Lien vers le site de l'outil publication
		if ((r.get_cheminRole() != null) && (r.get_cheminRole() != "")) {
			role.setForeground(new Color(getDisplay(), 0, 0, 255));
			role.setCursor( new Cursor(getDisplay(), SWT.CURSOR_HAND));
			role.addListener(SWT.MouseUp, new Listener(){
				public void handleEvent(Event e){
					EugesNavigateur nav = new EugesNavigateur(EugesElements._projet.get_cheminProcessus() + r.get_cheminRole());
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
		
		
		// Toutes les activites associ�es au r�le
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
		
		
		// Vide pour la pr�sentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		// Affichage d'un s�parateur pour la pr�sentation
		Label sep2 = new Label(this, SWT.SEPARATOR|SWT.HORIZONTAL);
		GridData dataSep2 = new GridData(GridData.FILL_HORIZONTAL);
		Point pointSep2 = sep2.computeSize(SWT.DEFAULT,SWT.DEFAULT);
		dataSep2.heightHint = pointSep2.y;
		dataSep2.horizontalSpan = 2;
		sep2.setLayoutData(dataSep2);
		
		
		// Vide pour la pr�sentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		
		// Toutes les personnes associ�es au r�le
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
