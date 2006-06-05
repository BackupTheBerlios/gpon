/**
 * GponBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package de.berlios.gpon.ws;

import de.berlios.gpon.xmlscript.LookupService;
import de.berlios.gpon.xmlscript.Processor;

public class GponBindingImpl implements de.berlios.gpon.ws.GponPortType{
    public de.berlios.gpon.xmlscript.GponScriptOutput runScript(de.berlios.gpon.xmlscript.GponScript request) throws java.rmi.RemoteException {
        
    	
    	Processor p = 
			(Processor)LookupService.getBeanForId("txScriptProcessor");
		
		return p.process(request);
    }

}
