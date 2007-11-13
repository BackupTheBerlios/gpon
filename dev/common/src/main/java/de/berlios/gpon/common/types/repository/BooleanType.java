package de.berlios.gpon.common.types.repository;

import de.berlios.gpon.common.types.Value;
import de.berlios.gpon.common.types.ValueType;
import de.berlios.gpon.common.types.ValueTypeValidationException;

public class BooleanType extends AbstractValue
implements Value 
{
	static final ValueType BOOLEANTYPE_VTD =
	    new ValueType("boolean",
	                  BooleanType.class.getName(),
	                  "boolean",                    
	                  false
	                  );
	
	String _data = null;
	
	public String getNormal() {
		return _data;
	}

	public void setDataInInputForm(String data) {
		_data = data;		
	}

	public void setDataInNormalForm(String dataInNf) {
		_data = dataInNf;
	}

	public ValueType getType() {
		return BOOLEANTYPE_VTD;
	}

	public void validate() throws ValueTypeValidationException {
		if (_data!=null && !(_data.equals("true") || _data.equals("false"))) 
		{
			throw new ValueTypeValidationException("boolean must be 'true' or 'false'");
		}
	}

	public String getHtml() {
		return getNormal();
	}

	public String getInput() {
		return getNormal();
	}

}
