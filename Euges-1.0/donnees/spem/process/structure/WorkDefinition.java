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

import java.util.Vector;

import donnees.spem.SpemVisitor;
import donnees.spem.core.ModelElement;

/**
 * @version $Revision: 1.5 $
 */
public class WorkDefinition extends ModelElement
{
	private Vector mSubWork = new Vector();
	private Vector mParentWork = new Vector();
	private ProcessPerformer mOwner;
	
	public WorkDefinition()
	{
	
	}
	
	public WorkDefinition(String name)
	{
		super(name);
	}
	
	public void visit(SpemVisitor visitor)
	{
		visitor.visitWorkDefinition(this);
	}
	
	public ProcessPerformer getOwner()
	{
		return mOwner;
	}
	
	public boolean setOwner(ProcessPerformer owner)
	{
		if(owner==null)
		{
			mOwner = owner;
			return true;
		}
		
		if(mOwner==null)
		{
			mOwner = owner;
			return true;
		}
		
		return false;
	}
	
	public Object clone()
	{
		WorkDefinition wd = (WorkDefinition)super.clone();
		
		wd.mSubWork = new Vector();
		wd.mParentWork = new Vector();
		wd.mOwner = null;
		
		return wd;
	}
	
}
