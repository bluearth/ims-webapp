package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class Depo extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 973261031267289515L;
	private String depoCode;
	private String depoName;
	private BigDecimal depoLongitude;
	private BigDecimal depoLatitude;
	private DepoTrxSummary depoTrxSummary;
	private SubRegion subRegion;
	private Set<Dealer> dealers = new HashSet<Dealer>(0);

	public Depo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Depo(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public String getDepoCode() {
		return depoCode;
	}

	public void setDepoCode(String depoCode) {
		this.depoCode = depoCode;
	}

	public String getDepoName() {
		return depoName;
	}

	public void setDepoName(String depoName) {
		this.depoName = depoName;
	}

	public BigDecimal getDepoLongitude() {
		return depoLongitude;
	}

	public void setDepoLongitude(BigDecimal depoLongitude) {
		this.depoLongitude = depoLongitude;
	}

	public BigDecimal getDepoLatitude() {
		return depoLatitude;
	}

	public void setDepoLatitude(BigDecimal depoLatitude) {
		this.depoLatitude = depoLatitude;
	}

	public DepoTrxSummary getDepoTrxSummary() {
		return depoTrxSummary;
	}

	public void setDepoTrxSummary(DepoTrxSummary depoTrxSummary) {
		this.depoTrxSummary = depoTrxSummary;
	}

	public SubRegion getSubRegion() {
		return subRegion;
	}

	public void setSubRegion(SubRegion subRegion) {
		this.subRegion = subRegion;
	}

	public Set<Dealer> getDealers() {
		return dealers;
	}

	public void setDealers(Set<Dealer> dealers) {
		this.dealers = dealers;
	}

	@Override
	public boolean isNew() {
		return (getId() == null);
	}

}
