/**
 * Gpon.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package de.berlios.gpon.ws;

public interface Gpon extends javax.xml.rpc.Service {
    public java.lang.String getGponPortAddress();

    public de.berlios.gpon.ws.GponPortType getGponPort() throws javax.xml.rpc.ServiceException;

    public de.berlios.gpon.ws.GponPortType getGponPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
