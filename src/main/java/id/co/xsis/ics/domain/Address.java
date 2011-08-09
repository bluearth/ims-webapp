package com.xsis.ics.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class Address extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = -5523042376927007717L;
	private String street;
	private String subdistrict;
	private String district;
	private String village;
	private String province;
	private Set<Outlet> outlets = new HashSet<Outlet>(0);
	private Set<Dealer> dealers = new HashSet<Dealer>(0);

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSubdistrict() {
		return subdistrict;
	}

	public void setSubdistrict(String subdistrict) {
		this.subdistrict = subdistrict;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Set<Outlet> getOutlets() {
		return outlets;
	}

	public void setOutlets(Set<Outlet> outlets) {
		this.outlets = outlets;
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

	/**
	 * @return the village
	 */
	public String getVillage() {
		return village;
	}

	/**
	 * @param village the village to set
	 */
	public void setVillage(String village) {
		this.village = village;
	}

}
