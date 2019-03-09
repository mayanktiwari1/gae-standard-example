/**
 * 
 */
package com.semicolons.pslchatbot.dtos;

import java.util.Date;

/**
 * @author tiwarim1
 *
 */
public class Pipeline {
	
	private String accountName;
	private Date proposedStartDate;
	private String contactperson;
	
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Date getProposedStartDate() {
		return proposedStartDate;
	}
	public void setProposedStartDate(Date proposedStartDate) {
		this.proposedStartDate = proposedStartDate;
	}
	public String getContactperson() {
		return contactperson;
	}
	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}

	
}
