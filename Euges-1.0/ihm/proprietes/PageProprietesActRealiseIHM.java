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

import application.EugesElements;

import utilitaires.EugesNavigateur;
import configuration.Config;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesProduit;
import donnees.eugesSpem.EugesVersion;


/**
 * @author Nicolas Elbeze
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageProprietesActRealiseIHM extends PageAssistantIHM {
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public PageProprietesActRealiseIHM(final Shell shell, final EugesActRealise actRea) {
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
		titre.setText(message.getString("PageProprietesActRealiseIHM.actRealise.titre"));

		// Vide pour la présentation
		CLabel lblVide1 = new CLabel(this,SWT.WRAP);
		lblVide1.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		// Nom de l'activité
		CLabel lblActivite = new CLabel(this,SWT.WRAP);
		lblActivite.setText(message.getString("PageProprietesActRealiseIHM.actRealise.lblActivite"));
		final Label activite = new Label(this,SWT.WRAP);
		activite.setText(actRea.get_activiteParent().getName());
		//activite.setEditable(false);
		
		// Lien vers le site de l'outil publication
		if ((actRea.get_activiteParent().get_cheminActivite() != null) && (actRea.get_activiteParent().get_cheminActivite() != "")) {
			activite.setForeground(new Color(getDisplay(), 0, 0, 255));
			activite.setCursor( new Cursor(getDisplay(), SWT.CURSOR_HAND));
			activite.addListener(SWT.MouseUp, new Listener(){
				public void handleEvent(Event e){
					EugesNavigateur nav = new EugesNavigateur(EugesElements._projet.get_cheminProcessus() + actRea.get_activiteParent().get_cheminActivite());
				}
			});
		}
		
		
		// réalisé à l'itération
		CLabel lblNumIteration = new CLabel(this,SWT.WRAP);
		lblNumIteration.setText(message.getString("PageProprietesActRealiseIHM.actRealise.lblNumIteration"));
		Text numIteration = new Text(this,SWT.WRAP);
		numIteration.setText(actRea.getIt() + "");
		numIteration.setEditable(false);
		
		// Vide pour la présentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		// Rôle associé
		if(actRea.get_activiteParent().getRoleCount()!=0){
			CLabel lblRole = new CLabel(this,SWT.WRAP);
			lblRole.setText(message.getString("PageProprietesActRealiseIHM.role.lblRole"));
			Text role = new Text(this,SWT.WRAP);
			role.setText(actRea.get_activiteParent().getRole(0).getName());
			role.setEditable(false);
		}
		// Vide pour la présentation
		CLabel lblVide2 = new CLabel(this,SWT.WRAP);
		lblVide2.setText("");
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
		
		
		// Affichage des produits en entrées de l'activité
		CLabel lblProduitsIn = new CLabel(this,SWT.WRAP);
		lblProduitsIn.setText(message.getString("PageProprietesActRealiseIHM.produit.lblProduitsIn"));
		Text auxLabelProduitIn;
		EugesProduit auxProduitIn;
		for (int i=0; i<actRea.get_activiteParent().getProduitInCount(); i++) {
			auxProduitIn = actRea.get_activiteParent().getProduitIn(i);
			auxLabelProduitIn = new Text(this, SWT.WRAP);
			auxLabelProduitIn.setText(auxProduitIn.getName());
			auxLabelProduitIn.setEditable(false);
			vide = new Label(this, SWT.NONE);
			vide.setText("");
		}
		
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		
		// Affichage des produits en sorites de l'activité
		CLabel lblProduitsOut = new CLabel(this,SWT.WRAP);
		lblProduitsOut.setText(message.getString("PageProprietesActRealiseIHM.produit.lblProduitsOut"));
		Text auxLabelProduitOut;
		EugesVersion auxProduitOut;
		for (int j=0; j<actRea.getProduitOutCount(); j++) {
			auxProduitOut = actRea.getProduitOut(j);
			auxLabelProduitOut = new Text(this, SWT.WRAP);
			auxLabelProduitOut.setText(auxProduitOut.get_produitParent().getName() + " " + auxProduitOut.get_nom());
			auxLabelProduitOut.setEditable(false);
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
