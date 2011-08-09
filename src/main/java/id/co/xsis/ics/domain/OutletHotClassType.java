package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class OutletHotClassType implements Serializable {
	private Long outletId;
	private static final long serialVersionUID = -6054061555662425819L;
	private String outletName;
	private String outletLocation;
	private String outletOwner;
	private String outletPhone;
	private String outletType;
	private BigDecimal outletLongitude;
	private BigDecimal outletLatitude;
	private Long outletSalesCount;
	private Long outletIdexId;
	private String outletMsisdnDompul;
	private String outletStatus;
	private String outletOwnerIdCard;
	private String outletLevel;
	private Canvasser canvasser;
	private Dealer dealer;
	private Address address;
	private String outletHotClass;
	private String outletMallName;
	private String outletMallFloor;
	private String outletMallCategory;	
	
	
	public String getOutletMallName() {
		return outletMallName;
	}
	public void setOutletMallName(String outletMallName) {
		this.outletMallName = outletMallName;
	}
	public String getOutletMallFloor() {
		return outletMallFloor;
	}
	public void setOutletMallFloor(String outletMallFloor) {
		this.outletMallFloor = outletMallFloor;
	}
	public String getOutletMallCategory() {
		return outletMallCategory;
	}
	public void setOutletMallCategory(String outletMallCategory) {
		this.outletMallCategory = outletMallCategory;
	}

	
	
	
	public String getOutletLocation() {
		return outletLocation;
	}
	public void setOutletLocation(String outletLocation) {
		this.outletLocation = outletLocation;
	}
	public String getOutletOwner() {
		return outletOwner;
	}
	public void setOutletOwner(String outletOwner) {
		this.outletOwner = outletOwner;
	}
	public String getOutletPhone() {
		return outletPhone;
	}
	public void setOutletPhone(String outletPhone) {
		this.outletPhone = outletPhone;
	}
	public String getOutletType() {
		return outletType;
	}
	public void setOutletType(String outletType) {
		this.outletType = outletType;
	}
	public BigDecimal getOutletLongitude() {
		return outletLongitude;
	}
	public void setOutletLongitude(BigDecimal outletLongitude) {
		this.outletLongitude = outletLongitude;
	}
	public BigDecimal getOutletLatitude() {
		return outletLatitude;
	}
	public void setOutletLatitude(BigDecimal outletLatitude) {
		this.outletLatitude = outletLatitude;
	}
	public Long getOutletSalesCount() {
		return outletSalesCount;
	}
	public void setOutletSalesCount(Long outletSalesCount) {
		this.outletSalesCount = outletSalesCount;
	}
	public Canvasser getCanvasser() {
		return canvasser;
	}
	public void setCanvasser(Canvasser canvasser) {
		this.canvasser = canvasser;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getOutletMsisdnDompul() {
		return outletMsisdnDompul;
	}
	public void setOutletMsisdnDompul(String outletMsisdnDompul) {
		this.outletMsisdnDompul = outletMsisdnDompul;
	}
	public String getOutletLevel() {
		return outletLevel;
	}
	public void setOutletLevel(String outletLevel) {
		this.outletLevel = outletLevel;
	}
	
	public Long getOutletIdexId() {
		return outletIdexId;
	}
	public void setOutletIdexId(Long outletIdexId) {
		this.outletIdexId = outletIdexId;
	}
	public Dealer getDealer() {
		return dealer;
	}
	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}
	public String getOutletHotClass() {
		return outletHotClass;
	}
	public void setOutletHotClass(String outletHotClass) {
		this.outletHotClass = outletHotClass;
	}
	public void setOutletStatus(String outletStatus) {
		this.outletStatus = outletStatus;
	}
	public String getOutletStatus() {
		return outletStatus;
	}
	public void setOutletOwnerIdCard(String outletOwnerIdCard) {
		this.outletOwnerIdCard = outletOwnerIdCard;
	}
	public String getOutletOwnerIdCard() {
		return outletOwnerIdCard;
	}
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	public Long getOutletId() {
		return outletId;
	}


}
