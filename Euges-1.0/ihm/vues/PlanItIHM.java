/*
 * Created on 15 janv. 2004
 *
 */
package ihm.vues;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import utilitaires.GestionImage;
import configuration.Config;

/**
 * @author Mathieu GAYRAUD
 *
 */
public class PlanItIHM extends Composite {

	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);

	private static CTabFolder _tabFolder;
	private static CTabItem[] _tabItems;

	/**
	 * @param parent
	 * @param nbIt
	 */
	public PlanItIHM(final Composite parent, int nbIt) {
		super(parent, SWT.BORDER);
		_tabFolder = new CTabFolder(this,SWT.NONE);
		_tabFolder.setSelectionBackground(new Color[] {
			parent.getDisplay().getSystemColor(SWT.COLOR_GRAY), 
			parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND),
			parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND)
			}, new int[] {77, 100}
		);
		_tabItems = new CTabItem[nbIt];
		ItIHM temp;
		for (int i=0; i<nbIt; i++) {
			_tabItems[i] = new CTabItem(_tabFolder,SWT.NONE);
			_tabItems[i].setText(message.getString("PlanItIHM.iteration") + i);
			_tabItems[i].setToolTipText(message.getString("PlanItIHM.iteration") + i);
			_tabItems[i].setImage(GestionImage._it);
			_tabItems[i].setDisabledImage(GestionImage._it_disabled);
			temp = new ItIHM(_tabItems[i].getParent(),i);
			_tabItems[i].setControl(temp);
		}
		_tabFolder.setSelection(0);
		
		addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				_tabFolder.setBounds(getClientArea());
			}
		});
	}
	
	public void majIt(int nbIt) {
		// on dispose les anciens tabItem
		for (int i = 0; i < _tabItems.length; i++) {
			_tabItems[i].dispose();
		}
		
		// on crée les nouveaux
		ItIHM temp;
		_tabItems = new CTabItem[nbIt];
		for (int i=0; i<nbIt; i++) {
			_tabItems[i] = new CTabItem(_tabFolder,SWT.NONE);
			_tabItems[i].setText(message.getString("PlanItIHM.iteration") + i);
			_tabItems[i].setToolTipText(message.getString("PlanItIHM.iteration") + i);
			_tabItems[i].setImage(GestionImage._it);
			_tabItems[i].setDisabledImage(GestionImage._it_disabled);
			temp = new ItIHM(_tabItems[i].getParent(),i);
			_tabItems[i].setControl(temp);
		}
		_tabFolder.setSelection(0);
	}

	public static void majContenuWidgets() {
		for (int i = 0; i < _tabItems.length; i++) {
			ItIHM it = (ItIHM) _tabItems[i].getControl();
			it.majAct();
			it.majProd();
		}
	}
}
