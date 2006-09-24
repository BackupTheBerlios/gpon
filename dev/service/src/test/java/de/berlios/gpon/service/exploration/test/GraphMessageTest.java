package de.berlios.gpon.service.exploration.test;

import java.io.BufferedReader;
import java.io.FileReader;
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
		
		graphNode1.setAttributes(attrs);
		
		attrs = new ArrayList();
		
		attrs.add(new Attribute("name","Fischer"));
		attrs.add(new Attribute("firstname","Jan"));
		
		graphNode2.setAttributes(attrs);
		
		GraphEdge graphEdge = new GraphEdge(new Long(4711),new Long(1), new Long(2), "association");
		
		GraphMessage graphMessage =
			new GraphMessage();
		
		List el = new ArrayList();
		el.add(graphEdge);
		
		List nl = new ArrayList();
		nl.add(graphNode1);
		nl.add(graphNode2);
		
		graphMessage.setGraphEdges(el);
		graphMessage.setGraphNodes(nl);
		
		System.out.println("XML: " + graphMessage.serialize());
	}
	
	public void testLoad()
	throws Exception
	{
		
	    	BufferedReader bufReader =
	    		new BufferedReader(new FileReader("graphMessage.xml"));
	    	
	    	StringBuffer msgBuffer = new StringBuffer();
	    	
	    	String line = null;
	    	
	    	while ((line = bufReader.readLine())!=null) 
	    	{
	    		msgBuffer.append(line);
	    	}
	    	
	    	bufReader.close();
	    	
	    	GraphMessage gm = 
	    		GraphMessage.deserialize(msgBuffer.toString());	
	    
	}
}
