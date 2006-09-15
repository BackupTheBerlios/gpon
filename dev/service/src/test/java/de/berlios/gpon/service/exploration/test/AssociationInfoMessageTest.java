package de.berlios.gpon.service.exploration.test;

import de.berlios.gpon.service.exploration.messages.AssociationInfo;
import de.berlios.gpon.service.exploration.messages.AssociationInfoMessage;
import junit.framework.TestCase;

public class AssociationInfoMessageTest extends TestCase {

	public void testSerialze() {
		AssociationInfo mar = new AssociationInfo();

		mar.setAssociationTypeDescription("Team");
		mar.setAssociationTypeId(new Long("12"));
		mar.setAssociationTypeName("team");
		mar.setItemId(new Long(42));
		mar.setItemARolename("looser");
		mar.setItemBRolename("winner");

		AssociationInfo[] mars = new AssociationInfo[1];

		mars[0] = mar;

		AssociationInfoMessage marMessage = new AssociationInfoMessage();

		marMessage.setAssociationInfos(mars);

		System.out.println("XML: " + marMessage.serialize());

	}

	public void testDeserialze() {

		String msg = "<associationInfoMessage>"
				+ "<associationInfos>"
				+ "<associationInfo>"
				+ "<itemId>12</itemId>"
				+ "<associationTypeId>42</associationTypeId>"
				+ "<associationTypeName>team</associationTypeName>"
				+ "<associationTypeDescription>Team</associationTypeDescription>"
				+ "<itemARolename>looser</itemARolename>"
				+ "<itemBRolename>winner</itemBRolename>"
				+ "</associationInfo>" 
				+ "</associationInfos>"
				+ "</associationInfoMessage>";
		
		AssociationInfoMessage marm = AssociationInfoMessage.deserialize(msg);
		
		System.out.println("XML 2: " + marm.serialize());
	}
}
