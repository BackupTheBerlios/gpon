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

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;

import de.berlios.gpon.common.util.ItemMappedByName;
import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.Evaluator;
import de.berlios.gpon.xmlscript.Property;
import de.berlios.gpon.xmlscript.ScriptContext;


public class PropertyEvaluator extends AbstractEvaluator {

	public Object evaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {

		Property property = (Property) xmlObject;

		ItemMappedByName mi = cd.getMappedItem();

		if (mi == null) {
			throw new IllegalArgumentException("No item to set propety");
		}

		// mixed content
		
		XmlCursor cursor =
			property.newCursor();
		
		cursor.toFirstContentToken();
		
		System.out.println("Cursor getChars: "+cursor.getChars());
		
		String value=cursor.getChars();
		
		cursor.dispose();
		
		if (value!=null && value.length() > 0) 
		{
			if (property.isSetValue()) {
				log.warn("Value defined as attribute and in node content.");
			}
		}
		else {
			value = property.getValue();
		}
		
		System.out.println("Setting prop " + property.getName() + " to "
				+ value);
		
		mi.setValue(property.getName(), value,ItemMappedByName.INPUT_FORM);

		return null;

	}

}
