package com.semicolons.pslchatbot.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.semicolons.pslchatbot.dtos.*;
import com.semicolons.pslchatbot.exception.ResourceNotFoundException;
import com.semicolons.pslchatbot.model.Accounts;
import com.semicolons.pslchatbot.model.HumanResource;
import com.semicolons.pslchatbot.model.Revenue;
import com.semicolons.pslchatbot.repository.AccountsRepository;
import com.semicolons.pslchatbot.repository.HumanResourceRepository;
import com.semicolons.pslchatbot.repository.RevenueRepository;
import com.semicolons.pslchatbot.repository.TicketRepository;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PslChatBotController {

	
	  @Autowired RevenueRepository revenueRepository;
	  @Autowired AccountsRepository accountsRepository;
	  @Autowired HumanResourceRepository humanResourceRepository;
	  @Autowired TicketRepository ticketRepository;
	  
	  // Get All Notes
	  
	  @GetMapping("/revenue") public List<Revenue> getAllNotes() { return
	  revenueRepository.findAll(); }
	  
	  // Create a new Note
	  
	  @PostMapping("/revenue") public Revenue createNote(@Valid @RequestBody
	  Revenue revenue) { return revenueRepository.save(revenue); }
	  
	  // Get a Single Note
	  
	  @GetMapping("/revenue/{id}") public Revenue getNoteById(@PathVariable(value =
	  "id") Long revenueId) { return revenueRepository.findById(revenueId)
	  .orElseThrow(() -> new ResourceNotFoundException("Revenue", "id",
	  revenueId)); }
	  
	  // Update a Note
	  
	  @PutMapping("/revenue/{id}") public Revenue updateNote(@PathVariable(value =
	  "id") Long revenueId,
	  
	  @Valid @RequestBody Revenue revenueDetails) {
	  
	  Revenue revenue = revenueRepository.findById(revenueId) .orElseThrow(() ->
	  new ResourceNotFoundException("Revenue", "id", revenueId));
	  
	  revenue.setBusinessUnit(revenueDetails.getBusinessUnit());
	  revenue.setQuarter(revenueDetails.getQuarter());
	  revenue.setQuarterlyRevenue(revenueDetails.getQuarterlyRevenue());
	  
	  Revenue updateRevenue = revenueRepository.save(revenue); return
	  updateRevenue; }
	  
	  // Delete a Note
	  
	  @DeleteMapping("/revenue/{id}") public ResponseEntity<?>
	  deleteNote(@PathVariable(value = "id") Long revenueId) { Revenue note =
	  revenueRepository.findById(revenueId) .orElseThrow(() -> new
	  ResourceNotFoundException("Revenue", "id", revenueId));
	  
	  revenueRepository.delete(note);
	  
	  return ResponseEntity.ok().build(); }
	 
    
    @GetMapping("/revenueAccounts")
    public List<String> getRevenueAccounts() {
    	List<String> list = new ArrayList<>();
    	list.add("Thermofisher");
    	list.add("Mobitv");
    	list.add("Wells Fargo");
    	return list;
    }
    
    @GetMapping("/accountsInfo") 
    public Response getAccountsInfo(@RequestParam("type") String type, @RequestParam("count") Integer count) { 
    	return accountStub(type);
    }
    
    private Map<String, String> createResponse(String string) {
		Map<String,String> map = new HashMap<>();
		map.put("response", string);
		return map;
	}

	@GetMapping("/humanResource") 
    public Map<String,String> getHumanResource(@RequestParam("type") String type, @RequestParam("quarter") Integer quarter,@RequestParam("location") String location) { 
    	StringBuilder str = new StringBuilder(); 
    	List<HumanResource> humanResourceList = humanResourceRepository.findByTypeAndQuarterAndLocation(type, quarter, location);
    	if(!CollectionUtils.isEmpty(humanResourceList)) {
    		HumanResource humanResource = humanResourceList.get(0);
    		if("head count".equalsIgnoreCase(type)) {
    			str.append(type + " of location " + location + " for quarter " + quarter + " is " + humanResource.getPercentage());
    		}else {
    			str.append(type + " rate of location " + location + " for quarter " + quarter + " is " + humanResource.getPercentage() + " percantege");
    		}
    	}else {
    		str.append("No information found about " + type + " for location " + location + " for quarter " + quarter);
    	}
    	
    	return createResponse(str.toString());
    }
    
	private Response accountStub(String type) {
		
		Response response = new Response();
		response.setStatus(true);
		
		response.setObject(getObjects(type));
		
		return response;
		
	}
	
	private List<Object> getObjects(String type) {
		List<Object> list = new ArrayList<Object>();
		switch (type) {
		case "revenue":
			
			com.semicolons.pslchatbot.dtos.Revenue r1 = new com.semicolons.pslchatbot.dtos.Revenue();
			r1.setAccountName("Thermo1");
			r1.setPosition(1);
			r1.setQuarter(1);
			r1.setRevenue(100000);
			
			com.semicolons.pslchatbot.dtos.Revenue r2 = new com.semicolons.pslchatbot.dtos.Revenue();
			r2.setAccountName("Thermo2");
			r2.setPosition(2);
			r2.setQuarter(1);
			r2.setRevenue(200000);
			
			com.semicolons.pslchatbot.dtos.Revenue r3 = new com.semicolons.pslchatbot.dtos.Revenue();
			r3.setAccountName("Thermo3");
			r3.setPosition(3);
			r3.setQuarter(1);
			r3.setRevenue(300000);
			
			com.semicolons.pslchatbot.dtos.Revenue r4 = new com.semicolons.pslchatbot.dtos.Revenue();
			r4.setAccountName("Thermo4");
			r4.setPosition(4);
			r4.setQuarter(1);
			r4.setRevenue(400000);
			
			com.semicolons.pslchatbot.dtos.Revenue r5 = new com.semicolons.pslchatbot.dtos.Revenue();
			r5.setAccountName("Thermo5");
			r5.setPosition(5);
			r5.setQuarter(1);
			r5.setRevenue(500000);
			
			list.add(r1);
			list.add(r2);
			list.add(r3);
			list.add(r4);
			list.add(r5);
			
			break;
		case "risk":
			
			break;
		default:
			break;
		}
		return list;
	}
}
