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


package donnees.spem.process.structure;

import donnees.spem.SpemVisitor;
import donnees.spem.core.ModelElement;
import java.util.Vector;

/**
 * @version $Revision: 1.3 $
 */
public class ProcessPerformer extends ModelElement
{
	private Vector mResponsibilities = new Vector();
	private Vector mFeature = new Vector();
	
	public ProcessPerformer()
	{
	
	}

	public ProcessPerformer(String name)
	{
		super(name);
	}

	public void visit(SpemVisitor visitor)
	{
		visitor.visitProcessPerformer(this);
	}
	
	
	
	/**
	 * Add a feature to this ProcessPerformer
	 *
	 * @param f the feature to associate
	 * @return true if the feature can be added, false otherwise
	 */
	public boolean addFeature(Activity f)
	{
		if(!containsFeature(f))
		{
			mFeature.add(f);
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * Remove a feature to this ProcessPerformer
	 *
	 * @param f the feature to remove
	 * @return true if the feature can be removed, false otherwise
	 */
	public boolean removeFeature(Activity f)
	{
		if(containsFeature(f))
		{
			mFeature.remove(f);
			return true;
		}

		return false;
	}
	
	
	
	/**
	 * Check if an activity is a feature of this ProcessPerformer
	 *
	 * @param f the Activity to test
	 * @return true if the Activity is a feature of this ProcessPerformer, false otherwise
	 */
	public boolean containsFeature(Activity f)
	{
		return mFeature.contains(f);
	}
	
	
	
	/**
	 * Add a responsibility to this ProcessPerformer
	 *
	 * @param f the responsibility to associate
	 * @return true if the responsibility can be added, false otherwise
	 */
	public boolean addResponsibility(WorkProduct r)
	{
		if(!containsResponsibility(r))
		{
			mResponsibilities.add(r);
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * Remove a responsibility to this ProcessPerformer
	 *
	 * @param r the responsibility to remove
	 * @return true if the feature can be removed, false otherwise
	 */
	public boolean removeResponsibility(WorkProduct r)
	{
		if(containsResponsibility(r))
		{
			mResponsibilities.remove(r);
			return true;
		}

		return false;
	}
	
	
	
	/**
	 * Check if a work product is a responsibility of this ProcessPerformer
	 *
	 * @param f the WorkProduct to test
	 * @return true if the WorkProduct is a responsibility of this ProcessPerformer, false otherwise
	 */
	public boolean containsResponsibility(WorkProduct r)
	{
		return mResponsibilities.contains(r);
	}
	
	
	public Object clone()
	{
		ProcessPerformer pp = (ProcessPerformer)super.clone();
		
		pp.mResponsibilities = new Vector();
		pp.mFeature = new Vector();
		
		return pp;
	}

	public int getFeatureCount()
	{
		return mFeature.size();
	}

	public int getResponsibilityCount()
	{
		return mResponsibilities.size();
	}
	
	public Activity getFeature(int i)
	{
		return (Activity)mFeature.get(i);
	}
	
	public WorkProduct getResponsibility(int i)
	{
		return (WorkProduct)mResponsibilities.get(i);
	}
};
