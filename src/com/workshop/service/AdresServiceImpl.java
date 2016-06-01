package com.workshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.dao.AdresDAOInterface;
import com.workshop.model.Adres;

@Service
@Transactional
public class AdresServiceImpl implements AdresService{

	@Autowired
	private AdresDAOInterface adresDAO;
	
	
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
		return (List<Adres>) adresDAO.findAll();
	}


	@Override
	public void deleteAdres(long id) {
		adresDAO.delete(id);		
	}
}
