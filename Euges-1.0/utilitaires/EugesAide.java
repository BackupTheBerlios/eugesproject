/*
 * Created on 28 d?c. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package utilitaires;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import configuration.Config;



/**
 * @author Nicolas Elbeze
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

public class EugesAide {
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	public EugesAide(String url) {
		Display display = Display.getCurrent();
		
		Shell shellAide = new Shell(display);
		shellAide.setSize(800,500);
		shellAide.setText(message.getString("eugesAide.barreTitre"));
		shellAide.setImage(GestionImage._euges);		
		

		
		
		// Barre d'outils
		final CoolBar coolBar = new CoolBar(shellAide, SWT.FLAT);
		CoolItem item1 = new CoolItem(coolBar, SWT.NONE);
		ToolBar toolbar = new ToolBar(coolBar, SWT.FLAT);

		
		// Icone Afficher
		ToolItem itemAfficher = new ToolItem(toolbar, SWT.FLAT);
		itemAfficher.setImage(GestionImage._masquerAide);
		itemAfficher.setToolTipText(message.getString("eugesAide.MasquerToolTipText"));
		itemAfficher.setText(message.getString("eugesAide.BoutonMasquer"));
		
		// Icone Pr?c?dent
		ToolItem itemPrecedent = new ToolItem(toolbar, SWT.FLAT);
		itemPrecedent.setImage(GestionImage._precedentAide);
		itemPrecedent.setToolTipText(message.getString("eugesAide.Pr?c?dentToolTipText"));
		itemPrecedent.setText(message.getString("eugesAide.BoutonPr?c?dent"));
		
		// Icone Suivant
		ToolItem itemSuivant = new ToolItem(toolbar, SWT.FLAT);
		itemSuivant.setImage(GestionImage._suivantAide);
		itemSuivant.setToolTipText(message.getString("eugesAide.SuivantToolTipText"));
		itemSuivant.setText(message.getString("eugesAide.BoutonSuivant"));
		
		toolbar.pack();
		
		Point size1 = toolbar.getSize();
		item1.setControl(toolbar);
		item1.setSize(toolbar.computeSize(size1.x, size1.y));
		item1.setMinimumSize(size1);

		coolBar.pack();
		
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		coolBar.setLayoutData(data);
		
		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 3;
		data.grabExcessHorizontalSpace = true;

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		shellAide.setLayout(gridLayout);
		

		final SashForm sashForm = new SashForm(shellAide, SWT.HORIZONTAL);
		//final SashPersoV sashForm = new SashPersoV(shellAide, 20);
		//final Composite left = sashForm.getLeft();
		//final Composite right = sashForm.getRight();
		
		// Arbre
		final Tree tree = new Tree(sashForm, SWT.MULTI | SWT.BORDER);
		//final Tree tree = new Tree(left, SWT.NONE);
		tree.setBounds(0, 0, 100, 100);
		
			// Parser le fichier XML
		readXML("configuration/help/fr/help.xml", tree);
		
		
		// Browser
		final Browser browser = new Browser(sashForm, SWT.NONE);
		//final Browser browser = new Browser(right, SWT.NONE);
		
		
		// Propri?t?s du sashForm
		sashForm.setWeights(new int [] {20,80});
		sashForm.setSize(800,400);
		
		
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.horizontalSpan = 3;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		sashForm.setLayoutData(data);
		
		
		final Label status = new Label(shellAide, SWT.NONE);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		status.setLayoutData(data);

		final ProgressBar progressBar = new ProgressBar(shellAide, SWT.NONE);
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		progressBar.setLayoutData(data);

		/* event handling */
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				ToolItem item = (ToolItem) event.widget;
				String string = item.getText();
				if (string.equals(message.getString("eugesAide.BoutonAfficher"))) {	
					// Afficher l'arbre
					item.setToolTipText(message.getString("eugesAide.MasquerToolTipText"));
					item.setImage(GestionImage._masquerAide);
					item.setText(message.getString("eugesAide.BoutonMasquer"));
					sashForm.setWeights(new int [] {20,80});	
				}
				else if (string.equals(message.getString("eugesAide.BoutonMasquer"))) {
					// Marsquer l'arbre
					item.setToolTipText(message.getString("eugesAide.AfficherToolTipText"));
					item.setImage(GestionImage._masquerAide);
					item.setText(message.getString("eugesAide.BoutonAfficher"));
					System.out.println("Masquer");
					sashForm.setWeights(new int [] {0,100});	
				}
				else if (string.equals(message.getString("eugesAide.BoutonPr?c?dent"))) {
					System.out.println("Pr?c?dent");
					browser.back();
				}
				else if (string.equals(message.getString("eugesAide.BoutonSuivant"))) {
					System.out.println("Suivant");
					browser.forward();
				}		
			}
		};
		
		
		browser.addProgressListener(new ProgressListener() {
			public void changed(ProgressEvent event) {
				if (event.total == 0)
					return;
				int ratio = event.current * 100 / event.total;
				progressBar.setSelection(ratio);
			}
			public void completed(ProgressEvent event) {
				progressBar.setSelection(0);
			}
		});
		
		browser.addStatusTextListener(new StatusTextListener() {
			public void changed(StatusTextEvent event) {
				status.setText(event.text);
			}
		});
		
		tree.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event e) {
				String nom = remplacementCaractere(tree.getSelection()[0].getText(), " ", "_");
				String fichier = "configuration\\help\\fr\\" + nom + ".xml";
				
				File file = new File(fichier);
				if (file.exists()) {
					browser.setUrl(file.getAbsolutePath());
				}
				else {
					browser.setText("Page introuvable");
				}
			}
		});
		
		tree.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event e) {
				if (tree.getSelectionCount() > 0) {
					if (tree.getSelection()[0].getExpanded()) {
						tree.getSelection()[0].setImage(GestionImage._bookOuvertAide);
					}
					else {
						if (((String) tree.getSelection()[0].getData()).equals("Noeud")) {
							tree.getSelection()[0].setImage(GestionImage._bookFermeAide);
						}
					}
				}
			}
		});
		itemAfficher.addListener(SWT.Selection, listener);
		itemPrecedent.addListener(SWT.Selection, listener);
		itemSuivant.addListener(SWT.Selection, listener);

		
		browser.setUrl(url);
		shellAide.open ();
		
	}
	
	
	// Pour cr?er Parser le fichier XML et cr?er le root
	public void readXML(String file, Tree tree) {
		try {
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbfactory.newDocumentBuilder();
			Document document = builder.parse(file);
	
			Element root = document.getDocumentElement();
			tree.removeAll();
			TreeItem ti = new TreeItem(tree, SWT.NULL);
			ti.setImage(GestionImage._bookOuvertAide);
			ti.setText(remplacementCaractere(root.getNodeName(), "_", " "));
			ti.setData("Noeud");
			
			NodeList childList = root.getChildNodes();
			readXML2(childList, ti);
			ti.setExpanded(true);
		}
		catch (Exception e) {

		}
	}

	// Pour afficher les fils
	public void readXML2(NodeList nlist, TreeItem pTree) {
		for (int i = 0; i < nlist.getLength(); i++) {
			Node node = nlist.item(i);
			TreeItem ti;
			switch (node.getNodeType()) {
				case Node.ELEMENT_NODE:
					ti = new TreeItem(pTree, SWT.NULL);
					ti.setText(remplacementCaractere(node.getNodeName(), "_", " "));
					
						
					// Pour afficher les attributs dans l'arbre
					/*
					 NamedNodeMap attrList = node.getAttributes();
					 for (int j = 0; j < attrList.getLength(); j++) {
						Node attr = attrList.item(j);
						TreeItem ti2 = new TreeItem(ti, SWT.NULL);
						//ti2.setImage(imgAttr);
						ti2.setText(attr.getNodeName() + " = " + attr.getNodeValue());
					}
					*/
					if (node.hasChildNodes()) {
						ti.setImage(GestionImage._bookFermeAide);
						ti.setData("Noeud");
						NodeList childList = node.getChildNodes();
						readXML2(childList, ti);
					}
					else {
						ti.setImage(GestionImage._bookFeuilleAide);
						ti.setData("Feuille");
					}
					break;
					
				default:
					break;
			}
		}
	}
	
	// Fonction utiliser pour affichier dans les noeuds du tree des espaces ? la place des _ 
	public static String remplacementCaractere(String chaine, String caractere, String remplacement)
	{
		//permet de remplacer les symboles caractere dans une chaine par les symboles remplacement
		int positionFin;
		int longueurCaractere=caractere.length();
		String resultat="";
		
		while( (positionFin=chaine.indexOf(caractere))!=(-1))
		{
			resultat=resultat+chaine.substring(0,positionFin)+remplacement;
			chaine=chaine.substring(positionFin+longueurCaractere);
		}
		resultat=resultat+chaine;
		return resultat;
	}
	
	// Fonction utilisee par tree pour afficher le contenu de la page d'aide
	public static String contenuFichier(String chemin) {
		try {
			StringBuffer contenu = new StringBuffer();
			File file = new File(chemin);
			FileReader f = new FileReader(chemin);
			
			long longueur = file.length();
			int dejaLu = 0;
			char car=0;
			while(dejaLu < longueur) {
				car = (char) f.read();
				dejaLu++;
				contenu.append(car);
			}
			return contenu.toString();
		}
		catch (IOException e) {
			System.out.println("erreur :" + e.toString());
			return null;	
		}
	}
	
}
