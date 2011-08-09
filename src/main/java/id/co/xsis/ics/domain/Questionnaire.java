package com.xsis.ics.domain;

import java.io.Serializable;
import java.util.Date;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class Questionnaire extends BaseDomain implements BaseEntity, Serializable{

	private String quesCode;
	private Date quesEffectiveDate;
	private Date quesIneffectiveDate;
	private String quesDescription;
	private String quesReferrenceTo;
	private Long quesReferrenceId;
	
	public String getQuesCode() {
		return quesCode;
	}


	public void setQuesCode(String quesCode) {
		this.quesCode = quesCode;
	}


	public Date getQuesEffectiveDate() {
		return quesEffectiveDate;
	}


	public void setQuesEffectiveDate(Date quesEffectiveDate) {
		this.quesEffectiveDate = quesEffectiveDate;
	}


	public Date getQuesIneffectiveDate() {
		return quesIneffectiveDate;
	}


	public void setQuesIneffectiveDate(Date quesIneffectiveDate) {
		this.quesIneffectiveDate = quesIneffectiveDate;
	}


	public String getQuesDescription() {
		return quesDescription;
	}


	public void setQuesDescription(String quesDescription) {
		this.quesDescription = quesDescription;
	}


	public String getQuesReferrenceTo() {
		return quesReferrenceTo;
	}


	public void setQuesReferrenceTo(String quesReferrenceTo) {
		this.quesReferrenceTo = quesReferrenceTo;
	}


	public Long getQuesReferrenceId() {
		return quesReferrenceId;
	}


	public void setQuesReferrenceId(Long quesReferrenceId) {
		this.quesReferrenceId = quesReferrenceId;
	}


	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return (getId() == null);
	}

}
