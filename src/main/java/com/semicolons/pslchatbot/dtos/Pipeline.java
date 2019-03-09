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
	
	private String projectName;
	private Date proposedStartDate;
	private String contactperson;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
