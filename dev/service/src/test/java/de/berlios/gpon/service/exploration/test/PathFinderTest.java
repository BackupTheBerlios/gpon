package de.berlios.gpon.service.exploration.test;

import java.util.Iterator;
import java.util.List;

import org.springframework.test.AbstractTransactionalSpringContextTests;

import de.berlios.gpon.common.util.path.ManyToOneStepPredicate;
import de.berlios.gpon.common.util.path.OneToManyStepPredicate;
import de.berlios.gpon.common.util.path.Path;
import de.berlios.gpon.service.exploration.paths.PathFinder;

public class PathFinderTest extends AbstractTransactionalSpringContextTests
{
	
	protected String[] getConfigLocations() {
		return new String[] { "service-context.xml" };
	}
	
	public void testFindAllPaths() 
	{
		PathFinder pf = (PathFinder)applicationContext.getBean("pathFinder");
		
		List paths = pf.collectAllPaths(new Long(4),new ManyToOneStepPredicate());
		
		if (paths!=null) 
		{
			Iterator it = paths.iterator();
			
			while (it.hasNext()) 
			{
				Path path = (Path)it.next();
				
				System.out.println("Path: "+path.getDigest());
			}
		}
		
		paths = pf.collectAllPaths(new Long(4),new OneToManyStepPredicate());
		
		if (paths!=null) 
		{
			Iterator it = paths.iterator();
			
			while (it.hasNext()) 
			{
				Path path = (Path)it.next();
				
				System.out.println("Path: "+path.getDigest());
			}
		}

		
	}

}
