package com.semicolons.pslchatbot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.semicolons.pslchatbot.model.HumanResource;

@Repository
	public interface HumanResourceRepository extends JpaRepository<HumanResource, Long> {
	
	public List<HumanResource> findByTypeAndQuarterAndLocation(String type,Integer quarter, String location);
	
}
	

