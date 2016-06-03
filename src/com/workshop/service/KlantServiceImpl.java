package com.workshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.dao.KlantDAO;
import com.workshop.model.Klant;

@Service
@Transactional
public class KlantServiceImpl implements KlantService{

	@Autowired
	private KlantDAO klantDAO;
	
	@Override
	public List<Klant> listKlanten() {
		return klantDAO.findAll();
	}

	@Override
	public void deleteKlant(long id) {
		klantDAO.delete(id);
	}

	@Override
	public void addKlant(Klant k) {
		klantDAO.save(k);
	}
	
	@Override
	public Klant findKlant(long id){
		return klantDAO.findOne(id);
	}

	@Override
	public List<Klant> findKlantByVoornaam(String voornaam) {
		return klantDAO.readByVoornaam(voornaam);
	}

}
