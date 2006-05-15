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

import de.berlios.gpon.persistence.search.SimpleQuery;
import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.Evaluator;
import de.berlios.gpon.xmlscript.Query;
import de.berlios.gpon.xmlscript.ScriptContext;

public class QueryEvaluator implements Evaluator {

	public Object evaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {
		
		CallData newCallData = new CallData();
		
		if (cd!=null) {
			newCallData.setLevel(cd.getLevel()+1);
			
		}
		
		Query q = (Query)xmlObject;
		
		System.out.println("Query: "+q.getSpec());
		
		SimpleQuery sq = 
			new SimpleQuery();
		
		sq.setType(q.getType());
		sq.setSpec(q.getSpec());
		
		
		// base set for this query node
		Set s = sc.getDataDao().search(sq);

		System.out.println("Query set #: "+s.size());
		
		newCallData.setBaseSet(s);
		
		if (q.isSetAssociated()
			|| q.isSetAnd()
			|| q.isSetOr()
			|| q.isSetXor()
			|| q.isSetNot())
		{
			Set newSet = (Set)new DefaultTraversingEvaluator().evaluate(xmlObject,sc,newCallData);
			if (q.isSetId()) {
				sc.setAttribute(q.getId(),newSet);
			}
			return newSet;
		}
		else {
			System.out.println("No child nodes -> base set returned");
			if (q.isSetId()) {
				sc.setAttribute(q.getId(),s);
			}
			return s;
		}
	}
	
}
