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


package de.berlios.gpon.wui.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.types.repository.ValueTypeRepository;
import de.berlios.gpon.persistence.GponModelDao;


public class ModelViewImpl implements ModelView {

	GponModelDao model;

	/* (non-Javadoc)
	 * @see de.berlios.gpon.wui.views.ModelView#getModel()
	 */
	public GponModelDao getModel() {
		return model;
	}

	/* (non-Javadoc)
	 * @see de.berlios.gpon.wui.views.ModelView#setModel(de.berlios.gpon.persistence.GponModelDao)
	 */
	public void setModel(GponModelDao model) {
		this.model = model;
	}
	
	public List getAllItemTypes() {
		return model.findAllItemTypes(); 
	} 
	
	public List getAllPropertyValueTypes() 
	  {
	    return ValueTypeRepository.getAllValueTypeDefinitions();  
	  }

	public Map getAllPropertyValueTypesMap() 
	{
	    return ValueTypeRepository.getValueTypeByTypeIdMap();  
	}
	
	public List getAllAssociationTypes() {
		return model.findAllAssociationTypes(); 
	}
	
	public List getAllMultiplicities() {
		    ArrayList list = new ArrayList();
		    
		    list.add(new NameLabelPair(AssociationType.MultiplicityConstants.ONE_TO_MANY,"1:N"));
		    list.add(new NameLabelPair(AssociationType.MultiplicityConstants.MANY_TO_MANY,"M:N"));
		  
		    return list;
		  }
	 public List getAllVisibilities() {
	   ArrayList list = new ArrayList();
		    
		    // list.add(new NameLabelPair(VisibilityConstants.AB,"A->B"));
		    list.add(new NameLabelPair(AssociationType.VisibilityConstants.BA,"A<-B"));
		    list.add(new NameLabelPair(AssociationType.VisibilityConstants.ABBA,"A<->B"));
		  
		    return list;
	 }
	
	
	  public class NameLabelPair {
		    String name;
		    String label;
		    
		    public NameLabelPair(String pName, String pLabel) {
		      name  = pName;
		      label = pLabel;
		    }
		  
		    public String getName() { return this.name; }
		    public String getLabel() { return this.label; }
		  }
	
}
