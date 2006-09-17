package de.berlios.gpon.service.exploration.test;

import org.springframework.test.AbstractTransactionalSpringContextTests;

import de.berlios.gpon.service.exploration.ExplorationService;
import de.berlios.gpon.service.exploration.messages.GraphMessage;

public class ExplorationServiceTest 
extends AbstractTransactionalSpringContextTests
{
	
	protected String[] getConfigLocations() {
		return new String[] { "service-context.xml" };
	}
	
	public void testGetEnvironment() {
		
		ExplorationService es =
			(ExplorationService)applicationContext.getBean("explorationService");
		
		GraphMessage gm = es.getEnvironment(new Long(1),10);
		
		System.out.println("graph: "+gm.serialize());
	}
	
}
