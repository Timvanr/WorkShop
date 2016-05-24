package com.workshop.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.workshop.model.Adres;

@Repository
@Transactional
public class AdresDAO implements AdresDAOInterface {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void createAdres(Adres adres) {
		this.em.persist(adres);		
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
	public void updateAdres(Adres adres){ 
		Adres oud = getAdresmetId(adres.getAdres_id());
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
