/**
 * 
 */
package com.semicolons.pslchatbot.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tiwarim1
 *
 */
public class Response {
	
	private boolean status;
	private List<Object> object = new ArrayList<Object>();
	private String errorMessage;
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<Object> getObject() {
		return object;
	}
	public void setObject(List<Object> object) {
		this.object = object;
	}
}
