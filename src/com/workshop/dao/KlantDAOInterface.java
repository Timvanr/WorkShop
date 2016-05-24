package com.workshop.dao;

import com.workshop.model.Klant;

public interface KlantDAOInterface {

	Klant getKlantWithKlantId(long klant_id);
	List<Klant> getKlantlijst();
	public void updateKlant(long id, Klant klant);
	public void deleteKlantWithKlantId(long klant_id);
	
	
	
}
