package com.workshop.dao;

import com.workshop.model.Klant;

public interface KlantDAOInterface {

	Klant getKlantWithKlantId(long klant_id);
	List<Klant> getKlantlijst();
	void updateKlant(long id, Klant klant);
	void deleteKlantWithKlantId(long klant_id);
	
	
	
}
