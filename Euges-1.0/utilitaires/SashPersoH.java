/*
 * Created on 21 déc. 2003
 *
 * SashForm Horizontal
 * 
 * +-------------------------------------+
 * |                                     |
 * |                                     |
 * |                                     |
 * |                                     |
 * |                top                  |
 * |                                     |
 * |                                     |
 * |                                     |
 * |-------------------------------------|
 * |               s a s h               |
 * |-------------------------------------|
 * |                                     |
 * |               bottom                |
 * |                                     |
 * +-------------------------------------+
 */
package utilitaires;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;

/**
 * @author Mathieu GAYRAUD
 *
 */
public class SashPersoH extends Composite {

	private int widthFirstDefault;
	private Composite top;
	private Composite bottom;

	/**
	 * @param parent Composite à l'intérieur duquel le SashPerso sera placé
	 * @param widthFirst Taille en pourcents de la largeur du premier volet
	 */
	public SashPersoH(Composite parent, int widthFirst) {
		super(parent, SWT.NONE);
		
		widthFirstDefault = widthFirst;
		
		top = new Composite (this, SWT.NONE);
		
		Label plus = new Label(this,SWT.NONE);
		plus.setImage(new Image(getDisplay(),"configuration/images/iconesShash/sashPersoH_plus.jpg"));
		plus.setToolTipText("Hide top frame");
		Label defaut = new Label(this,SWT.NONE);
		defaut.setImage(new Image(getDisplay(),"configuration/images/iconesShash/sashPersoH_default.jpg"));
		defaut.setToolTipText("Default");
		Label minus = new Label(this,SWT.BORDER);
		minus.setImage(new Image(getDisplay(),"configuration/images/iconesShash/sashPersoH_minus.jpg"));
		minus.setToolTipText("Hide bottom frame");

		final Sash sash = new Sash (this, SWT.HORIZONTAL|SWT.BORDER);
		bottom = new Composite (this, SWT.NONE);
		
		final FormLayout form = new FormLayout ();
		this.setLayout (form);

		final int limit = 0, percent = widthFirst;

		FormData topData = new FormData ();
		topData.left = new FormAttachment (0, 0);
		topData.right = new FormAttachment (100, 0);
		topData.top = new FormAttachment (0, 0);
		topData.bottom = new FormAttachment (sash, 0);
		top.setLayoutData (topData);
		
		final FormData plusData = new FormData();
		plusData.top = new FormAttachment (percent, 0);
		plusData.left = new FormAttachment(0,0);
		plus.setLayoutData(plusData);
		
		final FormData defautData = new FormData();
		defautData.top = new FormAttachment (percent, 0);
		defautData.left = new FormAttachment(plus,0);
		defaut.setLayoutData(defautData);
		
		final FormData minusData = new FormData();
		minusData.top = new FormAttachment (percent, 0);
		minusData.left = new FormAttachment(defaut,0);
		minus.setLayoutData(minusData);
		
		final FormData sashData = new FormData ();
		sashData.top = new FormAttachment (percent, 0);
		sashData.left = new FormAttachment (minus, 0);
		sashData.right = new FormAttachment (100, 0);
		sash.setLayoutData (sashData);

		sash.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				Rectangle sashRect = sash.getBounds ();
				Rectangle thisRect = getClientArea ();
				int bottom = thisRect.height - sashRect.height - limit;
				e.y = Math.max (Math.min (e.y, bottom), limit);
				if (e.y != sashRect.y)  {
					plusData.top = new FormAttachment (0, e.y);
					defautData.top = new FormAttachment (0, e.y);
					minusData.top = new FormAttachment (0, e.y);
					sashData.top = new FormAttachment (0, e.y);
					layout ();
				}
			}
		});
		plus.addMouseListener(new MouseAdapter() {
			public void mouseDown (MouseEvent e) {
				plusData.top = new FormAttachment (0, 0);
				defautData.top = new FormAttachment (0, 0);
				minusData.top = new FormAttachment (0, 0);
				sashData.top = new FormAttachment (0, 0);
				layout();
			}
			public void mouseUp (MouseEvent e) {
				
			}
		});
		defaut.addMouseListener(new MouseAdapter() {
			public void mouseDown (MouseEvent e) {
				int largeur = getBounds().height;
				int temp = largeur * widthFirstDefault / 100;
				plusData.top = new FormAttachment (0, temp);
				defautData.top = new FormAttachment (0, temp);
				minusData.top = new FormAttachment (0, temp);
				sashData.top = new FormAttachment (0, temp);
				layout ();
			}
			public void mouseUp (MouseEvent e) {
				
			}
		});
		minus.addMouseListener(new MouseAdapter() {
			public void mouseDown (MouseEvent e) {
				int hauteur = getBounds().height - sash.getBounds().height;
				plusData.top = new FormAttachment (0, hauteur);
				defautData.top = new FormAttachment (0, hauteur);
				minusData.top = new FormAttachment (0, hauteur);
				sashData.top = new FormAttachment (0, hauteur);
				layout();
			}
			public void mouseUp (MouseEvent e) {
				
			}
		});
		
		FormData bottomData = new FormData ();
		bottomData.left = new FormAttachment (0, 0);
		bottomData.right = new FormAttachment (100, 0);
		bottomData.top = new FormAttachment (sash, 0);
		bottomData.bottom = new FormAttachment (100, 0);
		bottom.setLayoutData (bottomData);

		// listener sur le parent (en cas de redimenssionnement)
		getParent().addListener(SWT.Resize, new Listener () {
			public void handleEvent(Event e) {
				setBounds(getParent().getClientArea());
			}
		});
		
		plus.setSize(sash.getSize().y,sash.getSize().y * 2);
		minus.setSize(sash.getSize().y,sash.getSize().y * 2);
		
}
	
	/**
	 * @return Returns the top composite.
	 */
	public Composite getTop() {
		return top;
	}

	/**
	 * @return Returns the bottom composite.
	 */
	public Composite getBottom() {
		return bottom;
	}
}
