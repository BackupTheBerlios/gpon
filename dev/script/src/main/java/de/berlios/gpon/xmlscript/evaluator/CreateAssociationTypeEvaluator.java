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

import org.apache.xmlbeans.XmlObject;

import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.CreateAssociationType;
import de.berlios.gpon.xmlscript.Evaluator;
import de.berlios.gpon.xmlscript.ScriptContext;


public class CreateAssociationTypeEvaluator implements Evaluator {

	public Object evaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {

		CreateAssociationType createAssoc =
			(CreateAssociationType)xmlObject;
		
		AssociationType associationType =
			new AssociationType();
		
		associationType.setDescription(createAssoc.getDescription());
		associationType.setName(createAssoc.getName());
		
		ItemType itemAType =
			sc.getModelDao().
			findItemTypeByName(createAssoc.getTypeOfA());
		
		ItemType itemBType =
			sc.getModelDao().
			findItemTypeByName(createAssoc.getTypeOfB());
		
		System.out.println("Item A type: "+itemAType);
		System.out.println("Item B type: "+itemBType);
		
		associationType.setItemARoleName(createAssoc.getRoleOfA());
		associationType.setItemAType(itemAType);
		
		associationType.setItemBRoleName(createAssoc.getRoleOfB());
		associationType.setItemBType(itemBType);
		
		associationType.setMultiplicity(createAssoc.getMultiplicity().toString());
		associationType.setVisibility(createAssoc.getVisibility().toString());
		
		sc.getModelDao().addAssociationType(associationType);
		
		System.out.println(associationType);
		
		System.out.println("atypeid: "+associationType.getItemAType().getId()+"btypeid: "+associationType.getItemBType().getId());
		
		return null;
	}

}
