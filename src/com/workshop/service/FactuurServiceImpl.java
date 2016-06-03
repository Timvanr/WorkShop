package com.workshop.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.workshop.dao.FactuurDAO;
import com.workshop.model.Factuur;

public class FactuurServiceImpl implements FactuurService {

	@Autowired
	private FactuurDAO factuurDao;
	
	@Override
	public void createFactuur(Factuur factuur) {
		factuurDao.save(factuur);
	}

	@Override
	public void verwijderFactuur(Long factuur_id) {
		factuurDao.delete(factuur_id);		
	}

	@Override
	public Factuur findFactuur(Long factuur_id) {
		return factuurDao.findOne(factuur_id);
		
	}

	@Override
	public Factuur findFactuurByBestelling_id(Long bestelling_id) {
		return factuurDao.findByBestelling_id(bestelling_id);
		
	}

}
