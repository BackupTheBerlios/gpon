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


package de.berlios.gpon.wui.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import de.berlios.gpon.common.ItemPropertyDecl;


public class ItemTypeForm extends ValidatorForm
{

    private static Log log = LogFactory.getLog(ItemTypeForm.class);

    Long id;
    String name;
    String description;
    Long baseItemTypeId;
    ItemPropertyDecl[]  propertyDecl = new ItemPropertyDecl[50];
    ItemPropertyDecl[]  newPropertyDecl = new ItemPropertyDecl[50]; 
    boolean[]           propertyDeclDelete;

  public void setId(Long id)
  {
    this.id = id;
  }


  public Long getId()
  {
    return id;
  }


  public void setName(String name)
  {
    this.name = name;
  }


  public String getName()
  {
    return name;
  }


  public void setDescription(String description)
  {
    this.description = description;
  }


  public String getDescription()
  {
    return description;
  }


  public void setBaseItemTypeId(Long baseItemTypeId)
  {
    this.baseItemTypeId = baseItemTypeId;
  }


  public Long getBaseItemTypeId()
  {
    return baseItemTypeId;
  }


  public void setPropertyDecl(ItemPropertyDecl[] propertyDecl)
  {
    this.propertyDecl = propertyDecl;
  }


  public ItemPropertyDecl[] getPropertyDecl()
  {
    return propertyDecl;
  }


  public void setNewPropertyDecl(ItemPropertyDecl[] newPropertyDecl)
  {
    this.newPropertyDecl = newPropertyDecl;
  }


  public ItemPropertyDecl[] getNewPropertyDecl()
  {
    return newPropertyDecl;
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
  {
  
    // global error indicator
    boolean hasIncompleteContent = false;
    ActionErrors errors = new ActionErrors();
  
    if (this.getName()==null || this.getName().trim().length() == 0) {
          hasIncompleteContent = true;
          errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.required","Name"));       
    }
  
    if (this.getDescription()==null || this.getDescription().trim().length() == 0) {
          hasIncompleteContent = true;
          errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.required","Beschreibung"));       
    }
  
    if (this.getBaseItemTypeId()!=null && this.getBaseItemTypeId().longValue() <= 0) {
      // Struts converts "" -> 0L 
      this.setBaseItemTypeId(null);    
    }
  
  
    // property declarations
    
    if (this.getPropertyDecl() != null) {
    
      ItemPropertyDecl[] propertyDecls = this.getPropertyDecl();
      
      for (int i = 0; i < propertyDecls.length; i++) {
      
        ItemPropertyDecl propDecl = propertyDecls[i];
      
        log.info("PropertyDecl "+i+" :"+propDecl);
  
        if (propDecl!=null && !propDecl.isComplete()) {
          errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("itempropertydecl.update.incomplete.msg",propDecl+""));       
          hasIncompleteContent=true;
        }
  
      }
    }
    else {
      log.info("propertyDecl empty");
    }
  
    if (this.getNewPropertyDecl() != null) {
    
      ItemPropertyDecl[] propertyDecls = this.getNewPropertyDecl();
      
      for (int i = 0; i < propertyDecls.length; i++) {
        ItemPropertyDecl propDecl = 
          propertyDecls[i];
        
        if (propDecl!=null && propDecl.isPartial()) {
          errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("itempropertydecl.update.incomplete.msg",propDecl+""));       
          hasIncompleteContent=true;
        }
      }
    }
    else {
      log.info("newPropertyDecl empty");
    }
  
    if (getPropertyDeclDelete()!=null) {
      for (int i=0; i < getPropertyDeclDelete().length; i++) {
        log.debug("Boolean "+i+" is "+getPropertyDeclDelete()[i]);
      }
    } else {
      log.info("Delete Array is null!");
    }
  
    if (hasIncompleteContent) {
      return errors;
    }
    else {
      return null;
    }  
  }  


  public void setPropertyDeclDelete(boolean[] propertyDeclDelete)
  {
    this.propertyDeclDelete = propertyDeclDelete;
  }


  public boolean[] getPropertyDeclDelete()
  {
    return propertyDeclDelete;
  }

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    log.info("reset called on this");
    
    // checkboxes
    
    if (getPropertyDeclDelete()!=null) {
      setPropertyDeclDelete(new boolean[getPropertyDeclDelete().length]);
    }
    
    if (getPropertyDecl()!=null) {
      ItemPropertyDecl[] propDecls = getPropertyDecl();
      
      for (int i=0; i< propDecls.length ; i++) 
      {
        if (propDecls[i]!=null) 
        {
          propDecls[i].setMandatory(Boolean.FALSE);
          propDecls[i].setTypic(Boolean.FALSE);
        }
      }
    }
  }
  
  public ItemPropertyDecl[] getPropertyDeclMarkedForDeletion() {
  
    ItemPropertyDecl[] propertyDecl       = getPropertyDecl();
    boolean[]          propertyDeclDelete = getPropertyDeclDelete();
    
    if (propertyDecl!=null && propertyDeclDelete!=null &&
        propertyDecl.length == propertyDeclDelete.length) 
    {
      ArrayList list = new ArrayList();

      for (int i = 0; i < propertyDecl.length; i++) {
        if (propertyDeclDelete[i]==true) {
          list.add(propertyDecl[i]);      
        }
      }
    
      return (ItemPropertyDecl[])list.toArray(new ItemPropertyDecl[0]);
    }
     
    return new ItemPropertyDecl[0]; 
  }
  
  public ItemPropertyDecl[] getFilledNewPropertyDecl() {
    // get all new properties (empty or filled out)
    ItemPropertyDecl[] newPropertyDecl = this.getNewPropertyDecl();
    
    if (newPropertyDecl != null) 
    {
      ArrayList list = new ArrayList();
    
      for (int i = 0; i < newPropertyDecl.length; i++) 
      {
        if (newPropertyDecl[i].isComplete()) {
          list.add(newPropertyDecl[i]);
        }      
      }  
      
      return (ItemPropertyDecl[])list.toArray(new ItemPropertyDecl[0]);
    }
    else 
    {
      return new ItemPropertyDecl[0];
    }
    
  }
}
