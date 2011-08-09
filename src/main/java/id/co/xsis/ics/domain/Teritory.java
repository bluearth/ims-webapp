package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class Teritory extends BaseDomain implements BaseEntity, Serializable{

	private static final long serialVersionUID = 8898893289228230693L;
	private String teritoryName;
	private String teritoryCode;
	private BigDecimal teritoryLongitude;
	private BigDecimal teritoryLatitude;
	private Set<Region> regions = new HashSet<Region>(0);
	
	public Teritory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Teritory(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public String getTeritoryCode() {
		return teritoryCode;
	}

	public void setTeritoryCode(String teritoryCode) {
		this.teritoryCode = teritoryCode;
	}

	public String getTeritoryName() {
		return teritoryName;
	}

	public void setTeritoryName(String teritoryName) {
		this.teritoryName = teritoryName;
	}

	public BigDecimal getTeritoryLongitude() {
		return teritoryLongitude;
	}

	public void setTeritoryLongitude(BigDecimal teritoryLongitude) {
		this.teritoryLongitude = teritoryLongitude;
	}

	public BigDecimal getTeritoryLatitude() {
		return teritoryLatitude;
	}

	public void setTeritoryLatitude(BigDecimal teritoryLatitude) {
		this.teritoryLatitude = teritoryLatitude;
	}

	public Set<Region> getRegions() {
		return regions;
	}

	public void setRegions(Set<Region> regions) {
		this.regions = regions;
	}

	@Override
	public boolean isNew() {
		return (getId() == null);
	}
	
//	public String toString() {
//	   return this.getTeritoryName();
//	}
}
