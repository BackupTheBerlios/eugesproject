/*
 * Created on 15 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package utilitaires;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import application.EugesElements;
import donnees.Iteration;
import donnees.eugesSpem.EugesActRealise;
import donnees.eugesSpem.EugesActivite;
import donnees.eugesSpem.EugesPersonne;
import donnees.eugesSpem.EugesProduit;
import donnees.eugesSpem.EugesRole;
import donnees.eugesSpem.EugesVersion;


/**
 * @author HUT
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

public class MySAXApp extends DefaultHandler{

	private static int creationAssociation = 0;
	private EugesElements eugesElements;
	private int numIt = 0;
	private int nbPersAct = 0;
	private int nbActIt = 0;
	private int nbProOutAct = 0;
	private int nbProInAct = 0;
	private int nbProAct = 0;
	private int nbVerPro = 0;
	private String personne = new String();
	private String produit = new String();
	private String activite = new String();
    // Variable qui vaut 1 si on ajoute un rôle a une activité, et 0 si on ajoute un rôle à une personne
	private int ajoutRoleAct = 1;
	
	/**
	 * Constructeur par defaut. 
	 */
	public MySAXApp() {
		super();
	}

	/**
	 * Definition du locator qui permet a tout moment pendant l'analyse, de localiser
	 * le traitement dans le flux. Le locator par defaut indique, par exemple, le numero
	 * de ligne et le numero de caractere sur la ligne.
	 * @param value le locator a utiliser.
	 * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
	 */
	public void setDocumentLocator(Locator value) {
		locator =  value;
	}

	/**
	 * Evenement envoye au demarrage du parse du flux xml.
	 * @throws SAXException en cas de probleme quelquonque ne permettant pas de
	 * se lancer dans l'analyse du document.
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	public void startDocument() throws SAXException {
		//System.out.println("Debut de l'analyse du document");
	}

	/**
	 * Evenement envoye a la fin de l'analyse du flux xml.
	 * @throws SAXException en cas de probleme quelquonque ne permettant pas de
	 * considerer l'analyse du document comme etant complete.
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	public void endDocument() throws SAXException {
		//System.out.println("Fin de l'analyse du document" );
		creationAssociation = 0;
		numIt = 0;
		nbPersAct = 0;
		nbActIt = 0;
		nbProOutAct = 0;
		nbProInAct = 0;
		nbProAct = 0;
		nbVerPro = 0;
		personne = "";
		produit = "";
		activite = "";
		ajoutRoleAct = 1;
	}

	/**
	 * Debut de traitement dans un espace de nommage.
	 * @param prefixe utilise pour cet espace de nommage dans cette partie de l'arborescence.
	 * @param URI de l'espace de nommage.
	 * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
	 */
	public void startPrefixMapping(String prefix, String URI) throws SAXException {
		//System.out.println("Traitement de l'espace de nommage : " + URI + ", prefixe choisi : " + prefix);
	}

	/**
	 * Fin de traitement de l'espace de nommage.
	 * @param prefixe le prefixe choisi a l'ouverture du traitement de l'espace nommage.
	 * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
	 */
	public void endPrefixMapping(String prefix) throws SAXException {
		//System.out.println("Fin de traitement de l'espace de nommage : " + prefix);
	}

	/**
	 * Evenement recu a chaque fois que l'analyseur rencontre une balise xml ouvrante.
	 * @param nameSpaceURI l'url de l'espace de nommage.
	 * @param localName le nom local de la balise.
	 * @param rawName nom de la balise en version 1.0 <code>nameSpaceURI + ":" + localName</code>
	 * @throws SAXException si la balise ne correspond pas a ce qui est attendu,
	 * comme par exemple non respect d'une dtd.
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) throws SAXException {  
		
		//System.out.println(localName);
	  
	  if(creationAssociation == 0)
	  {
		// Sauvegarde d'un nouveau projet
		if(localName.equals("Projet"))
		{
			String nomProjet = new String(attributs.getValue(0));
			String repDestination = new String(attributs.getValue(3));
			String processus = new String(attributs.getValue(4));
			String description = new String(attributs.getValue(5));
			MyDate dateDebut = new MyDate(attributs.getValue(1));
			MyDate dateFin = new MyDate(attributs.getValue(2));
			/*EugesElements._projet.set_nomProjet(nomProjet);
			EugesElements._projet.set_dateDebut(dateDebut);
			EugesElements._projet.set_dateFin(dateFin);
			EugesElements._projet.set_description(description);
			EugesElements._projet.set_processus(processus);
			EugesElements._projet.set_repDestination(repDestination);*/
			eugesElements = new EugesElements(nomProjet, dateDebut, dateFin, repDestination,processus,description);
		}
		
		// Ajout d'un role dans le vecteur
		if(localName.equals("EugesRole"))
		{
			EugesElements.ajouterElement(new EugesRole(attributs.getValue(1)));
		}
		
		// Ajout d'une personne dans le vecteur
		if(localName.equals("EugesPersonne"))
		{
			int id = Integer.parseInt(attributs.getValue(0));
			EugesElements.ajouterElement(new EugesPersonne(id, attributs.getValue(1), attributs.getValue(2), attributs.getValue(3)));
		}
		
		// Ajout d'un produit dans le vecteur
		if(localName.equals("EugesProduit"))
		{
			EugesElements.ajouterElement(new EugesProduit(attributs.getValue(0)));
		}
		
		// Ajout d'une activité dans le vecteur
		if(localName.equals("EugesActivite"))
		{
			EugesElements.ajouterElement(new EugesActivite(attributs.getValue(0)));
		}
		
		// Ajout d'une itération dans le vecteur
		if(localName.equals("Iteration"))
		{
			MyDate dateDebut = new MyDate(attributs.getValue(1));
			MyDate dateFin = new MyDate(attributs.getValue(2));
			EugesElements._projet.ajouterIteration(EugesElements._projet.getIteration(Integer.parseInt(attributs.getValue(0))), dateFin);
			Iteration it = EugesElements._projet.getIteration(Integer.parseInt(attributs.getValue(0))+1);
			if(it.get_dateFin().compare(it.get_dateDebut()) == 0)
				EugesElements._projet.supprimerIteration(it);
		}
	  }
	  
		// Ajout des assoications
	  if(localName.equals("_listeAssociations")) creationAssociation++;
	  if(creationAssociation == 1)
	  {
	  	// Creation d'une nouvelle itération
	  	if(localName.equals("Iteration"))
	  	{
	  		numIt = Integer.parseInt(attributs.getValue(0));
	  	}
	  	
	  	// Ajout des rôles d'une personne
	  	if(localName.equals("EugesPersonne"))
	  	{
	  		personne = attributs.getValue(0);
	  		ajoutRoleAct = 0;
	  	}
	  	if(localName.equals("EugesRole"))
	  	{
	  		// ajout du role à la personne dans l'activité de l'IT
	  		//EugesElements._projet.getIteration(numIt).ajouterAssociation(EugesElements._projet.getIteration(numIt).getActivite(nbActIt -1).getPersonne(nbPersAct -1), EugesElements.getRole(attributs.getValue(1)));
	  		// ajout du role à la personne dans l'IT
	  		EugesElements._projet.getIteration(numIt).ajouterAssociation(EugesElements.getPersonneDansListePersonnes(personne), EugesElements.getRole(attributs.getValue(1)));
	  		
	  	}
	  	
	  	// Ajout d'une activité dans l'itération
	  	if(localName.equals("EugesActRealise"))
	  	{
	  		EugesActRealise eugesActRealise = new EugesActRealise(EugesElements.getActivite(attributs.getValue(2)));
	  		eugesActRealise.set_chargeEstimee(Integer.parseInt(attributs.getValue(0)));
	  		eugesActRealise.set_chargeReelle(Integer.parseInt(attributs.getValue(1)));
	  		eugesActRealise.setIt(numIt);
	  		EugesElements._projet.getIteration(numIt).ajouterActivite(eugesActRealise);
			nbActIt = EugesElements._projet.getIteration(numIt).getActiviteCount();
			activite = attributs.getValue(2);
			nbProInAct = 0;
			nbProOutAct = 0;
			ajoutRoleAct = 1;
			EugesElements.getActivite(activite).ajouterActRealise(eugesActRealise);
	  	}
	  	
	  	// Ajout d'une personne a la dernière activité enregistrée de l'itération
	  	if(localName.equals("_personne"))
	  	{
			personne = attributs.getValue(0);
	  		EugesElements._projet.getIteration(numIt).getActivite(nbActIt -1).ajouterPersonne(EugesElements.getPersonneDansListePersonnes(personne));
	  		nbPersAct = EugesElements._projet.getIteration(numIt).getActivite(nbActIt -1).getPersonneCount();
	  		ajoutRoleAct = 0;
		}
	  	
	  	// Ajout d'un rôle à une personne de l'activité
	  	if(localName.equals("_roles"))
	  	{
	  		// Si on ajoute un role a une activité
	  		if(ajoutRoleAct == 1)
	  		{
	  			// lien entre l'activité et le rôle dans le vecteur activite  
	  			EugesElements.getActivite(activite).setRole(EugesElements.getRole(attributs.getValue(0)));

	  			// ajout du role dans l'activité réalisé de l'IT
	  			EugesElements._projet.getIteration(numIt).getActivite(nbActIt -1).get_activiteParent().setRole(EugesElements.getRole(attributs.getValue(0)));
	  		}
	  		// Si on ajoute un role à une personne
	  		else {
	  			// ajout du role à la personne dans l'activité réalisée
	  			//EugesElements._projet.getIteration(numIt).getActivite(nbActIt -1).getPersonne(nbPersAct -1).ajouterRole(EugesElements.getRole(attributs.getValue(0)));
	  			EugesElements._projet.getIteration(numIt).ajouterAssociation(EugesElements._projet.getIteration(numIt).getActivite(nbActIt -1).getPersonne(nbPersAct -1), EugesElements.getRole(attributs.getValue(0)));
	  			
	  			// ajout du rôle à la personne dans le vecteur global
	  			//EugesElements.getPersonneDansListePersonnes(personne).ajouterRole(EugesElements.getRole(attributs.getValue(0)));	  		
	  		}
	  	}

		// On va ajouter un produit en entrée
	  	if(localName.equals("ProduitsIn"))
	  	{
			produit = "in";
			nbVerPro = 0;
			nbProInAct++;
	  	}

		// On va ajouter un produit en sortie
	  	if(localName.equals("ProduitsOut"))
	  	{
			produit = "out";
			nbVerPro = 0;
			nbProOutAct++;
	  	}

		// Ajout d'une version
	  	if(localName.equals("_version"))
	  	{
			String nom = new String(attributs.getValue(0));
			String etat = new String(attributs.getValue(1));
			int realisation = Integer.parseInt(attributs.getValue(2));
			EugesPersonne responsable = EugesElements.getPersonneDansListePersonnes(attributs.getValue(3));
			EugesProduit produitParent = EugesElements.getProduitDansListeProduits(attributs.getValue(4));
			
			// Ajout d'un produit en entrée
			EugesVersion ev = new EugesVersion(nom, etat, realisation, responsable, produitParent);
			
			// On ajoute la version dans le vecteur de produit
			EugesElements.getProduitDansListeProduits(attributs.getValue(4)).ajouterVersion(ev);

			// Ajout d'un produit en entrée
			if(produit == "in")
			{
				// ajout du produit en entrée de l'activité
				EugesElements._projet.getIteration(numIt).getActivite(nbActIt -1).ajouterProduitIn(ev);
				// et dans le vecteur d'activités
				EugesElements.getActivite(activite).ajouterProduitIn(EugesElements.getProduitDansListeProduits(attributs.getValue(4)));

				// Nombre de produits de l'activité et de versions du dernier produit
				nbProAct = EugesElements._projet.getIteration(numIt).getActivite(nbActIt -1).getProduitInCount();
			}

			// Ajout d'un produit en sortie			
			if(produit == "out")
			{
				// ajout du produit en sortie de l'activité
				EugesElements._projet.getIteration(numIt).getActivite(nbActIt -1).ajouterProduitOut(ev);
				// et dans le vecteur d'activités
				EugesElements.getActivite(activite).ajouterProduitOut(EugesElements.getProduitDansListeProduits(attributs.getValue(4)));
				
				// Nombre de produits de l'activité et de versions du dernier produit
				nbProAct = EugesElements._projet.getIteration(numIt).getActivite(nbActIt -1).getProduitOutCount();
			}
			nbVerPro++;
		}

		// Ajout d'un acteur à la version d'un produit
	  	if(localName.equals("_acteur"))
	  	{
	  		EugesPersonne ep = EugesElements.getPersonneDansListePersonnes(attributs.getValue(0));
	  		if(produit == "in")
	  		{
	  			EugesElements._projet.getIteration(numIt).getActivite(nbActIt -1).getProduitIn(nbProInAct -1).ajouterModifieur(ep);
	  		}
	  		if(produit == "out")
	  		{
	  			EugesElements._projet.getIteration(numIt).getActivite(nbActIt -1).getProduitOut(nbProOutAct -1).ajouterModifieur(ep);
	  		}
		}
	  }
	}

	/**
	 * Evenement recu a chaque fermeture de balise.
	 * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException {
		// Fin d'enregistrement des itérations : numérotation des IT
		if(localName.equals("_listeIteration")) EugesElements._projet.numeroterIteration();
	}

	/**
	 * Evenement recu a chaque fois que l'analyseur rencontre des caracteres (entre
	 * deux balises).
	 * @param ch les caracteres proprement dits.
	 * @param start le rang du premier caractere a traiter effectivement.
	 * @param end le rang du dernier caractere a traiter effectivement
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	public void characters(char[] ch, int start, int end) throws SAXException {
		//System.out.println("#PCDATA : " + new String(ch, start, end));
	}

	/**
	 * Recu chaque fois que des caracteres d'espacement peuvent etre ignores au sens de
	 * XML. C'est a dire que cet evenement est envoye pour plusieurs espaces se succedant,
	 * les tabulations, et les retours chariot se succedants ainsi que toute combinaison de ces
	 * trois types d'occurrence.
	 * @param ch les caracteres proprement dits.
	 * @param start le rang du premier caractere a traiter effectivement.
	 * @param end le rang du dernier caractere a traiter effectivement
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	public void ignorableWhitespace(char[] ch, int start, int end) throws SAXException {
		//System.out.println("espaces inutiles rencontres : ..." + new String(ch, start, end) +  "...");
	}

	/**
	 * Rencontre une instruction de fonctionnement.
	 * @param target la cible de l'instruction de fonctionnement.
	 * @param data les valeurs associees a cette cible. En general, elle se presente sous la forme 
	 * d'une serie de paires nom/valeur.
	 * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
	 */
	public void processingInstruction(String target, String data) throws SAXException {
		//System.out.println("Instruction de fonctionnement : " + target);
		//System.out.println("  dont les arguments sont : " + data);
	}

	/**
	 * Recu a chaque fois qu'une balise est evitee dans le traitement a cause d'un
	 * probleme non bloque par le parser. Pour ma part je ne pense pas que vous
	 * en ayez besoin dans vos traitements.
	 * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
	 */
	public void skippedEntity(String arg0) throws SAXException {
		// Je ne fais rien, ce qui se passe n'est pas franchement normal.
		// Pour eviter cet evenement, le mieux est quand meme de specifier une dtd pour vos
		// documents xml et de les faire valider par votre parser.              
	}

	private Locator locator;

}

