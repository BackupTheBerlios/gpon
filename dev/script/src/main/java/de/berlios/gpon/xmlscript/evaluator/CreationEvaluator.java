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
import java.util.List;
import java.util.Set;

import org.apache.xmlbeans.XmlObject;

import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.Creation;
import de.berlios.gpon.xmlscript.Evaluator;
import de.berlios.gpon.xmlscript.ScriptContext;
import de.berlios.gpon.xmlscript.Union;


public class CreationEvaluator implements Evaluator {

	public Object evaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {
		Creation creation = (Creation)xmlObject;
		
		String type = creation.getType();
		
		ItemType itemType =
			sc.getModelDao().findItemTypeByName(type);
		
		if (itemType!=null) {
			// type found in gpon
			System.out.println("[Creation for ]"+itemType.getDescription());
		}
		
		CallData newCallData =
			new CallData();
		
		newCallData.setLevel(cd.getLevel()+1);
		newCallData.setItemType(itemType);
		
		// or update using keys
		DefaultTraversingEvaluator oukDe =
			new DefaultTraversingEvaluator();
		
		newCallData.setPattern("OrUpdateUsingKeys");
		List keys = (List)oukDe.evaluate(xmlObject,sc,newCallData);
		
		if (keys!=null && keys.size()>0) 
		{
			System.out.println("Keys: "+keys);	
			newCallData.setUpdateKeys(keys);
		}
		
		DefaultTraversingEvaluator de =
				new DefaultTraversingEvaluator();
		
		newCallData.setPattern("CreateObject");
		
		de.evaluate(xmlObject,sc,newCallData);
		
		
		Set set = new HashSet();
		
		if(de.getResults()!=null) 
		{
			set.addAll(de.getResults());
		}
		
		if (creation.getId()!=null) {
			sc.setAttribute(creation.getId(),set);
		}
		
		return set;
		
	}

}
