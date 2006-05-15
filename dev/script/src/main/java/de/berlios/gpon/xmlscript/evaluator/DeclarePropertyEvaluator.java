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


package de.berlios.gpon.xmlscript.evaluator;

import java.util.HashSet;
import java.util.Set;

import org.apache.xmlbeans.XmlObject;

import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.types.ValueType;
import de.berlios.gpon.common.types.repository.ValueTypeRepository;
import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.DeclareProperty;
import de.berlios.gpon.xmlscript.Evaluator;
import de.berlios.gpon.xmlscript.ScriptContext;


public class DeclarePropertyEvaluator implements Evaluator {

	public Object evaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {
		
		ItemType itemType = cd.getItemType();
		
		DeclareProperty declareProperty =
			(DeclareProperty)xmlObject;
		
		ItemPropertyDecl ipd =
			new ItemPropertyDecl();
		
		ipd.setDescription(declareProperty.getDescription());
		ipd.setItemType(itemType);
		
		if (declareProperty.isSetMandatory()) {
			ipd.setMandatory(new Boolean(declareProperty.getMandatory()));
		}
		
		ipd.setName(declareProperty.getName());
		
		ValueType vt = ValueTypeRepository.
			getValueTypeByClassName("de.berlios.gpon.common.types.repository."+
									declareProperty.getType());
		
		ipd.setPropertyValueTypeId(vt.getId());
		
		if (declareProperty.isSetRank()) {
			ipd.setRank(new Integer(declareProperty.getRank().intValue()));
		}
		
		if (itemType.getItemPropertyDecls()==null) 
		{
			Set propDecls =
				new HashSet();
			itemType.setItemPropertyDecls(propDecls);
		}
		
		itemType.getItemPropertyDecls().add(ipd);
		
		System.out.println("Pdcl: "+ipd);
		
		return null;
	}

}
