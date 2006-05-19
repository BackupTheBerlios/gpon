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

import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.search.PropertyCriterion;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.wui.actions.BaseAction;
import de.berlios.gpon.wui.forms.ItemSearchForm;
import de.berlios.gpon.wui.forms.SimpleItemTypeIdForm;

public class PrepareItemSearchAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read requested type
		
		Long itemTypeId = ((SimpleItemTypeIdForm)form).getItemTypeId();
		
		GponModelDao model = (GponModelDao)getObjectForBeanId("txGponModelDao");
		
		ItemType itemType = model.findItemTypeById(itemTypeId);
		
		if (itemType==null) 
		{
			// FIXME: spray errors
			return mapping.getInputForward();
		}
		
		// set type
		ItemSearchForm isf = new ItemSearchForm();
		
		isf.setItemType(itemType);

		// set criterion per property
		Set propDecls =
			itemType.getInheritedItemPropertyDecls();
		
		Iterator it = propDecls.iterator();
		
		while (it.hasNext()) 
		{
			ItemPropertyDecl ipd = (ItemPropertyDecl)it.next();
			isf.setCriterion(ipd.getName(),new PropertyCriterion());
		}
		
		
		request.getSession().setAttribute("ItemSearchForm",isf);
		
		return mapping.findForward("success");
	}

	
	
}
