package com.semicolons.pslchatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.semicolons.pslchatbot.model.Revenue;

@Repository
	public interface RevenueRepository extends JpaRepository<Revenue, Long> {

}
	

