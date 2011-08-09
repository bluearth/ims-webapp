package com.xsis.ics.domain;

import java.io.Serializable;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class Branding extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 7543975656797094797L;
	private Long numOfFlyerXL;
	private Long numOfPosterXL;
	private Long numOfShopBlindXL;
	private Long numOfNeonBoxXL;
	private String brandingPaintedXL;
	private Long numOfFlyerIsat;
	private Long numOfPosterIsat;
	private Long numOfShopBlindIsat;
	private Long numOfNeonBoxIsat;
	private Long numOfFlyerTSel;
	private Long numOfPosterTSel;
	private Long numOfShopBlindTsel;
	private Long numOfNeonBoxTsel;
	private String brandingLain;
	private CanvasserVisit canvasserVisit;

	public Long getNumOfFlyerXL() {
		return numOfFlyerXL;
	}

	public void setNumOfFlyerXL(Long numOfFlyerXL) {
		this.numOfFlyerXL = numOfFlyerXL;
	}

	public Long getNumOfPosterXL() {
		return numOfPosterXL;
	}

	public void setNumOfPosterXL(Long numOfPosterXL) {
		this.numOfPosterXL = numOfPosterXL;
	}

	public Long getNumOfShopBlindXL() {
		return numOfShopBlindXL;
	}

	public void setNumOfShopBlindXL(Long numOfShopBlindXL) {
		this.numOfShopBlindXL = numOfShopBlindXL;
	}

	public Long getNumOfNeonBoxXL() {
		return numOfNeonBoxXL;
	}

	public void setNumOfNeonBoxXL(Long numOfNeonBoxXL) {
		this.numOfNeonBoxXL = numOfNeonBoxXL;
	}

	public String getBrandingPaintedXL() {
		return brandingPaintedXL;
	}

	public void setBrandingPaintedXL(String brandingPaintedXL) {
		this.brandingPaintedXL = brandingPaintedXL;
	}

	public Long getNumOfFlyerIsat() {
		return numOfFlyerIsat;
	}

	public void setNumOfFlyerIsat(Long numOfFlyerIsat) {
		this.numOfFlyerIsat = numOfFlyerIsat;
	}

	public Long getNumOfPosterIsat() {
		return numOfPosterIsat;
	}

	public void setNumOfPosterIsat(Long numOfPosterIsat) {
		this.numOfPosterIsat = numOfPosterIsat;
	}

	public Long getNumOfShopBlindIsat() {
		return numOfShopBlindIsat;
	}

	public void setNumOfShopBlindIsat(Long numOfShopBlindIsat) {
		this.numOfShopBlindIsat = numOfShopBlindIsat;
	}

	public Long getNumOfNeonBoxIsat() {
		return numOfNeonBoxIsat;
	}

	public void setNumOfNeonBoxIsat(Long numOfNeonBoxIsat) {
		this.numOfNeonBoxIsat = numOfNeonBoxIsat;
	}

	public Long getNumOfFlyerTSel() {
		return numOfFlyerTSel;
	}

	public void setNumOfFlyerTSel(Long numOfFlyerTSel) {
		this.numOfFlyerTSel = numOfFlyerTSel;
	}

	public Long getNumOfPosterTSel() {
		return numOfPosterTSel;
	}

	public void setNumOfPosterTSel(Long numOfPosterTSel) {
		this.numOfPosterTSel = numOfPosterTSel;
	}

	public Long getNumOfShopBlindTsel() {
		return numOfShopBlindTsel;
	}

	public void setNumOfShopBlindTsel(Long numOfShopBlindTsel) {
		this.numOfShopBlindTsel = numOfShopBlindTsel;
	}

	public Long getNumOfNeonBoxTsel() {
		return numOfNeonBoxTsel;
	}

	public void setNumOfNeonBoxTsel(Long numOfNeonBoxTsel) {
		this.numOfNeonBoxTsel = numOfNeonBoxTsel;
	}

	public String getBrandingLain() {
		return brandingLain;
	}

	public void setBrandingLain(String brandingLain) {
		this.brandingLain = brandingLain;
	}

	public CanvasserVisit getCanvasserVisit() {
		return canvasserVisit;
	}

	public void setCanvasserVisit(CanvasserVisit canvasserVisit) {
		this.canvasserVisit = canvasserVisit;
	}

	@Override
	public boolean isNew() {
		return (getId() == null);
	}

}
