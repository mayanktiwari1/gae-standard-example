package com.semicolons.pslchatbot.model;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;


import java.io.Serializable;

@Entity
@Table(name = "available_leave")
@EntityListeners(AuditingEntityListener.class)
public class AvailableLeaves implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -8842106455499084134L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String leaveType;

   
    private Integer balance;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getLeaveType() {
		return leaveType;
	}


	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}


	public Integer getBalance() {
		return balance;
	}


	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	
	

	
    
    
   
}