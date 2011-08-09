/**
 * @(#)KPIDiagramVisitForm.java		Sep 23, 2010  6:11:20 PM
 *
 * 
 * @history
 * Sep 23, 2010		Widyananda Dhanny		First Draft
 */
package com.xsis.ics.domain.dto;

/**
 * @author Widyananda Dhanny
 *
 */
public class KPIDiagramVisitForm {
	private String name;
	private String description;
	private String category;
	private Double value;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	

}
