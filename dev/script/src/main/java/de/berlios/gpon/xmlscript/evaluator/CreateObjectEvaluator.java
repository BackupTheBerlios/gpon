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

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.xmlbeans.XmlObject;

import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemProperty;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.types.Value;
import de.berlios.gpon.common.util.ItemMappedByName;
import de.berlios.gpon.persistence.search.SimpleQuery;
import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.CreateObject;
import de.berlios.gpon.xmlscript.Evaluator;
import de.berlios.gpon.xmlscript.ScriptContext;


public class CreateObjectEvaluator implements Evaluator {

	public Object evaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {

		boolean createNew = true;
		
		CreateObject createObject = (CreateObject) xmlObject;

		ItemType itemType = cd.getItemType();

		if (itemType == null) {
			throw new IllegalArgumentException(
					"Item type not set for object creation.");
		}
		
		// Create new ItemMappedByName
		
		Item item = new Item();
		item.setItemType(itemType);
		
		ItemMappedByName mi =
			new ItemMappedByName(item);
		
		
		CallData newCallData =
			new CallData();
		
		// the property evaluator adds the 
		// ItemProperty objects to the mapped
		// item
		newCallData.setMappedItem(mi);
		newCallData.setLevel(cd.getLevel()+1);
		newCallData.setPattern("Property");
		
		DefaultTraversingEvaluator propDe =
			new DefaultTraversingEvaluator();
		
		propDe.evaluate(xmlObject,sc,newCallData);
		
		if (cd.getUpdateKeys()!=null && !cd.getUpdateKeys().isEmpty()) 
		{
			Iterator it = cd.getUpdateKeys().iterator();
			
			StringBuffer sb = new StringBuffer("");
			
			while (it.hasNext()) 
			{
				if (sb.length()>0) {
					sb.append(" and ");
				}
				
				String key = (String)it.next();
				sb.append("${").append(key).append("} = ");
				
				Value valueObject = mi.getValueObject(key);
				
				if (valueObject!=null) 
				{
					String value = valueObject.toNormal();
					try {
					if (!(valueObject.getType().isNumeric())) 
					{
						value = "'"+value+"'";
					}
					} catch (Exception e) {
						throw new EvaluatorException("Unable to get property type",e);						
					}
					sb.append(value);
				}
			}
		
			System.out.println("OrUpdateUsingKeys query: "+sb.toString());
			
			SimpleQuery sq = new SimpleQuery();
			sq.setSpec(sb.toString());
			sq.setType(itemType.getName());
			
			Set items =
				sc.getDataDao().search(sq);
			
			if (items != null && items.size()>0) {
				if (items.size() == 1) 
				{
				  createNew = false;	
				  Item dbItem = (Item)items.toArray(new Item[0])[0];
				  
				  // iterate item properties
				  
				  ItemMappedByName dbMi =
					  new ItemMappedByName(dbItem);
				  
				  dbMi.syncWith(mi);
				  
				  item = dbMi.getItem();
				  newCallData.setMappedItem(dbMi);
				  
				  
				  sc.getDataDao().updateItem(item);
				}
				
				if (items.size()>1) {
				  // FIXME: what to do here	
				  throw new EvaluatorException("Multiple items for: "+sq.getSpec());	
				}
			}
		}
		else {
			System.out.println("No OrUpdateUsingKeys query!");
		}
				
		if (createNew)
		{
			try {
				sc.getDataDao().addItem(item);
			
				System.out.println("Item: "+item);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
			
		// create associations
		DefaultTraversingEvaluator awDe =
			new DefaultTraversingEvaluator();
					
		newCallData.setPattern("AssociateWith");
		awDe.evaluate(xmlObject,sc,newCallData);
		
		System.out.println("[CreateObject] item: "+item);
		
		return item;
	}
}
