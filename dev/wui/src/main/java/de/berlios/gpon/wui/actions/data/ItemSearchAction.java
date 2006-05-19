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


package de.berlios.gpon.wui.actions.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.ItemMappedById;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.search.QueryStringHelper;
import de.berlios.gpon.persistence.search.SimpleQuery;
import de.berlios.gpon.wui.actions.BaseAction;
import de.berlios.gpon.wui.forms.ItemSearchForm;
import de.berlios.gpon.wui.util.ItemMap;

public class ItemSearchAction extends BaseAction {

	private static Log log = LogFactory.getLog(ItemSearchAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ItemSearchForm isf = (ItemSearchForm) form;
		
		// TODO: convert search criteria to a query
		
		ItemType it = isf.getItemType();
		
		log.debug("Item type: "+it.getDescription());
		
		SimpleQuery sq = new SimpleQuery();
		
		sq.setType(it.getName());
		
		String queryString 
			= QueryStringHelper.getQuery(it, isf.getMap());
		
		log.debug("Query string: "+queryString);
		
		sq.setSpec(queryString);
		
		GponDataDao data = (GponDataDao)getObjectForBeanId("gponDataDao");
		
		Set resultSet = data.search(sq);
		
		List idMapped =
			new ArrayList();
		
		Iterator iterator = resultSet.iterator();
		
		while (iterator.hasNext()) 
		{
			Item item = (Item)iterator.next();
			
			ItemMappedById mI =
				new ItemMappedById(item);
			
			idMapped.add(new ItemMap(mI));
		}
		
		isf.setResultList(idMapped);
		
		return mapping.findForward("success");
	}

}
