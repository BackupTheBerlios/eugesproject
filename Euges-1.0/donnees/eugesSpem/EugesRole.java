/*
 * Created on 10 janv. 2004
 *
 */
package donnees.eugesSpem;

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
}
