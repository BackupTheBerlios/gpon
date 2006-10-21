package de.berlios.gpon.common.util.path;

import de.berlios.gpon.common.AssociationType;


public class Step {

	AssociationType associationType;

	boolean reverse;

	public Step(AssociationType associationType, boolean reverse) {
		super();
		this.associationType = associationType;
		this.reverse = reverse;
	}

	public AssociationType getAssociationType() {
		return associationType;
	}

	public void setAssociationType(AssociationType associationType) {
		this.associationType = associationType;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public boolean equals(Object obj) {

		if (obj instanceof Step) {
			Step other = (Step) obj;

			return this.getAssociationType().equals(other.getAssociationType());
		} else {
			return false;
		}

	}

	public int hashCode() {
		return this.getAssociationType().hashCode();
	}

	public String getStepDigest() {
		return ""+(isReverse()?"-":"+")+getAssociationType().getId();
	}

	public Long getFromTypeId() 
	{
		return (isReverse())?getAssociationType().getItemBType().getId():getAssociationType().getItemAType().getId();
	}
	
	public Long getToTypeId() 
	{
		return (isReverse())?getAssociationType().getItemAType().getId():getAssociationType().getItemBType().getId();
	}
}
