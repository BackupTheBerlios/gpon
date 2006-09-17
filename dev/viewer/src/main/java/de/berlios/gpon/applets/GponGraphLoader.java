/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package de.berlios.gpon.applets;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.berlios.gpon.service.exploration.messages.Attribute;
import de.berlios.gpon.service.exploration.messages.GraphEdge;
import de.berlios.gpon.service.exploration.messages.GraphMessage;
import de.berlios.gpon.service.exploration.messages.GraphNode;

import edu.uci.ics.jung.exceptions.FatalException;
import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import edu.uci.ics.jung.graph.impl.UndirectedSparseGraph;
import edu.uci.ics.jung.utils.GraphUtils;
import edu.uci.ics.jung.utils.UserData;

/**
 * The default GraphML file handler to use to parse the xml file
 * @author Scott White
 */
public class GponGraphLoader  {
    private Graph mGraph;
    private StringLabeller mLabeller;

    /**
     * The default constructor
     */
    public GponGraphLoader() {
    }

    protected Graph getGraph() {
        return mGraph;
    }

    protected StringLabeller getLabeller() {
        return mLabeller;
    }

    protected Edge createEdge(GraphEdge edge) {
        if (mGraph == null) {
            throw new FatalException("Error parsing graph. Graph element must be specified before edge element.");
        }

        Vertex sourceVertex =
                mLabeller.getVertex(edge.getSource()+"");

        Vertex targetVertex =
                 mLabeller.getVertex(edge.getTarget()+"");

        Edge e = GraphUtils.addEdge(mGraph, sourceVertex, targetVertex);

        e.setUserDatum("type", edge.getType(), UserData.SHARED);
        
        return e;
    }

    protected void createGraph(boolean directed) {
        if (directed) {
            mGraph = new DirectedSparseGraph();
        } else {
            mGraph = new UndirectedSparseGraph();
        } 

        mLabeller = StringLabeller.getLabeller(mGraph);
    }

    protected ArchetypeVertex createVertex(GraphNode node) {
        if (mGraph == null) {
            throw new FatalException("Error parsing graph. Graph element must be specified before node element.");
        }

        ArchetypeVertex vertex = mGraph.addVertex(new SparseVertex());
        
        try {
            mLabeller.setLabel((Vertex) vertex,node.getObjectId()+"");
        } catch (StringLabeller.UniqueLabelException ule) {
            throw new FatalException("Ids must be unique");

        }

        for (int i = 0; node.getAttributes()!=null && i < node.getAttributes().length;i++) 
        {
            Attribute attr = node.getAttributes()[i];
            // vertex.setUserDatum(attr.getName(), attr.getValue(), UserData.SHARED);
            vertex.setUserDatum("prop"+i, attr.getValue(), UserData.SHARED);
        }
        return vertex;
    }

    public Graph load(GraphMessage message) 
    {
    	createGraph(false);
    	
    	for (int ni=0; ni < message.getGraphNodes().length; ni++) 
    	{
    		GraphNode gn = message.getGraphNodes()[ni];
    		createVertex(gn);
    	}
    	
    	for (int ei=0; ei < message.getGraphEdges().length; ei++) 
    	{
    		GraphEdge ge = message.getGraphEdges()[ei];
    		createEdge(ge);
    	}
    	
    	return mGraph;
    }
    
    public Graph load(Reader reader) 
    {
    	try {
    	BufferedReader bufReader =
    		new BufferedReader(reader);
    	
    	StringBuffer msgBuffer = new StringBuffer();
    	
    	String line = null;
    	
    	while ((line = bufReader.readLine())!=null) 
    	{
    		msgBuffer.append(line);
    	}
    	
    	bufReader.close();
    	
    	GraphMessage gm = 
    		GraphMessage.deserialize(msgBuffer.toString());
    	
    	return load(gm);
    	
    	}catch (Throwable t) 
    	{
    		throw new RuntimeException(t);
    	}
    }
    
}
