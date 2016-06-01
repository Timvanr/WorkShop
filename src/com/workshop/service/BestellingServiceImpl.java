package com.workshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.dao.AdresDAOInterface;
import com.workshop.dao.BestellingDAOInterface;
import com.workshop.model.Adres;
import com.workshop.model.Bestelling;

@Service
@Transactional
public class BestellingServiceImpl implements BestellingService{

	
	@Autowired
	private BestellingDAOInterface bestelDAO;
	
	@Override
	public Bestelling findBestelling(long bestelling_id) {
		return bestelDAO.findOne(bestelling_id);
	}

	@Override
	public void addAdres(Bestelling bestelling) {
		bestelDAO.save(bestelling);
	}

	@Override
	public List<Bestelling> listBestellingen() {
		return (List<Bestelling>) bestelDAO.findAll();
	}

	@Override
	public void deleteBestelling(long bestelling_id) {
		bestelDAO.delete(bestelling_id);
	}

}
