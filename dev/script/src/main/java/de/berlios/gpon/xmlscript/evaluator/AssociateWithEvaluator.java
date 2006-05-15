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

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;


import org.apache.xmlbeans.XmlObject;

import de.berlios.gpon.common.Association;
import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.util.ItemMappedByName;
import de.berlios.gpon.xmlscript.AssociateWith;
import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.ScriptContext;

public class AssociateWithEvaluator extends AbstractEvaluator {

	public Object evaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {

		AssociateWith associateWith = (AssociateWith) xmlObject;

		// Result should be a set like object

		DefaultTraversingEvaluator de = new DefaultTraversingEvaluator();

		CallData newCd = new CallData();
		newCd.setLevel(cd.getLevel() + 1);

		Set set = (Set) de.evaluate(xmlObject, sc, newCd);

		if (set != null) {
			System.out.println("Set to ass with: " + set.size());
		}

		if (set == null || set.size() == 0) {
			log.info("Nothing to associate with.");
			return null;
		}

		ItemMappedByName mi = cd.getMappedItem();

		Item item = null;

		if (mi != null) {
			item = mi.getItem();
		}

		// get association type
		AssociationType at = sc.getModelDao().findAssociationTypeByName(
				associateWith.getType());

		if (at == null) {
			throw new EvaluatorException("Association type "
					+ associateWith.getType() + " unknown!");
		}

		// role known ?

		String role = associateWith.getRole();

		boolean tupleReverseMode = false;

		Hashtable existingAssociationsMap = new Hashtable();
		Set assocSet = null;

		if (at.getItemARoleName().equals(role)) {
			// we add associations with
			// (CreatedObject.id, AssociatedObject.id)

			assocSet = item.getAssociationsA();

		} else if (at.getItemBRoleName().equals(role)) {
			// we add associations with
			// (AssociatedObject.id, CreatedObject.id)
			tupleReverseMode = true;

			assocSet = item.getAssociationsB();
		}

		// map existing associations by aid:bid

		if (assocSet != null) {

			Iterator assocIt = assocSet.iterator();

			while (assocIt.hasNext()) {
				Association assoc = (Association) assocIt.next();

				if (assoc.getAssociationType().equals(at)) 
				{
				 existingAssociationsMap.put(assoc.getAtAB(), assoc);
				}
			}
		}

		Iterator it = set.iterator();

		while (it.hasNext()) {
			Item associatedItem = (Item) it.next();

			Association association = new Association();

			association.setAssociationType(at);

			if (!tupleReverseMode) {
				log.info("Add association [" + item.getId() + ","
						+ associatedItem.getId() + "]");
				association.setItemA(item);
				association.setItemB(associatedItem);
			} else {
				log.info("Add association [" + associatedItem.getId() + ","
						+ item.getId() + "]");
				association.setItemA(associatedItem);
				association.setItemB(item);
			}

			if (!existingAssociationsMap.containsKey(association.getAtAB())) {
				sc.getDataDao().addAssociation(association);
			} else {
				log.info("Association already exists!");
			}
		}

		return set;

	}

}
