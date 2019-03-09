package com.semicolons.pslchatbot.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.semicolons.pslchatbot.model.AvailableLeaves;


@Repository
	public interface AvailableLeavesRepository extends JpaRepository<AvailableLeaves, Long> {

	public List<AvailableLeaves> findByLeaveType(String leaveType);
	
}
	

