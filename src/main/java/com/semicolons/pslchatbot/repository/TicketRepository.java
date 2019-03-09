package com.semicolons.pslchatbot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.semicolons.pslchatbot.model.HumanResource;
import com.semicolons.pslchatbot.model.Ticket;

@Repository
	public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	

}
	

