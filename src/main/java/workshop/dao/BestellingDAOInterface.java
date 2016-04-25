package workshop.dao;

import java.sql.*;
import java.util.Set;

import workshop.model.Bestelling;

public interface BestellingDAOInterface {
	
	//void maakTabel() throws SQLException ;
	Bestelling getBestelling(int id) throws SQLException;
	int haalKlant_id(int bestelling_id);
	void verwijderBestelling(int id);
	//void verwijderTabel() throws SQLException ;
	Set<Bestelling> haalBestellijst();
	void voegBestellingToe(Bestelling bestelling);
	
}
