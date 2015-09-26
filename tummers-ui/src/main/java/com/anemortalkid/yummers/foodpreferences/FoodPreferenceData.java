package com.anemortalkid.yummers.foodpreferences;

import com.anemortalkid.yummers.associates.AssociateData;

public class FoodPreferenceData {

	private String id;
	private AssociateData associate;
	private String preferenceType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AssociateData getAssociate() {
		return associate;
	}

	public void setAssociate(AssociateData associate) {
		this.associate = associate;
	}

	public String getPreferenceType() {
		return preferenceType;
	}

	public void setPreferenceType(String preferenceType) {
		this.preferenceType = preferenceType;
	}

}
