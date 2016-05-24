package workshop.dao;

import java.util.List;

import workshop.model.Bestelling;

public interface BestellingDAOInterface {
	
	Bestelling getBestelling(long id);
	long haalKlant_id(long id);
	void verwijderBestelling(long id);
	void updateBestelling(long id, Bestelling bestelling);
	List<Bestelling> haalBestellijst();
	List<Bestelling> getBestellijstByKlant(long klant_id);
	long createBestelling(Bestelling bestelling);
	
}
