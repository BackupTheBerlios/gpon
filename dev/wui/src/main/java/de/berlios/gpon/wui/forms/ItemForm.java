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


package de.berlios.gpon.wui.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemProperty;

public class ItemForm extends ValidatorForm {

	private Long itemId;
	private Item item;
	private HashMap map = new HashMap();
	private HashMap assMap = new HashMap();
	
	public ItemProperty getProperty(String key) 
    {
		// trick: if this form is resetted or created
		// by the framework, we create a Property object
		// for every unknown key
		
		if (!map.containsKey(key)) {
			map.put(key,new ItemProperty());
		}
		
        return (ItemProperty)map.get(key);
    }
    
    public void setProperty(String key, ItemProperty p) 
    { 
        map.put(key, p); 
    }

    public String getAssociated(String key) 
    {
    	if (!assMap.containsKey(key)) {
			assMap.put(key,new String());
		}

    	return (String)assMap.get(key); 
    }
    
    public void setAssociated(String key, String value) 
    {
    	assMap.put(key,value); 
    }
    
    public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public Map getMap() {return map;}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		map.clear();
		assMap.clear();
	}

	public HashMap getAssMap() {
		return assMap;
	}
}
