package com.workshop.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.workshop.dao.FactuurDAOInterface;
import com.workshop.model.Factuur;

public class FactuurServiceImpl implements FactuurService{

	@Autowired
	private FactuurDAOInterface factuurDAO;
	
	@Override
	public Factuur saveFactuur(long bestelling_id) {
		return factuurDAO.saveBybestelling_id(bestelling_id);
	}

	@Override
	public Factuur findFactuurByBestelling_id(long bestelling_id) {
		return factuurDAO.findBybestelling_id(bestelling_id);
	}

}
