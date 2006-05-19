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


package de.berlios.gpon.wui.util;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.util.AbstractMappedItem;
import de.berlios.gpon.common.util.ItemMappedById;


public class ItemMap implements Map {

	public static final String ITEM_PROPERTY_NAME = "item";
	public static final String ITEM_PROPERTY_ID = "id";
	
	AbstractMappedItem mi;
	Map baseMap;
	Hashtable additionalAttributes = new Hashtable();
	
	
	public ItemMap(ItemMappedById pMi) 
	{
		mi = pMi;
		baseMap = mi.getMap();
		
		additionalAttributes.put(ITEM_PROPERTY_NAME,mi.getItem());
		additionalAttributes.put(ITEM_PROPERTY_ID,mi.getItem().getId());
	}
	
	
	public int size() {
		return baseMap.size();
	}

	public boolean isEmpty() {
		return baseMap.isEmpty();
	}

	public boolean containsKey(Object obj) {
		return baseMap.containsKey(obj);
	}

	public boolean containsValue(Object obj) {
		return baseMap.containsValue(obj);
	}

	public Object get(Object obj) {
		
		// "item" or "id"
		if (additionalAttributes.containsKey(obj)) {
			return additionalAttributes.get(obj);
		};
		
		// prop names or prop ids
		return mi.getValueObject(obj.toString());
	}

	public Object put(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object remove(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void putAll(Map arg0) {
		// TODO Auto-generated method stub

	}

	public void clear() {
		// TODO Auto-generated method stub

	}

	public Set keySet() {
		return baseMap.keySet();
	}

	public Collection values() {
		return baseMap.values();
	}

	public Set entrySet() {
		return baseMap.entrySet();
	}
	
	public Item getItem () {
		return mi.getItem();
	}
}
