package com.semicolons.pslchatbot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.semicolons.pslchatbot.model.Accounts;

@Repository
	public interface AccountsRepository extends JpaRepository<Accounts, Long> {

	public List<Accounts> findByTypeAndQuarterOrderByPosition(String type, int quarter);
	
	public List<Accounts> findByTypeAndQuarterOrderByStartDate(String type, int quarter);
}
	

