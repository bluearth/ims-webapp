package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class Outlet extends BaseDomain implements BaseEntity, Serializable{
	
	private static final long serialVersionUID = -6054061555662425819L;
	private String outletName;
	private String outletLocation;
	private String outletOwner;
	private String outletPhone;
	private String outletType;
	private String outletMsisdnDompul;
	private BigDecimal outletLongitude;
	private BigDecimal outletLatitude;
	private Long outletSalesCount;
	private String outletStatus;
	private String outletProfile;
	private String outletBankAccount;
	private String outletBankName;
	private String outletLevel;
	private String outletTaxNo;
	private String outletTaxName;
	private String outletSize;
	private String outletCode;
	private Long outletIdexId;
	private Date outletIdexStartDate;
	private Date outletIdexEndDate;
	private Canvasser canvasser;
	private Dealer dealer;
	private Address address;
	private String outletHotClass;
	private String outletOwnerIdCard;
	private String picPhoneNumber;
	private String picName;
	private String outletMallName;
	private String outletMallFloor;
	private String outletMallCategory;
	
	// --
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

	public Outlet() {
		super();
	}

	public Outlet(Long id) {
		super(id);
	}

	@Override
	public boolean isNew() {
		return (getId() == null);
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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

	public String getOutletMsisdnDompul() {
		return outletMsisdnDompul;
	}

	public void setOutletMsisdnDompul(String outletMsisdnDompul) {
		this.outletMsisdnDompul = outletMsisdnDompul;
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

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}
	
	public String getOutletStatus() {
		return outletStatus;
	}

	public void setOutletStatus(String outletStatus) {
		this.outletStatus = outletStatus;
	}

	public Canvasser getCanvasser() {
		return canvasser;
	}

	public void setCanvasser(Canvasser canvasser) {
		this.canvasser = canvasser;
	}

	public String getOutletProfile() {
		return outletProfile;
	}

	public void setOutletProfile(String outletProfile) {
		this.outletProfile = outletProfile;
	}

	public String getOutletBankAccount() {
		return outletBankAccount;
	}

	public void setOutletBankAccount(String outletBankAccount) {
		this.outletBankAccount = outletBankAccount;
	}

	public String getOutletBankName() {
		return outletBankName;
	}

	public void setOutletBankName(String outletBankName) {
		this.outletBankName = outletBankName;
	}

	public String getOutletLevel() {
		return outletLevel;
	}

	public void setOutletLevel(String outletLevel) {
		this.outletLevel = outletLevel;
	}

	public String getOutletTaxNo() {
		return outletTaxNo;
	}

	public void setOutletTaxNo(String outletTaxNo) {
		this.outletTaxNo = outletTaxNo;
	}

	public String getOutletTaxName() {
		return outletTaxName;
	}

	public void setOutletTaxName(String outletTaxName) {
		this.outletTaxName = outletTaxName;
	}

	public String getOutletSize() {
		return outletSize;
	}

	public void setOutletSize(String outletSize) {
		this.outletSize = outletSize;
	}

	public String getOutletCode() {
		return outletCode;
	}

	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}

	public Long getOutletIdexId() {
		return outletIdexId;
	}

	public void setOutletIdexId(Long outletIdexId) {
		this.outletIdexId = outletIdexId;
	}

	public Date getOutletIdexStartDate() {
		return outletIdexStartDate;
	}

	public void setOutletIdexStartDate(Date outletIdexStartDate) {
		this.outletIdexStartDate = outletIdexStartDate;
	}

	public Date getOutletIdexEndDate() {
		return outletIdexEndDate;
	}

	public void setOutletIdexEndDate(Date outletIdexEndDate) {
		this.outletIdexEndDate = outletIdexEndDate;
	}

	public void setOutletHotClass(String outletHotClass) {
		this.outletHotClass = outletHotClass;
	}

	public String getOutletHotClass() {
		return outletHotClass;
	}

	public void setOutletOwnerIdCard(String outletOwnerIdCard) {
		this.outletOwnerIdCard = outletOwnerIdCard;
	}

	public String getOutletOwnerIdCard() {
		return outletOwnerIdCard;
	}

	/**
	 * @return the picPhoneNumber
	 */
	public String getPicPhoneNumber() {
		return picPhoneNumber;
	}

	/**
	 * @param picPhoneNumber the picPhoneNumber to set
	 */
	public void setPicPhoneNumber(String picPhoneNumber) {
		this.picPhoneNumber = picPhoneNumber;
	}

	/**
	 * @return the picName
	 */
	public String getPicName() {
		return picName;
	}

	/**
	 * @param picName the picName to set
	 */
	public void setPicName(String picName) {
		this.picName = picName;
	}

	
	
}
