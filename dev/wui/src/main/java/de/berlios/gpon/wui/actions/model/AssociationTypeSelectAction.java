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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.wui.actions.BaseAction;
import de.berlios.gpon.wui.forms.AssociationTypeForm;

public class AssociationTypeSelectAction extends BaseAction 
{
  private static Log log = LogFactory.getLog(AssociationTypeSelectAction.class);

  public AssociationTypeSelectAction()
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    
    if (request.getParameter("objectId")!=null) {
     // lets go and fill
      
     String objectId = request.getParameter("objectId"); 
      
     log.info("Selected objectId: " + objectId);
    
     GponModelDao model = (GponModelDao)getObjectForBeanId("txGponModelDao");

     AssociationType associationType = null;
     
     try {
      associationType = 
          model.findAssociationTypeById(new Long(objectId));
     } catch (Throwable ex) {
          log.error("Unable to get AssociationType with id "+objectId);    
          return mapping.findForward("error");
     } 
      
     if (associationType == null) {
      log.error("AssociationType is null for id "+objectId);   
      return mapping.findForward("error");
     } 
      
     // complexType is set
     AssociationTypeForm associationTypeForm = new AssociationTypeForm();
     
     if (associationTypeForm != null) {
      associationTypeForm.setId(associationType.getId());
      associationTypeForm.setName(associationType.getName());
      associationTypeForm.setDescription(associationType.getDescription());
      associationTypeForm.setBaseAssociationTypeId(associationType.getBaseAssociationTypeId());
      
      associationTypeForm.setItemARoleName(associationType.getItemARoleName());
      associationTypeForm.setItemATypeId(associationType.getItemAType().getId());
      
      associationTypeForm.setMultiplicity(associationType.getMultiplicity());
      
      associationTypeForm.setVisibility(associationType.getVisibility());
      
      associationTypeForm.setItemBRoleName(associationType.getItemBRoleName());
      associationTypeForm.setItemBTypeId(associationType.getItemBType().getId());
      
      associationTypeForm.setPredicates(associationType.getPredicates());
      associationTypeForm.setPredicatesA(associationType.getPredicatesA());
      associationTypeForm.setPredicatesB(associationType.getPredicatesB());
      
      // bind to session
      request.getSession().setAttribute("selectedAssociationTypeForm",associationTypeForm);
      
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
