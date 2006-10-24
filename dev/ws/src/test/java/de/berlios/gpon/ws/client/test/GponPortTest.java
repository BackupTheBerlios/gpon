package de.berlios.gpon.ws.client.test;

import java.io.File;

import org.apache.xmlbeans.XmlOptions;

import junit.framework.TestCase;
import de.berlios.gpon.ws.GponBindingStub;
import de.berlios.gpon.ws.GponLocator;
import de.berlios.gpon.xmlscript.GponScript;
import de.berlios.gpon.xmlscript.GponScriptDocument;
import de.berlios.gpon.xmlscript.GponScriptOutputDocument;
import de.berlios.gpon.xmlscript.Reference;

public class GponPortTest 
extends TestCase
{
	
	public void testGponRunScript() throws Exception {

	   if (true)
		return;
		
		GponBindingStub binding;
        try {
            binding = (GponBindingStub)
                          new GponLocator().getGponPort();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        GponScriptOutputDocument value = GponScriptOutputDocument.Factory.newInstance();
        
        GponScript gs = GponScript.Factory.newInstance();
        
        Reference r = gs.addNewReference();
        
        r.setRefid("bloo");
        
        String doc =
        	"<GponScript xmlns=\"http://berlios.de/gpon/xmlscript\">"+
        	"<Reference refid=\"bloobaloo\"/>"+
        	"</GponScript>";        
        
        XmlOptions xmlo = new XmlOptions();
        xmlo.setLoadStripWhitespace();
        xmlo.setSavePrettyPrint();
        
        GponScriptDocument gsd = GponScriptDocument.Factory.parse(new File("test.xml"),xmlo);
        
        gs = gsd.getGponScript();
        
        GponScript gs2 = (GponScript)gs.copy();
        
        // gs.dump();
        
        value.setGponScriptOutput(binding.runScript(gs2));
        // TBD - validate results
        
        // value.dump();
        // assertNotNull(value);
        
        System.out.println("GponScriptOutput: "+value.xmlText(xmlo));
    }
}
