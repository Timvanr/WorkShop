package workshop.dao;

import java.util.Set;

import workshop.model.Bestelling;

public interface BestellingDAOInterface {
	
	//void maakTabel() throws SQLException ;
	Bestelling getBestelling(int id);
	int haalKlant_id(int bestelling_id);
	void verwijderBestelling(int id);
	void updateBestelling(int bestelling_id, Bestelling bestelling);
	//void verwijderTabel() throws SQLException ;
	Set<Bestelling> haalBestellijst();
	Set<Bestelling> getBestellijstByKlant(int klant_id);
	void voegBestellingToe(Bestelling bestelling);
	
}
