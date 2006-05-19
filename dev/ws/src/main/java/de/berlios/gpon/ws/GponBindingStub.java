/**
 * GponBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package de.berlios.gpon.ws;

import de.berlios.gpon.xmlscript.GponScript;
import de.berlios.gpon.xmlscript.Processing;
import de.berlios.gpon.xmlscript.impl.GponScriptImpl;

public class GponBindingStub extends org.apache.axis.client.Stub implements de.berlios.gpon.ws.GponPortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[1];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("runScript");
        oper.addParameter(new javax.xml.namespace.QName("http://berlios.de/gpon/xmlscript", "GponScript"), new javax.xml.namespace.QName("http://berlios.de/gpon/xmlscript", ">GponScript"), de.berlios.gpon.xmlscript.GponScript.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://berlios.de/gpon/xmlscript", ">GponScriptOutput"));
        oper.setReturnClass(de.berlios.gpon.xmlscript.GponScriptOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://berlios.de/gpon/xmlscript", "GponScriptOutput"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

    }

    public GponBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public GponBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public GponBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            java.lang.Class beansf = org.apache.axis.encoding.ser.xbeans.XmlBeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.xbeans.XmlBeanDeserializerFactory.class;
                        
            qName = new javax.xml.namespace.QName("http://berlios.de/gpon/xmlscript", ">GponScriptOutput");
            cachedSerQNames.add(qName);
            cls = de.berlios.gpon.xmlscript.GponScriptOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://berlios.de/gpon/xmlscript", ">GponScript");
            cachedSerQNames.add(qName);
            cls = de.berlios.gpon.xmlscript.GponScript.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://berlios.de/gpon/xmlscript", ">Processing");
            cachedSerQNames.add(qName);
            cls = Processing.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call =
                    (org.apache.axis.client.Call) super.service.createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                        java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                        _call.registerTypeMapping(cls, qName, sf, df, false);
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public de.berlios.gpon.xmlscript.GponScriptOutput runScript(de.berlios.gpon.xmlscript.GponScript request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "runScript"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {(GponScript)request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (de.berlios.gpon.xmlscript.GponScriptOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (de.berlios.gpon.xmlscript.GponScriptOutput) org.apache.axis.utils.JavaUtils.convert(_resp, de.berlios.gpon.xmlscript.GponScriptOutput.class);
            }
        }
    }

}
