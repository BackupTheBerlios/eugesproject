/*
 * Created on 15 janv. 2004
 *
 */

package ihm;

import ihm.vues.planIt.PlanItIHM;

import java.util.ResourceBundle;

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
import application.TriArbre;
import configuration.Config;
import donnees.Iteration;
import donnees.Projet;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesProduit;
import donnees.eugesSpem.EugesRole;


/**
 * @author Nicolas Elbeze
 *
 */
public class ArbrePrincipalIHM extends ViewForm {

	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public static Tree _tree;
	public static TriArbre _tri;
	
	
	/**
	 * @param parent
	 */

	public ArbrePrincipalIHM(final Composite parent) {
		
		super(parent, SWT.BORDER | SWT.V_SCROLL);
		
		_tri = new TriArbre();
		final Composite treeComposite = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		treeComposite.setLayout(layout);
		
		
		// Barre d'outils verticale
		final CoolBar	treeCoolBar = new CoolBar(treeComposite, SWT.NONE);
		CoolItem treeCoolItem1 = new CoolItem(treeCoolBar, SWT.NONE);
		CoolItem treeCoolItem2 = new CoolItem(treeCoolBar, SWT.NONE);
		ToolBar treeToolBar1 = new ToolBar(treeCoolBar, SWT.FLAT);
		ToolBar treeToolBar2 = new ToolBar(treeCoolBar, SWT.FLAT);
		
		
		// Icone actualiser
		ToolItem itemActualiser = new ToolItem(treeToolBar1, SWT.NONE);
		itemActualiser.setImage(GestionImage._triRefresh);
		itemActualiser.setToolTipText(message.getString("treetoolbar.actualiser.tooltiptext"));
		itemActualiser.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				_tri.actualiser();
			}
		});
				
		
		// Icone tri defaut
		ToolItem itemTriDefaut = new ToolItem(treeToolBar2, SWT.PUSH);
		itemTriDefaut.setImage(GestionImage._triDefaut);
		itemTriDefaut.setToolTipText(message.getString("treetoolbar.triDefaut.tooltiptext"));
		itemTriDefaut.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				_tri.trier();
			}
		});
		
		
		// Icone tri iteration
		ToolItem itemTriIteration = new ToolItem(treeToolBar2, SWT.PUSH);
		itemTriIteration.setImage(GestionImage._triIteration);
		itemTriIteration.setToolTipText(message.getString("treetoolbar.triIteration.tooltiptext"));
		itemTriIteration.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				_tri.trierIterations();
			}
		});
		
		
		// Icone tri activite
		ToolItem itemTriActivite = new ToolItem(treeToolBar2, SWT.PUSH);
		itemTriActivite.setImage(GestionImage._triActivite);
		itemTriActivite.setToolTipText(message.getString("treetoolbar.triActivite.tooltiptext"));
		itemTriActivite.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				_tri.trierActivites();
			}
		});

		
		// Icone tri rôle
		ToolItem itemTriRole = new ToolItem(treeToolBar2, SWT.PUSH);
		itemTriRole.setImage(GestionImage._triRole);
		itemTriRole.setToolTipText(message.getString("treetoolbar.triRole.tooltiptext"));
		itemTriRole.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				_tri.trierRoles();
			}
		});

		
		
		// Icone tri personne
		ToolItem itemTriPersonne = new ToolItem(treeToolBar2, SWT.PUSH);
		itemTriPersonne.setImage(GestionImage._actor);
		itemTriPersonne.setToolTipText(message.getString("treetoolbar.triPersonne.tooltiptext"));
		itemTriPersonne.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				_tri.trierPersonnes();
			}
		});
		
		treeToolBar1.pack();
		
		Point size1 = treeToolBar1.getSize();
		treeCoolItem1.setControl(treeToolBar1);
		treeCoolItem1.setSize(treeToolBar1.computeSize(size1.x, size1.y));
		treeCoolItem1.setMinimumSize(size1);
		
		
		treeToolBar2.pack();
		
		Point size2 = treeToolBar2.getSize();
		treeCoolItem2.setControl(treeToolBar2);
		treeCoolItem2.setSize(treeToolBar2.computeSize(size2.x, size2.y));
		treeCoolItem2.setMinimumSize(size2);
		
		treeCoolBar.pack();		
		
		
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
				_tri.trier();
			}
		});
		
		MenuItem menuItemTrierIterations = new MenuItem(menuContextuel, SWT.NONE);
		menuItemTrierIterations.setText(message.getString("menu.trierIterations"));
		menuItemTrierIterations.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				_tri.trierIterations();
			}
		});
		
		MenuItem menuItemTrierActivites = new MenuItem(menuContextuel, SWT.NONE);
		menuItemTrierActivites.setText(message.getString("menu.trierActivites"));
		menuItemTrierActivites.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				_tri.trierActivites();
			}
		});

		MenuItem menuItemTrierRoles = new MenuItem(menuContextuel, SWT.NONE);
		menuItemTrierRoles.setText(message.getString("menu.trierRoles"));
		menuItemTrierRoles.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				_tri.trierRoles();
			}
		});

		MenuItem menuItemTrierPersonnes = new MenuItem(menuContextuel, SWT.NONE);
		menuItemTrierPersonnes.setText(message.getString("menu.trierPersonnes"));
		menuItemTrierPersonnes.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				_tri.trierPersonnes();
			}
		});
		
		MenuItem menuItemSep2 = new MenuItem(menuContextuel,SWT.SEPARATOR);
		
		MenuItem menuItemActualiser = new MenuItem(menuContextuel, SWT.NONE);
		menuItemActualiser.setText(message.getString("menu.actualiser"));
		menuItemActualiser.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				_tri.actualiser();
			}
		});

		MenuItem menuItemSep3 = new MenuItem(menuContextuel,SWT.SEPARATOR);
		
		MenuItem menuItemPropriete = new MenuItem(menuContextuel, SWT.NONE);
		menuItemPropriete.setText(message.getString("menu.propriete"));
		menuItemPropriete.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				TreeItem[] selection = _tree.getSelection();
				TreeItem noeud = selection[0];
				
				FenetreProprietesIHM fenetre = new FenetreProprietesIHM(parent.getShell());
				
				// Si le noeud séléctionné est le projet, mais non chargé
				if(noeud.getData() instanceof String && noeud.getData() == "projet") {
					fenetre.open(0, noeud.getData());
					return;
				}
				
				// Si le noeud séléctionné est le projet
				if(noeud.getData() instanceof Projet) {
					fenetre.open(1, noeud.getData());
					return;
				}

				// Si le noeud séléctionné est une liste d'itération
				if(noeud.getData() instanceof String && noeud.getData() == "iterations") {
					fenetre.open(2, noeud.getData());
					return;
				}
				
				// Si le noeud séléctionné est une itération
				if(noeud.getData() instanceof Iteration) {
					fenetre.open(3, noeud.getData());
					return;
				}

				// Si le noeud séléctionné est une liste d'activités
				if(noeud.getData() instanceof String && noeud.getData() == "activites") {
					fenetre.open(4, noeud.getData());
					return;
				}
				
				// Si le noeud séléctionné est une activité
				if(noeud.getData() instanceof EugesActivite) {
					fenetre.open(5, noeud.getData());
					return;
				}
				
				// Si le noeud séléctionné est une activité réalisé
				if(noeud.getData() instanceof EugesActRealise) {
					fenetre.open(6, noeud.getData());
					return;
				}

				// Si le noeud séléctionné est une liste de produits
				if(noeud.getData() instanceof String && noeud.getData() == "produits") {
					fenetre.open(7, noeud.getData());
					return;
				}

				// Si le noeud séléctionné est une liste de produits en entrées
				if(noeud.getData() instanceof String && noeud.getData() == "produitsIn") {
					fenetre.open(8, noeud.getData());
					return;
				}

				// Si le noeud séléctionné est une liste de produits en sorties
				if(noeud.getData() instanceof String && noeud.getData() == "produitOut") {
					fenetre.open(9, noeud.getData());
					return;
				}
				
				// Si le noeud séléctionné est un produit
				if(noeud.getData() instanceof EugesProduit) {
					fenetre.open(10, noeud.getData());
					return;
				}
				
				// Si le noeud séléctionné est une version d'un produit
				if(noeud.getData() instanceof EugesProduit) {
					fenetre.open(11, noeud.getData());
					return;
				}

				// Si le noeud séléctionné est une liste de rôles
				if(noeud.getData() instanceof String && noeud.getData() == "roles") {
					fenetre.open(12, noeud.getData());
					return;
				}
				
				// Si le noeud séléctionné est un rôle
				if(noeud.getData() instanceof EugesRole) {
					fenetre.open(13, noeud.getData());
					return;
				}

				// Si le noeud séléctionné est une liste de personnes
				if(noeud.getData() instanceof String && noeud.getData() == "personnes") {
					fenetre.open(14, noeud.getData());
					return;
				}
				
				// Si le noeud séléctionné est une personne
				if(noeud.getData() instanceof EugesPersonne) {
					fenetre.open(15, noeud.getData());
					return;
				}
				
			}
		});

		
		_tree.setMenu(menuContextuel);
		_tri.trier();
		_tree.pack();
		
		_tree.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				TreeItem[] selections = _tree.getSelection();
				TreeItem selection = selections[0];
				if (selection.getData() instanceof Iteration) {
					Iteration it = (Iteration) selection.getData();
					int numIt = it.get_numIt();
					PlanItIHM.get_tabFolder().setSelection(numIt);
				}
			} 
		});
		
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
	
}
