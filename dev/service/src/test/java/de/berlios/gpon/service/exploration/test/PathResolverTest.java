package de.berlios.gpon.service.exploration.test;

import java.util.Set;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.test.AbstractTransactionalSpringContextTests;

import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.util.ItemMappedByName;
import de.berlios.gpon.service.exploration.paths.PathResolver;

public class PathResolverTest extends AbstractTransactionalSpringContextTests
{
	
	protected String[] getConfigLocations() {
		return new String[] { "service-context.xml" };
	}
	
	public void testPathResolver() 
	{
			String pathDescriptor = "4:+4:+3:4";
			PathResolver pr = (PathResolver)applicationContext.getBean("pathResolver");
			Set items = pr.getItemsForPath(new Long(5), pathDescriptor);
			System.out.println("Items " + items);
			// reverse
			// all mountpoints for AIS
			pathDescriptor = "4:-3:-4:4";
			items = pr.getItemsForPath(new Long(5), pathDescriptor);
			System.out.println("Items " + items);
	}
}
