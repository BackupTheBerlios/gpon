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
import java.io.StringReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.functors.NotPredicate;
import org.w3c.dom.Attr;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import sun.util.logging.resources.logging;

import de.berlios.gpon.service.exploration.messages.Attribute;
import de.berlios.gpon.service.exploration.messages.GraphEdge;
import de.berlios.gpon.service.exploration.messages.GraphMessage;
import de.berlios.gpon.service.exploration.messages.GraphNode;

import edu.uci.ics.jung.exceptions.FatalException;
import edu.uci.ics.jung.graph.ArchetypeEdge;
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
 * 
 * @author Scott White
 */
public class GponGraphLoader {
	private Graph nGraph;

	private StringLabeller nLabeller;

	boolean update = false;

	Hashtable edgeIdMap = new Hashtable();

	/**
	 * The default constructor
	 */
	public GponGraphLoader() {
	}

	protected Graph getGraph() {
		return nGraph;
	}

	protected StringLabeller getLabeller() {
		return nLabeller;
	}

	protected Edge createEdge(GraphEdge edge) {
		if (nGraph == null) {
			throw new FatalException(
					"Error parsing graph. Graph element must be specified before edge element.");
		}

		if (isUpdate()) {
			if (edgeIdMap.containsKey(edge.getId())) {
				return (Edge) edgeIdMap.get(edge.getId());
			}
		}

		Vertex sourceVertex = nLabeller.getVertex(edge.getSource() + "");

		Vertex targetVertex = nLabeller.getVertex(edge.getTarget() + "");

		Edge e = GraphUtils.addEdge(nGraph, sourceVertex, targetVertex);

		e.setUserDatum("id", edge.getId(), UserData.SHARED);
		e.setUserDatum("type", edge.getType(), UserData.SHARED);

		return e;
	}

	protected void createGraph(boolean directed) {
		if (directed) {
			nGraph = new DirectedSparseGraph();
		} else {
			nGraph = new UndirectedSparseGraph();
		}

		// Not(Parallel()) will be removed

		Iterator it = nGraph.getEdgeConstraints().iterator();

		NotPredicate toBeRemoved = null;

		while (it.hasNext()) {
			Object o = it.next();

			if (o instanceof NotPredicate) {
				toBeRemoved = (NotPredicate) o;
			}
		}

		if (toBeRemoved != null) {
			nGraph.getEdgeConstraints().remove(toBeRemoved);
		}

		nLabeller = StringLabeller.getLabeller(nGraph);

	}

	protected ArchetypeVertex createVertex(GraphNode node) {
		if (nGraph == null) {
			throw new FatalException(
					"Error parsing graph. Graph element must be specified before node element.");
		}

		if (nLabeller.getVertex(node.getObjectId() + "") != null) {
			System.out
					.println("Node " + node.getObjectId() + "already exists!");
			return nLabeller.getVertex(node.getObjectId() + "");
		}

		ArchetypeVertex vertex = nGraph.addVertex(new SparseVertex());

		try {
			nLabeller.setLabel((Vertex) vertex, node.getObjectId() + "");
		} catch (StringLabeller.UniqueLabelException ule) {
			throw new FatalException("Ids must be unique");

		}

		vertex.setUserDatum("objectId", node.getObjectId().toString(),
				UserData.SHARED);
		vertex.setUserDatum("nodeType", node.getObjectType(),UserData.SHARED);
		vertex.setUserDatum("name", node.getObjectType(), UserData.SHARED);

		if (node.getAttributes() != null) {

			Attribute[] attrs = (Attribute[]) node.getAttributes().toArray(
					new Attribute[0]);

			for (int i = 0; attrs != null && i < attrs.length; i++) {
				Attribute attr = attrs[i];
				// vertex.setUserDatum(attr.getName(), attr.getValue(),
				// UserData.SHARED);
				vertex.setUserDatum("prop" + i, attr.getValue(),
						UserData.SHARED);
			}
		}
		return vertex;
	}

	public Graph load(GraphMessage message) {

		createGraph(false);

		Iterator nodeIterator = message.getGraphNodes().iterator();

		while (nodeIterator.hasNext()) {
			GraphNode gn = (GraphNode) nodeIterator.next();
			createVertex(gn);
		}

		if (message.getGraphEdges() != null) {

			Iterator edgeIterator = message.getGraphEdges().iterator();

			while (edgeIterator.hasNext()) {
				GraphEdge ge = (GraphEdge) edgeIterator.next();
				createEdge(ge);
			}
		}
		return nGraph;
	}

	private void update(Graph graph, GraphMessage message) {

		nGraph = graph;
		nLabeller = StringLabeller.getLabeller(nGraph);

		Iterator nodeIterator = message.getGraphNodes().iterator();

		while (nodeIterator.hasNext()) {
			GraphNode gn = (GraphNode) nodeIterator.next();
			createVertex(gn);
		}

		if (message.getGraphEdges() != null) {

			Iterator edgeIterator = message.getGraphEdges().iterator();

			while (edgeIterator.hasNext()) {
				GraphEdge ge = (GraphEdge) edgeIterator.next();
				createEdge(ge);
			}
		}
	}

	public Graph load(Reader reader) {
		try {

			GraphMessage gm = getGraphMessage(reader);

			System.out.println("Gm: " + gm);

			return load(gm);

		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	void update(Graph graph, StringReader reader) {
		try {

			setUpdate(true);

			Set edges = graph.getEdges();

			if (edges != null) {
				Iterator edgeIt = edges.iterator();

				while (edgeIt.hasNext()) {
					ArchetypeEdge edge = (ArchetypeEdge) edgeIt.next();
					edgeIdMap.put(edge.getUserDatum("id"), edge);
				}
			}
			GraphMessage gm = getGraphMessage(reader);

			update(graph, gm);

		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	GraphMessage getGraphMessage(Reader reader) {
		try {
			BufferedReader bufReader = new BufferedReader(reader);

			StringBuffer msgBuffer = new StringBuffer();

			String line = null;

			while ((line = bufReader.readLine()) != null) {
				msgBuffer.append(line);
			}

			bufReader.close();

			GraphMessage gm = GraphMessage.deserialize(msgBuffer.toString());

			return gm;

		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public Hashtable getEdgeIdMap() {
		return edgeIdMap;
	}

	public void setEdgeIdMap(Hashtable edgeIdMap) {
		this.edgeIdMap = edgeIdMap;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

}
