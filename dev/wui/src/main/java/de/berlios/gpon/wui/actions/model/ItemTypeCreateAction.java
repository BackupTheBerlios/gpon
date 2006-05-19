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


package de.berlios.gpon.wui.actions.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.wui.actions.BaseAction;
import de.berlios.gpon.wui.forms.ItemTypeForm;

public class ItemTypeCreateAction extends BaseAction 
{

	
/**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   * @return 
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
  
    ItemTypeForm itemTypeForm = (ItemTypeForm)form;
  
    String name           = itemTypeForm.getName();
    String description  = itemTypeForm.getDescription(); 
    Long baseItemTypeId = itemTypeForm.getBaseItemTypeId(); 
  
    GponModelDao model = (GponModelDao)getObjectForBeanId("txGponModelDao");

    try{	
      ItemType it = new ItemType();
      
      // name
      it.setName(name);
      
      // description
      it.setDescription(description);
      
      // basetype 
      if (baseItemTypeId!=null) 
      {
    	  ItemType baseType =
    		  model.findItemTypeById(baseItemTypeId);
    	  
    	  if (baseType!=null)
    	  {
    		  it.setBaseType(baseType);
    	  }
      }
      
      model.addItemType(it);
    } catch (Throwable ex) {
      ActionErrors errors = new ActionErrors();
      ActionError err = new ActionError("itemtype.creationerror.msg",
                                        ex.getMessage());
      
      errors.add(ActionErrors.GLOBAL_ERROR,err);
      
      this.saveErrors(request,errors);
                                        
      return mapping.findForward("error");
    }
    
    return mapping.findForward("success");
   }
}
