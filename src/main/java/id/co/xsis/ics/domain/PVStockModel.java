package com.xsis.ics.domain;

import java.math.BigDecimal;

public class PVStockModel {

	private Long PV10Xl;
	private Long PV50Xl;

	public Long getPV50Xl() {
		return PV50Xl;
	}

	public void setPV50Xl(Long pV50Xl) {
		PV50Xl = pV50Xl;
	}

	private Long PVIsat;
	private Long PVTsel;
	private Long PVLain;

	public Long getPV10Xl() {
		return PV10Xl;
	}

	public void setPV10Xl(Long pV10Xl) {
		PV10Xl = pV10Xl;
	}

	public Long getPVIsat() {
		return PVIsat;
	}

	public void setPVIsat(Long pVIsat) {
		PVIsat = pVIsat;
	}

	public Long getPVTsel() {
		return PVTsel;
	}

	public void setPVTsel(Long pVTsel) {
		PVTsel = pVTsel;
	}

	public Long getPVLain() {
		return PVLain;
	}

	public void setPVLain(Long pVLain) {
		PVLain = pVLain;
	}

}
