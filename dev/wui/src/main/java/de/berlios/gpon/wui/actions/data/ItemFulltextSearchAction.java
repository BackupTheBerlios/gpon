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

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.ItemMappedById;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.persistence.search.SimpleQuery;
import de.berlios.gpon.wui.actions.BaseAction;
import de.berlios.gpon.wui.forms.ItemFulltextSearchForm;

public class ItemFulltextSearchAction extends BaseAction {

	public ActionForward execute(
		ActionMapping mapping, 
		ActionForm form, 
		HttpServletRequest request, 
		HttpServletResponse response) 
	throws Exception {
		
		ItemFulltextSearchForm ifsf =
				(ItemFulltextSearchForm)form;
		
		GponDataDao  data = (GponDataDao)getObjectForBeanId("gponDataDao");
		GponModelDao model = (GponModelDao)getObjectForBeanId("gponModelDao");
		
		ItemType it =
			model.findItemTypeById(ifsf.getTypeId());
		
		Set props = it.getInheritedItemPropertyDecls();
		
		// get typic properties and search for them
		
		Iterator iterator = 
			props.iterator();
		
		String query = "";
		
		while (iterator.hasNext()) 
		{
			ItemPropertyDecl ipd =
				(ItemPropertyDecl)iterator.next();
			
			if (ipd.getTypic().booleanValue()) 
			{
				if (query.length()>0) 
				{
					query = query + "||";
				}
				
				query = query + "${"+ipd.getName()+"}";
				
			}
		}
		
		query = "lower("+query + ") like lower('"+ifsf.getSearchString()+"%')";
		
		SimpleQuery sq = new SimpleQuery();
		
		 
		
		sq.setType(it.getName());
		sq.setSpec(query);
				
		Set resultSet = data.search(sq);
		
		String json = getJson(it,resultSet);
		
		request.setAttribute("json",json);
		
		return mapping.findForward("success");
		
	}

	// result should look like that:
	// [
	//  ['Bob, b@ob.de',1],
	//  ['Brett, b@rett.net',23],
	//  ['Pitti, p@it.ti',233]
	// ]
	//
	
	private String getJson(ItemType it, Set items) 
	{
		StringBuffer jsonBuffer = new StringBuffer();
		
		jsonBuffer.append("[\n");
		
		Iterator itemIt = items.iterator();
		int itemCount = 0;
		
		Set propDecls = it.getInheritedItemPropertyDecls();
		
		
		while (itemIt.hasNext()) 
		{
			if (itemCount>0) 
			{
				jsonBuffer.append(",");
			}
			jsonBuffer.append(" [\n");
			Item item = (Item)itemIt.next();
			
			ItemMappedById itemMapped =
				new ItemMappedById(item);
			
			Iterator propDeclIt = propDecls.iterator();
			int propCount=0;
			jsonBuffer.append(" \"");
			
			// TODO: ordered output by rank
			while (propDeclIt.hasNext()) 
			{
				ItemPropertyDecl ipd =
					(ItemPropertyDecl)propDeclIt.next();
				
				if (ipd.getTypic().booleanValue()) 
				{	
					if (propCount>0) {
						jsonBuffer.append(",");
					}
					
					if (itemMapped.hasProperty(ipd.getId().toString())) 
					{
						jsonBuffer.append(itemMapped.getValueObject(ipd.getId().toString()).toInput());
					}
					else 
					{
						jsonBuffer.append("-");
					}
					
					propCount++;
				}
				
			}			
			jsonBuffer.append("\", "+item.getId()+" ]\n");
			itemCount++;
		}
			
		jsonBuffer.append("]");
		
		return jsonBuffer.toString();
	}
		
	
	
}
