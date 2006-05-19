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


import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.wui.actions.BaseAction;
import de.berlios.gpon.wui.forms.AssociationTypeForm;

public class AssociationTypeDeleteAction extends BaseAction 
{
  public AssociationTypeDeleteAction()
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    AssociationTypeForm itemTypeForm = (AssociationTypeForm)form;
    
    Long id           = itemTypeForm.getId();

    ActionErrors errors = new ActionErrors();
    
    GponModelDao model = (GponModelDao)getObjectForBeanId("txGponModelDao");

    
    try {    
      model.deleteAssociationType(id); 
    } catch (Throwable ex) {
      ActionError err = new ActionError("itemtype.deleteerror.msg",
                                        ex.getMessage());
      
      errors.add(ActionErrors.GLOBAL_ERROR,err);
      
      this.saveErrors(request,errors);
                                        
      return mapping.findForward("error");
    }
    
    return mapping.findForward("success");
  
  }
}
