package com.workshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.workshop.dao.BetalingDAO;
import com.workshop.model.Betaling;
import com.workshop.model.Betaling.Betaalwijze;

public class BetalingServiceImpl implements BetalingService{

	@Autowired
	private BetalingDAO betalingDao;
	
	@Override
	public Betaling findBetaling(Long betaling_id) {
		return betalingDao.findOne(betaling_id); 
	}

	@Override
	public List<Betaling> findBetalingPerFactuur(Long factuur_id) {
		return betalingDao.findByFactuur(factuur_id);
	}

	@Override
	public void doeBetaling(Betaling betaling) {
		betalingDao.save(betaling);
	}

	@Override
	public List<Betaling> findBetalingPerType(Betaalwijze betaalwijze) {
		return betalingDao.findByBetaalwijze(betaalwijze);
	}

}
