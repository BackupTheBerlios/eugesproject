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

import donnees.spem.core.Element;
import donnees.spem.core.ModelElement;
import donnees.spem.core.PresentationElement;
import donnees.spem.modelmanagement.SPackage;
import donnees.spem.process.components.ProcessComponent;
import donnees.spem.process.components.SProcess;
import donnees.spem.process.structure.WorkDefinition;
import donnees.spem.process.structure.ProcessPerformer;
import donnees.spem.process.structure.WorkProduct;
import donnees.spem.process.structure.ProcessRole;
import donnees.spem.process.structure.Activity;
import donnees.spem.basic.ExternalDescription;
import donnees.spem.basic.Guidance;
import donnees.spem.basic.GuidanceKind;
import donnees.spem.diagram.SpemDiagram;
import donnees.spem.diagram.ClassDiagram;
import donnees.spem.diagram.ActivityDiagram;


/**
 * Default visitor implementation that does nothing.
 * Its only work is to follow the inheritance tree.
 *
 * @version $Revision: 1.3 $
 */
public class DefaultSpemVisitor implements SpemVisitor
{
	/**
	 * Called when visiting an Element
	 *
	 * @param element the visited Element
	 */
	protected void visitElement(Element element) {}
	
	
	/**
	 * Called when visiting a ModelElement
	 * Simply treat it as an Element
	 *
	 * @param element the visited ModelElement
	 */
	protected void visitModelElement(ModelElement element)
	{
		visitElement(element);
	}


	/**
	 * Called when visiting a ModelElement
	 * Simply treat it as an Element
	 *
	 * @param element the visited ModelElement
	 */
	protected void visitPresentationElement(PresentationElement element)
	{
		visitElement(element);
	}

	
	/**
	 * Simply treat the Package as a ModelElement
	 *
	 * @param pack the visited package
	 */
	public void visitPackage(SPackage pack)
	{
		visitModelElement(pack);
	}
	

	/**
	 * Simply treat the ProcessComponent as a Package
	 *
	 * @param component the visited process component
	 */
	public void visitProcessComponent(ProcessComponent component)
	{
		visitPackage(component);
	}


	/**
	 * Simply treat the Process as a ProcessComponent
	 *
	 * @param process the visited process
	 */
	public void visitProcess(SProcess process)
	{
		visitProcessComponent(process);
	}


	/**
	 * Simply treat the WorkDefinition as a ModelElement
	 *
	 * @param work the visited work definition
	 */
	public void visitWorkDefinition(WorkDefinition work)
	{
		visitModelElement(work);
	}


	/**
	 * Simply treat the ProcessPerformer as a ModelElement
	 *
	 * @param performer the visited process performer
	 */
	public void visitProcessPerformer(ProcessPerformer performer)
	{
		visitModelElement(performer);
	}


	/**
	 * Simply treat the WorkProduct as a ModelElement
	 *
	 * @param product the visited work product
	 */
	public void visitProduct(WorkProduct product)
	{
		visitModelElement(product);
	}


	/**
	 * Simply treat the ProcessRole as a ProcessPerformer
	 *
	 * @param role the visited process role
	 */
	public void visitRole(ProcessRole role)
	{
		visitProcessPerformer(role);
	}


	/**
	 * Simply treat the Activity as a WorkDefinition
	 *
	 * @param activity the visited activity
	 */
	public void visitActivity(Activity activity)
	{
		visitWorkDefinition(activity);
	}


	/**
	 * Simply treat the ExternalDescription as a PresentationElement
	 *
	 * @param description the visited external description
	 */
	public void visitExternalDescription(ExternalDescription description)
	{
		visitPresentationElement(description);
	}


	/**
	 * Simply treat the Guidance as a ModelElement
	 *
	 * @param guidance the visited guidance
	 */
	public void visitGuidance(Guidance guidance)
	{
		visitModelElement(guidance);
	}


	/**
	 * Simply treat the GuidanceKind as a ModelElement
	 *
	 * @param kind the visited guidance kind
	 */
	public void visitGuidanceKind(GuidanceKind kind)
	{
		visitModelElement(kind);
	}
	
	
	/**
	 * Simply treat the SpemDiagram as a ModelElement
	 *
	 * @param diagram the visited spem diagram
	 */
	protected void visitSpemDiagram(SpemDiagram diagram)
	{
		visitModelElement(diagram);
	}
	
	
	/**
	 * Simply treat the ClassDiagram as a SpemDiagram
	 *
	 * @param diagram the visited class diagram
	 */
	public void visitClassDiagram(ClassDiagram diagram)
	{
		visitSpemDiagram(diagram);
	}
	
	
	/**
	 * Simply treat the ActivityDiagram as a SpemDiagram
	 *
	 * @param diagram the visited activity diagram
	 */
	public void visitActivityDiagram(ActivityDiagram diagram)
	{
		visitSpemDiagram(diagram);
	}
}
