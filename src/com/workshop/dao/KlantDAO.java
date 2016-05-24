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
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public long createKlant(Klant klant) {
		this.em.persist(klant);
		return klant.getId();
	}
	
	@Override
	public Klant getKlantWithKlantId(long klant_id) {
		return this.em.find(Klant.class, klant_id);
	}

	@Override
	public List<Klant> getKlantlijst() {
		return this.em.createNativeQuery("SELECT * FROM ", Klant.class).getResultList();
	}

	@Override
	public void updateKlant(long id, Klant klant) {
		Klant oud = getKlantWithKlantId(id);
		this.em.getTransaction().begin();
		oud.setVoornaam(klant.getVoornaam());
		oud.setTussenvoegsel(klant.getTussenvoegsel());
		oud.setAchternaam(klant.getAchternaam());
		oud.setEmail(klant.getEmail());
		this.em.getTransaction().commit();
	}

	@Override
	public void deleteKlantWithKlantId(long klant_id) {
		this.em.remove(getKlantWithKlantId(klant_id));
	}
