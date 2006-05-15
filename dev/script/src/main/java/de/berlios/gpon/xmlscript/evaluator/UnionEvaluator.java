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

import java.util.Set;

import org.apache.xmlbeans.XmlObject;

import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.Evaluator;
import de.berlios.gpon.xmlscript.ScriptContext;
import de.berlios.gpon.xmlscript.Union;


public class UnionEvaluator implements Evaluator {

	public Object evaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {
		System.out.println("[Intersection] calling Or");
		Set set = (Set) new OrEvaluator().coreEvaluate(xmlObject,sc,cd);
		
		Union union =
			(Union)xmlObject;
		
		if (union.getId()!=null) {
			sc.setAttribute(union.getId(),set);
		}
		return set;
	}

}
