/**
 * 
 */
package com.xsis.ics.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yose
 *
 */
public class CanvasserPlanVisit implements Serializable {
	private Long canvasserId;
	private Long outletId;
	private Date visitTime;
	private String visitFlag;
	private String planFlag;
	private String transactionFlag;
	private String visitNotes;
	private String tripLength;
	private Outlet outlet;
	private Canvasser canvasser;
	
	public CanvasserPlanVisit(){
		canvasserId = null;
		outletId = null;
		visitTime = null;
		visitFlag = "N";
		planFlag = "N";
		canvasser = new Canvasser();
		outlet = new Outlet();
	}
	
	/**
	 * @return the canvasserId
	 */
	public Long getCanvasserId() {
		return canvasserId;
	}
	/**
	 * @param canvasserId the canvasserId to set
	 */
	public void setCanvasserId(Long canvasserId) {
		this.canvasserId = canvasserId;
	}
	/**
	 * @return the outletId
	 */
	public Long getOutletId() {
		return outletId;
	}
	/**
	 * @param outletId the outletId to set
	 */
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	/**
	 * @return the date
	 */
	public Date getVisitTime() {
		return visitTime;
	}
	/**
	 * @param date the date to set
	 */
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}
	/**
	 * @return the visitFlag
	 */
	public String getVisitFlag() {
		return visitFlag;
	}
	/**
	 * @param visitFlag the visitFlag to set
	 */
	public void setVisitFlag(String visitFlag) {
		this.visitFlag = visitFlag;
	}
	/**
	 * @return the planFlag
	 */
	public String getPlanFlag() {
		return planFlag;
	}
	/**
	 * @param planFlag the planFlag to set
	 */
	public void setPlanFlag(String planFlag) {
		this.planFlag = planFlag;
	}

	/**
	 * @return the outlet
	 */
	public Outlet getOutlet() {
		return outlet;
	}

	/**
	 * @param outlet the outlet to set
	 */
	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	/**
	 * @return the canvasser
	 */
	public Canvasser getCanvasser() {
		return canvasser;
	}

	/**
	 * @param canvasser the canvasser to set
	 */
	public void setCanvasser(Canvasser canvasser) {
		this.canvasser = canvasser;
	}

	/**
	 * @return the visitNotes
	 */
	public String getVisitNotes() {
		return visitNotes;
	}

	/**
	 * @param visitNotes the visitNotes to set
	 */
	public void setVisitNotes(String visitNotes) {
		this.visitNotes = visitNotes;
	}

	/**
	 * @return the tripLength
	 */
	public String getTripLength() {
		return tripLength;
	}

	/**
	 * @param tripLength the tripLength to set
	 */
	public void setTripLength(String tripLength) {
		this.tripLength = tripLength;
	}

	/**
	 * @return the transactionFlag
	 */
	public String getTransactionFlag() {
		return transactionFlag;
	}

	/**
	 * @param transactionFlag the transactionFlag to set
	 */
	public void setTransactionFlag(String transactionFlag) {
		this.transactionFlag = transactionFlag;
	}
	
	
}
