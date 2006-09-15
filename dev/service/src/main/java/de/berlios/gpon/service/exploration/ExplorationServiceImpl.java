package de.berlios.gpon.service.exploration;

import de.berlios.gpon.common.Item;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.service.exploration.messages.AssociationInfoMessage;
import de.berlios.gpon.service.exploration.messages.GraphMessage;

public class ExplorationServiceImpl implements ExplorationService {

	GponDataDao gponDataDao;
	GponModelDao gponModelDao;
	
	public AssociationInfoMessage getAssociationInfos(Long itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	public GraphMessage getNeighbours(Long itemId, Long associationTypeId,
			boolean reverse) {
		// TODO Auto-generated method stub
		return null;
	}

	public GraphMessage getEnvironment(Long itemId, int radius) {
		
		Item item = gponDataDao.findItemById(itemId);
		
		System.out.println("Item: "+item);
		
		return null;
		
		
	}

	public void setGponDataDao(GponDataDao gponDataDao) {
		this.gponDataDao = gponDataDao;
	}

	public void setGponModelDao(GponModelDao gponModelDao) {
		this.gponModelDao = gponModelDao;
	}

	
}
