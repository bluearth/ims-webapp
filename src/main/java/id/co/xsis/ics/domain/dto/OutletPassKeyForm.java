/**
 * @(#)PassKeyAllForm.java		Sep 13, 2010 3:52:49 PM
 *
 * 
 * @history
 * Sep 13, 2010		Uke		First Draft
 */
package com.xsis.ics.domain.dto;

import com.xsis.ics.domain.Outlet;

/**
 * @author Uke
 *
 */

public class OutletPassKeyForm {

	private Outlet outlet;
	private String passKey;
	
	public Outlet getOutlet() {
		return outlet;
	}
	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}
	public String getPassKey() {
		return passKey;
	}
	public void setPassKey(String passKey) {
		this.passKey = passKey;
	}
	
	
}
