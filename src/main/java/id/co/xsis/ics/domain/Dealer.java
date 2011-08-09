package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class Dealer extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1705449143862847935L;
	
	private String dealerName;
	private String dealerPhone;
	private BigDecimal dealerLongitude;
	private BigDecimal dealerLatitude;
	private String dealerStatus;
	private String dealerCode;
	private String dealerType;
	private Date dealerIdexStartDate;
	private Date dealerIdexEndDate;
	private Long dealerIdexId;
	private String dealerIdexCode;
	private Depo depo;
	private Address address;
	
	private Set<Canvasser> canvassers = new HashSet<Canvasser>(0);
	private Set<SecUserClusterMapping> userMappings = new HashSet<SecUserClusterMapping>(0);

	public Dealer() {
		super();
	}

	public Dealer(Long id) {
		super(id);
	}


	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	private Set<Outlet> outlets = new HashSet<Outlet>(0);

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getDealerPhone() {
		return dealerPhone;
	}

	public void setDealerPhone(String dealerPhone) {
		this.dealerPhone = dealerPhone;
	}

	public BigDecimal getDealerLongitude() {
		return dealerLongitude;
	}

	public void setDealerLongitude(BigDecimal dealerLongitude) {
		this.dealerLongitude = dealerLongitude;
	}

	public BigDecimal getDealerLatitude() {
		return dealerLatitude;
	}

	public void setDealerLatitude(BigDecimal dealerLatitude) {
		this.dealerLatitude = dealerLatitude;
	}

	public String getDealerStatus() {
		return dealerStatus;
	}

	public void setDealerStatus(String dealerStatus) {
		this.dealerStatus = dealerStatus;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerType() {
		return dealerType;
	}

	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}

	public Date getDealerIdexStartDate() {
		return dealerIdexStartDate;
	}

	public void setDealerIdexStartDate(Date dealerIdexStartDate) {
		this.dealerIdexStartDate = dealerIdexStartDate;
	}

	public Date getDealerIdexEndDate() {
		return dealerIdexEndDate;
	}

	public void setDealerIdexEndDate(Date dealerIdexEndDate) {
		this.dealerIdexEndDate = dealerIdexEndDate;
	}

	public Long getDealerIdexId() {
		return dealerIdexId;
	}

	public void setDealerIdexId(Long dealerIdexId) {
		this.dealerIdexId = dealerIdexId;
	}

	public Depo getDepo() {
		return depo;
	}

	public void setDepo(Depo depo) {
		this.depo = depo;
	}

	public Set<Canvasser> getCanvassers() {
		return canvassers;
	}

	public void setCanvassers(Set<Canvasser> canvassers) {
		this.canvassers = canvassers;
	}

	public Set<Outlet> getOutlets() {
		return outlets;
	}

	public void setOutlets(Set<Outlet> outlets) {
		this.outlets = outlets;
	}
	
	/**
	 * @return the dealerIdexCode
	 */
	public String getDealerIdexCode() {
		return dealerIdexCode;
	}

	/**
	 * @param dealerIdexCode the dealerIdexCode to set
	 */
	public void setDealerIdexCode(String dealerIdexCode) {
		this.dealerIdexCode = dealerIdexCode;
	}

	@Override
	public boolean isNew() {
		return (getId() == null);
	}

	/**
	 * @return the userMappings
	 */
	public Set<SecUserClusterMapping> getUserMappings() {
		return userMappings;
	}

	/**
	 * @param userMappings the userMappings to set
	 */
	public void setUserMappings(Set<SecUserClusterMapping> userMappings) {
		this.userMappings = userMappings;
	}
	
	@Override
	public boolean equals(Object other){
		if (other != null && other instanceof Dealer){
			Dealer otherDealer = (Dealer) other;
			return otherDealer.getId().equals(this.getId());
		}
		return false;
	}

}