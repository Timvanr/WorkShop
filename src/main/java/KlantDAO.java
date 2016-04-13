

import java.sql.SQLException;
import java.util.ArrayList;

public interface KlantDAO {

	public int createKlant(Klant klant) throws SQLException;
	public void createKlantEnAdres(Klant klant, Adres adres) throws SQLException;
	public void createKlantEnAdresEnBestelling(Klant klant, Adres adres, Bestelling bestelling) throws SQLException;
	public void createKlantEnBestelling(Klant klant, Bestelling bestelling) throws SQLException;
	public Klant readKlantWithId(int klant_id) throws SQLException;
	public Klant readKlantWithFirstName(String voornaam) throws SQLException;
	public Klant readKlantWithFirstLastName(String voornaam, String tussenvoegsel, String achternaam)throws SQLException;
	public void UpdateKlantNaam(int klant_id) throws SQLException;
	public void updateEmail(int klant_id) throws SQLException;
	public ArrayList<Klant> readAll() throws SQLException;
	public void deleteAllFromKlantId(int klant_id) throws SQLException;
	public void deleteAllFromKlantNaam(String voornaam, String achternaam, String tussenvoegsel) throws SQLException;
	
}
