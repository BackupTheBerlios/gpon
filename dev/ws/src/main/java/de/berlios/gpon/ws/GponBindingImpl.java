/**
 * GponBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package de.berlios.gpon.ws;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.berlios.gpon.xmlscript.LookupService;
import de.berlios.gpon.xmlscript.Processor;

public class GponBindingImpl implements de.berlios.gpon.ws.GponPortType{
    public de.berlios.gpon.xmlscript.GponScriptOutput runScript(de.berlios.gpon.xmlscript.GponScript request) throws java.rmi.RemoteException {
        
    	HttpServlet srv = (HttpServlet) MessageContext.getCurrentContext().getProperty(HTTPConstants.MC_HTTP_SERVLET);
    	ServletContext context = srv.getServletContext();
    	
    	Processor p = 
			(Processor)WebApplicationContextUtils.
			   getWebApplicationContext(context).getBean("txScriptProcessor");
		
		return p.process(request);
    }

}
