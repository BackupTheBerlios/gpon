package de.berlios.gpon.service.exploration.test;

import java.util.ArrayList;
import java.util.List;

import de.berlios.gpon.service.exploration.messages.Attribute;
import de.berlios.gpon.service.exploration.messages.GraphEdge;
import de.berlios.gpon.service.exploration.messages.GraphMessage;
import de.berlios.gpon.service.exploration.messages.GraphNode;
import junit.framework.TestCase;

public class GraphMessageTest extends TestCase {

	public void testGraphMessage() 
	{
		
		GraphNode graphNode1 = new GraphNode();
		
		graphNode1.setObjectId(new Long(1));
		
		GraphNode graphNode2 = new GraphNode();
		
		graphNode2.setObjectId(new Long(2));
		
		List attrs = new ArrayList();
			
		attrs.add(new Attribute("name","Schulz"));
		attrs.add(new Attribute("firstname","Daniel"));
		
		graphNode1.setAttributes((Attribute[])attrs.toArray(new Attribute[0]));
		
		attrs = new ArrayList();
		
		attrs.add(new Attribute("name","Fischer"));
		attrs.add(new Attribute("firstname","Jan"));
		
		graphNode2.setAttributes((Attribute[])attrs.toArray(new Attribute[0]));
		
		GraphEdge graphEdge = new GraphEdge(new Long(4711),new Long(1), new Long(2), "association");
		
		GraphMessage graphMessage =
			new GraphMessage();
		
		graphMessage.setGraphEdges(new GraphEdge[] {graphEdge});
		graphMessage.setGraphNodes(new GraphNode[] {graphNode1, graphNode2});
		
		System.out.println("XML: " + graphMessage.serialize());
	}
	
	
}
