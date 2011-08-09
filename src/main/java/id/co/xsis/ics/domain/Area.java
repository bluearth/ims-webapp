package com.xsis.ics.domain;

import java.io.Serializable;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class Area extends BaseDomain implements BaseEntity, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -917028182081657290L;
	private String desa;
	private String district;
	private String subDistrict;
	private String province;
	private Dealer dealer;
	
	public String getDesa() {
		return desa;
	}

	public void setDesa(String desa) {
		this.desa = desa;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getSubDistrict() {
		return subDistrict;
	}

	public void setSubDistrict(String subDistrict) {
		this.subDistrict = subDistrict;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return (getId() == null);
	}

}
