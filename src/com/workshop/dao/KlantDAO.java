package com.workshop.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.model.Klant;

@Repository
public class KlantDAO{
	
	protected static Logger logger = LoggerFactory.getLogger(KlantDAO.class);
	private KlantDAOInterface klantDaoInterface;
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	 
	public SessionFactory getSessionFactory() {
	return sessionFactory;
	}

	@Transactional
	public void createKlant(Klant klant){
		klantDaoInterface.save(klant);
	}
	
	@Transactional
	public void delete(Klant klant){
		klantDaoInterface.delete(klant);
	}
	
	@Transactional
	public void deleteByAchternaam(String voornaam, String achternaam){
		klantDaoInterface.deleteByVoornaamAndAchternaam(voornaam, achternaam);
	}
	
	@Transactional
	public Klant findOne(Long klant_id){
		return klantDaoInterface.findOne(klant_id);
	}
	
	@Transactional
	public List<Klant> findAll(){
		return klantDaoInterface.findAll();		
	}
}
