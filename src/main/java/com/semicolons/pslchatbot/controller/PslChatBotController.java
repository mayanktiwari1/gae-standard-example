package com.semicolons.pslchatbot.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
    public Map<String,String> getAccountsInfo(@RequestParam("type") String type, @RequestParam("count") Integer count) { 
    	
    	StringBuilder str = new StringBuilder(); 
    	List<Accounts> accountsList = accountsRepository.findByTypeOrderByPosition(type);
    	if(!CollectionUtils.isEmpty(accountsList)) {
    		int pos = count > accountsList.size() ? accountsList.size() : count; 
    		accountsList =  accountsList.subList(0, pos);
    		str.append("Top " + type + " accounts are ");
    		accountsList.stream().forEach(x -> {
    			str.append(x.getAccountName() + " with contact person " + x.getContactPerson() + " , ");
    		});
    		str.setLength(str.length() - 3);
    	}else {
    		str.append("No information found for " + type + " accounts");
    	}
    	
    	return createResponse(str.toString());
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
    
}
