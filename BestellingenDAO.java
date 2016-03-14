import java.sql.*;
import java.util.ArrayList;

public interface BestellingenDAO {
	
	void maakTabel() throws SQLException ;
	void voegBestellingToe(Bestelling nieuw) throws SQLException ;
	int haalKlant_id(int bestelling_id) throws SQLException ;
	void updateBestelling(Bestelling bestelling) throws SQLException;
	void verwijderBestelling(int id) throws SQLException ;
	void verwijderTabel() throws SQLException ;
	void close() throws SQLException ;
	ArrayList<Bestelling> haalBestellijst() throws SQLException;
	
}
