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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.wui.actions.BaseAction;
import de.berlios.gpon.wui.forms.ItemTypeForm;

public class ItemTypeSelectAction extends BaseAction 
{
  private static Log log = LogFactory.getLog(ItemTypeSelectAction.class);

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
  
    if (request.getParameter("objectId")!=null) {
     // lets go and fill
      
     String objectId = request.getParameter("objectId"); 
      
     log.info("Selected objectId: " + objectId);
    
     GponModelDao model = (GponModelDao)getObjectForBeanId("txGponModelDao");
     ItemType it = null;
     
     try {
      it = 
          model.findItemTypeById(new Long(objectId));
     } catch (Throwable ex) {
          log.error("Unable to get ComplexItemType with id "+objectId);    
          return mapping.findForward("error");
     } 
      
     if (it == null) {
      log.error("Unable to get ComplexItemType is null for id "+objectId);   
      return mapping.findForward("error");
     } 
      
     // complexType is set
     ItemTypeForm itemTypeForm = new ItemTypeForm();
     
     if (itemTypeForm != null) {
      itemTypeForm.setId(it.getId());
      itemTypeForm.setName(it.getName());
      itemTypeForm.setDescription(it.getDescription());
      if (it.getBaseType()!=null) {
    	  itemTypeForm.setBaseItemTypeId(it.getBaseType().getId());
      }
      
      itemTypeForm.setPropertyDecl(
        (ItemPropertyDecl[])it.getInheritedItemPropertyDecls().toArray(new ItemPropertyDecl[0]));
        
      log.info("Item type props: "+it.getInheritedItemPropertyDecls());
      
      itemTypeForm.setPropertyDeclDelete(new boolean[it.getInheritedItemPropertyDecls().size()]);  
        
      // New Properties have to be preinitialized  
      
      ItemPropertyDecl[] newPropertyDecl = new ItemPropertyDecl[20];
      
      for (int i = 0; i < 20; i++) {
        newPropertyDecl[i] = new ItemPropertyDecl();
      }   
      
      itemTypeForm.setNewPropertyDecl(newPropertyDecl);  
      
      // bind to session
      request.getSession().setAttribute("selectedItemTypeForm",itemTypeForm);
      request.getSession().setAttribute("selectedItemType",it);
      
      
     } else {
      log.error("itemTypeForm is null");   
      return mapping.getInputForward();
     }
    } else {
     log.error("No objectId parameter!");
     return mapping.getInputForward();
    }
  
    return mapping.findForward("success");
  }
}
