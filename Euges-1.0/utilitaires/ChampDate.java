/*
 * Created on 1 déc. 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package utilitaires;

import ihm.CalendrierIHM;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import configuration.Config;

/**
 * @author will
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ChampDate extends Composite implements SelectionListener{
	
	private ResourceBundle message = ResourceBundle.getBundle(Config.config.getProperty("cheminTraduction") + "." + Config.locale.getLanguage() + getClass().getName().substring(getClass().getName().lastIndexOf('.')), Config.locale);

	private Label _jour;
	private Label _mois;
	private Label _annee;
	private Button _calendrier;
	
	private Shell shellDate;
	
	public ChampDate(Composite compo){
		//construction du composite
		super(compo, SWT.NONE);
		//shell
		shellDate=compo.getShell();
		//jour debut
		_jour = new Label(this, SWT.NONE);
		_jour.setToolTipText(message.getString("ChampDate.jour"));
		_jour.setBounds(5, 5, 23,25);		
		//mois debut
		Label slash1 = new Label(this, SWT.NONE);
		slash1.setText("/");
		slash1.pack();
		slash1.setLocation(30,5);
		_mois = new Label(this, SWT.NONE);
		_mois.setToolTipText(message.getString("ChampDate.mois"));
		_mois.setBounds(41, 5, 23,25);
		//annee debut
		Label slash2 = new Label(this, SWT.NONE);
		slash2.setText("/");
		slash2.pack();
		slash2.setLocation(65,5);
		_annee = new Label(this, SWT.NONE);
		_annee.setToolTipText(message.getString("ChampDate.annee"));
		_annee.setBounds(76, 5, 40,25);
		
		_calendrier = new Button(this, SWT.FLAT);
		_calendrier.setImage(GestionImage._iconeCalendrier);
		_calendrier.pack();
		_calendrier.setLocation(120,0);
		_calendrier.addSelectionListener(this);

	}
	/*
	 * permet de verifier que les donnees saisis dans les champs textes sont des chiffres
	 */
	private void verifCaracteres(Text txt){
		txt.addListener (SWT.Verify, new Listener () {
				public void handleEvent (Event e) {
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
	}
	/*
	 * permet de mettre a jour les champs jour et mois si le chiffre est iferieur a 9 (ajout d'un 0 dans la case)
	 */
	private void completeDonnees(final Text txt){
		txt.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				if (txt.getText().length()==1 && !txt.getText().equals("0"))
					txt.setText("0"+txt.getText());
				//a rajouter si le jour et le mois sont egaux a zero !!!!!!
			}
		});
	}
	/*
	 * convertir un champ date dans le format mydate
	 */
	public MyDate toMyDate(){
		return (new MyDate(Integer.parseInt(_jour.getText()), Integer.parseInt(_mois.getText()), Integer.parseInt(_annee.getText())));
	}
	/*
	 * verifie si un champ est valide
	 */
	public boolean estValide(){
		return toMyDate().estValide();
	}
	/*
	 *  surcharge de la methode toString()
	 * @see java.lang.Object#toString()
	 * convertit un champ en chaine de caracteres 
	 */
	public String toString(){
		return(_jour.getText() + "/" + _mois.getText() + "/" +_annee.getText());
	}
	/**
	 * retourne si le champ est vide
	 */
	public boolean isEmpty(){
		if (_jour.getText().length()==0 || _mois.getText().length()==0 || _annee.getText().length()==0){
			return true;
		}
		return false;
	}
	/**
	 * efface le champ date
	 *
	 */
	public void clear(){
		_jour.setText("");
		_mois.setText("");
		_annee.setText("");
	}

	

	/**
	 * @return Returns the message.
	 */
	public ResourceBundle getMessage() {
		return message;
	}
	/**
	 * remplit le le champ date a partir d'un element my date
	 * si la valeur de la date est nulle, les champs sont grisés
	 * @param date date servant a remplir le champ date
	 */
	public void setChamps(MyDate date){
		if (date!=null){
			_jour.setText(new Integer(date.get_jour()).toString());
			//si le jour est < à 10 on rajoute un zero devant
			if (date.get_jour()<10)
				_jour.setText("0" + _jour.getText());
			_mois.setText(new Integer(date.get_mois()).toString());
			//si le mois est < à 10 on rajoute un zero devant 
			if (date.get_mois()<10)
				_mois.setText("0" + _mois.getText());
			_annee.setText(new Integer(date.get_annee()).toString());
		}else{
			_jour.setEnabled(false);
			_mois.setEnabled(false);
			_annee.setEnabled(false);
		}
	}
	/**
	 * 
	 * @param s
	 */
	public void remplirJour (String s)
	{
		System.out.println (" valeur de la chaine ds ChampDate "+s);
		//this._jour.setText(s);
		System.out.println (" valeur du text "+this._jour.getText());
	}
	/**
	 * 
	 * @param j
	 * @param m
	 * @param a
	 */
	public void setText (String j, String m, String a)
	{
		_jour.setText(j);
		_mois.setText(m);
		_annee.setText(a);
	}
	/**
	 * 
	 * @param b
	 */
	public void setEditable (boolean b)
	{
		/*_jour.setEditable(b);
		_mois.setEditable(b);
		_annee.setEditable(b);
		_jour.setEnabled(b);
		_mois.setEnabled(b);
		_annee.setEnabled(b);*/
		
	}
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		Button button = (Button)e.getSource();
		if (button==_calendrier){
			CalendrierIHM calendrier = new CalendrierIHM(shellDate, this);
		}
		
	}
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
