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
import java.util.SortedSet;

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
import donnees.Projet;


/**
 * @author Nicolas Elbeze
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageProprietesProjetIHM extends PageAssistantIHM {
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public PageProprietesProjetIHM(final Shell shell, Projet p) {
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
		titre.setText(message.getString("PageProprietesProjetIHM.projet.titre"));

		// Vide pour la présentation
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		vide = new Label(this, SWT.NONE);
		vide.setText("");
		
		
		// Nom du projet
		CLabel lblProjet = new CLabel(this,SWT.WRAP);
		lblProjet.setText(message.getString("PageProprietesProjetIHM.projet.lblProjet"));
		Text projet = new Text(this,SWT.NONE);
		projet.setText(p.get_nomProjet());
		projet.setEditable(false);
		
		
		// Date de début projet
		CLabel lblDebut = new CLabel(this,SWT.WRAP);
		lblDebut.setText(message.getString("PageProprietesProjetIHM.projet.lblDebut"));
		Text debut = new Text(this,SWT.WRAP);
		debut.setText(p.get_dateDebut().toString());
		debut.setEditable(false);
		
		// Date de fin projet
		CLabel lblFin = new CLabel(this,SWT.WRAP);
		lblFin.setText(message.getString("PageProprietesProjetIHM.projet.lblFin"));
		Text fin = new Text(this,SWT.WRAP);
		fin.setText(p.get_dateFin().toString());
		fin.setEditable(false);
		
		// Processus
		CLabel lblProcessus = new CLabel(this,SWT.WRAP);
		lblProcessus.setText(message.getString("PageProprietesProjetIHM.projet.lblProcessus"));
		Text processus = new Text(this,SWT.WRAP);
		processus.setText(p.get_processus());
		processus.setEditable(false);
		
		// Description
		CLabel lblDescription = new CLabel(this,SWT.WRAP);
		lblDescription.setText(message.getString("PageProprietesProjetIHM.projet.lblDescription"));
		Label description;
		if (p.get_description().equals("")) {
			description = new Label(this, SWT.NONE);
			description.setText(p.get_description());
		}
		else {
			description = new Label(this, SWT.BORDER);
			description.setText(p.get_description());
		}
		
		
		// Pour aligner correctement le label description et la description
		GridData dataLblDesc = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		lblDescription.setLayoutData(dataLblDesc);
		GridData dataDesc = new GridData(GridData.FILL_HORIZONTAL);
		description.setLayoutData(dataDesc);
		

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
		
		
		// Toutes les itérations
		CLabel lblIterations = new CLabel(this,SWT.WRAP);
		lblIterations.setText(message.getString("PageProprietesProjetIHM.iteration.lblIterations"));
		Text auxLabelIteration;
		SortedSet iterations = EugesElements._projet._listeIteration;
		for (Iterator it = iterations.iterator(); it.hasNext();) {
			Iteration auxIteration = (Iteration) it.next();
			auxLabelIteration = new Text(this, SWT.WRAP);
			auxLabelIteration.setText(message.getString("PageProprietesProjetIHM.iteration") + " " + auxIteration.get_numIt());
			auxLabelIteration.setEditable(false);
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
