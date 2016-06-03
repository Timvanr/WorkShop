package com.workshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.dao.AdresDAO;
import com.workshop.model.Adres;

@Service
@Transactional
public class AdresServiceImpl implements AdresService{

	@Autowired
	private AdresDAO adresDAO;
	
	
	@Override
	public Adres findAdres(long id){		
		return adresDAO.findOne(id);		
	}

	@Override
	public void addAdres(Adres adres) {
		adresDAO.save(adres);		
	}

	@Override
	public List<Adres> listAdressen() {
		return adresDAO.findAll();
	}


	@Override
	public void deleteAdres(long id) {
		adresDAO.delete(id);		
	}
}
