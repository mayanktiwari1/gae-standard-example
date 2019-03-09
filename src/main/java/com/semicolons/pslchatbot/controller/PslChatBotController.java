package com.semicolons.pslchatbot.controller;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.semicolons.pslchatbot.dtos.AvaialbleLeaveDto;
import com.semicolons.pslchatbot.dtos.Response;
import com.semicolons.pslchatbot.dtos.TicketDto;
import com.semicolons.pslchatbot.model.Accounts;
import com.semicolons.pslchatbot.model.AvailableLeaves;
import com.semicolons.pslchatbot.model.HumanResource;
import com.semicolons.pslchatbot.model.LeaveDetails;
import com.semicolons.pslchatbot.model.Ticket;
import com.semicolons.pslchatbot.repository.AccountsRepository;
import com.semicolons.pslchatbot.repository.AvailableLeavesRepository;
import com.semicolons.pslchatbot.repository.HumanResourceRepository;
import com.semicolons.pslchatbot.repository.LeaveDetailsRepository;
import com.semicolons.pslchatbot.repository.TicketRepository;

@RestController
@RequestMapping("/api")
public class PslChatBotController {

	
	  @Autowired AccountsRepository accountsRepository;
	  @Autowired HumanResourceRepository humanResourceRepository;
	  @Autowired TicketRepository ticketRepository;
	  @Autowired AvailableLeavesRepository availableLeavesRepository;
	  @Autowired LeaveDetailsRepository leaveDetailsRepository;
	  
	 @GetMapping("/accountsInfo") 
    public Response getAccountsInfo(@RequestParam("type") String type, @RequestParam("quarter") int quarter, @RequestParam("count") Integer count) { 
    	List<Accounts> ac = accountsRepository.findByTypeAndQuarterOrderByPosition(type, quarter);
    	if(count < ac.size()) {
    		List<Accounts> sub = ac.subList(0, count);
    		return getResponse(sub, type);
    	} else {
    		return getResponse(ac, type);
    	}
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
					pipeline.setContactperson(ac.getContactPerson());
					pipeline.setProjectName(ac.getContactPerson());
				//	pipeline.setProposedStartDate();
					list.add(pipeline);
					
				default:
					break;
				}
				
				
			}
			response.setObject(list);	
		}
		
		return response;
		
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
    		response.setStatus(true);
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
