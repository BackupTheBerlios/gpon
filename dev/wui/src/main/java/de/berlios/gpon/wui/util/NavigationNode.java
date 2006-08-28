package de.berlios.gpon.wui.util;


import java.util.ArrayList;
import java.util.List;

public class NavigationNode {

	public String shortname;
	public String url;
	
	private String absolutePath = null;
	
	NavigationNode parent=null;
	
	List childNodes = new ArrayList();

	public NavigationNode(String shortname, String url) {
		super();
		// TODO Auto-generated constructor stub
		this.shortname = shortname;
		this.url = url;
	}

	public List getChildNodes() {
		return childNodes;
	}

	public String getShortname() {
		return shortname;
	}

	public String getUrl() {
		return url;
	}
	
	public void addChildNode(NavigationNode node) 
	{
		node.setParent(this);
		childNodes.add(node);
	}
	
	public String getAbsolutePath() 
	{
		if (absolutePath!=null) 
		{
			return absolutePath;
		}
		
		if (parent!=null) 
		{
			absolutePath = parent.getAbsolutePath()+"."+shortname;
			return absolutePath;
		}
		else 
		{
			absolutePath = shortname;
			return absolutePath;
		}	
	}
	
	public static void main(String[] args) {
		 NavigationNode root = new NavigationNode("root","");
		 
		 NavigationNode data = new NavigationNode("data","data.jsp");	
		 NavigationNode model = new NavigationNode("model","model.jsp");	
		 root.addChildNode(data);
		 root.addChildNode(model);
		 
		 NavigationNode datasearch = new NavigationNode("search","datasearch.jsp");	
		 NavigationNode dataview = new NavigationNode("view","dataview.jsp");
		 data.addChildNode(datasearch);
		 data.addChildNode(dataview);

		 NavigationNode modelexplain = new NavigationNode("explain","modelexplain.jsp");	
		 model.addChildNode(modelexplain);
		 
		 System.out.println("r: "+root.getAbsolutePath());
		 System.out.println("rd: "+data.getAbsolutePath());
		 System.out.println("rm: "+model.getAbsolutePath());
		 System.out.println("rds: "+datasearch.getAbsolutePath());
		 System.out.println("rdv: "+dataview.getAbsolutePath());
		 System.out.println("rme: "+modelexplain.getAbsolutePath());
		 		 
		 
	}

	public void setParent(NavigationNode parent) {
		this.parent = parent;
	}
	
	
}