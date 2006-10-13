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
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import de.berlios.gpon.common.Association;
import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.Item;
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
		
		List associationList = null;
		
		if (itemForm.getAssMap() != null
				&& itemForm.getAssMap().keySet() != null) {
			 associationList = getAssociationList(itmbn.getItem(), itemForm
					.getAssMap());
		}
		
		try {
			data.addItem(itmbn.getItem(),associationList);
		} catch (GponDataDaoException ex) {
			if (ex.getValidationErrors() != null) {
				saveErrors(request, convertValidationErrors(ex
						.getValidationErrors()));
				return mapping.getInputForward();
			}
		}
		
		
		return mapping.findForward("success");
		
	}

	List getAssociationList(Item item, Map assocMap) {
	
		List assocList = new ArrayList();
	
		GponModelDao model = (GponModelDao) getObjectForBeanId("txGponModelDao");
		GponDataDao data = (GponDataDao) getObjectForBeanId("txGponDataDao");
	
		Set keySet = assocMap.keySet();
	
		Iterator keyIt = keySet.iterator();
	
		while (keyIt.hasNext()) {
	
			// key is like [ab]_<assoc type id> -> a_4711 or b_815
			// value: id [',' id]*
			String key = (String) keyIt.next();
	
			String val = (String) assocMap.get(key);
			
			if (val.trim().length()==0)
			{
				continue;
			}
	
			String[] keySplit = key.split("_");
			String[] valSplit = val.split(",");
	
			AssociationType at = model.findAssociationTypeById(new Long(
					keySplit[1]));
	
			for (int i = 0; i < valSplit.length; i++) {
				Long id = new Long(valSplit[i]);
				Association assoc = new Association();
				assoc.setAssociationType(at);
	
				if (keySplit[0].equals("a")) {
					assoc.setItemA(item);
					assoc.setItemB(data.findItemById(id));
				} else {
					assoc.setItemB(item);
					assoc.setItemA(data.findItemById(id));
				}
	
				assocList.add(assoc);
			}
		}
	
		return assocList;
	
	}

}
