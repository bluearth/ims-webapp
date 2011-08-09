package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class Region extends BaseDomain implements BaseEntity, Serializable{

	private static final long serialVersionUID = 1290269195679795824L;
	private String regionName;
	private BigDecimal regionLongitude;
	private BigDecimal regionLatitude;
	private String regionCode;
	private Teritory teritory;
	private Set<SubRegion> subRegions = new HashSet<SubRegion>();
	
	public Region() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Region(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	public BigDecimal getRegionLongitude() {
		return regionLongitude;
	}

	public void setRegionLongitude(BigDecimal regionLongitude) {
		this.regionLongitude = regionLongitude;
	}

	public BigDecimal getRegionLatitude() {
		return regionLatitude;
	}

	public void setRegionLatitude(BigDecimal regionLatitude) {
		this.regionLatitude = regionLatitude;
	}

	public Teritory getTeritory() {
		return teritory;
	}

	public void setTeritory(Teritory teritory) {
		this.teritory = teritory;
	}

	public Set<SubRegion> getSubRegions() {
		return subRegions;
	}

	public void setSubRegions(Set<SubRegion> subRegions) {
		this.subRegions = subRegions;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	@Override
	public boolean isNew() {
		return (getId() == null);
	}
	
//	public String toString(){
//		return this.getRegionName();
//	}

}
