package com.workshop.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.dao.*;
import com.workshop.model.*;

@Service("klantService")
@Transactional
public class KlantServiceImpl implements KlantService{

	@Autowired
	private KlantDAO klantDAO;
	@Autowired
	private AdresDAO adresDAO;
	@Autowired
	private AccountDAO accountDAO;
	
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
	
	public void createAccount(Klant klant, Adres adres, Account account) {
		adresDAO.save(adres);
		Map<Adres, AdresType> adressen = new HashMap<>();
		AdresType adresType = new AdresType();
		adresType.setAdres_type_id(1);
		adressen.put(adres, adresType);
		klant.setAdressen(adressen);
		klantDAO.save(klant);
		account.setKlant(klant);
		Date date = new Date();
		account.setCreateDatum(date);
		accountDAO.save(account);
	}
}
