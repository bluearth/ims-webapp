/**
 * @(#)KPIDiagramForm.java		Sep 6, 2010 8:00:49 PM
 *
 * 
 * @history
 * Sep 6, 2010		Widyananda Dhanny		First Draft
 */
package com.xsis.ics.domain.dto;


/**
 * @author Widyananda Dhanny
 *
 */
public class KPIDiagramForm {
	private int id;
	private String salesTarget;
	private String name;
	private Long valueSalesTarget;
	
	
	public KPIDiagramForm(KPIDiagramForm kpi, Long value){
		this.id = kpi.getId();
		this.salesTarget = kpi.getSalesTarget();
		this.name = kpi.getName();
		this.valueSalesTarget = value;
	}
	
	public KPIDiagramForm() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the salesTarget
	 */
	public String getSalesTarget() {
		return salesTarget;
	}
	/**
	 * @param salesTarget the salesTarget to set
	 */
	public void setSalesTarget(String salesTarget) {
		this.salesTarget = salesTarget;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getValueSalesTarget() {
		return valueSalesTarget;
	}
	
	public void setValueSalesTarget(Long valueSalesTarget) {
		this.valueSalesTarget = valueSalesTarget;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	
	
	
	
}
