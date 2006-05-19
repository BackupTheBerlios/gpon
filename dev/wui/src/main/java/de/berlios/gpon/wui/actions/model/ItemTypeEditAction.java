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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.ItemTypeMappedById;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.wui.actions.BaseAction;
import de.berlios.gpon.wui.forms.ItemTypeForm;

public class ItemTypeEditAction extends BaseAction
{
  private static Log log = LogFactory.getLog(ItemTypeEditAction.class);

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
  
    // Action Errors
    ActionErrors errors = new ActionErrors();
    
    ItemTypeForm itemTypeForm = (ItemTypeForm)form;
    
    Long   id             = itemTypeForm.getId();
    String name           = itemTypeForm.getName();
    String description    = itemTypeForm.getDescription();
    Long   baseItemTypeId = itemTypeForm.getBaseItemTypeId();
  
    log.info("Edit item type: [id="+id+", name="+name+", description="+description+"]");
    
    // now do the update
 
    
    
    try {
      GponModelDao model = (GponModelDao)getObjectForBeanId("txGponModelDao");  
    
      // extract the complex form content as a complex item type
      ItemType it = model.findItemTypeById(id);
      
      it.setName(name);
      it.setDescription(description);
      
      // base type
      if (baseItemTypeId==null) {
       it.setBaseType(null); 
      } else {
       it.setBaseType(model.findItemTypeById(baseItemTypeId));  
      }
      
      updateItemTypeByForm(it,itemTypeForm);   
      
      // delegate 
      model.updateItemType(it);
      
    } catch (Throwable ex) {
      ActionError err = new ActionError("itemtype.updateerror.msg",
                                        ex.getMessage());
      
      errors.add(ActionErrors.GLOBAL_ERROR,err);
      
      this.saveErrors(request,errors);
      
      log.error("Exception",ex);
      
      return mapping.findForward("error");  
    }   
  
    return mapping.findForward("success");
  }
   
  
  private void updateItemTypeByForm(ItemType it,ItemTypeForm itemTypeForm) 
  {
	  
	  ItemTypeMappedById mIt =
		  new ItemTypeMappedById(it);
	  
	  // for all in we set the values
	  
	  ItemPropertyDecl[] decls = itemTypeForm.getPropertyDecl();
	  
	  if (decls!=null) {
		  for (int i=0; i < decls.length; i++) {
			  ItemPropertyDecl ipd =
				  decls[i];
			  mIt.setItemPropertyDecl(ipd.getId().toString(),ipd);
		  }
	  }
	  
	  
	  // for all new we set the values
	  
	  decls = itemTypeForm.getFilledNewPropertyDecl();
	  
	  int newId = -1;
	  
	  if (decls!=null) {
		  for (int i=0; i < decls.length; i++) {
			  newId--;
			  ItemPropertyDecl ipd =
				  decls[i];
			  mIt.setItemPropertyDecl(new Long(newId).toString(),ipd);
		  }
	  }
	  
	  // for all deleted we remove 
	  decls = itemTypeForm.getPropertyDeclMarkedForDeletion();
	  
	  if (decls!=null) {
		  for (int i=0; i < decls.length; i++) {
			  ItemPropertyDecl ipd =
				  decls[i];
			  mIt.removeItemPropertyDecl(ipd.getId().toString());
		  }
	  }
  }
  
}
