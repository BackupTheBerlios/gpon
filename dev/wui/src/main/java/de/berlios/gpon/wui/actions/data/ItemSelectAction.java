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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemProperty;
import de.berlios.gpon.common.types.Value;
import de.berlios.gpon.common.util.ItemMappedById;
import de.berlios.gpon.common.util.ItemMappedByName;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.wui.actions.BaseAction;
import de.berlios.gpon.wui.forms.ItemForm;
import de.berlios.gpon.wui.util.ItemMap;

public class ItemSelectAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception 
	{
		
		// Prepopulating as in the struts newbie faq
		
		ItemForm itemForm =
			(ItemForm)form;
		
		GponDataDao data = (GponDataDao)getObjectForBeanId("gponDataDao");
		
		Item item = 
			data.findItemById(itemForm.getItemId());
		
		ItemMappedByName imbn =
			new ItemMappedByName(item);
		
		Iterator it = imbn.getMap().keySet().iterator();
		
		while (it.hasNext()) {
			String propName =
				(String)it.next();
			Value v = imbn.getValueObject(propName);
			
			ItemProperty ip = new ItemProperty();
			
			String value = "";
			
			if (v!=null) {
				value = v.getInput();
			}
			
			ip.setValue(value);
			
			itemForm.setProperty(propName,ip);
			
		}
		
		itemForm.setItem(item);
		
		request.setAttribute("json",imbn.getJson());
		request.setAttribute("itemAsMap",new ItemMap(new ItemMappedById(item)));
		
		return mapping.findForward("success");
	}
}
