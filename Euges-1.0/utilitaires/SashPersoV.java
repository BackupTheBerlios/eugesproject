/*
 * Created on 21 déc. 2003
 *
 * SashForm Vertical
 * 
 * +---------+-+-------------------------+
 * |         | |                         |
 * |         | |                         |
 * |         |s|                         |
 * |         | |                         |
 * |         |a|                         |
 * |   left  | |          right          |
 * |         |s|                         |
 * |         | |                         |
 * |         |h|                         |
 * |         | |                         |
 * |         | |                         |
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
public class SashPersoV extends Composite {

	private int widthFirstDefault;
	private Composite left;
	private Composite right;
	
	/**
	 * @param parent Composite à l'intérieur duquel le SashPerso sera placé
	 * @param widthFirst Taille en pourcents de la largeur du premier volet
	 */
	public SashPersoV(Composite parent, int widthFirst) {
		super(parent, SWT.NONE);
		
		widthFirstDefault = widthFirst;
		
		left = new Composite (this, SWT.NONE);

		Label plus = new Label(this,SWT.NONE);
		plus.setImage(new Image(getDisplay(),"configuration/images/iconesShash/sashPersoV_plus.jpg"));
		plus.setToolTipText("Hide right frame");
		Label defaut = new Label(this,SWT.NONE);
		defaut.setImage(new Image(getDisplay(),"configuration/images/iconesShash/sashPersoV_default.jpg"));
		defaut.setToolTipText("Default");
		Label minus = new Label(this,SWT.BORDER);
		minus.setImage(new Image(getDisplay(),"configuration/images/iconesShash/sashPersoV_minus.jpg"));
		minus.setToolTipText("Hide left frame");

		final Sash sash = new Sash (this, SWT.VERTICAL|SWT.BORDER);
		right = new Composite (this, SWT.NONE);
		
		final FormLayout form = new FormLayout ();
		this.setLayout (form);

		final int limit = 0, percent = widthFirst;

		FormData leftData = new FormData ();
		leftData.left = new FormAttachment (0, 0);
		leftData.right = new FormAttachment (sash, 0);
		leftData.top = new FormAttachment (0, 0);
		leftData.bottom = new FormAttachment (100, 0);
		left.setLayoutData (leftData);
		
		final FormData plusData = new FormData();
		plusData.left = new FormAttachment (percent, 0);
		plusData.top = new FormAttachment(0,0);
		plus.setLayoutData(plusData);
		
		final FormData defautData = new FormData();
		defautData.left = new FormAttachment (percent, 0);
		defautData.top = new FormAttachment(plus,0);
		defaut.setLayoutData(defautData);
		
		final FormData minusData = new FormData();
		minusData.left = new FormAttachment (percent, 0);
		minusData.top = new FormAttachment(defaut,0);
		minus.setLayoutData(minusData);
		
		final FormData sashData = new FormData ();
		sashData.left = new FormAttachment (percent, 0);
		sashData.top = new FormAttachment (minus, 0);
		sashData.bottom = new FormAttachment (100, 0);
		sash.setLayoutData (sashData);

		sash.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				Rectangle sashRect = sash.getBounds ();
				Rectangle thisRect = getClientArea ();
				int right = thisRect.width - sashRect.width - limit;
				e.x = Math.max (Math.min (e.x, right), limit);
				if (e.x != sashRect.x)  {
					plusData.left = new FormAttachment (0, e.x);
					defautData.left = new FormAttachment (0, e.x);
					minusData.left = new FormAttachment (0, e.x);
					sashData.left = new FormAttachment (0, e.x);
					layout ();
				}
			}
		});
		plus.addMouseListener(new MouseAdapter() {
			public void mouseDown (MouseEvent e) {
				int largeur = getBounds().width - sash.getBounds().width;

				plusData.left = new FormAttachment (0, largeur);
				defautData.left = new FormAttachment (0, largeur);
				minusData.left = new FormAttachment (0, largeur);
				sashData.left = new FormAttachment (0, largeur);
				layout();
			}
			public void mouseUp (MouseEvent e) {
				
			}
		});
		defaut.addMouseListener(new MouseAdapter() {
			public void mouseDown (MouseEvent e) {
				int largeur = getBounds().width;
				int temp = largeur * widthFirstDefault / 100;

				plusData.left = new FormAttachment (0, temp);
				defautData.left = new FormAttachment (0, temp);
				minusData.left = new FormAttachment (0, temp);
				sashData.left = new FormAttachment (0, temp);
				layout ();
			}
			public void mouseUp (MouseEvent e) {
				
			}
		});
		minus.addMouseListener(new MouseAdapter() {
			public void mouseDown (MouseEvent e) {
				plusData.left = new FormAttachment (0, 0);
				defautData.left = new FormAttachment (0, 0);
				minusData.left = new FormAttachment (0, 0);
				sashData.left = new FormAttachment (0, 0);
				layout();
			}
			public void mouseUp (MouseEvent e) {
				
			}
		});
		
		FormData rightData = new FormData ();
		rightData.left = new FormAttachment (sash, 0);
		rightData.right = new FormAttachment (100, 0);
		rightData.top = new FormAttachment (0, 0);
		rightData.bottom = new FormAttachment (100, 0);
		right.setLayoutData (rightData);

		// listener sur le parent (en cas de redimenssionnement)
		getParent().addListener(SWT.Resize, new Listener () {
			public void handleEvent(Event e) {
				setBounds(getParent().getClientArea());
			}
		});
	}
	
	/**
	 * @return Returns the left.
	 */
	public Composite getLeft() {
		return left;
	}

	/**
	 * @return Returns the right.
	 */
	public Composite getRight() {
		return right;
	}
}
