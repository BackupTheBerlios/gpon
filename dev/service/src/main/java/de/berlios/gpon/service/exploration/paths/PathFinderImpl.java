package de.berlios.gpon.service.exploration.paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.path.Path;
import de.berlios.gpon.common.util.path.Step;
import de.berlios.gpon.common.util.path.StepPredicate;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;

public class PathFinderImpl implements PathFinder {

	GponModelDao gponModelDao;
	GponDataDao   gponDataDao;
	
	Log log = LogFactory.getLog(PathFinderImpl.class);

	/* (non-Javadoc)
	 * @see de.gema.tac.middletier.exploration.PathFinder#collectAllPaths(java.lang.Long, de.gema.tac.middletier.exploration.StepPredicate)
	 */
	public List collectAllPaths(Long typeId, StepPredicate linkPredicate)
			 {

		Path currentPath = new Path();

		return collectAllPaths(typeId, linkPredicate, currentPath, 0, -1);

	}

	/* (non-Javadoc)
	 * @see de.gema.tac.middletier.exploration.PathFinder#collectAllPaths(java.lang.Long, de.gema.tac.middletier.exploration.StepPredicate, int)
	 */
	public List collectAllPaths(Long typeId, StepPredicate linkPredicate,
			int maxlevel) {

		Path currentPath = new Path();

		return collectAllPaths(typeId, linkPredicate, currentPath, 1, maxlevel);

	}


	private List collectAllPaths(Long typeId, StepPredicate linkPredicate,
			Path currentPath, int level, int maxlevel){

	

		if (maxlevel >0 && level > maxlevel) 
		{
			return null;
		}
	
		log.info("(collectAllPaths) Level: " + level);
		
		ItemType cit = getGponModelDao().findItemTypeById(typeId);

		if (currentPath.getStart() == null) {
			currentPath.setStart(cit);
		}

		// as far as we know we are the end
		currentPath.setEnd(cit);

		Set relAts = new HashSet();
			
	    relAts.addAll(cit.getInheritedAssociationTypesA());
	    relAts.addAll(cit.getInheritedAssociationTypesB());

		Iterator it = relAts.iterator();

		List allPathsList = new ArrayList();

		boolean leafNode = true;

		while (it.hasNext()) {
			AssociationType at = (AssociationType) it.next();

			boolean at_added = false;

			if (cit.getBaseTypes().contains(at.getItemAType())) {
				Step step = new Step(at, false);

				if (linkPredicate.evaluate(step)) {
					if (!currentPath.containsStep(step)) {

						Path newCurrentPath = currentPath.duplicate();

						newCurrentPath.addStep(step);

						allPathsList.add(newCurrentPath);
						at_added = true;

						List result = collectAllPaths(at.getItemBType().getId(),
								linkPredicate, newCurrentPath, level + 1,maxlevel);

						if (result != null && result.size() > 0) {
							allPathsList.addAll(result);
						}
					}
				}
			}

			if (cit.getBaseTypes().contains(at.getItemBType())) {
				Step step = new Step(at, true);

				if (linkPredicate.evaluate(step)) {
					if (!currentPath.containsStep(step)) {

						Path newCurrentPath = currentPath.duplicate();
						newCurrentPath.addStep(step);

						if (!at_added) {
							allPathsList.add(newCurrentPath);
						}

						List result = collectAllPaths(at.getItemAType().getId(),
								linkPredicate, newCurrentPath, level + 1,maxlevel);

						if (result != null && result.size() > 0) {
							allPathsList.addAll(result);
						}

					}
				}
			}

		}

		return allPathsList;
	}



	/* (non-Javadoc)
	 * @see de.gema.tac.middletier.exploration.PathFinder#getPathByDescriptor(java.lang.String)
	 */
	public Path getPathByDescriptor(String descriptor) {
		try {
			Path path = new Path();

			StringTokenizer strtok = new StringTokenizer(descriptor, ":");

			List list = Collections.list(strtok);

			int size = list.size();

			Long startTypeId = new Long((String) list.get(0));
			Long endTypeId = new Long((String) list.get(size - 1));

			path.setStart(getGponModelDao().findItemTypeById(startTypeId));
			path.setEnd(getGponModelDao().findItemTypeById(endTypeId));

			for (int i = 1; i < size - 1; i++) {
				boolean reverse = false;

				String token = (String) list.get(i);

				// PLUS sign will cause an number format exception
				if (token.startsWith("+")) {
					token = token.substring(1);
				}

				long atIdValue = new Long(token).longValue();

				if (atIdValue < 0L) {
					reverse = true;
					atIdValue = atIdValue * -1L;
				}

				AssociationType at = getGponModelDao().findAssociationTypeById(new Long(
						atIdValue));

				Step step = new Step(at, reverse);

				path.addStep(step);
			}

			return path;

		} catch (Throwable t) {
			throw new RuntimeException(t);
		}

	}

	public GponDataDao getGponDataDao() {
		return gponDataDao;
	}

	public void setGponDataDao(GponDataDao gponDataDao) {
		this.gponDataDao = gponDataDao;
	}

	public GponModelDao getGponModelDao() {
		return gponModelDao;
	}

	public void setGponModelDao(GponModelDao gponModelDao) {
		this.gponModelDao = gponModelDao;
	}

}
