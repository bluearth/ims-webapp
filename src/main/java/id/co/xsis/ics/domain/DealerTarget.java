package com.xsis.ics.domain;

import java.io.Serializable;
import java.util.Date;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class DealerTarget extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = -4243953070726457836L;
	private Date effectiveDate;
	private Date ineffectiveDate;
	private Long dealerTargetDp;
	private Long dealerTargetPv;
	private Long dealerTargetMonth;
	private Long dealerTargetYear;
	private Long dealerTargetSp;
	private Dealer dealer;

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

	public Long getDealerTargetDp() {
		return dealerTargetDp;
	}

	public void setDealerTargetDp(Long dealerTargetDp) {
		this.dealerTargetDp = dealerTargetDp;
	}

	public Long getDealerTargetPv() {
		return dealerTargetPv;
	}

	public void setDealerTargetPv(Long dealerTargetPv) {
		this.dealerTargetPv = dealerTargetPv;
	}

	public Long getDealerTargetMonth() {
		return dealerTargetMonth;
	}

	public void setDealerTargetMonth(Long dealerTargetMonth) {
		this.dealerTargetMonth = dealerTargetMonth;
	}

	public Long getDealerTargetYear() {
		return dealerTargetYear;
	}

	public void setDealerTargetYear(Long dealerTargetYear) {
		this.dealerTargetYear = dealerTargetYear;
	}

	public Long getDealerTargetSp() {
		return dealerTargetSp;
	}

	public void setDealerTargetSp(Long dealerTargetSp) {
		this.dealerTargetSp = dealerTargetSp;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	@Override
	public boolean isNew() {
		return (getId() == null);
	}

}
