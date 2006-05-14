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


package de.berlios.gpon.common.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.berlios.gpon.common.Association;
import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.Item;


public class AssociationValidator implements DataValidator {

	private static Log log = LogFactory.getLog(AssociationValidator.class);
	
	Association assoc = null;
	
	public AssociationValidator(Association pAssoc) 
	{
		assoc = pAssoc;
	}
	
	public DataValidationError[] validate() {
		
		List errors = new ArrayList();		
			
		AssociationType at =
			assoc.getAssociationType();
		
		Item a = assoc.getItemA();
		
		if (!at.getItemAType().getSpecializedTypesDeep().contains(a.getItemType())) 
		{
			errors.add(new DataValidationError(
						DataValidationError.WRONG_ITEM_TYPE_ERROR,
						new Object[] {a}));
			log.error("Wrong item type for a: "+a);
		}
	
		Item b = assoc.getItemB();
		
		if (!at.getItemBType().getSpecializedTypesDeep().contains(b.getItemType())) 
		{
			errors.add(new DataValidationError(
						DataValidationError.WRONG_ITEM_TYPE_ERROR,
						new Object[] {b}));
			log.error("Wrong item type for b: "+b);
		}
	
		
		if (errors.size() > 0) {
			return (DataValidationError[]) errors
					.toArray(new DataValidationError[0]);
		}

		return null;
	}

}
