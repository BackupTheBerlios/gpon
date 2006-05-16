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


package de.berlios.gpon.common.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemProperty;
import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.types.Value;
import de.berlios.gpon.common.types.repository.ValueTypeRepository;


public abstract class AbstractMappedItem  {

	private static Log log = LogFactory.getLog(AbstractMappedItem.class);
	
	public static final int NORMAL_FORM = 1;
	public static final int INPUT_FORM = 2;
	
	Item item = null;

	AbstractMappedItemType mappedItemType = null;

	protected Hashtable propertyMap = new Hashtable();

	public AbstractMappedItem() {
	}
	
	public boolean hasProperty(String key) {
		return propertyMap.containsKey(key);
	}
	
	
	/** Copies values from other into this object. New properties are added, orphan entries will be removed.
	 * @param other the AbstractMappedItem to sync with
	 */
	public void syncWith(AbstractMappedItem other) 
	{
		Map myMap =
			getMap();
		
		Map otherMap =
			other.getMap();

		// nothing to do
		if (otherMap==null && myMap==null) 
		{
			return;
		}
		
			
		Set myKeys = null;
		Set otherKeys = null;
		
		if (myMap!=null) 
		{
			myKeys = myMap.keySet();
		}
				
		if (otherMap!=null) 
		{
			otherKeys = otherMap.keySet();
		}
		
		if (myKeys!=null) {
		// orphan = my.keys - other.keys
		Set orphan = new HashSet();
		orphan.addAll(myKeys);
		if (otherKeys!=null) {
			orphan.removeAll(otherKeys);
		}
		if (!orphan.isEmpty()) 
		{
			Iterator it =
				orphan.iterator();
			
			while (it.hasNext()) 
			{
				Object key =
					it.next();
				log.info("Removing attribute "+key);
				this.removeValue(key.toString());
			}
		}
		}
		if (otherKeys!=null) {
			Iterator it = otherKeys.iterator();
			
			while (it.hasNext()) 
			{
				Object key = it.next();
				
				log.info("Setting value for "+key+" to "+other.getValueInNormalForm(key.toString()));
				this.setValue(key.toString(),other.getValueInNormalForm(key.toString()),NORMAL_FORM);
			}
		}
	}
	
	
	/**
	 * Simply sets the property identified by declaration key to the given value
	 * 
	 * @param key is the identifier for the property declaration
	 * @param value is the property value
	 */
	public void setValue(String key, String value, int form) {
		if (getMappedItemType().hasItemPropertyDeclaration(key)) {
			ItemProperty prop = null;

			if (propertyMap.containsKey(key)) {
				prop = (ItemProperty)propertyMap.get(key);
				
			} else {
				prop = new ItemProperty();
				prop.setItem(this.item);
				prop.setPropertyDecl(getMappedItemType().getItemPropertyDecl(
						key));
				// keep item and property map in sync
				propertyMap.put(key, prop);
				
				if (item.getProperties()==null) {
					item.setProperties(new HashSet());
				}
				
				item.getProperties().add(prop);
			}

			if (form==NORMAL_FORM) {
				prop.setValue(value);
			} else {
				// INPUT_FORM
				Value valueObject = ValueTypeRepository
				.getValueTypeObjectByTypeId(prop.getPropertyDecl()
						.getPropertyValueTypeId());
				
				valueObject.setDataInInputForm(value);
				
				prop.setValue(valueObject.toNormal());
			}
			
			
			
		} else {
			throw new IllegalArgumentException(
					"Unknown property declaration for key " + key);
		}
	}

	public void removeValue(String key) 
	{
		if (propertyMap.containsKey(key)) {
			ItemProperty prop = (ItemProperty)propertyMap.get(key);

			// remove from map
			propertyMap.remove(key);
			// remove from item
			if (!item.getProperties().remove(prop)) 
			{
			 throw new IllegalArgumentException("Unable to remove property "+key);
			}
			
		}
	}
	
	public String getValueInNormalForm(String key) 
	{
		if (!propertyMap.containsKey(key)) {
			return null;
		}
		
		ItemProperty ip =
			(ItemProperty)propertyMap.get(key);
	
		if (ip!=null) {
			return ip.getValue();
		}
		
		return null;
	}
	
	public Value getValueObject(String key) {
		
		if (!propertyMap.containsKey(key)) {
			return null;
		}
	
		
		ItemProperty ip =
		 (ItemProperty)propertyMap.get(key);
		
		if (ip.getValue()==null || ip.getValue().length()==0) {
			return null;
		}
		
		Value value = ValueTypeRepository
				.getValueTypeObjectByTypeId(ip.getPropertyDecl()
						.getPropertyValueTypeId());

		// item property is always in database normal form
		// i.e. date is stored as YYYYMMDDhhmmss
		value.setDataInNormalForm(ip.getValue());
		
		
		return value;
	}
	
	protected void init(Item item) {
		if (item == null)
			throw new IllegalArgumentException(
					"cannot initialize with null item.");

		if (item.getItemType() == null)
			throw new IllegalArgumentException(
					"cannot initialize without item type.");

		this.item = item;
		this.mappedItemType = getMappedItemType(item.getItemType());
		
		if (item.getProperties() != null) {
			Iterator it = item.getProperties().iterator();
			while (it.hasNext()) {
				ItemProperty ip = (ItemProperty) it.next();

				ItemPropertyDecl ipd = ip.getPropertyDecl();

				if (ipd == null)
					throw new IllegalArgumentException(
							"item property declaration not found. giving up.");

				Object hashKey = getHashKeyForItemPropertyDeclaration(ipd);

				propertyMap.put(hashKey, ip);
			}
		}
	}

	// has to be implemented by subclasses
	protected abstract String getHashKeyForItemPropertyDeclaration(
			ItemPropertyDecl ipd);

	protected abstract AbstractMappedItemType getMappedItemType(ItemType itemType);

	private AbstractMappedItemType getMappedItemType() {
		return mappedItemType;
	}
	
	public Item getItem() 
	{
		return item;		
	}
	
	public Map getMap() {
		return propertyMap;
	}

	public String getJson() 
	{
		//
		// {'<prop 1>':'<value 1>', .... , '<prop n>':'<value n>' }
		//
		
		StringBuffer buf = new StringBuffer();
		
		buf.append("{");
		
		buf.append("\"Id\":"+getItem().getId());
		
		List list = new ArrayList(getMap().keySet());
		
		if (list.size()>0) 
		{
		
			for (int i = 0; i < list.size(); i++) 
			{
				buf.append(" , ");
				String key = (String)list.get(i);
				buf.append("\""+key+"\" : ");
				if (getValueObject(key)!=null) {
					buf.append("\""+getValueObject(key).toInput()+"\"");
				}
				else
				{
					buf.append("\"\"");
				}
			}
			
		}
		buf.append("}");
		
		return buf.toString();
	}
	
}
