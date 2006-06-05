package de.berlios.gpon.persistence.misc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import de.berlios.gpon.common.AssociationType.MultiplicityConstants;

public class JavaDbMultiplicityCheck {

	public static int checkMultiplicityClient(int assId, int aId, int bId,
			String user, String password) throws Exception,SQLException {
		Properties props = new Properties();
		props.put("user", user);
		props.put("password", password);

		Connection conn = DriverManager.getConnection(
				"jdbc:derby://localhost/gpon;create=true", props);

		return _checkMultiplicity(assId, aId, bId, conn);
	}

	public static int checkMultiplicityDB(int assId, int aId, int bId)
			throws Exception,SQLException {
		Connection conn = DriverManager
				.getConnection("jdbc:default:connection");

		return _checkMultiplicity(assId, aId, bId, conn);
	}

	private static int _checkMultiplicity(int assId, int aId, int bId,
			Connection conn) throws Exception,SQLException {
		String multiplicity = null;

		Statement stmt = null;
		
		try {

			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from t_association_type where id = "+assId);

			if (rs.next()) {
				multiplicity = rs.getString("multiplicity");
			}

			rs.close();

			if (multiplicity.equals(MultiplicityConstants.ONE_TO_MANY)) {
				
				rs = stmt.executeQuery("select count(*)from T_ASSOCIATION"
						+ " where ASSOCIATION_TYPE_ID = "+assId
						+ " and ITEM_B_ID = "+bId);

				if (rs.next()) {
					int count = rs.getInt(1);

					if (count > 0) {
						rs.close();
						throw new Exception("OneToMany violated.");
					}
				}

				rs.close();
			}
			
			return 0;
		} finally {
			if (stmt!=null) 
			{
				stmt.close();
			}
			conn.close();
		}
	}
}
