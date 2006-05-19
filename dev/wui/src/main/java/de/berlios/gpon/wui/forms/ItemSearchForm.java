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
import java.util.List;
import java.util.Map;


import org.apache.struts.validator.ValidatorForm;

import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.search.PropertyCriterion;

public class ItemSearchForm extends ValidatorForm {

	private ItemType itemType;
	private HashMap map = new HashMap();
	
	int resultsPerPage = 10;
	
	List resultList;
	
	public PropertyCriterion getCriterion(String key) 
    {
		// trick: if this form is resetted or created
		// by the framework, we create a PropertyCriterion object
		// for every unknown key
		
		if (!map.containsKey(key)) {
			map.put(key,new PropertyCriterion());
		}
		
        return (PropertyCriterion)map.get(key);
    }
    
    public void setCriterion(String key, PropertyCriterion p) 
    { 
        map.put(key, p); 
    }

    public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	
	public Map getMap() {return map;}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public int getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}
}
