/*
 * Created on 7 mars 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ihm.vues.grapheCharges;


import org.eclipse.swt.graphics.Rectangle;

import donnees.eugesSpem.EugesActRealise;

/**
 * @author Nicolas
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ActiviteGraphique {
	
	private EugesActRealise _eugesActRealisee;
	
	private Rectangle _rectangle;
	
	private int [] _dessus;
	
	private int [] _cote;
	
	public ActiviteGraphique(EugesActRealise act, Rectangle rect, int [] des, int [] cot) {
		_eugesActRealisee = act;
		_rectangle = rect;
		_dessus = des;
		_cote = cot;
	}
	
	public boolean inRect(int x,int y) {
		if (_rectangle.contains(x,y))
			return true;
		else if ((x>_dessus[0])&&(x<_dessus[6])&&(x>_dessus[2])&&(x<_dessus[4])&&(y<_dessus[1])&&(y>_dessus[3])&&(y<_dessus[7])&&(y>_dessus[5]))
			return true;
		else if ((x>_cote[0])&&(x<_cote[6])&&(x>_cote[2])&&(x<_cote[4])&&(y<_cote[1])&&(y>_cote[3])&&(y<_cote[7])&&(y>_cote[5]))
			return true;
		else return false;
	
	}
	
	public EugesActRealise getActiviteRealise() {
		return _eugesActRealisee;
	}

}
