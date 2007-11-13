package de.berlios.gpon.common.validation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.berlios.gpon.common.Association;
import de.berlios.gpon.common.Item;

public class NonValidatingDataValidator implements DataValidator {

	Log log = LogFactory.getLog(NonValidatingDataValidator.class);
	
	public DataValidationError[] validate(Item pItem) {
		log.warn("item "+pItem.getId()+" is not validated!");
		return null;
	}

	public DataValidationError[] validate(Association pAssoc) {
		log.warn("item "+pAssoc.getId()+" is not validated!");
		return null;
	}

}
