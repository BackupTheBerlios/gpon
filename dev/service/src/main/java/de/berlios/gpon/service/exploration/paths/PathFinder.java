package de.berlios.gpon.service.exploration.paths;

import java.util.List;

import de.berlios.gpon.common.util.path.Path;
import de.berlios.gpon.common.util.path.StepPredicate;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;

public interface PathFinder {

	public List collectAllPaths(Long typeId, StepPredicate linkPredicate);

	public List collectAllPaths(Long typeId, StepPredicate linkPredicate,
			int maxlevel);

	public Path getPathByDescriptor(String descriptor);

	
	// properties
	public GponDataDao getGponDataDao();
	public void setGponDataDao(GponDataDao gponDataDao);

	public GponModelDao getGponModelDao();
	public void setGponModelDao(GponModelDao gponModelDao);
	
}