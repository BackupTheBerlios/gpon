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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import de.berlios.gpon.common.ItemProperty;
import de.berlios.gpon.common.util.ItemMappedByName;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponDataDaoException;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.wui.actions.BaseAction;
import de.berlios.gpon.wui.forms.ItemForm;

public class ItemCreateAction extends BaseAction {

	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
	
		
		ItemForm itemForm = (ItemForm)form;
		
		// refresh itemForm
		// TODO: clean solution
		
		GponModelDao model = (GponModelDao)getObjectForBeanId("txGponModelDao");
		
		System.out.println("!!!Refresh!!!");
		
		model.refresh(itemForm.getItem().getItemType());
		
		
		// construct item and add it
		
		ItemMappedByName itmbn =
			new ItemMappedByName(itemForm.getItem());
		
		Map map = itemForm.getMap();
		
		Iterator it = map.keySet().iterator();
		
		while (it.hasNext()) 
		{
			String propName = 
				(String)it.next();
			
			ItemProperty ip =
				(ItemProperty)map.get(propName);
			
			String value = ip.getValue();
			
			if (value !=null && value.trim().length()>0) {
				try {
					itmbn.setValue(propName,
						   	   	   value,
						   	       ItemMappedByName.INPUT_FORM);
				} catch (Exception ex) {
					saveErrors(request,convertException(ex));
					return mapping.getInputForward();
				}
			}
		}
		
		GponDataDao data = (GponDataDao)getObjectForBeanId("txGponDataDao");
		
		try 
		{
			data.addItem(itmbn.getItem());
		} 
		catch (GponDataDaoException ex) 
		{
			if (ex.getValidationErrors()!=null) {
				saveErrors(request,convertValidationErrors(ex.getValidationErrors()));
				return mapping.getInputForward();
			}	
		}
		
		return mapping.findForward("success");
		
	}

}
