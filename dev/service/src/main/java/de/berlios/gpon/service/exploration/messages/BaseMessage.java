package de.berlios.gpon.service.exploration.messages;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

public class BaseMessage {
	
	public String serialize() 
	 {
		try {
			StringWriter sw = new StringWriter();
			Marshaller marshaller = new Marshaller(sw);
			
			Mapping mapping = loadMapping();
			marshaller.setMapping(mapping);
			
			marshaller.marshal(this);
			
			return sw.toString();
		
		} catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
	 }
	
	protected static Object _deserialize(String xml) 
	{
		try {
		Unmarshaller u = new Unmarshaller();
		Mapping mapping = loadMapping();
		u.setMapping(mapping);
		
		return u.unmarshal(new StringReader(xml));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private static Mapping loadMapping()
		throws Exception
	{
		URL url = BaseMessage.class.getResource("/de/berlios/gpon/service/exploration/messages/mapping.xml");
		
		System.out.println(url);
		
		Mapping mapping = new Mapping();
		mapping.loadMapping(url);
		
		return mapping;
	}
}
