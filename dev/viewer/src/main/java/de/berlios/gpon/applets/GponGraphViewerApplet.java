package de.berlios.gpon.applets;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.collections.Predicate;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

import edu.uci.ics.jung.graph.ArchetypeEdge;
import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.ConstantDirectionalEdgeValue;
import edu.uci.ics.jung.graph.decorators.ConstantVertexFontFunction;
import edu.uci.ics.jung.graph.decorators.EdgeFontFunction;
import edu.uci.ics.jung.graph.decorators.EdgeShape;
import edu.uci.ics.jung.graph.decorators.EdgeStringer;
import edu.uci.ics.jung.graph.decorators.ToolTipFunction;
import edu.uci.ics.jung.graph.decorators.VertexAspectRatioFunction;
import edu.uci.ics.jung.graph.decorators.VertexPaintFunction;
import edu.uci.ics.jung.graph.decorators.VertexShapeFunction;
import edu.uci.ics.jung.graph.decorators.VertexSizeFunction;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.decorators.VertexStrokeFunction;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import edu.uci.ics.jung.utils.GraphUtils;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.GraphLabelRenderer;
import edu.uci.ics.jung.visualization.GraphMouseListener;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.PickEventListener;
import edu.uci.ics.jung.visualization.PickedInfo;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.ShapePickSupport;
import edu.uci.ics.jung.visualization.VertexShapeFactory;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.VisualizationViewer.GraphMouse;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.transform.MutableAffineTransformer;


/**
 * 
 * @author Tom Nelson - RABA Technologies
 * @author Daniel Schulz
 * 
 */
public class GponGraphViewerApplet 
  extends JApplet
{
	
	GraphViewerConfiguration gvConfig = null;
	GponGraphLoader ggl = new GponGraphLoader();
	
	
   	private static final long serialVersionUID = -4230466891458736802L;
  
    ArchetypeVertex pickedVertex;

    final Hashtable vertexColorsByType = new Hashtable();

    final Color colorMap[] = new Color[] {
      new Color(240,240,255),
      Color.GREEN,
      Color.BLUE,
      Color.CYAN,
      Color.MAGENTA,
      Color.ORANGE,
      Color.PINK,
      Color.RED,
      Color.YELLOW
    };

    /**
     * the graph
     */
    final Graph graph = new DirectedSparseGraph();

    /**
     * the visual component and renderer for the graph
     */
    VisualizationViewer vv;
    
    public void paint(Graphics g)
    {
      super.paint(g);
    }
    
    /**
     * create an instance of a simple graph with controls to
     * demo the zoom features.
     * 
     */
    public void start() {
        // all the final stuff for the EventHandler inner classes
        final JApplet japplet = this;
        final Graph graph = getGraph();
        
        final PluggableRenderer pr = new PluggableRenderer();
        
        final FRLayout layout = new FRLayout(graph);
        
        vv =  new VisualizationViewer(layout, pr);
        layout.resize(gvConfig.getViewDimension());
        vv.setPreferredSize(gvConfig.getViewDimension());
        vv.setPickSupport(new ShapePickSupport());
        pr.setEdgeShapeFunction(new EdgeShape.QuadCurve());
        
        // vertex stroke highlight (cool stuff)
        VertexStrokeHighlight vsh = new VertexStrokeHighlight(vv.getPickedState());
        
        pr.setVertexStrokeFunction(vsh);
        
        // get picked vertex
        
        final GponGraphViewerApplet graphExport = this;
        
        final MyDetailButton detailButton = 
          new MyDetailButton("Detail (select node)");
        detailButton.doLayout();
        detailButton.setEnabled(false);
          
          
        detailButton.addActionListener(
          new ActionListener() {
          
            public void actionPerformed(ActionEvent e) 
            {
              try {
              // applet parameters
              String detailURL = gvConfig.getDetailURL();
              String detailTarget = gvConfig.getDetailTarget();
              // see GraphML export format
              String objectId = (String)graphExport.getPickedVertex().getUserDatum("objectId");
              japplet.getAppletContext().showDocument(new URL(detailURL+objectId),detailTarget);
            } catch (Exception ex) {
              ex.printStackTrace();
            }
            
            }
          
          }
        
        
        );  
          
        
        final JButton newButton = 
            new JButton("New");
                      
          newButton.addActionListener(
            new ActionListener() {
            
              public void actionPerformed(ActionEvent e) 
              {
                try {
                
                String objectId = (String)graphExport.getPickedVertex().getUserDatum("objectId");

                Iterator it = layout.getVertexIterator();
                
                // lock
                while (it.hasNext()) 
                {
                	Vertex v = (Vertex)it.next();
                	layout.lockVertex(v);
                }
                
            	try {
            		  
        	    	  String urlString = "http://localhost:9080/wui/exploration/environment.do?objectId="+objectId+"&radius=1";
            	      HttpClient client = new HttpClient();
            	      System.out.println("Graph URL: "+urlString);
            	      HttpMethod get = new GetMethod(urlString);
            	      client.executeMethod(get);
            	      String response = get.getResponseBodyAsString();
            	      System.out.println(response);
            	      getGgl().update(graph,new StringReader(response));
            	    	} catch (Exception e2) 
            	    	{
            	    		throw new RuntimeException(e2);
            	    	}
                
                layout.update();    
                layout.advancePositions();
                
                // unlock
                it = layout.getVertexIterator();
                
                while (it.hasNext()) 
                {
                	Vertex v = (Vertex)it.next();
                	layout.unlockVertex(v);
                }
                
                
                vv.repaint();
                
                
              } catch (Exception ex) {
                ex.printStackTrace();
              }
              
              }
            
            }
          
          
          );
        
        vv.getPickedState().addListener(
          new PickEventListener() 
          {
           public void vertexPicked(edu.uci.ics.jung.graph.ArchetypeVertex vertex)
            {
              graphExport.setPickedVertex(vertex);
              detailButton.setLabel("Detail "+vertex.toString()+"");
              detailButton.setEnabled(true);
              detailButton.repaint();
            }
          
           public void vertexUnpicked(edu.uci.ics.jung.graph.ArchetypeVertex vertex)
            {
              graphExport.setPickedVertex(null);
              detailButton.setLabel("Detail (select node)");
              detailButton.setEnabled(false);
              detailButton.repaint();
            }
          
           public void edgePicked(edu.uci.ics.jung.graph.ArchetypeEdge param1)
            {
            }
          
           public void edgeUnpicked(edu.uci.ics.jung.graph.ArchetypeEdge param1)
            {
            }
          });
        
        // if the edge type is inheritance,
        // an arrow is shown
        pr.setEdgeArrowPredicate(new Predicate () {
          public boolean evaluate(Object input) 
          {
        	try {  
            if (input instanceof ArchetypeEdge) {
              return ((ArchetypeEdge)input).getUserDatum("type").equals("inheritance");
            }
        	} catch (Throwable t) {} 
            return false;
          }
        });
        
        
        // Vertices are painted black and filled blue
        pr.setVertexPaintFunction(new VertexPaintFunction() {
        
          public Paint getFillPaint(Vertex v) {
          
            if (gvConfig.isNodeTypeColors()) {
            
              if (v.getUserDatum("nodeType")!=null) 
              {
                String nodeType = v.getUserDatum("nodeType").toString();
                
                if (vertexColorsByType.containsKey(nodeType)) 
                {
                  return (Color)vertexColorsByType.get(nodeType);                
                }
                else {
                  // new type, get new Color
                  
                  // colors before
                  int noOfColors = 
                    vertexColorsByType.size();
                  
                  int colorMapIndex = (noOfColors % colorMap.length);
                  
                  Color c = colorMap[colorMapIndex];
                  
                  vertexColorsByType.put(nodeType,c);
                  
                  return c;
                }
                
              }
            
            
            }
          
          
            return new Color(240,240,255);
          }

          public Paint getDrawPaint(Vertex v) {return Color.black;}
        
        });
        
        // edge labels are displayed in the middle      
        pr.setEdgeLabelClosenessFunction(new ConstantDirectionalEdgeValue(0.5, 0.5));

        
       final VertexSizeFunction myVsf = new VertexSizeFunction() 
        {
          public int getSize(Vertex v) 
          {
            // number of characters * 5
            Iterator it = v.getUserDatumKeyIterator();
            
            int len = 15;
            
            while (it.hasNext()) 
            {
              Object key = it.next();
            
              Object val = v.getUserDatum(key);
            
              if (val instanceof String)
                len = Math.max(val.toString().length(),len);    
            }
            
            return len * 7;            
          } 
        };
        
       final MyVertexAspectRatioFunction myVarf = new MyVertexAspectRatioFunction() 
        {
          private VertexSizeFunction vsf;
        
          public void setVertexSizeFunction(VertexSizeFunction v) 
          {
            vsf = v;
          }
        
          public float getAspectRatio(Vertex v) 
          {
            int size = vsf.getSize(v);
            
            // number of characters * 5
            Iterator it = v.getUserDatumKeyIterator();
            
            int count = 0;
            while (it.hasNext()) {
              it.next(); // infinite loop would be the alternative
              count++;
            }
          
            // x = max(15,maxChar) * 7
            // y = (2 + count) * 7 * 1.8
          
            float ratio = ((count) * 7.0f * 1.84f)/size; 
            
            return ratio;
          }
        
        
        };
        
        myVarf.setVertexSizeFunction(myVsf);

        // if we show node details, the vertex size is computed
        // according to the attribute count and maximum string length
        if (gvConfig.isNodeDetails()) {        
          pr.setVertexShapeFunction(new VertexShapeFunction() {
             public Shape getShape(Vertex v) {
              VertexShapeFactory vsf = 
                new VertexShapeFactory(myVsf, 
                                       myVarf); 
              Shape s = vsf.getRegularPolygon(v,4);
              return s;
             }
          
          });
          
          pr.setVertexLabelCentering(true);
        
          pr.setVertexStringer(new VertexStringer() {
            public String getLabel(ArchetypeVertex v) {
              StringBuffer label = new StringBuffer("<html><body>"+v.getUserDatum("name")+
                     "<hr>");
                
              for (int i = 0; i < 30; i++) {
                if (v.getUserDatum("prop"+i)!=null) {
                  label.append(v.getUserDatum("prop"+i)+"<br>");
                }
                else 
                  break;
              }  
              
              label.append("</body></html>");
              
              return label.toString();
            }
          });
        } // end of detailed node section
        else 
        { // no node details
          pr.setVertexStringer(new VertexStringer() {
            public String getLabel(ArchetypeVertex v) {
              // first property 
              if (v.getUserDatum("prop0")!=null) {
                return v.getUserDatum("prop0").toString();
              }
              // or object id
              if (v.getUserDatum("objectId")!=null) {
                return "#"+v.getUserDatum("objectId").toString();
              }
              
              return v.toString();
            }
          });
        
        }
        
        vv.setToolTipFunction(new ToolTipFunction() {
        	public String getToolTipText(Vertex v)
          {
            StringBuffer label = new StringBuffer("<html><body>"+v.getUserDatum("name")+
                     "<hr>");
                
              for (int i = 0; i < 30; i++) {
                if (v.getUserDatum("prop"+i)!=null) {
                  label.append(v.getUserDatum("prop"+i)+"<br>");
                }
                else 
                  break;
              }  
              
              label.append("</body></html>");
              
              return label.toString();
          }

          public String getToolTipText(Edge e)
          {
            return e.toString();
          }
          
          public String getToolTipText(MouseEvent me)
          {
            return null;
          }
        });


        // print TAC logo pre-renderer :)
        final VisualizationViewer.Paintable preRenderer=
          new VisualizationViewer.Paintable(){
                public void paint(Graphics g) {
                    printLogo(g);
                }
                public boolean useTransform() { return false; }
            };

        vv.addPreRenderPaintable(preRenderer);       

       // how about edges ?         
       pr.setEdgeStringer(new EdgeStringer() {
       
        public String getLabel(ArchetypeEdge e) 
        {
          if (e.getUserDatum("type")!=null) {
            return e.getUserDatum("type").toString();
          }
          return "";
        }
       });
        
       pr.setEdgeFontFunction(new EdgeFontFunction() {
        
        Font myfnt = new Font("Helvetica",Font.PLAIN,10);
       
        public Font getFont(Edge e) {
          return myfnt;
        }
       
       }); 
        
       GraphLabelRenderer glr = pr.getGraphLabelRenderer();
       
       glr.setRotateEdgeLabels(false);
        
       vv.setBackground(new Color(250,250,255));
        
        
       vv.addGraphMouseListener(new TestGraphMouseListener(this));
        
       // add my listener for ToolTips
       // vv.setToolTipFunction(new DefaultToolTipFunction());
        
       vv.setFont(new Font("Helvetica",Font.PLAIN,10));
        
       // Font
       pr.setVertexFontFunction(new ConstantVertexFontFunction(Font.getFont("Arial")));
        
        Container content = getContentPane();
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        content.add(panel);
        
        final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        
        
        
        ((DefaultModalGraphMouse)graphMouse).setMode(ModalGraphMouse.Mode.PICKING);
        
        vv.setGraphMouse((GraphMouse)graphMouse);
        
        final JPanel controls = new JPanel();
        
        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // call listener in graph mouse instead of manipulating vv scale directly
                // this is so the crossover from zoom to scale works with the buttons
                // as well as with the mouse wheel
                Dimension d = vv.getSize();
                graphMouse.mouseWheelMoved(new MouseWheelEvent(vv,MouseEvent.MOUSE_WHEEL,
                        System.currentTimeMillis(),0,d.width/2,d.height/2,1,false, 
                        MouseWheelEvent.WHEEL_UNIT_SCROLL,1,1));
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // call listener in graph mouse instead of manipulating vv scale directly
                // this is so the crossover from zoom to scale works with the buttons
                // as well as with the mouse wheel
                Dimension d = vv.getSize();
                graphMouse.mouseWheelMoved(new MouseWheelEvent(vv,MouseEvent.MOUSE_WHEEL,
                        System.currentTimeMillis(),0,d.width/2,d.height/2,1,false, 
                        MouseWheelEvent.WHEEL_UNIT_SCROLL,1,-1));
            }
        });
        
        final JComboBox xCombo = new JComboBox(new String[] {"800","1024","1600","2400"});
        final JComboBox yCombo = new JComboBox(new String[] {"600","768","1200","1800"});
        
        xCombo.setSelectedItem("1600");
        yCombo.setSelectedItem("1200");
        
        // dschulz: test file export
        JButton export = new JButton("export");

        

        export.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
          
          boolean params_ok = true;
          
          int desiredX = new Integer(xCombo.getSelectedItem().toString()).intValue();
          int desiredY = new Integer(yCombo.getSelectedItem().toString()).intValue();
          
          BufferedImage image = (BufferedImage)vv.createImage(desiredX,desiredY);
          
          Graphics2D g = image.createGraphics();    // Get a Graphics2D object
          
          Dimension preferredSize1 = new Dimension(desiredX,desiredY);

          Rectangle r = vv.getBounds(); 

          Dimension oldPreferredLayoutSize = 
            vv.getGraphLayout().getCurrentSize();  
          
          vv.getGraphLayout().resize(preferredSize1);

          System.out.println("Layout size: "+vv.getGraphLayout().getCurrentSize());
          System.out.println("Layout tran: "+vv.getLayoutTransformer());
          System.out.println("View   tran: "+vv.getViewTransformer());

          // save old display values  
          
          AffineTransform vat = vv.getViewTransformer().getTransform();
          AffineTransform lat = vv.getLayoutTransformer().getTransform();

          Rectangle visibleRect = vv.getVisibleRect();
          System.out.println("Visible Recet: "+visibleRect);
          
          MutableAffineTransformer vmat =
            new MutableAffineTransformer(vat);
          
          MutableAffineTransformer lmat =
            new MutableAffineTransformer(lat);

          // prepare to export
          vv.setBounds(new Rectangle(desiredX,desiredY));
          vv.getViewTransformer().setToIdentity(); 
          vv.getViewTransformer().setTranslate(20,20);
          // vv.getViewTransformer().scale(1.2,1.2,new Point2D.Double(1000,500));
          vv.getLayoutTransformer().setToIdentity();
      
          vv.paint(g);

          // remove traces          
          vv.setBounds(r);
          vv.setViewTransformer(vmat);
          vv.setLayoutTransformer(lmat);
          vv.getGraphLayout().resize(oldPreferredLayoutSize);
      
          
          if (gvConfig.isDebug()) 
          {
            try {
              ImageIO.write(image, "png", new File("c:\\temp\\test.png"));
            } catch (Exception ex) 
            {
              ex.printStackTrace();
            }
          }
          
          try {
            String postURL    = gvConfig.getGraphImagePostURL();
            String displayURL = gvConfig.getGraphImageDisplayURL();
            String keybase    = gvConfig.getKeybase();
          
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bos);
            bos.close();
            
            byte[] out =bos.toByteArray();
            
            HttpClient client = new HttpClient();

            PostMethod httppost = new PostMethod(new URL(postURL).toExternalForm());
      
            httppost.addParameter("keybase",keybase);
      
            Part[] parts = {
                            new FilePart("file", new ByteArrayPartSource("foo",out))
                        };
            
            httppost.setRequestEntity(
                            new MultipartRequestEntity(parts, httppost.getParams())
                            );
            
            try {
                client.executeMethod(httppost);
        
                if (httppost.getStatusCode() == HttpStatus.SC_OK) {
                    System.out.println(httppost.getResponseBodyAsString());
                    japplet.getAppletContext().showDocument(new URL(displayURL+"keybase="+keybase),"_blank_");
                    
                } else {
                  System.out.println("Unexpected failure: " + httppost.getStatusLine().toString());
                }
            } finally {
                httppost.releaseConnection();
            }
            
          } catch (Exception ex) {
            ex.printStackTrace();  
          }
            }
        });


        controls.setBackground(Color.white);
        controls.add(newButton);
        controls.add(detailButton);
        controls.add(plus);
        controls.add(minus);
        controls.add(new JLabel("Mouse mode:"));
        graphMouse.getModeComboBox().setSelectedItem(ModalGraphMouse.Mode.PICKING);;
        controls.add(graphMouse.getModeComboBox());
        controls.add(new JLabel("export X:"));
        controls.add(xCombo);
        controls.add(new JLabel("Y:"));
        controls.add(yCombo);
        controls.add(export);
        content.setBackground(Color.white);
        content.add(controls, BorderLayout.SOUTH);
    }
    

    /**
     * A nested class to demo the GraphMouseListener finding the
     * right vertices after zoom/pan
     */
    static class TestGraphMouseListener implements GraphMouseListener {
        
        GponGraphViewerApplet applet;
        
        TestGraphMouseListener(GponGraphViewerApplet pApplet) {
          applet = pApplet;
        }
        
    		public void graphClicked(Vertex v, MouseEvent me) {
        
            if (me.getClickCount()<2) {
              return;
            }
        
    		    System.err.println("Vertex "+v+" was clicked at ("+me.getX()+","+me.getY()+")");
            // vv.getGraphLayout().forceMove(v,vv.getCenter().getX(),vv.getCenter().getY());
            try {
              // applet parameters
              String detailURL = applet.gvConfig.getDetailURL();
              String detailTarget = applet.gvConfig.getDetailTarget();
              // see GraphML export format
              String objectId = (String)v.getUserDatum("objectId");
              applet.getAppletContext().showDocument(new URL(detailURL+objectId),detailTarget);
            } catch (Exception ex) {
              ex.printStackTrace();
            }
    		}
    		public void graphPressed(Vertex v, MouseEvent me) {
    		    System.err.println("Vertex "+v+" was pressed at ("+me.getX()+","+me.getY()+")");
    		}
    		public void graphReleased(Vertex v, MouseEvent me) {
    		    System.err.println("Vertex "+v+" was released at ("+me.getX()+","+me.getY()+")");
    		}
    }

    private final static class VertexStrokeHighlight implements VertexStrokeFunction
    {
        protected boolean highlight = true;
        protected Stroke heavy = new BasicStroke(5);
        protected Stroke medium = new BasicStroke(3);
        protected Stroke light = new BasicStroke(1);
        protected PickedInfo pi;
        
        public VertexStrokeHighlight(PickedInfo pi)
        {
            this.pi = pi;
        }
        
        public void setHighlight(boolean highlight)
        {
            this.highlight = highlight;
        }
        
        public Stroke getStroke(Vertex v)
        {
            if (highlight)
            {
                if (pi.isPicked(v))
                    return heavy;
                else
                {
                    for (Iterator iter = v.getNeighbors().iterator(); iter.hasNext(); )
                    {
                        Vertex w = (Vertex)iter.next();
                        if (pi.isPicked(w))
                            return medium;
                    }
                    return light;
                }
            }
            else
                return light; 
        }
    }

    /**
     * a driver for this demo
     */
    public static void main(String[] args) 
    {
        JFrame frame = new JFrame();
        Container content = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GponGraphViewerApplet gzspd =
          new GponGraphViewerApplet();

        gzspd.gvConfig = GraphViewerConfiguration.getDebugConfiguration("node.details=off node.typecolor=off");
        
        // gzspd.configureApplet("node.details=off graph.layout=spring");
        gzspd.start();
        
        content.add(gzspd);
        frame.pack();
        frame.show();
    }
  
  private Graph getGraph() 
  {
    
    if (gvConfig.isDebug()) {
    	try {
    		if (true) {
      return ggl.load(new FileReader("graphMessage.xml"));
    		} else {
    		String urlString = "http://localhost:9080/wui/exploration/environment.do?objectId=8&radius=1";
      HttpClient client = new HttpClient();
      System.out.println("Graph URL: "+urlString);
      HttpMethod get = new GetMethod(urlString);
      client.executeMethod(get);
      String response = get.getResponseBodyAsString();
      System.out.println(response);
      return getGgl().load(new StringReader(response));
    		}
    	} catch (Exception e) 
    	{
    		throw new RuntimeException(e);
    	}
    }

    String urlString  = gvConfig.getGraphUrl();

    if (gvConfig.getSessionId()!=null) 
    {
      urlString = urlString+";jsessionid="+gvConfig.getSessionId();
    }

    try
    {
      HttpClient client = new HttpClient();
      System.out.println("Graph URL: "+urlString);
      HttpMethod get = new GetMethod(urlString);
      client.executeMethod(get);
      String response = get.getResponseBodyAsString();
      System.out.println(response);
      return getGgl().load(new StringReader(response));
    }
    catch (Exception e)
    {
    	e.printStackTrace();
    }

//    try
//    {
//      URL url = new URL(urlString);
//      
//      InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream());
//
//      // System.out.println(response);
//      return gmlFile.load(isr);
//    }
//    catch (Exception e)
//    {
//    }
    return null;
  }

  public void printLogo(Graphics g) {
  
   int fontSize   = 8;
   int lineStartX = 10;
   int lineStartY = 40;
   
   String[] lines = new String[] {
     " GPON graph viewer applet"};
   g.setFont(new Font("Courier",Font.PLAIN,fontSize));
   g.setColor(new Color(0,0,0,100));
   
   for (int i = 0; i < lines.length; i++) {
    g.drawString(lines[i],lineStartX,lineStartY+i*fontSize);
   } 
  }

  /**
   * Called by the browser or applet viewer to inform
   * this applet that it has been loaded into the system. It is always
   * called before the first time that the <code>start</code> method is
   * called.
   * <p>
   * A subclass of <code>Applet</code> should override this method if
   * it has initialization to perform. For example, an applet with
   * threads would use the <code>init</code> method to create the
   * threads and the <code>destroy</code> method to kill them.
   * <p>
   * The implementation of this method provided by the
   * <code>Applet</code> class does nothing.
   * 
   * @see     java.applet.Applet#destroy()
   * @see     java.applet.Applet#start()
   * @see     java.applet.Applet#stop()
   */
  public void init()
  {
    try {
      this.gvConfig = GraphViewerConfiguration.getConfiguration(this); 
    }
    catch (Throwable t) 
    {
      this.gvConfig = GraphViewerConfiguration.getConfiguration("test.properties");
    }
  }

  public void setPickedVertex(ArchetypeVertex pickedVertex)
  {
    this.pickedVertex = pickedVertex;
  }


  public ArchetypeVertex getPickedVertex()
  {
    return pickedVertex;
  }

public GponGraphLoader getGgl() {
	return ggl;
}


}


/** VertexAspectRatioFunction, that needs the vertex x-size.
 * 
 */

interface MyVertexAspectRatioFunction 
  extends VertexAspectRatioFunction 
{
  
  public void setVertexSizeFunction(VertexSizeFunction vsf);
  
}

class MyDetailButton extends JButton 
{
  public MyDetailButton(String label) 
  {
    super(label);
  }


  public void setLabel(String label) 
  {
    super.setLabel(label);
  }
}

