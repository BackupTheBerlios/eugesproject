/*
 * APES is a Process Engineering Software
 * Copyright (C) 2002-2003 IPSquad
 * team@ipsquad.tuxfamily.org
 *
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */


package donnees.spem;

import donnees.spem.basic.ExternalDescription;
import donnees.spem.basic.Guidance;
import donnees.spem.basic.GuidanceKind;
import donnees.spem.diagram.ActivityDiagram;
import donnees.spem.diagram.ClassDiagram;
import donnees.spem.modelmanagement.SPackage;
import donnees.spem.process.components.ProcessComponent;
import donnees.spem.process.components.SProcess;
import donnees.spem.process.structure.Activity;
import donnees.spem.process.structure.ProcessPerformer;
import donnees.spem.process.structure.ProcessRole;
import donnees.spem.process.structure.WorkDefinition;
import donnees.spem.process.structure.WorkProduct;


/**
 * Interface for visiting SPEM models
 * For more details see the visitor design pattern
 *
 * @version $Revision: 1.5 $
 */
public interface SpemVisitor
{
	/**
	 * Called when the visited element is a package
	 *
	 * @param pack the visited package
	 */
	public void visitPackage(SPackage pack);


	/**
	 * Called when the visited element is a process component
	 *
	 * @param component the visited process component
	 */
	public void visitProcessComponent(ProcessComponent component);


	/**
	 * Called when the visited element is a process
	 *
	 * @param process the visited process
	 */
	public void visitProcess(SProcess process);


	/**
	 * Called when the visited element is a work definition
	 *
	 * @param work the visited work definition
	 */
	public void visitWorkDefinition(WorkDefinition work);


	/**
	 * Called when the visited element is a process performer
	 *
	 * @param performer the visited process performer
	 */
	public void visitProcessPerformer(ProcessPerformer performer);


	/**
	 * Called when the visited element is a work product
	 *
	 * @param product the visited work product
	 */
	public void visitProduct(WorkProduct product);


	/**
	 * Called when the visited element is a process role
	 *
	 * @param role the visited process role
	 */
	public void visitRole(ProcessRole role);


	/**
	 * Called when the visited element is an activity
	 *
	 * @param activity the visited activity
	 */
	public void visitActivity(Activity activity);


	/**
	 * Called when the visited element is an external description
	 *
	 * @param description the visited external description
	 */
	public void visitExternalDescription(ExternalDescription description);


	/**
	 * Called when the visited element is a guidance
	 *
	 * @param guidance the visited guidance
	 */
	public void visitGuidance(Guidance guidance);


	/**
	 * Called when the visited element is a guidance kind
	 *
	 * @param kind the visited guidance kind
	 */
	public void visitGuidanceKind(GuidanceKind kind);
	
	
	/**
	 * Called when the visited element is a class diagram
	 *
	 * @param diagram the visited class diagram
	 */
	public void visitClassDiagram(ClassDiagram diagram);
	
	
	/**
	 * Called when the visited element is an activity diagram
	 *
	 * @param diagram the visited activity diagram
	 */
	public void visitActivityDiagram(ActivityDiagram diagram);
}
