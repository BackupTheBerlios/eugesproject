/*
 * Created on 25 nov. 2003
 *
 */
package ihm;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import utilitaires.GestionImage;
import application.EugesElements;
import application.FenetreGestionProduits;
import configuration.Config;
import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesProduit;
/**
 * @author Mathieu GAYRAUD
 *
 */
public class FenetreGestionProduitsIHM {

	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);

	Display _display;
	Shell _shell;
	private Tree _activites;
	private List _listProd;
	
	private Vector vecteurPersonnes = new Vector (0,1);
	
	private Vector listR = new Vector (0,1);
	
	/**
	 * @param shell fenêtre...
	 */
	public FenetreGestionProduitsIHM (final Shell shell) {
		_display = shell.getDisplay();
		_shell = new Shell(shell,SWT.CLOSE|SWT.APPLICATION_MODAL);
		_shell.setText(message.getString("FenetreGestionProduitsIHM.titreShell"));
		_shell.setImage(GestionImage._euges);
	}
	
	public void open() {
		// Appel au constructeur de l'objet Composite
		// Objet GridLayout pour placer les objets
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		_shell.setLayout(gridLayout);

		// titre de la page
		Font font = new Font(_display, "Arial", 15, 15);
		Label titre = new Label(_shell, SWT.NONE);
		titre.setFont(font);
		titre.setText(message.getString("FenetreGestionProduitsIHM.titrePage"));

		// titre de l'arbre
		CLabel ssTitre1 = new CLabel(_shell,SWT.WRAP);
		ssTitre1.setText(message.getString("FenetreGestionProduitsIHM.ssTitreGauche"));
		ssTitre1.setImage(GestionImage._activite);
		
		// objet vide pour remplir une cellule qui ne sert rien
		Label vide1 = new Label(_shell,SWT.WRAP);
		vide1.setText("");
		
		// titre de la liste
		CLabel ssTitre2 = new CLabel(_shell,SWT.WRAP);
		ssTitre2.setText(message.getString("FenetreGestionProduitsIHM.ssTitreDroit"));
		ssTitre2.setImage(GestionImage._produit);
		
		// arbre des activites
		_activites = new Tree(_shell,SWT.SINGLE|SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
		chargerArbreAct();
		
		// bouton pour ajouter un nouveau produit
		Button newProd = new Button(_shell,SWT.PUSH);
		newProd.setText("+>");
		newProd.setToolTipText(message.getString("FenetreGestionProduitsIHM.toolTipNewProd"));
		
		// liste des produits
		_listProd = new List(_shell,SWT.MULTI|SWT.H_SCROLL|SWT.BORDER|SWT.V_SCROLL);
		// on charge la liste des produits
		chargerListeProd();

		// contrôle de l'évènement du bouton ajouter un nouveau produit
		newProd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				// ouverture d'une nouvelle fenêtre
				PageNouveauProduitIHM np = new PageNouveauProduitIHM(_shell);
				// on re-rempli l'arbre
				chargerArbreAct();
				// on recharge la liste des produits
				chargerListeProd();
			}
		});

		// bouton pour supprimer un produit d'une activité
		Button delProd = new Button(_shell,SWT.PUSH);
		delProd.setText(message.getString("FenetreGestionProduitsIHM.delProd"));
		delProd.setToolTipText(message.getString("FenetreGestionProduitsIHM.toolTipDelProd"));

		// contrôle de l'évènement du bouton delProd
		delProd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				TreeItem[] selection = _activites.getSelection();
				if (selection.length == 1) {
					if (selection[0].getParentItem() == null) {
						// on supprime tous les produits IN et OUT
						EugesActivite act = (EugesActivite)selection[0].getData();
						FenetreGestionProduits.supprimerProd(act);
						// on supprime dans l'arbre
						TreeItem[] fils = selection[0].getItems();
						for (int i = 0; i < fils.length; i++) {
							TreeItem[] prods = fils[i].getItems();
							for (int j = 0; j < prods.length; j++) {
								prods[j].dispose();
							}
						}
					}
					else if (selection[0].getItemCount() > 0) {
						// on supprime les produits en entrée ou en sortie
						if (selection[0].getText().equals(message.getString("FenetreGestionProduitsIHM.prodIn")))
							FenetreGestionProduits.supprimerProd((EugesActivite)selection[0].getParentItem().getData(), "IN");
						else
							FenetreGestionProduits.supprimerProd((EugesActivite)selection[0].getParentItem().getData(), "OUT");
						TreeItem[] prods = selection[0].getItems();
						// on supprime dans l'arbre
						for (int j = 0; j < prods.length; j++) {
							prods[j].dispose();
						}
					}
					else {
						if (selection[0].getParentItem().getText().equals(message.getString("FenetreGestionProduitsIHM.prodIn")))
							FenetreGestionProduits.supprimerProd((EugesProduit)selection[0].getData(), (EugesActivite)selection[0].getParentItem().getParentItem().getData(), "IN");
						else
							FenetreGestionProduits.supprimerProd((EugesProduit)selection[0].getData(), (EugesActivite)selection[0].getParentItem().getParentItem().getData(), "OUT");
						// on supprime dans l'arbre
						selection[0].dispose();
					}
					//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
					EugesElements.processusEnregistre = false;
				}
			}
		});

		// bouton pour associer un ou plusieurs produits en entrée à une activité
		Button addProdIn = new Button(_shell,SWT.PUSH);
		addProdIn.setText(message.getString("FenetreGestionProduitsIHM.addProdIn"));
		addProdIn.setToolTipText(message.getString("FenetreGestionProduitsIHM.toolTipAddProdIn"));
		// contrôle de l'évènement de ce bouton de ce bouton
		addProdIn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				String[] prodSelected = _listProd.getSelection();
				TreeItem[] actSelected = _activites.getSelection();
				if (prodSelected.length == 0) {
					// si aucun produit sélectionné -> ERREUR
					MessageBox msgErreur = new MessageBox(_shell,SWT.ICON_ERROR);
					msgErreur.setMessage(message.getString("FenetreGestionProduitsIHM.msgbox1Msg"));
					msgErreur.setText(message.getString("FenetreGestionProduitsIHM.msgbox1Title"));
					msgErreur.open();
				}
				else {
					if (actSelected.length > 0) {
						//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
						EugesElements.processusEnregistre = false;
						TreeItem temp = actSelected[0];
						while (temp.getParentItem() != null) {
							temp = temp.getParentItem();
						}
						EugesProduit p;
						for (int i = 0; i < prodSelected.length; i++) {
							p = FenetreGestionProduits.ajouterProdIn(prodSelected[i],(EugesActivite)temp.getData());
							if (p != null) {
								TreeItem item = new TreeItem(temp.getItems()[0],SWT.NONE);
								item.setText(p.toString());
								item.setImage(GestionImage._produit);
								item.setData(p);
							}
						}
					
						temp.setExpanded(true);
						temp.getItems()[0].setExpanded(true);
						_listProd.deselectAll();
					}
				}
			}
		});

		// bouton pour ajouter un produit en sortie à une activité
		Button addProdOut = new Button(_shell,SWT.PUSH);
		addProdOut.setText(message.getString("FenetreGestionProduitsIHM.addProdOut"));
		addProdOut.setToolTipText(message.getString("FenetreGestionProduitsIHM.toolTipAddProdOut"));
		// control de l'évènement de ce bouton
		addProdOut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				String[] prodSelected = _listProd.getSelection();
				TreeItem[] actSelected = _activites.getSelection();
				if (prodSelected.length == 0) {
					// si aucun produit sélectionné -> ERREUR
					MessageBox msgErreur = new MessageBox(_shell,SWT.ICON_ERROR);
					msgErreur.setMessage(message.getString("FenetreGestionProduitsIHM.msgbox1Msg"));
					msgErreur.setText(message.getString("FenetreGestionProduitsIHM.msgbox1Title"));
					msgErreur.open();
				}
				else {
					if (actSelected.length > 0) {
						//le processus est modifie, on change la variable qui permet de savoir que des modifications ont été faites
						EugesElements.processusEnregistre = false;
						TreeItem temp = actSelected[0];
						while (temp.getParentItem() != null) {
							temp = temp.getParentItem();
						}
						EugesProduit p;
						for (int i = 0; i < prodSelected.length; i++) {
							p = FenetreGestionProduits.ajouterProdOut(prodSelected[i],(EugesActivite)temp.getData());
							if (p != null) {
								TreeItem item = new TreeItem(temp.getItems()[1],SWT.NONE);
								item.setText(p.toString());
								item.setImage(GestionImage._produit);
								item.setData(p);
							}
						}
						
						temp.setExpanded(true);
						temp.getItems()[1].setExpanded(true);
						_listProd.deselectAll();
					}
				}
			}
		});
		
		// objet vide pour remplir une cellule qui ne sert rien
		Label vide2 = new Label(_shell,SWT.WRAP);
		vide2.setText("");

		// mise en place des caractéristiques du GridLayout (hauteur, largeur, remplissage, span, ...)
		GridData data = new GridData();
		Point point = titre.computeSize(SWT.DEFAULT,SWT.DEFAULT);
		data.heightHint = point.y;
		data.horizontalSpan = 3;
		titre.setLayoutData(data);

		data = new GridData();
		point = ssTitre1.computeSize(SWT.DEFAULT,SWT.DEFAULT);
		int largeurSsTitre1 = point.x;
		data.heightHint = point.y;
		data.verticalAlignment = GridData.CENTER;
		ssTitre1.setLayoutData(data);

		data = new GridData();
		data.heightHint = point.y;
		vide1.setLayoutData(data);

		data = new GridData();
		data.heightHint = point.y;
		data.verticalAlignment = GridData.CENTER;
		point = ssTitre2.computeSize(SWT.DEFAULT,SWT.DEFAULT);
		int largeurSsTitre2 = point.x;
		ssTitre2.setLayoutData(data);
		
		int larg = Math.max(
				Math.max(
					newProd.computeSize(SWT.DEFAULT,SWT.DEFAULT).x,
					delProd.computeSize(SWT.DEFAULT,SWT.DEFAULT).x),
				Math.max(
					addProdIn.computeSize(SWT.DEFAULT,SWT.DEFAULT).x,
					addProdOut.computeSize(SWT.DEFAULT,SWT.DEFAULT).x));
		
		data = new GridData();
		point = newProd.computeSize(SWT.DEFAULT,SWT.DEFAULT);
		int widthBut = point.x;
		data.heightHint = point.y;
		data.widthHint = larg;
		newProd.setLayoutData(data);
		
		data = new GridData();
		data.heightHint = point.y;
		data.widthHint = larg;
		delProd.setLayoutData(data);
		
		data = new GridData();
		data.heightHint = point.y;
		data.widthHint = larg;
		addProdIn.setLayoutData(data);
		
		data = new GridData();
		data.heightHint = point.y;
		data.widthHint = larg;
		addProdOut.setLayoutData(data);
		
		int largeur;
		if (largeurSsTitre1 > largeurSsTitre2)
			largeur = largeurSsTitre1;
		else
			largeur = largeurSsTitre2;
		data = new GridData(GridData.FILL_BOTH);
		data.widthHint = largeur;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.verticalSpan = 5;
		_activites.setLayoutData(data);
		
		data = new GridData(GridData.FILL_BOTH);
		data.widthHint = largeur;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.verticalSpan = 5;
		_listProd.setLayoutData(data);

		Composite basDePage = new Composite(_shell,SWT.NONE);
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		basDePage.setLayout(gridLayout2);
		Label sep = new Label(basDePage,SWT.SEPARATOR|SWT.HORIZONTAL);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		sep.setLayoutData(data);
		
		Label copyright = new Label(basDePage,SWT.NONE);
		copyright.setText("www.euges.fr.st");
		copyright.setEnabled(false);
		copyright.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING|GridData.HORIZONTAL_ALIGN_BEGINNING));

		Composite c = new Composite(basDePage,SWT.NONE);
		RowLayout rowLayout = new RowLayout();
		rowLayout.wrap = false;
		rowLayout.pack = false;
		rowLayout.justify = false;
		rowLayout.type = SWT.HORIZONTAL;
		rowLayout.marginLeft = 5;
		rowLayout.marginTop = 5;
		rowLayout.marginRight = 5;
		rowLayout.marginBottom = 5;
		rowLayout.spacing = 5;
		c.setLayout(rowLayout);
		Button fermerBut = new Button(c,SWT.PUSH);
		fermerBut.setText(message.getString("FenetreGestionProduitsIHM.fermer"));
		fermerBut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
					_shell.dispose();
			}
		});

		c.pack();
		data = new GridData(GridData.HORIZONTAL_ALIGN_END);
		c.setLayoutData(data);

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		basDePage.setLayoutData(data);

		_shell.pack();
		// ouvrir la fenêtre au centre de l'écran
		Rectangle bounds = _shell.getParent().getBounds ();
		Rectangle rect = _shell.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		_shell.setLocation (x, y);
		_shell.open();
		
		while(!_shell.isDisposed()){
		if(!_shell.getDisplay().readAndDispatch())
			_shell.getDisplay().sleep();
		}
	}

	public void chargerArbreAct() {
		_activites.removeAll();
		for (Iterator iter = EugesElements.listeActivites.iterator(); iter.hasNext();) {
			EugesActivite e = (EugesActivite) iter.next();
		
			// Activité
			TreeItem temp = new TreeItem(_activites,SWT.NONE);
			temp.setImage(GestionImage._activite);
			temp.setText(e.toString());
			temp.setData(e);
			// Produits IN
			TreeItem temp2 = new TreeItem(temp,SWT.NONE);
			temp2.setText(message.getString("FenetreGestionProduitsIHM.prodIn"));
			
			for (int i = 0; i < e.getProduitInCount(); i++) {
				TreeItem temp3 = new TreeItem(temp2,SWT.NONE);
				EugesProduit p = e.getProduitIn(i);
				System.out.println(p);
				temp3.setText(e.getProduitIn(i).toString());
				temp3.setImage(GestionImage._produit);
				temp3.setData(e.getProduitIn(i));
			}
			// Produits OUT
			temp2 = new TreeItem(temp,SWT.NONE);
			temp2.setText(message.getString("FenetreGestionProduitsIHM.prodOut"));
			
			for (int i = 0; i < e.getProduitOutCount(); i++) {
				TreeItem temp3 = new TreeItem(temp2,SWT.NONE);
				temp3.setText(e.getProduitOut(i).toString());
				temp3.setImage(GestionImage._produit);
				temp3.setData(e.getProduitOut(i));
			}
		}
	}
	
	// charger les éléments dans listP avec la liste globale des produits
	public void chargerListeProd(){
		// déf d'un tableau pour ensuite le mettre dans la liste de l'ihm
		String[] tabEugesProduit;
		// on appelle la fonction qui remplie ce tableau
		tabEugesProduit = EugesElements.getTableauListeProduit();
		// On associe le tableau à la liste de l'ihm
		_listProd.removeAll();
		if (tabEugesProduit != null){
			_listProd.setItems(tabEugesProduit);
		}
	}
}
