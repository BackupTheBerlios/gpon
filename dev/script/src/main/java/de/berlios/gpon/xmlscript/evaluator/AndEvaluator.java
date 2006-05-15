/*
GPON General Purpose Object Network
Copyright (C) 2006 Daniel Schulz

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/


package de.berlios.gpon.xmlscript.evaluator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.xmlbeans.XmlObject;

import de.berlios.gpon.xmlscript.And;
import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.Evaluator;
import de.berlios.gpon.xmlscript.ScriptContext;




/** Computes the intersection of all nested result sets.
 * @author daniel
 *
 */
public class AndEvaluator 
	implements Evaluator
{

	public Object evaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {
		Set result = (Set)coreEvaluate(xmlObject,sc,cd);
		
		And and = (And)xmlObject;
		
		if (and.getId()!=null) {
			sc.setAttribute(and.getId(),result);
		}
		
		return result;
	}
	
	
	public Object coreEvaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {
		
		DefaultTraversingEvaluator de = 
			new DefaultTraversingEvaluator();
		
		CallData cdNew = new CallData();
		
		cdNew.setBaseSet(cd.getBaseSet());
		cdNew.setLevel(cd.getLevel()+1);
		
		de.evaluate(xmlObject,sc,cdNew);
		
		List results = de.getResults();
		
		Iterator it = results.iterator();
		
		Set result = new HashSet();
		
		boolean start = true;
		
		while (it.hasNext()) {
			Set set = (Set) it.next();
			
			if (start) {
				start = false;
				// first set is the starting point
				result.addAll(set);
			}
			else {
				// intersection for subsequent sets
				result.retainAll(set);				
			}	
			
			System.out.println("[And Intersection] Set size: "+result.size());
		}
		
		
		System.out.println("[And] Result set size: "+result.size());
		
		return result;
		
	}

}
