/**
 * 
 */
package com.semicolons.pslchatbot.dtos;

/**
 * @author tiwarim1
 *
 */
public class Risk {
	
	private String accountName;
	private int position;
	private String contactPerson;
	private int quarter;
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	
}
