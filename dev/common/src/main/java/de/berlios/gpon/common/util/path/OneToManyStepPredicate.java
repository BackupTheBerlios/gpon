package de.berlios.gpon.common.util.path;

import de.berlios.gpon.common.AssociationType.MultiplicityConstants;


public class OneToManyStepPredicate implements StepPredicate {

	// if at is not reversed (that means that we are viewing from the b side)
	public boolean evaluate(Step at) {
		return !at.isReverse() && at.getAssociationType().getMultiplicity().equals(MultiplicityConstants.ONE_TO_MANY);
	}

}
