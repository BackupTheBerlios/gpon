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
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import de.berlios.gpon.common.AssociationType;

public class AssociationTypeForm extends ValidatorForm 
{
  Long id;
  String name;
  String multiplicity;
  String description;
  Long baseAssociationTypeId;
  Long itemATypeId;
  Long itemBTypeId;
  String itemARoleName;
  String itemBRoleName;
  String visibility;
  
  String predicates;
  String predicatesA;
  String predicatesB;

  public AssociationTypeForm()
  {
  }


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


  public void setMultiplicity(String multiplicity)
  {
    this.multiplicity = multiplicity;
  }


  public String getMultiplicity()
  {
    return multiplicity;
  }


  public void setDescription(String description)
  {
    this.description = description;
  }


  public String getDescription()
  {
    return description;
  }


  public void setBaseAssociationTypeId(Long baseAssociationTypeId)
  {
    this.baseAssociationTypeId = baseAssociationTypeId;
  }


  public Long getBaseAssociationTypeId()
  {
    return baseAssociationTypeId;
  }


  public void setItemATypeId(Long itemATypeId)
  {
    this.itemATypeId = itemATypeId;
  }


  public Long getItemATypeId()
  {
    return itemATypeId;
  }


  public void setItemBTypeId(Long itemBTypeId)
  {
    this.itemBTypeId = itemBTypeId;
  }


  public Long getItemBTypeId()
  {
    return itemBTypeId;
  }


  public void setItemARoleName(String itemARoleName)
  {
    this.itemARoleName = itemARoleName;
  }


  public String getItemARoleName()
  {
    return itemARoleName;
  }


  public void setItemBRoleName(String itemBRoleName)
  {
    this.itemBRoleName = itemBRoleName;
  }


  public String getItemBRoleName()
  {
    return itemBRoleName;
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
  {
    boolean hasErrors=false;
    ActionErrors errors = new ActionErrors();
  
    if (getName()==null || getName().trim().length()==0) {
      hasErrors = true;
      errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.required","Name"));       
    }
    
    if (getMultiplicity()==null || getMultiplicity().trim().length()==0) {
      hasErrors = true;
      errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.required","Multiplizität"));       
    }
    
    if (getDescription()==null || getDescription().trim().length()==0) {
      hasErrors = true; 
      errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.required","Beschreibung"));       
    }
    
    if (getItemATypeId()==null && getItemATypeId().longValue()<=0) {
      hasErrors = true;
      errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.required","Objekttyp A"));       
    }
    
    if (getItemBTypeId()==null && getItemBTypeId().longValue()<=0) {
      hasErrors = true;
      errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.required","Objekttyp B"));       
    }
    
    if (getItemARoleName()==null || getItemARoleName().trim().length()==0) {
      hasErrors = true;
      errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.required","Rolle A"));
    }
    
    if (getItemBRoleName()==null || getItemBRoleName().trim().length()==0) {
      hasErrors = true;
      errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.required","Rolle B"));
    }
    
    if (this.getBaseAssociationTypeId()!=null && this.getBaseAssociationTypeId().longValue() <= 0) {
      // Struts converts "" -> 0L 
      this.setBaseAssociationTypeId(null);    
    }
    
    if (hasErrors) {
      return errors;
    } 
    else {
      return null;
    }
    
  }


  public void setVisibility(String visibility)
  {
    this.visibility = visibility;
  }


  public String getVisibility()
  {
    return visibility;
  }


public String getPredicates() {
	return predicates;
}


public void setPredicates(String predicates) {
	this.predicates = predicates;
}


public String getPredicatesA() {
	return predicatesA;
}


public void setPredicatesA(String predicatesA) {
	this.predicatesA = predicatesA;
}


public String getPredicatesB() {
	return predicatesB;
}


public void setPredicatesB(String predicatesB) {
	this.predicatesB = predicatesB;
}
}
