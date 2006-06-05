package de.berlios.gpon.persistence.misc.test;

import java.sql.SQLException;

import de.berlios.gpon.persistence.misc.JavaDbMultiplicityCheck;
import junit.framework.TestCase;

public class JavaDbMultiplicityCheckTest extends TestCase {

	public void testCheckMultiplicity()
		throws Exception
	{
		
		Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
		
		JavaDbMultiplicityCheck.checkMultiplicityClient(2,10,8,"gpon","gpon");
			
	} 
	
}
