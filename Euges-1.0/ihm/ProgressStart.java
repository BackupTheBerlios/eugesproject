/*
 * Created on 28 janv. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import utilitaires.GestionImage;

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ProgressStart{
//label content le texte a afficher
	private Label _chargement;
	private ProgressBar bar;
	private Shell shell;
	final private Display display;
	/**
	 * fenetre de progression pour le lancement du logiciel
	 *
	 */
	public ProgressStart(Display d, boolean demarrage){
		display=d;
		shell = new Shell(display, SWT.ON_TOP);
		GestionImage gestionImage = new GestionImage(display);
		shell.setBounds(GestionImage._start.getBounds());
		//barre de progresssion
		/*bar = new ProgressBar (shell, SWT.SMOOTH);
		bar.setBounds(45,115,300,10);
		bar.setMinimum(0);
		bar.setMaximum(100);
		bar.setSelection(0);*/
		
		//initialisation
		_chargement = new Label(shell, SWT.NONE);
		_chargement.setLocation(10, 200);
		
		// ouvrir la fenêtre au centre de l'écran
		Monitor primary = display.getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = shell.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation (x, y);
		shell.open();
		shell.addPaintListener(new PaintListener(){
			public void paintControl(PaintEvent e) {
				//ajout de l'image
				GC gc = new GC(shell);
				gc.drawImage(GestionImage._start, 0, 0);
			}
			
		});
		if (demarrage==false){
			shell.addMouseListener(new MouseAdapter(){
				/* (non-Javadoc)
				 * @see org.eclipse.swt.events.MouseAdapter#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
				 */
				public void mouseDoubleClick(MouseEvent e) {
					shell.dispose();
				}
			});
			shell.setToolTipText("Double click");
			String listeNoms = "                                           Logiciel développé par : Mathieu Gayraud - Nicolas Broueilh - Nicolas Elbeze - William Ferreira - Nicolas Giroire - Bruno Chauvet - Cyril Gerla - Julien Hays - Ludovic Pradel - Nicolas Teulier";
			Text auteurs = new Text(shell, SWT.BORDER);
			auteurs.setSize(200,20);
			auteurs.setLocation(10,200);
			defilerNom(auteurs, listeNoms, 100);
		}
	}
	/**
	 * ferme la fenetre de progression
	 *
	 */
	public void dispose(){
		shell.dispose();
	}
	/**
	 * thread permettant de faire progresser la barre de progression
	 * @param display display de l'application
	 * @param bar barre de progression
	 * @param valeur valeur a ateindre
	 */
	public void progression(final int valeur, String texte){
		//affichage du texte
		_chargement.setText(texte);
		_chargement.pack();
		//thread d'attente avant la fermeture de la fenetre
		if (valeur==100){
			new Thread () {
				public void run () {
						try {Thread.sleep (1500);} catch (Throwable th) {}
						display.asyncExec (new Runnable () {
							public void run () {
								if (shell.isDisposed ()) return;
								dispose();
							}
						});
				}
			}.start ();
		}
	}
	/**
	 * thread permettant de faire defiler un texte dans un champ texte de la doite vers la gauche
	 * @param champ
	 * @param texte
	 */
	public void defilerNom(final Text champ, final String texte, final int vitesse){
		new Thread () {
			int i=0, j=35;
			public void run () {
				while(!shell.isDisposed()){
					try {Thread.sleep (vitesse);} catch (Throwable th) {}
					display.asyncExec (new Runnable () {
						public void run () {
							if (shell.isDisposed ()) return;
							champ.setText(texte.substring(i, j));
							if (j<=texte.length()-1)
								j++;
							if (i<j)
								i++;
							if (i==j){
								i=0; j=35;
							}
						}
					});
				}
			}
		}.start ();
	}
}
