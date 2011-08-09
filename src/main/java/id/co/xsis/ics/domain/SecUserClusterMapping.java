/**
 * 
 */
package com.xsis.ics.domain;

import java.io.Serializable;

import com.xsis.ics.domain.Dealer;
import com.xsis.security.model.SecUser;

/**
 * @author yose
 *
 */
public class SecUserClusterMapping implements Serializable{
	private Long id;
	private SecUser user;
	private Dealer dealer;
	
	public SecUserClusterMapping(){
		this(null,null);
	}
	
	public SecUserClusterMapping(SecUser user, Dealer dealer){
		this.setDealer(dealer);
		this.setUser(user);
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
	/**
	 * @return the user
	 */
	public SecUser getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(SecUser user) {
		this.user = user;
	}
	/**
	 * @return the dealers
	 */
	public Dealer getDealer() {
		return dealer;
	}
	/**
	 * @param dealers the dealers to set
	 */
	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}
	
	
}
