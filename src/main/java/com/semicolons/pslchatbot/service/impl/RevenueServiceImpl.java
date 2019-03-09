package com.semicolons.pslchatbot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.semicolons.pslchatbot.model.Revenue;
import com.semicolons.pslchatbot.repository.RevenueRepository;
import com.semicolons.pslchatbot.service.RevenueService;

public class RevenueServiceImpl implements RevenueService {

    @Autowired
	private RevenueRepository repository;

    @Override
    public List<Revenue> findAll() {

        List<Revenue> revenues = (List<Revenue>) repository.findAll();
        
        return revenues;
    }
}
