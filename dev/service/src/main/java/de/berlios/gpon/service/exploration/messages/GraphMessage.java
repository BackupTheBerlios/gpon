package de.berlios.gpon.service.exploration.messages;

import java.util.Collection;

public class GraphMessage 
extends BaseMessage
{
Collection graphNodes;
Collection graphEdges;

public GraphMessage() {
	// TODO Auto-generated constructor stub
}

public Collection getGraphEdges() {
	return graphEdges;
}


public void setGraphEdges(Collection graphEdges) {
	this.graphEdges = graphEdges;
}


public Collection getGraphNodes() {
	return graphNodes;
}


public void setGraphNodes(Collection graphNodes) {
	this.graphNodes = graphNodes;
}


public static GraphMessage deserialize(String msg) 
{
	 return (GraphMessage)_deserialize(msg);
}
}
