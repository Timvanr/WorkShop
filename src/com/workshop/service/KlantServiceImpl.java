package com.workshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.dao.KlantDAOInterface;
import com.workshop.model.Klant;

@Service("klantService")
@Transactional
public class KlantServiceImpl implements KlantService{

	@Autowired
	private KlantDAOInterface klantDAO;
	
	@Override
	public List<Klant> listKlanten() {
		return klantDAO.findAll();
	}

}
