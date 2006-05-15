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

import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.RemoveAssociationType;
import de.berlios.gpon.xmlscript.RemoveObjectType;
import de.berlios.gpon.xmlscript.ScriptContext;

public class RemoveAssociationTypeEvaluator extends AbstractEvaluator {

	public Object evaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {
		
		RemoveAssociationType removeAssociationType =
			(RemoveAssociationType)xmlObject;
		
		ItemType it =
			sc.getModelDao().findItemTypeByName(removeAssociationType.getName());
		
		if (it==null) 
		{
			log.warn("Association type "+removeAssociationType.getName()+"does not exist.");
		}
		else 
		{
			sc.getModelDao().deleteItemType(it.getId());
		}
		
		
		
		return null;
	}

}
