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
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.types.Value;
import de.berlios.gpon.common.types.ValueTypeValidationException;
import de.berlios.gpon.common.util.ItemMappedById;

public class DefaultDataValidator implements DataValidator {

	private static Log log = LogFactory.getLog(DefaultDataValidator.class);

	private Item item;

	/**
	 * 
	 * @note
	 */
	public DefaultDataValidator(Item pItem) {
		this.item = pItem;
	}

	public DataValidationError[] validate() {

		ItemMappedById mappedItem = new ItemMappedById(this.item);

		List errors = new ArrayList();

		Iterator declIt = this.item.getItemType()
				.getInheritedItemPropertyDecls().iterator();

		while (declIt.hasNext()) {
			ItemPropertyDecl itemPropertyDecl = (ItemPropertyDecl) declIt
					.next();

			// MappedItem: DeclId.toString() -> property

			Long declIdKey = itemPropertyDecl.getId();

			if (mappedItem.hasProperty(declIdKey.toString())) {
				
				Value value = mappedItem.getValueObject(declIdKey.toString());
				
				if (value == null || value.getInput().trim().length() == 0) {
					// no value, but mandatory
					if (itemPropertyDecl.getMandatory().equals(Boolean.TRUE)) {
						errors.add(new DataValidationError(
								DataValidationError.MANDATORY_FIELD_ERROR,
								new Object[] { itemPropertyDecl }));
					}
				} else {
					// value is set
					// construct a typed object
					try {
						value.validate();
					} catch (ValueTypeValidationException ex) {
						errors
								.add(new DataValidationError(
										DataValidationError.FIELD_VALIDATION_ERROR,
										new Object[] {
												value,
												ex.getCause().getClass()
														.getName()
														+ ":"
														+ ex.getCause()
																.getMessage() }));
						log.error("Validation exception:", ex);
					} catch (Exception ex) {
						log.error("Property type validation error.", ex);
					}
				}
			} else {
				// item property not present (in hash)
				if (itemPropertyDecl.getMandatory().equals(Boolean.TRUE)) {
					// property not set, but mandatory
					errors.add(new DataValidationError(
							DataValidationError.MANDATORY_FIELD_ERROR,
							new Object[] { itemPropertyDecl }));
				}

			}

		}

		if (errors.size() > 0) {
			return (DataValidationError[]) errors
					.toArray(new DataValidationError[0]);
		}

		return null;
	}

}
