package com.semicolons.pslchatbot.controller;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.semicolons.pslchatbot.dtos.AvaialbleLeaveDto;
import com.semicolons.pslchatbot.dtos.Response;
import com.semicolons.pslchatbot.dtos.TicketDto;
import com.semicolons.pslchatbot.exception.ResourceNotFoundException;
import com.semicolons.pslchatbot.model.Accounts;
import com.semicolons.pslchatbot.model.AvailableLeaves;
import com.semicolons.pslchatbot.model.HumanResource;
import com.semicolons.pslchatbot.model.LeaveDetails;
import com.semicolons.pslchatbot.model.Revenue;
import com.semicolons.pslchatbot.model.Ticket;
import com.semicolons.pslchatbot.repository.AccountsRepository;
import com.semicolons.pslchatbot.repository.AvailableLeavesRepository;
import com.semicolons.pslchatbot.repository.HumanResourceRepository;
import com.semicolons.pslchatbot.repository.LeaveDetailsRepository;
import com.semicolons.pslchatbot.repository.RevenueRepository;
import com.semicolons.pslchatbot.repository.TicketRepository;

@RestController
@RequestMapping("/api")
public class PslChatBotController {

	
	  @Autowired RevenueRepository revenueRepository;
	  @Autowired AccountsRepository accountsRepository;
	  @Autowired HumanResourceRepository humanResourceRepository;
	  @Autowired TicketRepository ticketRepository;
	  @Autowired AvailableLeavesRepository availableLeavesRepository;
	  @Autowired LeaveDetailsRepository leaveDetailsRepository;
	  
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
    public Response getAccountsInfo(@RequestParam("type") String type, @RequestParam("quarter") int quarter, @RequestParam("count") Integer count) { 
    	List<Accounts> ac = accountsRepository.findByTypeAndQuarterOrderByPosition(type, quarter);
    	List<Accounts> sub = ac.subList(0, count);
    	
    	return getResponse(sub, type);
    }
    
  
	@GetMapping("/humanResource") 
    public Object getHumanResource(@RequestParam("type") String type, @RequestParam("quarter") Integer quarter,@RequestParam("location") String location) { 
    	
    	List<HumanResource> humanResourceList = humanResourceRepository.findByTypeAndQuarterAndLocation(type, quarter, location);
    	Response response = new Response();
    	if(!CollectionUtils.isEmpty(humanResourceList)) {
    		HumanResource humanResource = humanResourceList.get(0);
    		humanResource.setLocation(humanResource.getLocation());
    		humanResource.setPercentage(humanResource.getPercentage());
    		humanResource.setQuarter(humanResource.getQuarter());
    		humanResource.setType(humanResource.getType());
    		response.setStatus(true);
    		List<Object> objList = new ArrayList<>();
    		objList.add(humanResource);
    		response.setObject(objList);
    	}else {
    		response.setStatus(false);
    		response.setErrorMessage("No information found about " + type + " for location " + location + " for quarter " + quarter);
    	}
    	
    	return response;
    }
    
	private Response getResponse(List<Accounts> acs, String type) {
		
		Response response = new Response();
		
		if(CollectionUtils.isEmpty(acs)) {
			response.setStatus(false);
		} else {
			response.setStatus(true);
			
			List<Object> list = new ArrayList<Object>();
			
			for(Accounts ac : acs) {
				switch (type) {
				case "revenue":
					
					com.semicolons.pslchatbot.dtos.Revenue r1 = new com.semicolons.pslchatbot.dtos.Revenue();
					r1.setAccountName(ac.getAccountName());
					r1.setPosition(ac.getPosition());
					r1.setQuarter(ac.getQuarter());
					r1.setRevenue(ac.getRevenue());
					list.add(r1);
					break;
				
				case "risk":
					
					com.semicolons.pslchatbot.dtos.Risk risk = new com.semicolons.pslchatbot.dtos.Risk();
					risk.setAccountName(ac.getAccountName());
					risk.setContactPerson(ac.getContactPerson());
					risk.setPosition(ac.getPosition());
					risk.setQuarter(ac.getQuarter());
					list.add(risk);
					break;
				
				case "business_pipeline":
					
					com.semicolons.pslchatbot.dtos.Pipeline pipeline = new com.semicolons.pslchatbot.dtos.Pipeline();
					list.add(pipeline);
					
				default:
					break;
				}
				
				
			}
			response.setObject(list);	
		}
		
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
	
	@PostMapping("/raiseTicket") 
    public Object raiseTicket(@RequestParam("department") String department, @RequestParam("priority") Integer priority,@RequestParam("description") String description) { 
    	
		Ticket ticket = new Ticket();
		ticket.setDepartment(department);
		ticket.setDescription(description);
		ticket.setPriority(priority);
		ticket.setRequestNumber(generateRequestNumber());
		ticket.setTurnAroundtime(getTime(priority));
		ticketRepository.save(ticket);
		Response response = new Response();
		List<Object> objList = new ArrayList<>();
		TicketDto ticketDto = new TicketDto();
		ticketDto.setRequestId(ticket.getRequestNumber());
		ticketDto.setRequestTime(ticket.getTurnAroundtime());
		objList.add(ticketDto);
		response.setObject(objList);
		response.setStatus(true);
		return response;
    }

	private Integer getTime(Integer priority) {
		if(priority.intValue()==1) {
			return 1;
		}else if(priority.intValue()==2) {
			return 2;
		}
		return 3;
	}

	private String generateRequestNumber() {
		
		Random rnd = new Random();
	    int number = rnd.nextInt(999999);

	    // this will convert any number sequence into 6 character.
	    return "REQ_" + String.format("%06d", number);
	
	}
	
	@GetMapping("/getAvailableLeaves") 
    public Object getAvailableLeaves(@RequestParam(value = "", required=false)  String leaveType) { 
    	
		List<AvailableLeaves> availableLeavesList = new ArrayList<>();
		if("".equals(leaveType)) {
			availableLeavesList = availableLeavesRepository.findAll();
		}else {
			availableLeavesList = availableLeavesRepository.findByLeaveType(leaveType);
		}
		
		Response response = new Response();
		AvaialbleLeaveDto availableLeaves = null;
		List<Object> objList = new ArrayList<>();
    	if(!CollectionUtils.isEmpty(availableLeavesList)) {
    		for(AvailableLeaves availableLeavesDb : availableLeavesList ) {
    			availableLeaves = new  AvaialbleLeaveDto();
    			availableLeaves.setLeaveType(availableLeavesDb.getLeaveType());
    			availableLeaves.setBalance(availableLeavesDb.getBalance());
    			objList.add(availableLeaves);
    		}
    		
    		response.setObject(objList);
    	
    	}else {
    		response.setStatus(false);
    		response.setErrorMessage("No information found for leave " + leaveType);
    	}
    	
    	return response;
    }
	
	@PostMapping("/raiseLeave") 
    public Object raiseLeave(@RequestParam("leaveType") String leaveType, @RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate) { 
    	
		Response response = new Response(); 
		
		try {
		
		List<AvailableLeaves> availableLeavesList = new ArrayList<>();
		availableLeavesList = availableLeavesRepository.findByLeaveType(leaveType);
		
		if(CollectionUtils.isEmpty(availableLeavesList)) {
			response.setStatus(false);
			response.setErrorMessage(leaveType + " does not exists in system.");
			return response;
		}
	
		 Date sDate = getDate(startDate);
		 Date eDate = getDate(endDate);
		 
		 if(null==sDate || eDate==null) {
			 response.setStatus(false);
			 response.setErrorMessage("Start date or end date cannot be derived.");
			 return response;
		 }
		 
		 long days = getDifferenceDays(sDate, eDate);
		 
		 if(days < 0) {
			 response.setStatus(false);
			 response.setErrorMessage("Start date cannot be greater than end date.");
			 return response;
		 }
		 
		 int balance = availableLeavesList.get(0).getBalance();
		 
		 if(balance < days) {
			 response.setStatus(false);
			 response.setErrorMessage("Leave cannot be applied as you have only " + balance + " " + leaveType + ".");
			 return response;
		 }
		 
		 balance = balance - ((int)days);
		 
		 AvailableLeaves availableLeaves = availableLeavesList.get(0);
		 availableLeaves.setBalance(balance);
		 availableLeavesRepository.save(availableLeaves);
		 
		 LeaveDetails leaveDetails = new LeaveDetails();
		 leaveDetails.setDays((int)days);
		 leaveDetails.setStartDate(sDate);
		 leaveDetails.setEndDate(eDate);
		 leaveDetails.setLeaveType(leaveType);
		 
		 leaveDetailsRepository.save(leaveDetails);
		 
		 response.setStatus(true);
		 return response;
		 
		}catch(Exception e) {
			response.setStatus(false);
			response.setErrorMessage("Currently system is down. Please try after some time.");
			e.printStackTrace();
			return response;
		}
	}
	
	public Date getDate(String strDate) {
	
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = null;
		try {
			 date = df.parse(strDate);
			
		} catch (ParseException e) {
			return null;
		}

		return date;
	}

	public long getDifferenceDays(Date d1, Date d2) {
	    long diff = d2.getTime() - d1.getTime();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
	}

}
