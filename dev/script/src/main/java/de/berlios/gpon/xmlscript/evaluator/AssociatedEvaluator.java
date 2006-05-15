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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;

import de.berlios.gpon.common.Association;
import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.Item;
import de.berlios.gpon.persistence.search.SimpleQuery;
import de.berlios.gpon.xmlscript.Associated;
import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.Evaluator;
import de.berlios.gpon.xmlscript.ScriptContext;

public class AssociatedEvaluator implements Evaluator {

	Log log = LogFactory.getLog(AssociatedEvaluator.class);
	
	public Object evaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {
		
		boolean any = false;
		
		CallData newCallData = new CallData();
		
		if (cd!=null) {
			newCallData.setLevel(cd.getLevel()+1);
		}
		
		Associated associated = (Associated)xmlObject;
		
		String associationType = associated.getBy();
		
		System.out.println("Associated by "+associationType);
		
		Set base = cd.getBaseSet();
		
		// associated set specified
		XmlCursor cursor = associated.newCursor();
		
		cursor.toFirstContentToken();
		
		if (cursor.isFinish()) {
			// no content means: asociated with any other object
			any = true;
		}
		
		cursor.dispose();
		
		if (base != null) {
			System.out.println("Base set size: "+base.size());
		}
		
		if (!any) {
			// get Associated Set
			Set associatedSet =
				(Set)new DefaultTraversingEvaluator().evaluate(xmlObject,sc,newCallData);
			
			if (associatedSet!=null) {
				System.out.println("Associated set size: "+associatedSet.size());
			}
		
			return computeAssociatedCondition(base,
					  associatedSet,
					  associated.getBy(),
					  associated.getRole(),
					  sc);
			
		}
		
		return  computeAssociatedCondition(base,
				  associated.getBy(),
				  associated.getRole(),
				  sc);
		
		
	}
	
	
//
//									    	+-------------------------+
//									    	|elements matching spec   |
//<Query spec="${name}like 'A%'"    --> 	|are members of the base  |
//   with="..">                             |query set (BQ)           |
//  ...	                                    +-------------------------+
//  <Associated by="A">                		
//    <Referece refid="P">
//  </Associated>
//</Query>
//
// 	
// Query Result set Q members q match the following condition QCOND:
//  q.isElementOf(BQ) and p.isElementOf(P) and new Tuple(q,p).isElementOf(A)
//	
// with=any: 	
//	
	
	
	private Set computeAssociatedCondition(
			Set querySet, 
			Set associatedSet, 
			String associationType,
			String role,
			ScriptContext sc
			) 
	{
		
		// if the association type is defeined as A to B, but our base query type
		// is B, we have to search the reversed tuples 
		
		boolean tupleReverse = false; 
				
		if (querySet==null || querySet.size()==0) 
		{
			log.info("query set is empty. (association condition will not be computed)");
		}
		
		AssociationType at =
			sc.getModelDao().findAssociationTypeByName(associationType);
		
		if (at==null) {
			log.error("association type "+associationType+" unknown");
		}
		
		if (at.getItemARoleName().equals(role)) 
		{
			
		} 
		else if (at.getItemBRoleName().equals(role)) 
		{
			tupleReverse = true;			
		}
		else 
		{
			log.error("role "+role+" unknown in association type "+associationType);
		}
	
		List associationData =
			sc.getDataDao().findAssociationsByType(at.getId());
		
		Iterator it = associationData.iterator();
		
		Set setA = (!tupleReverse)?querySet:associatedSet;
		Set setB = (tupleReverse)?querySet:associatedSet;
		
		Set resultSet = new HashSet();
		
		while (it.hasNext()) {		
			Association assoc = (Association)it.next();
			
			Item itemA = assoc.getItemA();
			Item itemB = assoc.getItemB();
				
			
			if (setA.contains(itemA) && 
				setB.contains(itemB)) 
			{
				if (tupleReverse) {
					resultSet.add(assoc.getItemB());
				}
				else  {
					resultSet.add(assoc.getItemA());
				}
					
			}
		}
		
		System.out.println("Result set size: "+resultSet.size());
		
		return resultSet;
	}
	
	
	private Set computeAssociatedCondition(
			Set querySet,  
			String associationType,
			String role,
			ScriptContext sc
			) 
	{
		// TODO: performance
		
		AssociationType at =
			sc.getModelDao().findAssociationTypeByName(associationType);
		
		if (at==null) {
			log.error("association type "+associationType+" unknown");
		}
		
		SimpleQuery sq = new SimpleQuery();
		
		String otherRole = "";
		
		if (at.getItemARoleName().equals(role)) {
			// get all items of b type		
			sq.setType(at.getItemBType().getName());
			otherRole = at.getItemBRoleName();
		} else if (at.getItemBRoleName().equals(role)) {
			// get all items of a type			
			sq.setType(at.getItemAType().getName());
			otherRole = at.getItemARoleName();
		}
		else 
		{
			log.error("role name "+role+"unknown");
		}
		
		Set associatedSet =
			sc.getDataDao().search(sq);

		if (associatedSet!=null) {
			System.out.println(associatedSet.size()+" items for other role "+otherRole);
		}
		
		return computeAssociatedCondition(
				querySet,
				associatedSet,
				associationType,
				role,
				sc);
		
	}
	

}
