package com.semicolons.pslchatbot.model;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Table(name = "revenue")
@EntityListeners(AuditingEntityListener.class)
public class Revenue implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -8842106455499084134L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String businessUnit;

    @NotNull
    private Integer quarter;
    
    @NotNull
    private Integer quarterlyRevenue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public Integer getQuarter() {
		return quarter;
	}

	public void setQuarter(Integer quarter) {
		this.quarter = quarter;
	}

	public Integer getQuarterlyRevenue() {
		return quarterlyRevenue;
	}

	public void setQuarterlyRevenue(Integer quarterlyRevenue) {
		this.quarterlyRevenue = quarterlyRevenue;
	}

	
    
    
   
}