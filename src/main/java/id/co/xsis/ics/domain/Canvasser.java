package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class Canvasser extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = -8404695857944378201L;
	private String canvasserName;
	private String canvasserPhone;
	private String canvasserStatus;
	private String canvasserImei;
	private String canvasserSn;
	private String canvasserBattSn1;
	private String canvasserBattSn2;
	private String canvasserSex;
	private Date canvasserTime;
	private Date canvasserBirthDate;
	private Long canvasserSalary;
	private Long canvasserCommision;
	private BigDecimal canvasserLongitude;
	private BigDecimal canvasserLatitude;
	private Dealer dealer;
	private Set<Outlet> outlets = new HashSet<Outlet>(0);
	private Set<CanvasserRoutes> canvasserRouteses = new HashSet<CanvasserRoutes>(0);
	private Set<CanvasserTarget> canvasserTargets = new HashSet<CanvasserTarget>(0);
	private Set<CanvasserTrack> canvasserTracks = new HashSet<CanvasserTrack>(0);
//	private CanvasserTrxSummary canvasserTrxSummary;
	private Set<CanvasserVisit> canvasserVisits = new HashSet<CanvasserVisit>(0);
	private Address address;
	private String canvasserLogin;
	private String canvasserPassword;
	private Date canvasserJoinDate;
	private String canvasserType;
	
	public Canvasser() {
		super();
	}

	public Canvasser(Long id) {
		super(id);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getCanvasserName() {
		return canvasserName;
	}

	public void setCanvasserName(String canvasserName) {
		this.canvasserName = canvasserName;
	}

	public String getCanvasserPhone() {
		return canvasserPhone;
	}

	public void setCanvasserPhone(String canvasserPhone) {
		this.canvasserPhone = canvasserPhone;
	}

	public String getCanvasserStatus() {
		return canvasserStatus;
	}

	public void setCanvasserStatus(String canvasserStatus) {
		this.canvasserStatus = canvasserStatus;
	}

	public String getCanvasserImei() {
		return canvasserImei;
	}

	public void setCanvasserImei(String canvasserImei) {
		this.canvasserImei = canvasserImei;
	}

	public String getCanvasserSn() {
		return canvasserSn;
	}

	public void setCanvasserSn(String canvasserSn) {
		this.canvasserSn = canvasserSn;
	}

	public String getCanvasserBattSn1() {
		return canvasserBattSn1;
	}

	public void setCanvasserBattSn1(String canvasserBattSn1) {
		this.canvasserBattSn1 = canvasserBattSn1;
	}

	public String getCanvasserBattSn2() {
		return canvasserBattSn2;
	}

	public void setCanvasserBattSn2(String canvasserBattSn2) {
		this.canvasserBattSn2 = canvasserBattSn2;
	}

	public String getCanvasserSex() {
		return canvasserSex;
	}

	public void setCanvasserSex(String canvasserSex) {
		this.canvasserSex = canvasserSex;
	}

	public Date getCanvasserTime() {
		return canvasserTime;
	}

	public void setCanvasserTime(Date canvasserTime) {
		this.canvasserTime = canvasserTime;
	}

	public Date getCanvasserBirthDate() {
		return canvasserBirthDate;
	}

	public void setCanvasserBirthDate(Date canvasserBirthDate) {
		this.canvasserBirthDate = canvasserBirthDate;
	}

	public Long getCanvasserSalary() {
		return canvasserSalary;
	}

	public void setCanvasserSalary(Long canvasserSalary) {
		this.canvasserSalary = canvasserSalary;
	}

	public Long getCanvasserCommision() {
		return canvasserCommision;
	}

	public void setCanvasserCommision(Long canvasserCommision) {
		this.canvasserCommision = canvasserCommision;
	}

	public BigDecimal getCanvasserLongitude() {
		return canvasserLongitude;
	}

	public void setCanvasserLongitude(BigDecimal canvasserLongitude) {
		this.canvasserLongitude = canvasserLongitude;
	}

	public BigDecimal getCanvasserLatitude() {
		return canvasserLatitude;
	}

	public void setCanvasserLatitude(BigDecimal canvasserLatitude) {
		this.canvasserLatitude = canvasserLatitude;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Set<CanvasserRoutes> getCanvasserRouteses() {
		return canvasserRouteses;
	}

	public void setCanvasserRouteses(Set<CanvasserRoutes> canvasserRouteses) {
		this.canvasserRouteses = canvasserRouteses;
	}

	public Set<CanvasserTarget> getCanvasserTargets() {
		return canvasserTargets;
	}

	public void setCanvasserTargets(Set<CanvasserTarget> canvasserTargets) {
		this.canvasserTargets = canvasserTargets;
	}

	public Set<CanvasserTrack> getCanvasserTracks() {
		return canvasserTracks;
	}

	public void setCanvasserTracks(Set<CanvasserTrack> canvasserTracks) {
		this.canvasserTracks = canvasserTracks;
	}

	
//	public CanvasserTrxSummary getCanvasserTrxSummary() {
//		return canvasserTrxSummary;
//	}
//
//	public void setCanvasserTrxSummary(CanvasserTrxSummary canvasserTrxSummary) {
//		this.canvasserTrxSummary = canvasserTrxSummary;
//	}

	@Override
	public boolean isNew() {
		return (getId() == null);
	}

	public void setCanvasserVisits(Set<CanvasserVisit> canvasserVisits) {
		this.canvasserVisits = canvasserVisits;
	}

	public Set<CanvasserVisit> getCanvasserVisits() {
		return canvasserVisits;
	}
	
	public void setOutlets(Set<Outlet> outlets) {
		this.outlets = outlets;
	}

	public Set<Outlet> getOutlets() {
		return outlets;
	}

	public String getCanvasserLogin() {
		return canvasserLogin;
	}

	public void setCanvasserLogin(String canvasserLogin) {
		this.canvasserLogin = canvasserLogin;
	}

	public String getCanvasserPassword() {
		return canvasserPassword;
	}

	public void setCanvasserPassword(String canvasserPassword) {
		this.canvasserPassword = canvasserPassword;
	}

	public void setCanvasserJoinDate(Date canvasserJoinDate) {
		this.canvasserJoinDate = canvasserJoinDate;
	}

	public Date getCanvasserJoinDate() {
		return canvasserJoinDate;
	}

	/**
	 * @return the canvasserType
	 */
	public String getCanvasserType() {
		return canvasserType;
	}

	/**
	 * @param canvasserType the canvasserType to set
	 */
	public void setCanvasserType(String canvasserType) {
		this.canvasserType = canvasserType;
	}




}
