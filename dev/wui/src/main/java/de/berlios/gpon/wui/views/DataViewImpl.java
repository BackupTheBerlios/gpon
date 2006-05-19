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


package de.berlios.gpon.wui.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import de.berlios.gpon.common.Association;
import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.util.ItemMappedById;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.wui.util.ItemMap;
import de.berlios.gpon.wui.util.LazyElMap;
import de.berlios.gpon.wui.util.LazyElMapGetHandler;


public class DataViewImpl implements DataView {

	GponDataDao data;

	GponModelDao model;

	private String focusItemId;

	private String associatedSpec;

	// the handler for that lazy map searches
	// all objects for the given key and returns
	// the result list
	LazyElMap focusItemLazyMap = new LazyElMap(new LazyElMapGetHandler() {
		public Object get(Object key) {

			DataViewImpl newDvi = new DataViewImpl();

			newDvi.setData(getData());
			newDvi.setModel(getModel());

			newDvi.setAssociatedSpec(getAssociatedSpec());
			newDvi.setFocusItemId(key.toString());

			return newDvi;
		}
	});

	LazyElMap associatedLazyMap = new LazyElMap(new LazyElMapGetHandler() {
		public Object get(Object key) {

			DataViewImpl newDvi = new DataViewImpl();

			newDvi.setData(getData());
			newDvi.setModel(getModel());

			newDvi.setAssociatedSpec(key.toString());
			newDvi.setFocusItemId(getFocusItemId());

			return newDvi;
		}
	});
	
	public GponDataDao getData() {
		return data;
	}

	public void setData(GponDataDao data) {
		this.data = data;

	}


	public GponModelDao getModel() {
		return model;
	}

	public void setModel(GponModelDao model) {
		this.model = model;
	}

	public List getList() {
	
		Item item = null;
		
		if (getFocusItemId()!=null) 
		{
			item = getData().findItemById(new Long(getFocusItemId()));
		}

		if (item!=null && getAssociatedSpec()!=null) 
		{
			StringTokenizer strtok =
				new StringTokenizer(getAssociatedSpec(),"|");
			
			List list = Collections.list(strtok);
			
			if (list.size()!=2) 
			{
				throw new IllegalArgumentException("| delimited association spec "+getAssociatedSpec()+" does not contain 2 elements");
			}
		
			String side = (String)list.get(0);
			String association = (String)list.get(1);
			
			Set associations = null;
			
			if (side.equalsIgnoreCase("a")) 
			{
				associations = item.getAssociationsA();
			}
			else 
			{
				associations = item.getAssociationsB();
			}
			
			List result = new ArrayList();
			
			if (associations!=null) {
				Iterator it = associations.iterator();
			
				while (it.hasNext()) 
				{
					Association assoc = (Association)it.next();
					
					if (assoc.getAssociationType().getDescription().equals(association)) 
					{
						if (side.equalsIgnoreCase("a")) 
						{
							result.add(new ItemMap(new ItemMappedById(assoc.getItemB())));
						}
						else 
						{
							result.add(new ItemMap(new ItemMappedById(assoc.getItemA())));
						}	
					}
				}
				
			}
			
			return result;
		}
		
		return null;
	}

	public Map getFocusItem() {
		// TODO Auto-generated method stub
		return focusItemLazyMap;
	}

	public Map getAssociatedItems() {
		// TODO Auto-generated method stub
		return associatedLazyMap;
	}

	public String getAssociatedSpec() {
		return associatedSpec;
	}

	public void setAssociatedSpec(String associatedSpec) {
		this.associatedSpec = associatedSpec;
	}

	public String getFocusItemId() {
		return focusItemId;
	}

	public void setFocusItemId(String focusItemId) {
		this.focusItemId = focusItemId;
	}
}
