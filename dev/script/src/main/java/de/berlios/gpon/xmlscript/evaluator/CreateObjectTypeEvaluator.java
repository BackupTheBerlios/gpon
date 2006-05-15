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

import java.util.List;

import org.apache.xmlbeans.XmlObject;

import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.CreateObject;
import de.berlios.gpon.xmlscript.CreateObjectType;
import de.berlios.gpon.xmlscript.Evaluator;
import de.berlios.gpon.xmlscript.ScriptContext;


public class CreateObjectTypeEvaluator implements Evaluator {

	public Object evaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {
		
		CreateObjectType createObjectType = (CreateObjectType) xmlObject;
		
		ItemType itemType = new ItemType();
		
		itemType.setName(createObjectType.getName());
		itemType.setDescription(createObjectType.getDescription());
		
		if (createObjectType.isSetBaseType()) 
		{
		 ItemType baseType = sc.getModelDao().findItemTypeByName(createObjectType.getBaseType()); 
		 itemType.setBaseType(baseType);
		}
		
		CallData newCd =
			new CallData();
		newCd.setLevel(cd.getLevel()+1);
		newCd.setItemType(itemType);
		
		DefaultTraversingEvaluator de =
			new DefaultTraversingEvaluator();
		
		de.evaluate(xmlObject, sc, newCd);
		
		System.out.println("Item type: "+itemType+" #pdcl: "+itemType.getItemPropertyDecls().size());
		
		// persist
		sc.getModelDao().addItemType(itemType);
				
		return null;
				
	}

}
