package de.berlios.gpon.service.exploration.messages;

public class GraphMessage 
extends BaseMessage
{
GraphNode[] graphNodes;
GraphEdge[] graphEdges;
public GraphEdge[] getGraphEdges() {
	return graphEdges;
}
public void setGraphEdges(GraphEdge[] graphEdges) {
	this.graphEdges = graphEdges;
}
public GraphNode[] getGraphNodes() {
	return graphNodes;
}
public void setGraphNodes(GraphNode[] graphNodes) {
	this.graphNodes = graphNodes;
}

public static GraphMessage deserialize(String msg) 
{
	 return (GraphMessage)getXStream().fromXML(msg);
}
}
