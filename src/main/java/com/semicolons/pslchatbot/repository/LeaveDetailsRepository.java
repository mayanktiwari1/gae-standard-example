package com.semicolons.pslchatbot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.semicolons.pslchatbot.model.LeaveDetails;


@Repository
	public interface LeaveDetailsRepository extends JpaRepository<LeaveDetails, Long> {

	
	
}
	

