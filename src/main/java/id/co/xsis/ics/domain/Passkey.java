package com.xsis.ics.domain;

import java.io.Serializable;
import java.util.Date;

import com.xsis.ics.domain.base.BaseDomain;

public class Passkey extends BaseDomain implements Serializable{
	private Long passkeyId;
	private String generatedPasskey;
	private Date effectiveDate;
	private Date ineffectiveDate;
	private Long passkeyRefferenceId;
	private String passkeyRefferenceTo;
	private Date creationDate;
	private Long createdBy;
	private Date lastUpdatedDate;
	private Long lastUpdatedBy;
	
	public Long getPasskeyId() {
		return passkeyId;
	}
	public void setPasskeyId(Long passkeyId) {
		this.passkeyId = passkeyId;
	}
	public String getGeneratedPasskey() {
		return generatedPasskey;
	}
	public void setGeneratedPasskey(String generatedPasskey) {
		this.generatedPasskey = generatedPasskey;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getIneffectiveDate() {
		return ineffectiveDate;
	}
	public void setIneffectiveDate(Date ineffectiveDate) {
		this.ineffectiveDate = ineffectiveDate;
	}
	public Long getPasskeyRefferenceId() {
		return passkeyRefferenceId;
	}
	public void setPasskeyRefferenceId(Long passkeyRefferenceId) {
		this.passkeyRefferenceId = passkeyRefferenceId;
	}
	public String getPasskeyRefferenceTo() {
		return passkeyRefferenceTo;
	}
	public void setPasskeyRefferenceTo(String passkeyRefferenceTo) {
		this.passkeyRefferenceTo = passkeyRefferenceTo;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the lastUpdatedDate
	 */
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	/**
	 * @param lastUpdatedDate the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	/**
	 * @return the lastUpdatedBy
	 */
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	/**
	 * @param lastUpdatedBy the lastUpdatedBy to set
	 */
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

}
