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


package donnees.spem.diagram;

import java.util.Vector;

import donnees.spem.SpemVisitor;
import donnees.spem.core.ModelElement;
import donnees.spem.process.structure.Activity;
import donnees.spem.process.structure.ProcessRole;
import donnees.spem.process.structure.WorkProduct;

//import org.ipsquad.utils.ErrorManager;

/**
 * Base class for the class diagram
 *
 * @version $Revision: 1.5 $
 */
public class ClassDiagram extends SpemDiagram
{
	private Vector mElements = new Vector();
	
	public ClassDiagram()
	{
	
	}
	
	public ClassDiagram(String name)
	{
		super(name);
	}
	
	public void visit(SpemVisitor visitor)
	{
		visitor.visitClassDiagram(this);
	}
	
	public boolean addModelElement(ModelElement me)
	{
		if(me instanceof Activity)
		{
			return addActivity((Activity)me);
		}
		else if(me instanceof ProcessRole)
		{
			return addProcessRole((ProcessRole)me);
		}
		else if(me instanceof WorkProduct)
		{
			return addWorkProduct((WorkProduct)me);
		}
		
		return false;
	}
	
	/**
	 * Add a role to the class diagram
	 *
	 * @param r the process role to associate
	 * @return true if the process role can be added, false otherwise
	 */
	public boolean addProcessRole(ProcessRole r)
	{
		if(!containsModelElement(r))
		{
			mElements.add(r);
			return true;
		}

		return false;
	}



	/**
	 * Add an activity to the class diagram
	 *
	 * @param a the activity to associate
	 * @return true if the activity can be added, false otherwise
	 */
	public boolean addActivity(Activity a)
	{
		if(!containsModelElement(a))
		{
			mElements.add(a);
			return true;
		}
		
		return false;
	}
	
	
	
	/**
	 * Add a work product to the class diagram
	 *
	 * @param a the work product to associate
	 * @return true if the work product can be added, false otherwise
	 */
	public boolean addWorkProduct(WorkProduct p)
	{
		if(!containsModelElement(p))
		{
			mElements.add(p);
			return true;
		}
		
		return false;
	}
	
	
	public boolean canAddModelElement(ModelElement me)
	{
		if(me instanceof Activity
		|| me instanceof ProcessRole
		|| me instanceof WorkProduct)
		{
			if(!containsModelElement(me))
			{
				return true;
			}
			else
			{
//				ErrorManager.getInstance().printKey("errorElementAlreadyPresent");
				return false;
			}
		}
		
//		ErrorManager.getInstance().printKey("errorElementForbidden");
		return false;
	}
	
	/**
	 * Remove a model element from this diagram
	 *
	 * @param e the model element to remove
	 * @return true if the model element can be removed, false otherwise
	 */
	public boolean removeModelElement(ModelElement e)
	{
		if(containsModelElement(e))
		{
			mElements.remove(e);
			return true;
		}

		return false;
	}
	
	
	
	/**
	 * Check if a model element is in this diagram
	 *
	 * @param e the model element to test
	 * @return true if the model element is in this diagram, false otherwise
	 */
	public boolean containsModelElement(ModelElement e)
	{
		return mElements.contains(e);
	}
	
	
	
	/**
	 * Get a model element by its index
	 *
	 * @param i the model element index
	 * @return the model element or null if the index is invalid
	 */
	public ModelElement getModelElement(int i)
	{
		if(i<0 || i>=mElements.size())
		{
			return null;
		}
		
		return (ModelElement) mElements.get(i);
	}
	
	
	
	/**
	 * Get the number of model elements in this diagram
	 *
	 * @return the number of model elements contained in this diagram
	 */
	public int modelElementCount()
	{
		return mElements.size();
	}
	
	
	
	public boolean createLinkModelElements(ModelElement source,ModelElement target)
	{
		if(source instanceof ProcessRole)
		{
			if(target instanceof Activity)
			{
				return createLinkProcessRoleActivity((ProcessRole)source,(Activity)target);
			}
			else if(target instanceof WorkProduct)
			{
				return createLinkProcessRoleWorkProduct((ProcessRole)source,(WorkProduct)target);
			}
		}
		else if(source instanceof WorkProduct)
		{
			if(target instanceof Activity)
			{
				return createLinkWorkProductActivityInput((WorkProduct)source,(Activity)target);
			}
		}
		else if(source instanceof Activity)
		{
			if(target instanceof WorkProduct)
			{
				return createLinkWorkProductActivityOutput((WorkProduct)target,(Activity)source);
			}
		}
		
		
		return false;
	}
	
	
	
	/**
	 * Create a link between a process role and an activity
	 *
	 * @param r the Process Role to be linked
	 * @param a the Activity to be linked
	 * @return true if the link can be created, false otherwise
	 */
	public boolean createLinkProcessRoleActivity(ProcessRole r, Activity a)
	{
		if (containsModelElement(r) && containsModelElement(a))
		{
			if (!r.containsFeature(a) && a.getOwner()==null)
			{
				r.addFeature(a);
				a.setOwner(r);
				return true;
			}
		}
		return false;
	}
	
	
	
	
	/**
	 * Create a link between a process role and a work product
	 *
	 * @param r the Process Role to be linked
	 * @param a the Work Product to be linked
	 * @return true if the link can be created, false otherwise
	 */
	public boolean createLinkProcessRoleWorkProduct(ProcessRole r, WorkProduct w)
	{
		if (containsModelElement(r) && containsModelElement(w))
		{
			if (!r.containsResponsibility(w) && w.getResponsible()==null)
			{
				r.addResponsibility(w);
				w.setResponsible(r);
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * Create a link with a work product in input of an activity
	 *
	 * @param w the work product to be linked
	 * @param a the activity to be linked in input
	 * @return true if the link can be created, false otherwise
	 */
	public boolean createLinkWorkProductActivityInput(WorkProduct w, Activity a)
	{
		if (containsModelElement(a) && containsModelElement(w))
		{
			if(!a.containsInputWorkProduct(w) && !w.containsOutputWorkDefinition(a))
			{
				a.addInputWorkProduct(w);
				w.addOutputWorkDefinition(a);
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * Create a link with a work product in output of an activity
	 *
	 * @param w the work product to be linked
	 * @param a the activity to be linked in output
	 * @return true if the link can be created, false otherwise
	 */
	public boolean createLinkWorkProductActivityOutput(WorkProduct w, Activity a)
	{
		if(containsModelElement(a) && containsModelElement(w))
		{
			if(!a.containsOutputWorkProduct(w) && !w.containsInputWorkDefinition(a))
			{
				a.addOutputWorkProduct(w);
				w.addInputWorkDefinition(a);
				return true;
			}
		}
		return false;
	}
	
	
	
	public boolean removeLinkModelElements(ModelElement source,ModelElement target)
	{
		if(source instanceof ProcessRole)
		{
			if(target instanceof Activity)
			{
				return removeLinkProcessRoleActivity((ProcessRole)source,(Activity)target);
			}
			else if(target instanceof WorkProduct)
			{
				return removeLinkProcessRoleWorkProduct((ProcessRole)source,(WorkProduct)target);
			}
		}
		else if(source instanceof WorkProduct)
		{
			if(target instanceof Activity)
			{
				return removeLinkWorkProductActivityInput((WorkProduct)source,(Activity)target);
			}
		}
		else if(source instanceof Activity)
		{
			if(target instanceof WorkProduct)
			{
				return removeLinkWorkProductActivityOutput((WorkProduct)target,(Activity)source);
			}
		}
		return false;
	}
	
	
	
	/**
	 * Remove a link between a process role and an activity
	 *
	 * @param r the Process Role to be unlinked
	 * @param a the Activity to be unlinked
	 * @return true if the link can be removed, false otherwise
	 */
	public boolean removeLinkProcessRoleActivity(ProcessRole r, Activity a)
	{
		if (containsModelElement(r) && containsModelElement(a))
		{
			if(r.removeFeature(a))
			{
				a.setOwner(null);
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * Remove a link between a process role and an work product
	 *
	 * @param r the Process Role to be unlinked
	 * @param a the WorkProduct to be unlinked
	 * @return true if the link can be removed, false otherwise
	 */
	public boolean removeLinkProcessRoleWorkProduct(ProcessRole r, WorkProduct w)
	{
		if (containsModelElement(r) && containsModelElement(w))
		{
			if (r.removeResponsibility(w))
			{
				w.setResponsible(null);
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * Remove a link with a work product in input of an activity
	 *
	 * @param w the work product to be unlinked
	 * @param a the activity to be unlinked in input
	 * @return true if the link can be removed, false otherwise
	 */
	public boolean removeLinkWorkProductActivityInput(WorkProduct w, Activity a)
	{
		if (containsModelElement(a) && containsModelElement(w))
		{
			if(a.removeInputWorkProduct(w))
			{
				w.removeOutputWorkDefinition(a);
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * Remove a link with a work product in output of an activity
	 *
	 * @param w the work product to be unlinked
	 * @param a the activity to be unlinked in input
	 * @return true if the link can be removed, false otherwise
	 */
	public boolean removeLinkWorkProductActivityOutput(WorkProduct w, Activity a)
	{
		if (containsModelElement(a) && containsModelElement(w))
		{
			if(a.removeOutputWorkProduct(w))
			{
				w.removeInputWorkDefinition(a);
				return true;
			}
		}
		return false;
	}
	
	
	
	public boolean areLinkableModelElements(ModelElement source,ModelElement target)
	{
		if(source instanceof ProcessRole)
		{
			if(target instanceof Activity)
			{
				return areLinkableProcessRoleActivity((ProcessRole)source,(Activity)target);
			}
			else if(target instanceof WorkProduct)
			{
				return areLinkableProcessRoleWorkProduct((ProcessRole)source,(WorkProduct)target);
			}
		}
		else if(source instanceof WorkProduct)
		{
			if(target instanceof Activity)
			{
				return areLinkableWorkProductActivityInput((WorkProduct)source,(Activity)target);
			}
		}
		else if(source instanceof Activity)
		{
			if(target instanceof WorkProduct)
			{
				return areLinkableWorkProductActivityOutput((WorkProduct)target,(Activity)source);
			}
		}
		
//		ErrorManager.getInstance().printKey("errorNotLinkableElements");
		return false;
	}
	
	
	
	/**
	 * Test if a link between a process role and an activity can be created
	 *
	 * @param r the Process Role to be tested
	 * @param a the Activity to be tested
	 * @return true if the link can be created, false otherwise
	 */
	public boolean areLinkableProcessRoleActivity(ProcessRole r, Activity a)
	{
		if (containsModelElement(r) && containsModelElement(a))
		{
			if (!r.containsFeature(a))
			{
				return true;
			}
		}

//		ErrorManager.getInstance().printKey("errorAlreadyLinkedElements");
		return false;
	}
	
	
	
	/**
	 * Test if a link between a process role and a work product can be created
	 *
	 * @param r the Process Role to be tested
	 * @param a the Work Product to be tested
	 * @return true if the link can be created, false otherwise
	 */
	public boolean areLinkableProcessRoleWorkProduct(ProcessRole r, WorkProduct w)
	{
		if (containsModelElement(r) && containsModelElement(w))
		{
			if (!r.containsResponsibility(w) && w.getResponsible()==null)
			{
				return true;
			}
		}
		
//		ErrorManager.getInstance().printKey("errorAlreadyLinkedElements");
		return false;
	}
	
	
	
	/**
	 * Test if a link with a work product in input of an activity can be created
	 *
	 * @param w the work product to be tested
	 * @param a the activity to be tested in input
	 * @return true if the link can be created, false otherwise
	 */
	public boolean areLinkableWorkProductActivityInput(WorkProduct w, Activity a)
	{
		if (containsModelElement(a) && containsModelElement(w))
		{
			if (!a.containsInputWorkProduct(w))
			{
				return true;
			}
		}
		
//		ErrorManager.getInstance().printKey("errorAlreadyLinkedElements");
		return false;
	}
	
	
	
	/**
	 * Test if a link with a work product in output of an activity can be created
	 *
	 * @param w the work product to be tested
	 * @param a the activity to be tested in output
	 * @return true if the link can be created, false otherwise
	 */
	public boolean areLinkableWorkProductActivityOutput(WorkProduct w, Activity a)
	{
		if (containsModelElement(a) && containsModelElement(w))
		{
			if (!a.containsOutputWorkProduct(w))
			{
				return true;
			}
		}
		
//		ErrorManager.getInstance().printKey("errorAlreadyLinkedElements");
		return false;
	}
	
	
	
	public boolean existsLinkModelElements(ModelElement source,ModelElement target)
	{
		if(source instanceof ProcessRole)
		{
			if(target instanceof Activity)
			{
				return existsLinkProcessRoleActivity((ProcessRole)source,(Activity)target);
			}
			else if(target instanceof WorkProduct)
			{
				return existsLinkProcessRoleWorkProduct((ProcessRole)source,(WorkProduct)target);
			}
		}
		else if(source instanceof WorkProduct)
		{
			if(target instanceof Activity)
			{
				return existsLinkWorkProductActivityInput((WorkProduct)source,(Activity)target);
			}
		}
		else if(source instanceof Activity)
		{
			if(target instanceof WorkProduct)
			{
				return existsLinkWorkProductActivityOutput((WorkProduct)target,(Activity)source);
			}
		}
		return false;
	}
	
	
	
	/**
	 * Test if a link between a process role and an activity exists
	 *
	 * @param r the Process Role to be tested
	 * @param a the Activity to be tested
	 * @return true if the link exists, false otherwise
	 */
	public boolean existsLinkProcessRoleActivity(ProcessRole r, Activity a)
	{
		if (containsModelElement(r) && containsModelElement(a))
		{
			if (r.containsFeature(a))
			{
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * Test if a link between a process role and a work product exists
	 *
	 * @param r the Process Role to be tested
	 * @param a the Work Product to be tested
	 * @return true if the link exists, false otherwise
	 */
	public boolean existsLinkProcessRoleWorkProduct(ProcessRole r, WorkProduct w)
	{
		if (containsModelElement(r) && containsModelElement(w))
		{
			if (r.containsResponsibility(w) && w.getResponsible()==r)
			{
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * Test if a link with a work product in input of an activity exists
	 *
	 * @param w the work product to be tested
	 * @param a the activity to be tested in input
	 * @return true if the link exists, false otherwise
	 */
	public boolean existsLinkWorkProductActivityInput(WorkProduct w, Activity a)
	{
		if (containsModelElement(a) && containsModelElement(w))
		{
			if (a.containsInputWorkProduct(w))
			{
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * Test if a link with a work product in output of an activity exists
	 *
	 * @param w the work product to be tested
	 * @param a the activity to be tested in output
	 * @return true if the link exists, false otherwise
	 */
	public boolean existsLinkWorkProductActivityOutput(WorkProduct w, Activity a)
	{
		if (containsModelElement(a) && containsModelElement(w))
		{
			if (a.containsOutputWorkProduct(w))
			{
				return true;
			}
		}
		return false;
	}
	
	public Object clone()
	{
		ClassDiagram d = (ClassDiagram) super.clone();
		d.mElements = (Vector) mElements.clone();
		return d;
	}
}
