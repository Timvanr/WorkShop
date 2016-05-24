package com.workshop.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.workshop.model.Adres;

@Repository
@Transactional
public class AdresDAO implements AdresDAOInterface {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public long createAdres(Adres adres) {
		this.em.persist(adres);
		return adres.getId();
		
	}
	
	@Override
	public Adres getAdresmetId(long adres_id) {
		return this.em.find(Adres.class, adres_id);
	}
	
	@Override
	public List<Adres> getAdreslijst() {
		return this.em.createNativeQuery("SELECT * FROM ", Adres.class).getResultList();
	}
	
	@Override
	public void updateAdres(long id, Adres adres) {
		Adres oud = getAdresmetId(id);
		this.em.getTransaction().begin();
		oud.setStraatnaam(adres.getStraatnaam());
		oud.setPostcode(adres.getPostcode());
		oud.setToevoeging(adres.getToevoeging());
		oud.setWoonplaats(adres.getWoonplaats());
		this.em.getTransaction().commit();
	}
	
	@Override
	public void deleteAdres(long adres_id) {
		this.em.remove(getAdresmetId(adres_id));
	}

}
