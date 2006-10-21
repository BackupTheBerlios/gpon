package de.berlios.gpon.common.util.path;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import de.berlios.gpon.common.ItemType;


public class Path {

	
	List steps;
	ItemType start;
	ItemType end;
	
	public Path() {
		steps = new ArrayList();
	}
	
	public ItemType getEnd() {
		return end;
	}
	public void setEnd(ItemType end) {
		this.end = end;
	}
	public ItemType getStart() {
		return start;
	}
	public void setStart(ItemType start) {
		this.start = start;
	}

	public void addStep(Step s) 
	{
		steps.add(s);
	}
	
	public boolean containsStep(Step s) 
	{
		return steps.contains(s);
	}
	
	public Path duplicate() 
	{
		Path p = new Path();
		
		p.start = this.start;
		p.end = this.end;
		p.steps.addAll(this.steps);
		
		return p;
	}

	public List getSteps() {
		return steps;
	}
	
	/** computes the path digest.
	 * 
	 * path digest: [start type id]:[step digest 1]:...:[step digest n]:[end type id]
	 * 
	 * @return
	 */
	public String getDigest() 
	{
		StringBuffer digestBuffer =
			new StringBuffer();
		
		digestBuffer.append(getStart().getId()).append(":");
		
		for (int i=0; i < getSteps().size(); i++) 
		{
			digestBuffer.append(((Step)getSteps().get(i)).getStepDigest()).append(":");
			
		}
		
		digestBuffer.append(getEnd().getId());
		
		return digestBuffer.toString();
		
	}
	
	public String getDisplayWithoutStart() 
	{
		return getDisplay(false,true);
	}
	
	public String getDisplay(boolean withStart, boolean withEnd) {

		String mn = "m:n";
		String om = "1:m";
		String mo = "m:1";

		StringBuffer out = new StringBuffer();
		
		if (withStart) 
		{
			out.append("[").append(getStart().getDescription()).append("] ");	
		}
		
		
		Iterator iit = getSteps().iterator();
		boolean isFirst = true;
		while (iit.hasNext()) {
			Step step = (Step) iit.next();
			
			if (isFirst) 
			{
				out.append(" -> ");
			}
			isFirst=false;
			
			out.append(step.getAssociationType().getDescription()+ (step.isReverse() ? " (rev)" :""));
			out.append(" -> ");
			
		}
		if (withEnd)
		{
			out.append(" [" + getEnd().getDescription() + "] ");
		}
		
		return out.toString();
	}
	
	
	
	
}
