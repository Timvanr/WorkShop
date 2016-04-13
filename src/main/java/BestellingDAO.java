

import java.sql.*;
import java.util.ArrayList;

public interface BestellingDAO {
	
	//void maakTabel() throws SQLException ;
	void voegBestellingToe(Bestelling bestelling);
	Bestelling getBestelling(int id) throws SQLException;
	int haalKlant_id(int bestelling_id);
	void updateBestelling(int bestelling_id, Bestelling bestelling);
	void verwijderBestelling(int id);
	//void verwijderTabel() throws SQLException ;
	ArrayList<Bestelling> haalBestellijst();
	
}
