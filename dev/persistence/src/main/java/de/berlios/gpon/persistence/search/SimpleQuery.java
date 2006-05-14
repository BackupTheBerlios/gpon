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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SimpleQuery {
	String type;

	String spec;

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List getSpecProperties() {

		ArrayList list = new ArrayList();

		String inSpec = getSpec();

		while (inSpec.indexOf("${") >= 0) {

			int startIndex = inSpec.indexOf("${");

			int endIndex = inSpec.indexOf("}", startIndex);

			String propName = inSpec.substring(startIndex + 2, endIndex);

			if (!list.contains(propName)) {
				list.add(propName);
			}

			inSpec = inSpec.substring(endIndex + 1);
		}

		return list;
	}
	
	public String getReplacedSpec(Hashtable replacements) {
	    
	    String inSpec = getSpec();
	    String outputString = "";
	    
	    while (inSpec.indexOf("${")>=0) {
	    
	      int startIndex = inSpec.indexOf("${");
	      
	      int endIndex = inSpec.indexOf("}",startIndex);
	      
	      String subExpression =
	        inSpec.substring(startIndex+2,endIndex);
	    
	      String subExpressionResult = (String)replacements.get(subExpression);
	      
	      outputString =
	        outputString + 
	        inSpec.substring(0,startIndex)+
	        subExpressionResult;
	        
	      inSpec =
	        inSpec.substring(endIndex+1);  
	        
	    }
	    
	    // Gibt es einen Rest
	    if (inSpec.length()>0) {
	      outputString =
	        outputString + 
	        inSpec;
	    }
	    
	    return outputString;
	}
	
}
