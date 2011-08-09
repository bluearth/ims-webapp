package com.xsis.ics.domain;

import java.io.Serializable;

public class Filiale implements Serializable {

	private static final long serialVersionUID = -639862883698401190L;
	private Long filId;
	private String filNr;
	private String filBezeichnung;
	private String filName1;
	private String filName2;
	private String filOrt;
	private Short vers;

	public Long getFilId() {
		return filId;
	}

	public void setFilId(Long filId) {
		this.filId = filId;
	}

	public String getFilNr() {
		return filNr;
	}

	public void setFilNr(String filNr) {
		this.filNr = filNr;
	}

	public String getFilBezeichnung() {
		return filBezeichnung;
	}

	public void setFilBezeichnung(String filBezeichnung) {
		this.filBezeichnung = filBezeichnung;
	}

	public String getFilName1() {
		return filName1;
	}

	public void setFilName1(String filName1) {
		this.filName1 = filName1;
	}

	public String getFilName2() {
		return filName2;
	}

	public void setFilName2(String filName2) {
		this.filName2 = filName2;
	}

	public String getFilOrt() {
		return filOrt;
	}

	public void setFilOrt(String filOrt) {
		this.filOrt = filOrt;
	}

	public Short getVers() {
		return vers;
	}

	public void setVers(Short vers) {
		this.vers = vers;
	}
}
