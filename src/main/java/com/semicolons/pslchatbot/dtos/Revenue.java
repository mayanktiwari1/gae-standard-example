/**
 * 
 */
package com.semicolons.pslchatbot.dtos;

/**
 * @author tiwarim1
 *
 */
public class Revenue {

	private String accountName;
	private int position;
	private double revenue;
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
	public double getRevenue() {
		return revenue;
	}
	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
}
