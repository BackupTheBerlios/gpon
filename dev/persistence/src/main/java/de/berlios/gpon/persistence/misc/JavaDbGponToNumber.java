package de.berlios.gpon.persistence.misc;

import java.sql.SQLException;

public class JavaDbGponToNumber {
	public static double gponToNumber(String value, String format) 
		throws SQLException
	{
		try {
			return new Double(value).doubleValue();
		}
		catch (Exception ex) 
		{
			return Double.NaN;
		}
	}
}
