package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CanvasserTrack implements Serializable {

	private static final long serialVersionUID = 2776927175772387684L;
	private Long trackId;
	private Date trackTime;
	private BigDecimal trackLongitude;
	private BigDecimal trackLatitude;
	private Canvasser canvasser;

	public Long getTrackId() {
		return trackId;
	}

	public void setTrackId(Long trackId) {
		this.trackId = trackId;
	}

	public Date getTrackTime() {
		return trackTime;
	}

	public void setTrackTime(Date trackTime) {
		this.trackTime = trackTime;
	}

	public BigDecimal getTrackLongitude() {
		return trackLongitude;
	}

	public void setTrackLongitude(BigDecimal trackLongitude) {
		this.trackLongitude = trackLongitude;
	}

	public BigDecimal getTrackLatitude() {
		return trackLatitude;
	}

	public void setTrackLatitude(BigDecimal trackLatitude) {
		this.trackLatitude = trackLatitude;
	}

	public Canvasser getCanvasser() {
		return canvasser;
	}

	public void setCanvasser(Canvasser canvasser) {
		this.canvasser = canvasser;
	}

}
