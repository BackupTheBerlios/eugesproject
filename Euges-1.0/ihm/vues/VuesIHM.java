/*
 * Created on 17 févr. 2004
 *
 */
package ihm.vues;

import ihm.vues.grapheActivites.GrapheIHM;
import ihm.vues.grapheCharges.GrapheChargesIHM;
import ihm.vues.planIt.PlanItIHM;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;

/**
 * @author will
 *
 */
public class VuesIHM extends ViewForm{
	//nombre de pages
	private int nombrePages=3;
	//vecteur de vues
	private PageVuesIHM[] _vues;
	private int pageCourante=0;
	
	/**
	 * @param parent
	 */
	public VuesIHM(final SashForm parent) {
		super(parent, SWT.BORDER);
		//tableau contenant les vues
		_vues=new PageVuesIHM[nombrePages];
		//ajout des vues dans la liste des vues
		_vues[0]=new PlanItIHM(this, 0);
		_vues[1]=new GrapheIHM(this);
		_vues[2]=new GrapheChargesIHM(this);
	}
	
	/**
	 * affiche la page dont le numero est passé en parametre
	 * @param numPage numero de la page a afficher
	 */ 
	public void setVisible(int numPage){
		_vues[numPage].loadData();
		//on place la nouvelle vue au premier plan
		_vues[numPage].moveAbove(_vues[pageCourante]);
		
		pageCourante=numPage;
	}
	
	/**
	 * @param i indice du composite a retourner
	 * @return composite voulu
	 */
	public  PageVuesIHM elementAt(int i){
		return _vues[i];
	}
	
	/**
	 * recharge les pages
	 *
	 */
	public void reloadPages(){
		for(int i=0; i<_vues.length-1; i++){
			elementAt(i).loadData();
			elementAt(i).setVisible(false);
		}
	}
}