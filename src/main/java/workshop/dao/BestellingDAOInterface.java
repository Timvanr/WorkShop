

import java.sql.*;
import java.util.ArrayList;

public interface BestellingDAO {
	
	//void maakTabel() throws SQLException ;
	Bestelling getBestelling(int id) throws SQLException;
	int haalKlant_id(int bestelling_id);
	void verwijderBestelling(int id);
	//void verwijderTabel() throws SQLException ;
	ArrayList<Bestelling> haalBestellijst();
	void voegBestellingToe(int klant_id, Bestelling bestelling);
	
}
