package de.berlios.gpon.service.exploration.messages;

public class GraphEdge {

	Long source;
	Long target;
	String type;
	
	public GraphEdge(Long source, Long target, String type) {
		super();
		// TODO Auto-generated constructor stub
		this.source = source;
		this.target = target;
		this.type = type;
	}
	public Long getSource() {
		return source;
	}
	public void setSource(Long source) {
		this.source = source;
	}
	public Long getTarget() {
		return target;
	}
	public void setTarget(Long target) {
		this.target = target;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
