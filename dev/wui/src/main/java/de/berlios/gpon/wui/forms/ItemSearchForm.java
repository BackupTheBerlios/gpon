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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.path.Path;
import de.berlios.gpon.common.util.search.PropertyCriterion;

public class ItemSearchForm extends ValidatorForm {

	private ItemType itemType;

	private HashMap map = new HashMap();

	// paths to one-nodes in OneToMany-Associations
	private List pathsToParents;

	int resultsPerPage = 10;

	List resultList;

	// map of displayed properties
	HashMap displayPropertyMap = new HashMap();

	public Object getDisplayProperty(String key) {
		return displayPropertyMap.get(new Long((String) key));
	}

	public void setDisplayProperty(String key, Object value) {
		displayPropertyMap.put(new Long((String) key), value);
	}

	// mapped property (I wish I had known earlier)
	HashMap associatedPropertyMap = new HashMap();

	public Object getAssociatedProperty(String key) {
		return associatedPropertyMap.get(key);
	}

	public void setAssociatedProperty(String key, Object value) {
		associatedPropertyMap.put(key, value);
	}

	public PropertyCriterion getCriterion(String key) {
		// trick: if this form is resetted or created
		// by the framework, we create a PropertyCriterion object
		// for every unknown key

		if (!map.containsKey(key)) {
			map.put(key, new PropertyCriterion());
		}

		return (PropertyCriterion) map.get(key);
	}

	public void setCriterion(String key, PropertyCriterion p) {
		map.put(key, p);
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public Map getMap() {
		return map;
	}

	public List getResultList() {
		return resultList;
	}

	public static String[] splitAssociatedPropertyKey(String key) 
	  {
		  return key.split("\\|");
	  }

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public Hashtable getPathDisplayForDescriptor() 
	{
		Hashtable map = new Hashtable();
		
		if (getPathsToParents()!=null) {
			Iterator it = getPathsToParents().iterator();
		
			while (it.hasNext()) 
			{
				Path path = (Path)it.next();
				map.put(path.getDigest(),path.getDisplayWithoutStart());
			}
		}
	
		return map;
	}

	public int getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}

	public List getPathsToParents() {
		return pathsToParents;
	}

	public void setPathsToParents(List pathsToParents) {
		this.pathsToParents = pathsToParents;
	}

	public int getPathsToParentsSize() {
		if (pathsToParents == null)
			return 0;

		return pathsToParents.size();
	}

	// used by PrepareItemSearchAction
	public void prefillOwnPropertyMap() {
		if (itemType != null
				&& itemType.getInheritedItemPropertyDecls() != null) {
			Set ipds = itemType.getInheritedItemPropertyDecls();

			Iterator ipdIt = ipds.iterator();

			while (ipdIt.hasNext()) {
				ItemPropertyDecl ipd = (ItemPropertyDecl) ipdIt.next();

				displayPropertyMap.put(ipd.getId(), ipd.getId().toString());
			}

		}
	}

//	 map: item property key (own or with path) -> Object not null

	public Map getDisplay() 
	{
		Hashtable map = new Hashtable();
		
		Iterator ownKeyIt = displayPropertyMap.keySet().iterator();
		
		while (ownKeyIt.hasNext()) 
		{
			Object key = ownKeyIt.next();
			
			map.put(key,Boolean.TRUE);
		}
		
		Iterator assKeyIt = associatedPropertyMap.keySet().iterator();
		
		while (assKeyIt.hasNext()) 
		{
			Object key = assKeyIt.next();
			
			map.put(key,Boolean.TRUE);
		}
		
		return map;
	}

	public int getDisplayCount() 
	{
		Map map = getDisplay();
		
		if (map==null)
			return 0;
		
		return map.size();
	}

	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		
		displayPropertyMap = new HashMap();
		associatedPropertyMap = new HashMap();
		
	}
	
	public HashMap getAssociatedPropertyMap() {
		return associatedPropertyMap;
	}

	public Set getAssociatedPropertyKeys() 
	{
		return new TreeSet(associatedPropertyMap.keySet());
	}

	public Hashtable getPathDisplayForAssociatedPropertyKey() 
	{
		if (getAssociatedPropertyMap()==null || getAssociatedPropertyMap().isEmpty()) 
		{
			return null;
		}
			
		Hashtable map = new Hashtable();
		
		Hashtable pathDisplayMap = getPathDisplayForDescriptor();
		
		Iterator it = getAssociatedPropertyMap().keySet().iterator();
	
		while (it.hasNext()) 
		{
			String key = (String)it.next();
			String[] keySplit = ItemSearchForm.splitAssociatedPropertyKey(key);
			
			if (pathDisplayMap.containsKey(keySplit[0])) 
			{
				map.put(key,pathDisplayMap.get(keySplit[0]));
			}
			else 
			{
				map.put(key,key);
			}
		}
		
		return map;
	}

	
	

}
