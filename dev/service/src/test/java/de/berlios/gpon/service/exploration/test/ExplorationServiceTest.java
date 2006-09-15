package de.berlios.gpon.service.exploration.test;

import org.springframework.test.AbstractTransactionalSpringContextTests;

import de.berlios.gpon.service.exploration.ExplorationService;

public class ExplorationServiceTest 
extends AbstractTransactionalSpringContextTests
{
	
	protected String[] getConfigLocations() {
		return new String[] { "service-context.xml" };
	}
	
	public void testGetEnvironment() {
		
		ExplorationService es =
			(ExplorationService)applicationContext.getBean("explorationService");
		
		es.getEnvironment(new Long(1),1);
	}
	
}
