/*
 * Created on 10 janv. 2004
 *
 */
package donnees.eugesSpem;

import java.io.BufferedWriter;
import java.io.IOException;

import donnees.spem.process.structure.ProcessRole;

/**
 * @author Nicolas
 *
 */
public class EugesRole extends ProcessRole {
	
	private String _cheminRole;
	
	public EugesRole() {
		super();
	}

	public EugesRole(String name) {
		super(name);
	}

	public EugesRole(String name, String chemin) {
		super(name);
		_cheminRole = chemin;
	}
	
	public String toString() {
		return this.getName();
	}
	

	/**
	 * @return Returns the _cheminRole.
	 */
	public String get_cheminRole() {
		return _cheminRole;
	}

	/**
	 * @param role The _cheminRole to set.
	 */
	public void set_cheminRole(String role) {
		_cheminRole = role;
	}
	
	
	/**
	 * @param ecriture
	 */
	public void sauvegarder(BufferedWriter ecriture) {
		try {
			ecriture.write("<EugesRole  _id=\""+this.getID()+"\" _name=\""+this.getName()+"\" _parent=\""+this.getParent()+"\"/>\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sauvegarderAssociation(BufferedWriter ecriture) {
		try {
			ecriture.write("<_roles _string=\""+this+"\"/>\n");
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
	public void genereMenu(BufferedWriter buffer) {
		try {
			buffer.write("<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+this.getName()+"</td></tr>");
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
}
