/*
 * Created on 15 janv. 2004
 *
 */

package ihm;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.SortedSet;

import configuration.Config;
import application.EugesElements;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import utilitaires.GestionImage;

import donnees.Iteration;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesProduit;
import donnees.eugesSpem.EugesRole;
import donnees.spem.process.structure.WorkProduct;


/**
 * @author Nicolas Elbeze
 *
 */
public class ArbrePrincipalIHM extends ViewForm {

	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	private Tree _tree;
	private int _format;
	
	/**
	 * @param parent
	 */

	public ArbrePrincipalIHM(Composite parent) {
		
		super(parent, SWT.BORDER | SWT.V_SCROLL);
		_format = 0;
		
		
		final Composite treeComposite = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		treeComposite.setLayout(layout);
		
		
		// Barre d'outils verticale
		final CoolBar	treeCoolBar = new CoolBar(treeComposite, SWT.NONE);
		CoolItem treeCoolItem1 = new CoolItem(treeCoolBar, SWT.NONE);
		ToolBar treeToolBar = new ToolBar(treeCoolBar, SWT.FLAT);
		
		
		// Icone tri defaut
		ToolItem itemTriDefaut = new ToolItem(treeToolBar, SWT.PUSH);
		itemTriDefaut.setImage(GestionImage._triDefaut);
		itemTriDefaut.setToolTipText(message.getString("treetoolbar.triDefaut.tooltiptext"));
		itemTriDefaut.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				trier();
			}
		});
		
		
		// Icone tri iteration
		ToolItem itemTriIteration = new ToolItem(treeToolBar, SWT.PUSH);
		itemTriIteration.setImage(GestionImage._triIteration);
		itemTriIteration.setToolTipText(message.getString("treetoolbar.triIteration.tooltiptext"));
		itemTriIteration.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				trierIterations();
			}
		});
		
		
		// Icone tri activite
		ToolItem itemTriActivite = new ToolItem(treeToolBar, SWT.PUSH);
		itemTriActivite.setImage(GestionImage._triActivite);
		itemTriActivite.setToolTipText(message.getString("treetoolbar.triActivite.tooltiptext"));
		itemTriActivite.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				trierActivites();
			}
		});

		
		// Icone tri rôle
		ToolItem itemTriRole = new ToolItem(treeToolBar, SWT.PUSH);
		itemTriRole.setImage(GestionImage._triRole);
		itemTriRole.setToolTipText(message.getString("treetoolbar.triRole.tooltiptext"));
		itemTriRole.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				trierRoles();
			}
		});

		treeToolBar.pack();
		
		Point size1 = treeToolBar.getSize();
		treeCoolItem1.setControl(treeToolBar);
		treeCoolItem1.setSize(treeToolBar.computeSize(size1.x, size1.y));
		treeCoolItem1.setMinimumSize(size1);
		
		treeToolBar.pack();		
		
		
		_tree = new Tree(treeComposite, SWT.MULTI | SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		treeCoolBar.setLayoutData(data);

		data = new GridData(GridData.FILL_BOTH);
		_tree.setLayoutData(data);
		
		
		
		// Menu Contextuel
		Menu menuContextuel = new Menu(parent.getShell(), SWT.POP_UP);

		MenuItem menuItem1 = new MenuItem(menuContextuel, SWT.NONE);
		menuItem1.setText("...");
		menuItem1.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				
			}
		});
		
		MenuItem menuItemSep1 = new MenuItem(menuContextuel,SWT.SEPARATOR);
		
		MenuItem menuItemTrier = new MenuItem(menuContextuel, SWT.NONE);
		menuItemTrier.setText(message.getString("menu.trier"));
		menuItemTrier.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				trier();
			}
		});
		
		MenuItem menuItemTrierIterations = new MenuItem(menuContextuel, SWT.NONE);
		menuItemTrierIterations.setText(message.getString("menu.trierIterations"));
		menuItemTrierIterations.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				trierIterations();
			}
		});
		
		MenuItem menuItemTrierRôles = new MenuItem(menuContextuel, SWT.NONE);
		menuItemTrierRôles.setText(message.getString("menu.trierRoles"));
		menuItemTrierRôles.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				trierRoles();
			}
		});

		MenuItem menuItemTrierActivites = new MenuItem(menuContextuel, SWT.NONE);
		menuItemTrierActivites.setText(message.getString("menu.trierActivites"));
		menuItemTrierActivites.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				trierActivites();
			}
		});
		
		MenuItem menuItemSep2 = new MenuItem(menuContextuel,SWT.SEPARATOR);
		
		MenuItem menuItemActualiser = new MenuItem(menuContextuel, SWT.NONE);
		menuItemActualiser.setText(message.getString("menu.actualiser"));
		menuItemActualiser.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				actualiser();
			}
		});

		MenuItem menuItemSep3 = new MenuItem(menuContextuel,SWT.SEPARATOR);
		
		MenuItem menuItemPropriete = new MenuItem(menuContextuel, SWT.NONE);
		menuItemPropriete.setText(message.getString("menu.propriete"));
		menuItemPropriete.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				
			}
		});

		
		_tree.setMenu(menuContextuel);
		trier();
		_tree.pack();
		
		addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				treeCoolBar.layout(true);
			}
		});
		
		addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				treeComposite.setBounds(getClientArea());
				treeCoolBar.layout(true);
			}
		});
		
		
	}
	

	
	public void actualiser() {
		switch (_format) {
			case 0 :
				trier();
				break;
				
			case 1 :
				trierIterations();
				break;
				
			case 2 :
				trierActivites();
				break;
				
			case 3 :
				trierRoles();
				break;
				
			default :
				break;
		}
	}
	
	
	
	public void trier() {
		_tree.removeAll();
		_format = 0;
		
		TreeItem itemProjet = new TreeItem(_tree, SWT.NONE);
		TreeItem itemIteration = new TreeItem(itemProjet, SWT.NONE);
		TreeItem itemActivite = new TreeItem(itemProjet, SWT.NONE);
		TreeItem itemProduit = new TreeItem(itemProjet, SWT.NONE);
		TreeItem itemRôle = new TreeItem(itemProjet, SWT.NONE);
		TreeItem itemPersonne = new TreeItem(itemProjet, SWT.NONE);
		
		
		// Principaux noeuds
		if (EugesElements._projet == null) {
			itemProjet.setText(message.getString("arbre.projet"));
		}
		else {
			itemProjet.setText(EugesElements._projet.get_nomProjet());
		}
		
		itemIteration.setText(message.getString("arbre.iterations"));
		itemActivite.setText(message.getString("arbre.activites"));
		itemProduit.setText(message.getString("arbre.produits"));
		itemRôle.setText(message.getString("arbre.roles"));
		itemPersonne.setText(message.getString("arbre.personnes"));

		
		// Les images
		itemIteration.setImage(GestionImage._it);
		itemActivite.setImage(GestionImage._activite);
		itemProduit.setImage(GestionImage._produit);
		itemRôle.setImage(GestionImage._role);
		itemPersonne.setImage(GestionImage._actor);
		
		
		// Les iterations
		if (EugesElements._projet == null) {
			System.out.println("NULL");
		}
		else {			
			SortedSet vectIteration = EugesElements._projet.get_listeIteration();
			int nbIteration = vectIteration.size();
			TreeItem itemAuxIteration;
			Iterator it = vectIteration.iterator();
			while (it.hasNext()) {
				itemAuxIteration = new TreeItem(itemIteration, SWT.NONE);
				itemAuxIteration.setText(message.getString("arbre.iteration") + " " + ((Iteration) it.next()).get_numIt());
			}
		}
		
		
		// Les activites
		Vector vectActiv = EugesElements.listeActivites;
		int nbActiv = vectActiv.size();
		TreeItem itemAuxActivites; 
		for (int i=0; i<nbActiv; i++) {
			itemAuxActivites = new TreeItem(itemActivite, SWT.NONE);
			itemAuxActivites.setText(((EugesActivite) vectActiv.elementAt(i)).getName());
		}
		
		
		// Les produits
		Vector vectProduits = EugesElements.listeProduits;
		int nbProduits = vectProduits.size();
		TreeItem itemAuxProduit; 
		for (int i=0; i<nbProduits; i++) {
			itemAuxProduit = new TreeItem(itemProduit, SWT.NONE);
			itemAuxProduit.setText(((EugesProduit) vectProduits.elementAt(i)).getName());
		}
		
		
		
		// Les rôles
		Vector vectRôles = EugesElements.listeRoles;
		int nbRôles = vectRôles.size();
		TreeItem itemAuxRôles; 
		for (int i=0; i<nbRôles; i++) {
			itemAuxRôles = new TreeItem(itemRôle, SWT.NONE);
			itemAuxRôles.setText(((EugesRole) vectRôles.get(i)).getName());
		}
		
		
		
		// Les personnes
		Vector vectPersonnes = EugesElements.listePersonnes;
		int nbPersonnes = vectPersonnes.size();
		TreeItem itemAuxPersonnes;
		for (int i=0; i<nbPersonnes; i++) {
			itemAuxPersonnes = new TreeItem(itemPersonne, SWT.NONE);
			itemAuxPersonnes.setText(((EugesPersonne) vectPersonnes.get(i)).getNom());
		}
		
		itemProjet.setExpanded(true);
	}

	
	
	public void trierIterations() {
		_tree.removeAll();
		_format = 1;
		
		// Noeud principale
		TreeItem itemProjet = new TreeItem(_tree, SWT.NONE);
		if (EugesElements._projet == null) {
			itemProjet.setText(message.getString("arbre.projet"));
		}
		else {
			itemProjet.setText(EugesElements._projet.get_nomProjet());
		}

		
		
		// Les iterations
		if (EugesElements._projet == null) {
			System.out.println("NULL");
		}
		else {
			TreeItem itemAuxIteration;
			Iteration auxIteration;
			for (Iterator it = EugesElements._projet.get_listeIteration().iterator(); it.hasNext();) {
				auxIteration = (Iteration) it.next();
				itemAuxIteration = new TreeItem(itemProjet, SWT.NONE);
				itemAuxIteration.setText(message.getString("arbre.iteration") + " " + auxIteration.get_numIt());
				
				
				// Recuperation des Activites
				TreeItem itemAuxActivite; 
				EugesActRealise auxActRealise;
				for (int j=0; j<auxIteration.getActiviteCount(); j++) {
					auxActRealise = auxIteration.getActivite(j);
					itemAuxActivite = new TreeItem(itemAuxIteration, SWT.NONE);
					itemAuxActivite.setText(auxActRealise.get_activiteParent().getName());
					
					
					// Noeuds Fixes
					TreeItem itemProduit = new TreeItem(itemAuxActivite, SWT.NONE);
					TreeItem itemProduitsIn = new TreeItem(itemProduit, SWT.NONE);
					TreeItem itemProduitsOut = new TreeItem(itemProduit, SWT.NONE);
					itemProduit.setText(message.getString("arbre.produits"));
					itemProduitsIn.setText(message.getString("arbre.produits.in"));
					itemProduitsOut.setText(message.getString("arbre.produits.out"));
					itemProduit.setImage(GestionImage._produit);
					
					
					// Recuperation des Produits en entrees de l'activite
					TreeItem itemAuxProduitIn;
					WorkProduct auxProduitIn;
					for (int k=0; k<auxActRealise.get_activiteParent().getInputCount(); k++) {
						auxProduitIn = auxActRealise.get_activiteParent().getInput(k);
						itemAuxProduitIn = new TreeItem(itemProduitsIn, SWT.NONE);
						itemAuxProduitIn.setText(auxProduitIn.getName());
					}
					
					
					// Recuperation des Produits en sorties de l'activite
					TreeItem itemAuxProduitOut;
					WorkProduct auxProduitOut;
					for (int k=0; k<auxActRealise.get_activiteParent().getOutputCount(); k++) {
						auxProduitOut = auxActRealise.get_activiteParent().getOutput(k);
						itemAuxProduitOut = new TreeItem(itemProduitsOut, SWT.NONE);
						itemAuxProduitOut.setText(auxProduitOut.getName());
					}
					
	
					// Noeud fixe 'Personne'
					TreeItem itemPersonne = new TreeItem(itemAuxActivite, SWT.NONE);
					itemPersonne.setText(message.getString("arbre.personnes"));
					itemPersonne.setImage(GestionImage._actor);
					
					
					
					// Recuperation des Personnes
					TreeItem itemAuxPersonne;
					EugesPersonne auxPersonne;
					for (int l=0; l<auxActRealise.getPersonneCount(); l++) {
						auxPersonne = auxActRealise.getPersonne(l);
						itemAuxPersonne = new TreeItem(itemPersonne, SWT.NONE);
						itemAuxPersonne.setText(auxPersonne.getNom());
					}
				}				
			}
		}
		itemProjet.setExpanded(true);
	}

	
	
	public void trierActivites() {
		_tree.removeAll();
		_format = 2;
		
		
		// Noeud principale
		TreeItem itemProjet = new TreeItem(_tree, SWT.NONE);
		if (EugesElements._projet == null) {
			itemProjet.setText(message.getString("arbre.projet"));
		}
		else {
			itemProjet.setText(EugesElements._projet.get_nomProjet());
		}
		
		
		// Les Activites
		TreeItem itemAuxActivite;
		EugesActivite auxActivite;
		for (int i=0; i<EugesElements.listeActivites.size(); i++) {
			auxActivite = (EugesActivite) EugesElements.listeActivites.elementAt(i);
			itemAuxActivite = new TreeItem(itemProjet, SWT.NONE);
			itemAuxActivite.setText(auxActivite.getName());
			
			
			// Noeud fixe
			TreeItem itemIterations = new TreeItem(itemAuxActivite, SWT.NONE);
			itemIterations.setText(message.getString("arbre.iterations"));
			itemIterations.setImage(GestionImage._it);
			
			
			// Recuperation des iterations correspondantes à l'activite
			TreeItem itemAuxIteration;
			EugesActRealise auxActRealise;
			System.out.println(auxActivite.getActRealiseCount());
			for (int j=0; j<auxActivite.getActRealiseCount(); j++) {
				auxActRealise = auxActivite.getActRealise(j);
				itemAuxIteration = new TreeItem(itemIterations, SWT.NONE);
				itemAuxIteration.setText(message.getString("arbre.iteration") + " " + auxActRealise.getIt());
				
				
				// Noeud fixe
				TreeItem itemPersonnes = new TreeItem(itemAuxIteration, SWT.NONE);
				itemPersonnes.setText(message.getString("arbre.personnes"));
				itemPersonnes.setImage(GestionImage._actor);
				
				
				
				// Recuperation des personnes qui travaillent sur l'activite et à cette iteration 
				TreeItem itemAuxPersonne; 
				EugesPersonne auxPersonne;
				for (int k=0; k<auxActRealise.getPersonneCount(); k++) {
					auxPersonne = auxActRealise.getPersonne(k);
					itemAuxPersonne = new TreeItem(itemPersonnes, SWT.NONE);
					itemAuxPersonne.setText(auxPersonne.getNom());
				}
				
				
				// Noeud fixe
				TreeItem itemProduits = new TreeItem(itemAuxIteration, SWT.NONE);
				TreeItem itemProduitsIn = new TreeItem(itemProduits, SWT.NONE);
				TreeItem itemProduitsOut = new TreeItem(itemProduits, SWT.NONE);
				
				itemProduits.setText(message.getString("arbre.produits"));
				itemProduitsIn.setText(message.getString("arbre.produits.in"));
				itemProduitsOut.setText(message.getString("arbre.produits.out"));
				
				itemProduits.setImage(GestionImage._produit);
				
				
				// Recuperation des produits en entree de l'activite
				TreeItem itemAuxProduitsIn; 
				EugesProduit auxProduitsIn;
				for (int k=0; k<auxActivite.getInputCount(); k++) {
					auxProduitsIn = (EugesProduit) auxActivite.getInput(k);
					itemAuxProduitsIn = new TreeItem(itemProduitsIn, SWT.NONE);
					itemAuxProduitsIn.setText(auxProduitsIn.getName());
				}					
				
				
				// Recuperation des produits en sortie de l'activite 
				TreeItem itemAuxProduitsOut; 
				EugesProduit auxProduitsOut;
				for (int k=0; k<auxActivite.getOutputCount(); k++) {
					auxProduitsOut = (EugesProduit) auxActivite.getOutput(k);
					itemAuxProduitsOut = new TreeItem(itemProduitsOut, SWT.NONE);
					itemAuxProduitsOut.setText(auxProduitsOut.getName());
				}					
			}			
		}
		itemProjet.setExpanded(true);
	}
	
	
	public void trierRoles() {
		_tree.removeAll();
		_format = 3;
		
		
		// Noeud principale
		TreeItem itemProjet = new TreeItem(_tree, SWT.NONE);
		if (EugesElements._projet == null) {
			itemProjet.setText(message.getString("arbre.projet"));
		}
		else {
			itemProjet.setText(EugesElements._projet.get_nomProjet());
		}
		
		
		// Les Rôles
		TreeItem itemAuxRôle;
		EugesRole auxRôle;
		for (int i=0; i<EugesElements.listeRoles.size(); i++) {
			auxRôle = (EugesRole) EugesElements.listeRoles.elementAt(i);
			itemAuxRôle = new TreeItem(itemProjet, SWT.NONE);
			itemAuxRôle.setText(auxRôle.getName());
			
			// Noeud fixe 'Personne'
			TreeItem itemPersonnes = new TreeItem(itemAuxRôle, SWT.NONE);
			itemPersonnes.setText(message.getString("arbre.personnes"));
			itemPersonnes.setImage(GestionImage._actor);
			
			
			
			// Recuperation des Personnes
			// On parcours toutes les personnes du projet
			// pour chaque personne,  
			// si le rôle : 'auxRôle' appartient à la liste des rôles de la personne
			// alors on affiche la personne dans l'arbre
			TreeItem itemAuxPersonne;
			EugesPersonne auxPersonne;
			boolean appartient = false;
			Iterator it = EugesElements.listePersonnes.iterator();
			while(!appartient && it.hasNext()){
				auxPersonne = (EugesPersonne) it.next();
				System.out.println(auxPersonne.getRoleCount());
				for (int n=0; n<auxPersonne.getRoleCount(); n++) { 
					if(auxPersonne.getRole(n) == auxRôle && !appartient) {
						itemAuxPersonne = new TreeItem(itemPersonnes, SWT.NONE);
						itemAuxPersonne.setText(auxPersonne.getNom());
						appartient = true;
					}
				}
			}
			
			

			// Noeud fixe 'Personne'
			TreeItem itemActivites = new TreeItem(itemAuxRôle, SWT.NONE);
			itemActivites.setText(message.getString("arbre.activites"));
			itemActivites.setImage(GestionImage._it);
			
			
			// Recuperation des Activites
			TreeItem itemAuxActivite;
			EugesActivite auxActivite;
			EugesActRealise auxActRealise;
			boolean appartient2 = false;
			Iterator it2 = EugesElements.listeActivites.iterator();
			while(!appartient2 && it2.hasNext()){
				auxActivite = (EugesActivite) it2.next();
				for (int n=0; n<auxActivite.getRoleCount(); n++) { 
					if(auxActivite.getRole(n) == auxRôle && !appartient2) {
						itemAuxActivite= new TreeItem(itemActivites, SWT.NONE);
						itemAuxActivite.setText(auxActivite.getName());
						appartient = true;
					}
				}
			}
			
		}
	itemProjet.setExpanded(true);
	}


}
