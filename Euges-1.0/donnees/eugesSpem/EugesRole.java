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
	
	public EugesRole() {
		super();
	}

	public EugesRole(String name) {
		super(name);
	}
	
	public String toString() {
		return this.getName();
	}
	/**
	 * @param ecriture
	 */
	public void sauvegarder(BufferedWriter ecriture) {
		try {
			ecriture.write("<EugesRole  _id=\""+this.getID()+"\" _name=\""+this.getName()+"\" _parent=\""+this.getParent()+"\"/>\n");
		} catch (IOException e) {
			System.out.println(e);
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
}
