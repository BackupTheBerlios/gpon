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
import java.util.Set;


import org.apache.xmlbeans.XmlObject;

import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.Evaluator;
import de.berlios.gpon.xmlscript.Not;
import de.berlios.gpon.xmlscript.ScriptContext;

public class NotEvaluator implements Evaluator {

	public Object evaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {
		
		Set result = (Set)coreEvaluate(xmlObject,sc,cd);
		
		Not not = (Not)xmlObject;
		
		if (not.getId()!=null) {
			sc.setAttribute(not.getId(),result);
		}
		
		return result;
		
	}
	
	
	public Object coreEvaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {
		
		DefaultTraversingEvaluator de = 
				new DefaultTraversingEvaluator();

		CallData cdNew = new CallData();
		cdNew.setBaseSet(cd.getBaseSet());
		cdNew.setLevel(cd.getLevel()+1);
		
		System.out.println("Not calling default evaluator.");
		
		Set result = (Set)de.evaluate(xmlObject,sc,cdNew); 
		
		System.out.println("Not input size: "+result.size());
		
		Set base = cd.getBaseSet();
		
		Set baseMinusResult = new HashSet();
		
		baseMinusResult.addAll(base);
		
		baseMinusResult.removeAll(result);		
		
		System.out.println("Not output size: "+baseMinusResult.size());
		
		return baseMinusResult;
		
	}

}
