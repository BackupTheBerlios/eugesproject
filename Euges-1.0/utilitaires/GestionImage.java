/*
 * Created on 24 janv. 2004
 *
 */
package utilitaires;

import java.io.IOException;
import java.net.URL;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * @author Mathieu GAYRAUD
 *
 */
public class GestionImage {
	public static Image _activite;
	public static Image _actor;
	public static Image _eclipse;
	public static Image _euges;
	public static Image _eugesAssistant;
	public static Image _it;
	public static Image _it_disabled;
	public static Image _produit;
	public static Image _role;
	public static Image _aideBar;
	public static Image _enregistrerBar;
	public static Image _fermerBar;
	public static Image _mailBar;
	public static Image _nouveauBar;
	public static Image _ouvrirBar;
	public static Image _afficherAide;
	public static Image _masquerAide;
	public static Image _precedentAide;
	public static Image _suivantAide;
	public static Image _plus;
	public static Image _moins;
	public static Image _start;
	public static Image _triRefresh;
	public static Image _triActivite;
	public static Image _triDefaut;
	public static Image _triIteration;
	public static Image _triRole;
	public static Image _iconeCalendrier;
	public static Image _navBack;
	public static Image _navForward;
	public static Image _navRefresh;
	public static Image _navStop;
	public static Image _navGo;
	
	public static Image _toolTabIt;
	public static Image _toolGraphIt;
	public static Image _toolGraphLine;
	private URL url;
	
	private String _basePath = "/configuration/images/";
	
	public GestionImage(Display display) {
		try {
			url = getClass().getResource(_basePath + "activite.gif");
			_activite = new Image(display,url.openStream());

			url = getClass().getResource(_basePath + "actor.gif");
			_actor = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "eclipse.png");
			_eclipse = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "euges.ico");
			_euges = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "eugesAssistant.png");
			_eugesAssistant = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "it.gif");
			_it = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "it_disabled.gif");
			_it_disabled = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "iconesAide/afficher.ico");
			_afficherAide = new Image(display,url.openStream());
			
//			url = getClass().getResource(_basePath + "icones/enregistrer.ico");
//			_enregistrerBar = new Image(display,url.openStream());
//			
//			url = getClass().getResource(_basePath + "icones/fermer.ico");
//			_fermerBar = new Image(display,url.openStream());
//			
//			url = getClass().getResource(_basePath + "icones/nouveau.ico");
//			_nouveauBar = new Image(display,url.openStream());
//			
//			url = getClass().getResource(_basePath + "icones/ouvrir.ico");
//			_ouvrirBar = new Image(display,url.openStream());
//			
//			url = getClass().getResource(_basePath + "icones/mail.ico");
//			_mailBar = new Image(display,url.openStream());
//			
//			url = getClass().getResource(_basePath + "icones/aide.ico");
//			_aideBar = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "icones/tri_refresh.ico");
			_triRefresh = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "icones/tri_activite.ico");
			_triActivite = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "icones/tri_defaut.ico");
			_triDefaut = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "icones/tri_iteration.ico");
			_triIteration = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "icones/tri_role.ico");
			_triRole = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "iconesAide/masquer.ico");
			_masquerAide = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "iconesAide/précédent.ico");
			_precedentAide = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "produit.gif");
			_produit = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "role.gif");
			_role = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "iconesAide/suivant.ico");
			_suivantAide = new Image(display,url.openStream());

			url = getClass().getResource(_basePath + "plus.gif");
			_plus = new Image(display,url.openStream());

			url = getClass().getResource(_basePath + "moins.gif");
			_moins = new Image(display,url.openStream());
			
			//image popup pour le demarrage
			url = getClass().getResource(_basePath + "start.jpg");
			_start = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "calendrier.png");
			_iconeCalendrier = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "navigatorGif/back.gif");
			_navBack = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "navigatorGif/forward.gif");
			_navForward = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "navigatorGif/go.gif");
			_navGo = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "navigatorGif/refresh.gif");
			_navRefresh = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "navigatorGif/stop.gif");
			_navStop = new Image(display,url.openStream());
	
			url = getClass().getResource(_basePath + "icones/tabIt.png");
			_toolTabIt = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "icones/graphIt.png");
			_toolGraphIt = new Image(display,url.openStream());
			
			url = getClass().getResource(_basePath + "icones/graphLine.png");
			_toolGraphLine = new Image(display,url.openStream());
			
		} catch (IOException e) {
			System.out.println("Erreur au chargement d'une image");
			e.printStackTrace();
		}
	}
}
