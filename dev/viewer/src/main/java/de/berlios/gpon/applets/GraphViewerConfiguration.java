package de.berlios.gpon.applets;

import java.awt.Dimension;
import java.util.StringTokenizer;

import javax.swing.JApplet;

public class GraphViewerConfiguration {
	// String constants
    public static final String CONFIGURATION_DETAILS = "configDetails";

    // Session ID
    public static final String SESSION_ID_PARAMETER = "sessionId";

    // Small Parameters
    public static final String NODE_DETAILS_PARAMETER = "node.details";
    public static final String NODE_DETAILS_VALUE_OFF = "off";

    public static final String GRAPH_LAYOUT_TYPE_PARAMETER    = "graph.layout";
    public static final String GRAPH_LAYOUT_TYPE_VALUE_SPRING = "spring";

    public static final String NODE_TYPECOLOR_PARAMETER = "node.typecolor";
    public static final String NODE_TYPECOLOR_VALUE_ON = "on";

    // Default dimension
    public static final Dimension VIEW_DIMENSION = new Dimension(1000,600);
    
    boolean debug = false;
    
    String sessionId = null;
    Dimension viewDimension = null;
    String detailURL;
    String detailTarget;
    String graphImagePostURL;
    String graphImageDisplayURL;
    String keybase;
    String graphUrl;
    String configurationDetails;
    
    // computed by configuration details
    boolean nodeDetails = true;
    boolean nodeTypeColors = false;
    boolean springLayout = false;
    
    static GraphViewerConfiguration getConfiguration(JApplet applet) 
    {
    	GraphViewerConfiguration gvc =
    		new GraphViewerConfiguration();
    	
    	
    	gvc.setDetailURL(applet.getParameter("detailURL"));
        gvc.setDetailTarget(applet.getParameter("detailTarget"));
        gvc.setGraphImagePostURL(applet.getParameter("graphImagePostURL"));
        gvc.setGraphImageDisplayURL(applet.getParameter("graphImageDisplayURL"));
        gvc.setKeybase(applet.getParameter("keybase"));
        gvc.setGraphUrl(applet.getParameter("graphURL"));
        gvc.setConfigurationDetails(applet.getParameter("configDetails"));
        gvc.setSessionId(applet.getParameter("sessionId"));
        gvc.setViewDimension(VIEW_DIMENSION);
        
    	return gvc;
    }
    
    static GraphViewerConfiguration getDebugConfiguration(String configDetails) 
    {
    	GraphViewerConfiguration gvc =
    		new GraphViewerConfiguration();
    	    	
        gvc.setViewDimension(VIEW_DIMENSION);
        gvc.setConfigurationDetails(configDetails);
        gvc.setDebug(true);
        
    	return gvc;
    }
    
    static GraphViewerConfiguration getConfiguration(String propertyFile) 
    {
    	
    	return null;
    }

	public String getConfigurationDetails() {
		return configurationDetails;
	}

	public void setConfigurationDetails(String configurationDetails) {
		this.configurationDetails = configurationDetails;
		this.configureByDetails();
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getDetailTarget() {
		return detailTarget;
	}

	public void setDetailTarget(String detailTarget) {
		this.detailTarget = detailTarget;
	}

	public String getDetailURL() {
		return detailURL;
	}

	public void setDetailURL(String detailURL) {
		this.detailURL = detailURL;
	}

	public String getGraphImageDisplayURL() {
		return graphImageDisplayURL;
	}

	public void setGraphImageDisplayURL(String graphImageDisplayURL) {
		this.graphImageDisplayURL = graphImageDisplayURL;
	}

	public String getGraphImagePostURL() {
		return graphImagePostURL;
	}

	public void setGraphImagePostURL(String graphImagePostURL) {
		this.graphImagePostURL = graphImagePostURL;
	}

	public String getGraphUrl() {
		return graphUrl;
	}

	public void setGraphUrl(String graphUrl) {
		this.graphUrl = graphUrl;
	}

	public String getKeybase() {
		return keybase;
	}

	public void setKeybase(String keybase) {
		this.keybase = keybase;
	}

	public boolean isNodeDetails() {
		return nodeDetails;
	}

	public void setNodeDetails(boolean nodeDetails) {
		this.nodeDetails = nodeDetails;
	}

	public boolean isNodeTypeColors() {
		return nodeTypeColors;
	}

	public void setNodeTypeColors(boolean nodeTypeColors) {
		this.nodeTypeColors = nodeTypeColors;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public boolean isSpringLayout() {
		return springLayout;
	}

	public void setSpringLayout(boolean springLayout) {
		this.springLayout = springLayout;
	}

	public Dimension getViewDimension() {
		return viewDimension;
	}

	public void setViewDimension(Dimension viewDimension) {
		this.viewDimension = viewDimension;
	}

	public void configureByDetails() 
	  {
	    // the configuration string consists of space seperated
	    // key,value-pairs
	    //
	    // i.e. "node.details=off edge.labels=on"
	    
	    if (getConfigurationDetails()==null || getConfigurationDetails().trim().length()==0) 
	    {
	      // nothing to do
	      return;
	    }
	    
	    StringTokenizer strtok = new StringTokenizer(getConfigurationDetails());
	    
	    while (strtok.hasMoreTokens()) {
	    
	      String kvpString = strtok.nextToken();
	      
	      String[] kvpArray = kvpString.split("=");
	      String key = kvpArray[0];
	      String value = kvpArray[1];
	
	      // node details ?      
	      if (key.equals(NODE_DETAILS_PARAMETER)) 
	      {
	        if (value.equals(NODE_DETAILS_VALUE_OFF)) 
	        {
	          setNodeDetails(false);
	        }      
	      }
	      
	      // other layout ?      
	      if (key.equals(GRAPH_LAYOUT_TYPE_PARAMETER)) 
	      {
	        if (value.equals(GRAPH_LAYOUT_TYPE_VALUE_SPRING)) 
	        {
	          setSpringLayout(true);
	        }      
	      }
	      
	      // other layout ?      
	      if (key.equals(NODE_TYPECOLOR_PARAMETER)) 
	      {
	        if (value.equals(NODE_TYPECOLOR_VALUE_ON)) 
	        {
	          setNodeTypeColors(true);
	        }      
	      }
	      
	    }
	  }
    
}
