/*
 * Created on 27 nov. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import configuration.Config;
/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PageAccueilIHM  extends PageAssistantIHM{
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);
	
	//definition des boutons activés pour l'assistant
	final boolean[] tab={false,false,true,false,true};
	
	public PageAccueilIHM(final Shell shell) {
			//appel du constructeur de la classe composite
			  super(shell);
			  
			  //mise en place du layout
			  GridLayout gridLayout = new GridLayout();
			  gridLayout.numColumns=1;
			  this.setLayout(gridLayout);
					
			  //titre		
			  Font font = new Font(getDisplay(), "Arial", 15, 15);
			  Label titre = new Label(this, SWT.NONE);
			  titre.setFont(font);
			  titre.setText(message.getString("pageAccueilIHM.titre"));
			  titre.pack();
			  //champ texte
			  Text presentation = new Text(this, SWT.BORDER | SWT.WRAP | SWT.MULTI);
			  presentation.setText(message.getString("pageAccueilIHM.texte"));
			  presentation.setEnabled(false);
			  //titre
			  GridData gridData1 = new GridData();
			  titre.setLayoutData(gridData1);
			  //champ texte
			  gridData1 = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
			  presentation.setLayoutData(gridData1);

			  //activation des boutons
			  set_activeBoutons(tab);
	}

	/* (non-Javadoc)
	 * @see ihm.PageAssistantIHM#verifDonnees()
	 */
	public boolean verifDonnees() {
		return true;
	}

	/* (non-Javadoc)
	 * @see ihm.PageAssistantIHM#loadData()
	 */
	public void loadData() {
		// TODO Auto-generated method stub
	}
}
