/**
 * 
 */
package com.xsis.ics.dao.impl;

import java.io.Serializable;

/**
 * @author yose
 *
 */
public class CanvasserNameIdDTO implements Serializable{
	private String canvasserName;
	private Long id;
	private String canvasserType;
	
	public String getCanvasserType() {
		return canvasserType;
	}
	public void setCanvasserType(String canvasserType) {
		this.canvasserType = canvasserType;
	}
	/**
	 * @return the canvasserName
	 */
	public String getCanvasserName() {
		return canvasserName;
	}
	/**
	 * @param canvasserName the canvasserName to set
	 */
	public void setCanvasserName(String canvasserName) {
		this.canvasserName = canvasserName;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
}
