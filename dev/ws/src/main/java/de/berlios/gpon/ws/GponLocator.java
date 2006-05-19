/**
 * GponLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package de.berlios.gpon.ws;

public class GponLocator extends org.apache.axis.client.Service implements de.berlios.gpon.ws.Gpon {

    public GponLocator() {
    }


    public GponLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    // Use to get a proxy class for GponPort
    private java.lang.String GponPort_address = "http://localhost:9080/ws/services/Gpon";

    public java.lang.String getGponPortAddress() {
        return GponPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GponPortWSDDServiceName = "GponPort";

    public java.lang.String getGponPortWSDDServiceName() {
        return GponPortWSDDServiceName;
    }

    public void setGponPortWSDDServiceName(java.lang.String name) {
        GponPortWSDDServiceName = name;
    }

    public de.berlios.gpon.ws.GponPortType getGponPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GponPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGponPort(endpoint);
    }

    public de.berlios.gpon.ws.GponPortType getGponPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            de.berlios.gpon.ws.GponBindingStub _stub = new de.berlios.gpon.ws.GponBindingStub(portAddress, this);
            _stub.setPortName(getGponPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGponPortEndpointAddress(java.lang.String address) {
        GponPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (de.berlios.gpon.ws.GponPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                de.berlios.gpon.ws.GponBindingStub _stub = new de.berlios.gpon.ws.GponBindingStub(new java.net.URL(GponPort_address), this);
                _stub.setPortName(getGponPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("GponPort".equals(inputPortName)) {
            return getGponPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://berlios.de/gpon", "Gpon");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://berlios.de/gpon", "GponPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("GponPort".equals(portName)) {
            setGponPortEndpointAddress(address);
        }
        else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
