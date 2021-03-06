/*
 * Created on 14 f�vr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.proprietes;


import ihm.PageAssistantIHM;

import java.util.ResourceBundle;

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
public class PageProprietesActiviteIHM extends PageAssistantIHM {
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public PageProprietesActiviteIHM(final Shell shell, final EugesActivite act) {
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
		titre.setText(message.getString("PageProprietesActiviteIHM.activite.titre"));

		// Vide pour la pr�sentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		// Nom de l'activit�
		CLabel lblActivite = new CLabel(this,SWT.WRAP);
		lblActivite.setText(message.getString("PageProprietesActiviteIHM.activite.lblActivite"));
		final Label activite = new Label(this,SWT.WRAP);
		activite.setText(act.getName());
		//activite.setEditable(false);
		
		// Lien vers le site de l'outil publication
		if ((act.get_cheminActivite() != null) && (!act.get_cheminActivite().equals(""))) {
			activite.setForeground(new Color(getDisplay(), 0, 0, 255));
			activite.setCursor( new Cursor(getDisplay(), SWT.CURSOR_HAND));
			activite.addListener(SWT.MouseUp, new Listener(){
				public void handleEvent(Event e){
					new EugesNavigateur(EugesElements._projet.get_cheminProcessus() + act.get_cheminActivite());
				}
			});
		}
		
		
		// R�le associ�
		if(act.getRole()!=null){
			CLabel lblRole = new CLabel(this,SWT.WRAP);
			lblRole.setText(message.getString("PageProprietesActiviteIHM.activite.lblRole"));
			Text role = new Text(this,SWT.WRAP);
			role.setText(act.getRole().getName());
			role.setEditable(false);
		}
		// Vide pour la pr�sentation
		CLabel lblVide2 = new CLabel(this,SWT.WRAP);
		lblVide2.setText("");
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
		
		
		// Affichage des produits en entr�es de l'activit�
		CLabel lblProduitsIn = new CLabel(this,SWT.WRAP);
		lblProduitsIn.setText(message.getString("PageProprietesActiviteIHM.produit.lblProduitsIn"));
		Text auxLabelProduitIn;
		EugesProduit auxProduitIn;
		for (int i=0; i<act.getProduitInCount(); i++) {
			auxProduitIn = act.getProduitIn(i);
			auxLabelProduitIn = new Text(this, SWT.WRAP);
			auxLabelProduitIn.setText(auxProduitIn.getName());
			auxLabelProduitIn.setEditable(false);
			vide = new Label(this, SWT.NONE);
			vide.setText("");
		}
		
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		
		// Affichage des produits en sorites de l'activit�
		CLabel lblProduitsOut = new CLabel(this,SWT.WRAP);
		lblProduitsOut.setText(message.getString("PageProprietesActiviteIHM.produit.lblProduitsOut"));
		Text auxLabelProduitOut;
		EugesProduit auxProduitOut;
		for (int j=0; j<act.getProduitOutCount(); j++) {
			auxProduitOut = act.getProduitOut(j);
			auxLabelProduitOut = new Text(this, SWT.WRAP);
			auxLabelProduitOut.setText(auxProduitOut.getName());
			auxLabelProduitOut.setEditable(false);
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
