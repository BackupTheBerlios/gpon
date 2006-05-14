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


package de.berlios.gpon.persistence.search;

import java.util.Iterator;
import java.util.Map;

import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.constants.SearchOp;
import de.berlios.gpon.common.types.ValueType;
import de.berlios.gpon.common.types.repository.ValueTypeRepository;
import de.berlios.gpon.common.util.ItemTypeMappedByName;
import de.berlios.gpon.common.util.search.PropertyCriterion;


public class QueryStringHelper {

	public static final String CONJUNCTION_AND = "and";
	public static final String CONJUNCTION_OR = "or";
	
	public static String getQuery(ItemType it, Map nameToCriterionMap) 
	{
		return getQuery(it, nameToCriterionMap, CONJUNCTION_AND);
	}
	
	
	public static String getQuery(ItemType it, Map nameToCriterionMap, String conjunction) 
	{
		ItemTypeMappedByName mappedType = new ItemTypeMappedByName(it);
		
		Iterator keyIterator = nameToCriterionMap.keySet().iterator();
		
		StringBuffer queryBuffer = new StringBuffer("");
		
		while (keyIterator.hasNext()) 
		{
			Object key = keyIterator.next();
			
			String comparison =
				getComparison((PropertyCriterion)nameToCriterionMap.get(key),
							  mappedType);
				
			if (comparison != null) {
				if (queryBuffer.length()>0) {
					queryBuffer.append(" "+conjunction+" ");
				}
				queryBuffer.append(comparison);
			}
		}
		
		if (queryBuffer.length()==0) {
			return null;
		}
		
		return queryBuffer.toString();
	}
	
	public static String getComparison(PropertyCriterion pc, ItemTypeMappedByName it) {
		
		if (pc.getValue()!=null && pc.getValue().length()>0) 
		{
			ItemPropertyDecl ipd =
				it.getItemPropertyDecl(pc.getName());
			
			ValueType vt =
				ValueTypeRepository.getValueTypeByTypeId(ipd.getPropertyValueTypeId());
			
			switch (pc.getOp()) {
				case SearchOp.OP_INCLUDES:
					return "${"+pc.getName()+"} like '%"+pc.getValue()+"%'";
				case SearchOp.OP_STARTS_WITH:
					return "${"+pc.getName()+"} like '"+pc.getValue()+"%'";
				case SearchOp.OP_ENDS_WITH:
					return "${"+pc.getName()+"} like '%"+pc.getValue()+"'";
				case SearchOp.OP_EQUALS:
					if (vt.isNumeric()) {
						return 
							"tac_to_number(${"+pc.getName()+"},'9999999999.99999') ="+
							"tac_to_number('"+pc.getValue()+"','9999999999.99999')";
						
					} else {
						return "${"+pc.getName()+"} = '"+pc.getValue()+"'";
					}
				case SearchOp.OP_NOT_EQUALS:
					if (vt.isNumeric()) {
						return 
							"tac_to_number(${"+pc.getName()+"},'9999999999.99999') <>"+
							"tac_to_number('"+pc.getValue()+"','9999999999.99999')";
						
					} else {
						return "${"+pc.getName()+"} <> '"+pc.getValue()+"'";
					}
				case SearchOp.OP_GREATER_THAN:
					if (vt.isNumeric()) {
						return 
							"tac_to_number(${"+pc.getName()+"},'9999999999.99999') >"+
							"tac_to_number('"+pc.getValue()+"','9999999999.99999')";
						
					} else {
						return "${"+pc.getName()+"} > '"+pc.getValue()+"'";
					}
				case SearchOp.OP_LESS_THAN:
					if (vt.isNumeric()) {
						return 
							"tac_to_number(${"+pc.getName()+"},'9999999999.99999') <"+
							"tac_to_number('"+pc.getValue()+"','9999999999.99999')";
						
					} else {
						return "${"+pc.getName()+"} < '"+pc.getValue()+"'";
					}
			}
		}
		
		return null;
	}
	
}
