/*
 * Created on 15 janv. 2004
 *
 */
package ihm;

import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.custom.TableTreeEditor;
import org.eclipse.swt.custom.TableTreeItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import utilitaires.GestionImage;
import application.EugesElements;
import application.It;
import configuration.Config;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesActivite;

/**
 * @author Mathieu GAYRAUD
 *
 */
public class ItIHM extends Composite {

	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);

	private CLabel _rolesLABEL;
	private ToolBar _rolesBAR;
	private ToolItem _rolesMNG;
	
	private CLabel _activitesLABEL;
	private ToolBar _activitesBAR1;
	private ToolBar _activitesBAR2;
	private Combo _activitesCOMBO;
	private ToolItem _activitesADD;
	private ToolItem _activitesDEL;
	private ToolItem _activitesMNG;
	private ToolItem _activitesPAR;
	private TableTree _activitesTABLE;
	private Point _mouseDown;
	private int _EDITABLECOLUMN;
	private Vector _participants = new Vector();

	private CLabel _produitsLABEL;
	private ToolBar _produitsBAR1;
	private ToolBar _produitsBAR2;
	private Combo _produitsCOMBO;
	private ToolItem _produitsADD;
	private ToolItem _produitsDEL;
	private ToolItem _produitsMNG;
	private ToolItem _produitsPAR;
	private Table _produitsInTABLE;
	private TableTree _produitsOutTABLE;
	
	/**
	 * @param parent
	 * @param numIt
	 */
	public ItIHM(final Composite parent, final int numIt) {
		super(parent, SWT.NONE|SWT.V_SCROLL);
		
		final Composite c = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		c.setLayout(layout);
		
		final ScrollBar vBar = this.getVerticalBar ();
		vBar.setIncrement(5);
		System.out.println(vBar);
		vBar.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				Point location = c.getLocation ();
				location.y = -vBar.getSelection ();
				c.setLocation (location);
			}
		});
		
		GridData data;
		Label toolSep;
		ToolItem sep;

		// titre
		Font font = new Font(parent.getDisplay(), "Arial", 15, 15);
		Label titre = new Label(c, SWT.NONE|SWT.CENTER);
		titre.setFont(font);
		titre.setText(message.getString("ItIHM.iteration") + numIt);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = titre.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		titre.setLayoutData(data);

		// Dates
		font = new Font(parent.getDisplay(), "Arial", 12, SWT.NONE);
		Label date = new Label(c, SWT.NONE|SWT.CENTER);
		date.setFont(font);
		date.setText(message.getString("ItIHM.de")
				+ EugesElements._projet.getIteration(numIt).get_dateDebut().toString()
				+ " "
				+ message.getString("ItIHM.a")
				+ EugesElements._projet.getIteration(numIt).get_dateFin().toString());
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = date.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		date.setLayoutData(data);

		// Roles
		Label vide = new Label(c,SWT.NONE);
		vide.setText("");
		font = new Font(parent.getDisplay(), "Arial", 12, 15);
		vide.setFont(font);
		data = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_END);
		data.heightHint = vide.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		vide.setLayoutData(data);
		
		_rolesLABEL = new CLabel(c, SWT.NONE|SWT.RIGHT);
		_rolesLABEL.setText(message.getString("ItIHM.roles"));
		_rolesLABEL.setImage(GestionImage._role);
		font = new Font(parent.getDisplay(), "Arial", 12, 15);
		_rolesLABEL.setFont(font);
		_rolesLABEL.pack ();
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = _rolesLABEL.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		_rolesLABEL.setLayoutData(data);

		toolSep = new Label(c,SWT.SEPARATOR|SWT.HORIZONTAL);
		data = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_END);
		data.heightHint = toolSep.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		toolSep.setLayoutData(data);
		
		_rolesBAR = new ToolBar (c, SWT.FLAT);

		_rolesMNG = new ToolItem(_rolesBAR, SWT.NONE);
		_rolesMNG.setText(message.getString("ItIHM.mngRoles"));
		_rolesMNG.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				FenetreAttributionRoleIHM fenetre = new FenetreAttributionRoleIHM(parent.getShell(),EugesElements._projet.getIteration(numIt));
				fenetre.open();
			}
		});
		
		_rolesBAR.pack ();
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = _rolesBAR.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		_rolesBAR.setLayoutData(data);

		toolSep = new Label(c,SWT.SEPARATOR|SWT.HORIZONTAL);
		data = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_BEGINNING);
		data.heightHint = toolSep.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		toolSep.setLayoutData(data);

		// activit�s
		vide = new Label(c,SWT.NONE);
		vide.setText("");
		font = new Font(parent.getDisplay(), "Arial", 12, 15);
		vide.setFont(font);
		data = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_END);
		data.heightHint = vide.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		vide.setLayoutData(data);
		
		_activitesLABEL = new CLabel(c, SWT.NONE|SWT.RIGHT|SWT.BOTTOM);
		_activitesLABEL.setText(message.getString("ItIHM.activites"));
		_activitesLABEL.setImage(GestionImage._activite);
		font = new Font(parent.getDisplay(), "Arial", 12, 15);
		_activitesLABEL.setFont(font);
		_activitesLABEL.pack ();
		data = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_END);
		data.heightHint = _activitesLABEL.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		_activitesLABEL.setLayoutData(data);

		toolSep = new Label(c,SWT.SEPARATOR|SWT.HORIZONTAL);
		data = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_END);
		data.heightHint = toolSep.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		toolSep.setLayoutData(data);
		
		final Composite c1 = new Composite(c, SWT.NONE);
		c1.setLayout(new GridLayout(3,false));
		
//		sep = new ToolItem(_activitesBAR1, SWT.SEPARATOR);
		_activitesCOMBO = new Combo(c1, SWT.READ_ONLY|SWT.FLAT|SWT.DROP_DOWN);
		for (Iterator iter = EugesElements.listeActivites.iterator(); iter.hasNext();) {
			EugesActivite e = (EugesActivite) iter.next();
			_activitesCOMBO.add(e.toString());
		}
//		_activitesCOMBO.pack ();
		_activitesCOMBO.select(0);
//		sep.setWidth(_activitesCOMBO.getSize().x);
//		sep.setControl(_activitesCOMBO);

		_activitesBAR1 = new ToolBar (c1, SWT.FLAT);

		_activitesADD = new ToolItem(_activitesBAR1, SWT.NONE);
		_activitesADD.setImage(GestionImage._plus);
		_activitesADD.setToolTipText(message.getString("ItIHM.addAct"));
		_activitesADD.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				if (!_activitesCOMBO.getText().equals("")) {
					TableTreeItem[] tab = _activitesTABLE.getItems();
					boolean trouve=false;
					for (int i = 0; i < tab.length; i++) {
						if (((EugesActRealise)tab[i].getData()).toString().equalsIgnoreCase(_activitesCOMBO.getText())) {
							trouve=true;
							break;
						}
					}
					if (!trouve) {
						TableTreeItem item = new TableTreeItem(_activitesTABLE, SWT.NONE);
						item.setText(0,_activitesCOMBO.getText());
						item.setText(1,"0");
						item.setText(2,"0");
						item.setData(It.ajouterActRealise(_activitesCOMBO.getText(),numIt));
					}
//					TableTreeItem subitem = new TableTreeItem(item, SWT.NONE);
//					subitem.setText(0,"Participants...");
//					subitem.setText(1,"");
//					subitem.setText(2,"");
				}
			}
		});

		_activitesDEL = new ToolItem(_activitesBAR1, SWT.FLAT);
		_activitesDEL.setImage(GestionImage._moins);
		_activitesDEL.setToolTipText(message.getString("ItIHM.delAct"));
		_activitesDEL.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				TableTreeItem[] select = _activitesTABLE.getSelection();
				if (select.length != 0) {
					if (select[0].getParentItem() == null) {
						It.supprimerActRealise((EugesActRealise) select[0].getData(), numIt);
						select[0].dispose();
					}
				}
			}
		});

		_activitesBAR1.pack ();

		_activitesBAR2 = new ToolBar (c1, SWT.FLAT);

		ToolItem sep2 = new ToolItem(_activitesBAR2, SWT.SEPARATOR);

		_activitesMNG = new ToolItem(_activitesBAR2, SWT.NONE);
		_activitesMNG.setText(message.getString("ItIHM.mngAct"));
		_activitesMNG.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				Shell shel = new Shell(parent.getShell());
				PageGestionActivitesIHM pageGestionActivitesIHM = new PageGestionActivitesIHM(shel);
				PlanItIHM.majActivites();
//				// on r�gle la taille de la liste d�roulante
//				int maxSize = c.getSize().x - _activitesBAR2.getSize().x - _activitesADD.getWidth()*2;
//				_activitesCOMBO.setSize(
//						Math.min(maxSize,
//								_activitesCOMBO.computeSize(SWT.DEFAULT,SWT.DEFAULT).x),
//						_activitesCOMBO.computeSize(SWT.DEFAULT,SWT.DEFAULT).y);
			}
		});
		
		_activitesPAR = new ToolItem(_activitesBAR2, SWT.NONE);
		_activitesPAR.setText(message.getString("ItIHM.addPar"));
		_activitesPAR.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				if (_activitesTABLE.getSelectionCount() != 0) {
					TableTreeItem item = _activitesTABLE.getSelection()[0];
					if (item.getParentItem() != null) {
						item = item.getParentItem();
					}
					NouveauParticipantIHM page = new NouveauParticipantIHM(getShell());
					EugesActRealise a = (EugesActRealise)item.getData();
					page.open(a, numIt);
					TableTreeItem[] items = item.getItems();
					// on efface les anciens items
					for (int i=0; i<items.length; i++)
						items[i].dispose();
					
					// on cr�e les nouveaux
					TableTreeItem subitem;
					for (int i=0; i<a.getPersonneCount(); i++) {
						subitem = new TableTreeItem(item, SWT.NONE);
						subitem.setText(0,a.getPersonne(i).toString());
						subitem.setText(1,"");
						subitem.setText(2,"");
					}
					item.setExpanded(true);
				}
			}
		});

		_activitesBAR2.pack ();

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = c1.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		c1.setLayoutData(data);

		toolSep = new Label(c,SWT.SEPARATOR|SWT.HORIZONTAL);
		data = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_BEGINNING);
		data.heightHint = toolSep.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		toolSep.setLayoutData(data);
		
		//tableau des activit�s
		_activitesTABLE = new TableTree(c, SWT.FULL_SELECTION|SWT.BORDER);
		final Table table = _activitesTABLE.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		//colonnes du tableau
		TableColumn colonne1 = new TableColumn(table, SWT.LEFT);
		TableColumn colonne2 = new TableColumn(table, SWT.LEFT);
		TableColumn colonne3 = new TableColumn(table, SWT.LEFT);
		
		colonne1.setText(message.getString("ItIHM.colAct"));
		colonne2.setText(message.getString("ItIHM.colChPr"));
		colonne3.setText(message.getString("ItIHM.colChRe"));
		
		_activitesTABLE.removeAll();
		
		_activitesTABLE.setToolTipText(message.getString("ItIHM.toolTipTabAct"));

		data = new GridData(GridData.FILL_BOTH);
		data.heightHint = _activitesTABLE.computeSize(SWT.DEFAULT,SWT.DEFAULT).y * 2;
		_activitesTABLE.setLayoutData(data);

		final TableTreeEditor editor = new TableTreeEditor(_activitesTABLE);
		//The editor must have the same size as the cell and must
		//not be any smaller than 50 pixels.
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
		
		table.addListener (SWT.MouseDown, new Listener () {
			public void handleEvent (Event event) {
				_mouseDown = new Point (event.x, event.y);
			}
		});

		_activitesTABLE.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null) oldEditor.dispose();
	
				// Identify the selected row
				final TableTreeItem item = (TableTreeItem)e.item;
				if (item == null) return;

				if (item.getParentItem() != null) return;

				Table table = _activitesTABLE.getTable();
				int width = _activitesTABLE.getClientArea().width;
				int largeur;
				largeur = width - width / 4;
				
				if (_mouseDown.x < largeur)
					_EDITABLECOLUMN = 1;
				else
					_EDITABLECOLUMN = 2;
				
				// The control that will be the editor must be a child of the Table
				Text newEditor = new Text(table, SWT.NONE);
				newEditor.setText(item.getText(_EDITABLECOLUMN));
				newEditor.addVerifyListener(new VerifyListener() {
					public void verifyText(VerifyEvent e) {
						String text = e.text;
						char [] chars = new char [text.length ()];
						text.getChars (0, chars.length, chars, 0);
						for (int i=0; i<chars.length; i++) {
							if (!('0' <= chars [i] && chars [i] <= '9')) {
								e.doit = false;
								return;
							}
						}
					}
				});
				newEditor.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						Text text = (Text)editor.getEditor();
						editor.getItem().setText(_EDITABLECOLUMN, text.getText());

						EugesActRealise a = (EugesActRealise) item.getData();
						// on ins�re les nouvelles heures
						if (_EDITABLECOLUMN == 1) { // charge pr�vue
							a.set_chargeEstimee(new Integer(item.getText(_EDITABLECOLUMN)).intValue());
							System.out.println(a.get_chargeEstimee());
						}
						else { // charge r�elle
							a.set_chargeReelle(new Integer(item.getText(_EDITABLECOLUMN)).intValue());
							System.out.println(a.get_chargeReelle());
						}
					}
				});
				newEditor.selectAll();
				newEditor.setFocus();
				editor.setEditor(newEditor, item, _EDITABLECOLUMN);
			}
		});
		
		// Produits
		vide = new Label(c,SWT.NONE);
		vide.setText("");
		font = new Font(parent.getDisplay(), "Arial", 12, 15);
		vide.setFont(font);
		data = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_END);
		data.heightHint = vide.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		vide.setLayoutData(data);
		
		_produitsLABEL = new CLabel(c, SWT.NONE|SWT.RIGHT);
		_produitsLABEL.setText(message.getString("ItIHM.produits"));
		_produitsLABEL.setImage(GestionImage._produit);
		font = new Font(parent.getDisplay(), "Arial", 12, 15);
		_produitsLABEL.setFont(font);
		_produitsLABEL.pack ();
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = _produitsLABEL.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		_produitsLABEL.setLayoutData(data);

		toolSep = new Label(c,SWT.SEPARATOR|SWT.HORIZONTAL);
		data = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_END);
		data.heightHint = toolSep.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		toolSep.setLayoutData(data);
		
		final Composite c2 = new Composite(c, SWT.NONE);
		c2.setLayout(new GridLayout(3,false));
		
		_produitsCOMBO = new Combo(c2, SWT.READ_ONLY|SWT.FLAT|SWT.DROP_DOWN);
		for (Iterator iter = EugesElements.listeActivites.iterator(); iter.hasNext();) {
			EugesActivite e = (EugesActivite) iter.next();
			_produitsCOMBO.add(e.toString());
		}
		_produitsCOMBO.select(0);

		_produitsBAR1 = new ToolBar (c2, SWT.FLAT);

		_produitsADD = new ToolItem(_produitsBAR1, SWT.NONE);
		_produitsADD.setImage(GestionImage._plus);
		_produitsADD.setToolTipText(message.getString("ItIHM.addVer"));
		_produitsADD.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				if (!_produitsCOMBO.getText().equals("")) {
					TableTreeItem[] tab = _produitsOutTABLE.getItems();
					boolean trouve=false;
					for (int i = 0; i < tab.length; i++) {
						if (((EugesActRealise)tab[i].getData()).toString().equalsIgnoreCase(_produitsCOMBO.getText())) {
							trouve=true;
							break;
						}
					}
					if (!trouve) {
						TableTreeItem item = new TableTreeItem(_produitsOutTABLE, SWT.NONE);
						item.setText(0,_produitsCOMBO.getText());
						item.setText(1,"0");
						item.setText(2,"0");
						item.setData(It.ajouterActRealise(_produitsCOMBO.getText(),numIt));
					}
				}
			}
		});

		_produitsDEL = new ToolItem(_produitsBAR1, SWT.FLAT);
		_produitsDEL.setImage(GestionImage._moins);
		_produitsDEL.setToolTipText(message.getString("ItIHM.delVer"));
		_produitsDEL.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				TableTreeItem[] select = _produitsOutTABLE.getSelection();
				if (select.length != 0) {
					if (select[0].getParentItem() == null) {
						It.supprimerActRealise((EugesActRealise) select[0].getData(), numIt);
						select[0].dispose();
					}
				}
			}
		});

		_produitsBAR1.pack ();

		_produitsBAR2 = new ToolBar (c2, SWT.FLAT);

		ToolItem sep3 = new ToolItem(_produitsBAR2, SWT.SEPARATOR);

		_produitsMNG = new ToolItem(_produitsBAR2, SWT.NONE);
		_produitsMNG.setText(message.getString("ItIHM.mngProd"));
		_produitsMNG.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				FenetreGestionProduitsIHM fenetre = new FenetreGestionProduitsIHM(parent.getShell());
				fenetre.open();
			}
		});

		_produitsPAR = new ToolItem(_produitsBAR2, SWT.NONE);
		_produitsPAR.setText(message.getString("ItIHM.parProd"));
		_produitsPAR.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				Shell shel = new Shell(parent.getShell());
				PageAttributionProduitIHM pageAttributionProduitIHM= new PageAttributionProduitIHM(shel.getDisplay());
			}
		});

		_produitsBAR2.pack ();
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = c2.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		c2.setLayoutData(data);

		toolSep = new Label(c,SWT.SEPARATOR|SWT.HORIZONTAL);
		data = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_BEGINNING);
		data.heightHint = toolSep.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		toolSep.setLayoutData(data);
		
		// Produits en sortie
		toolSep = new Label(c,SWT.SEPARATOR|SWT.HORIZONTAL);
		data = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_END);
		data.heightHint = toolSep.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		toolSep.setLayoutData(data);
		
		//tableau des produits en sortie
		_produitsOutTABLE = new TableTree(c, SWT.BORDER);
		final Table table2 = _produitsOutTABLE.getTable();
		table2.setLinesVisible(true);
		table2.setHeaderVisible(true);
		//colonnes du tableau
		colonne1 = new TableColumn(table2, SWT.LEFT);
		colonne2 = new TableColumn(table2, SWT.LEFT);
		colonne3 = new TableColumn(table2, SWT.LEFT);
		
		colonne1.setText(message.getString("ItIHM.prodOut"));
		colonne2.setText(message.getString("ItIHM.prodResp"));
		colonne3.setText(message.getString("ItIHM.prodEtat"));
		
		_produitsOutTABLE.removeAll();

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = _produitsOutTABLE.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		_produitsOutTABLE.setLayoutData(data);
		
		//tableau des produits en entr�e
		_produitsInTABLE = new Table(c, SWT.BORDER);
		_produitsInTABLE.setLinesVisible(true);
		_produitsInTABLE.setHeaderVisible(true);
		//colonnes du tableau
		colonne1 = new TableColumn(_produitsInTABLE, SWT.LEFT);
		colonne2 = new TableColumn(_produitsInTABLE, SWT.LEFT);
		colonne3 = new TableColumn(_produitsInTABLE, SWT.LEFT);
		
		colonne1.setText(message.getString("ItIHM.prodIn"));
		colonne2.setText(message.getString("ItIHM.prodResp"));
		colonne3.setText(message.getString("ItIHM.prodEtat"));
		
		_produitsInTABLE.removeAll();

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = _produitsInTABLE.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
		_produitsInTABLE.setLayoutData(data);

		c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				int height = c.computeSize(SWT.DEFAULT,SWT.DEFAULT).y;
				int width = getClientArea().width;
				c.setSize(width,height);
				resizeTab();
				
				Point size = c.getSize ();
				Rectangle rect = getClientArea ();
				vBar.setMaximum (size.y);
				vBar.setThumb (Math.min (size.y, rect.height));
				int hPage = size.x - rect.width;
				int vPage = size.y - rect.height;
				int vSelection = vBar.getSelection ();
				Point location = c.getLocation ();
				if (vSelection >= vPage) {
					if (vPage <= 0) vSelection = 0;
					location.y = -vSelection;
				}
				c.setLocation (location);
			}
		});
	}

	private void resizeTab() {
		TableColumn[] cols;
		int height;
		int width;
		int width2;
		int total;
		int temp;

		width = _activitesTABLE.getClientArea().width;
		Table table = _activitesTABLE.getTable();
		cols = table.getColumns();
		for (int i=1; i<cols.length; i++)
			cols[i].setWidth(width / 4);
		total = (width / 2);
		cols[0].setWidth(width - total);
		
		width = _produitsInTABLE.getClientArea().width;
		cols = _produitsInTABLE.getColumns();
		for (int i=1; i<cols.length; i++)
			cols[i].setWidth(width / cols.length);
		total = (width / cols.length) * (cols.length - 1);
		cols[0].setWidth(width - total);

		width = _produitsOutTABLE.getClientArea().width;
		Table table2 = _produitsOutTABLE.getTable();
		cols = table2.getColumns();
		for (int i=1; i<cols.length; i++)
			cols[i].setWidth(width / 6);
		total = (width / 3);
		cols[0].setWidth(width - total);
	}
	
	public void majActivitesCOMBO () {
		_activitesCOMBO.removeAll();
		for (Iterator iter = EugesElements.listeActivites.iterator(); iter.hasNext();) {
			EugesActivite e1 = (EugesActivite) iter.next();
			_activitesCOMBO.add(e1.toString());
		}
		_activitesCOMBO.select(0);
	}
}
