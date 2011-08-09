package com.xsis.ics.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class SubRegion extends BaseDomain implements BaseEntity {
	private String name;
	private String code;
	private Region region;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private Set<Depo> depos = new HashSet<Depo>();

	public SubRegion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubRegion(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}		

	public Set<Depo> getDepos() {
		return depos;
	}

	public void setDepos(Set<Depo> depos) {
		this.depos = depos;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	@Override
	public boolean isNew() {
		return (getId() == null);
	}
}
