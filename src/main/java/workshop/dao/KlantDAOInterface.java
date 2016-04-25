package workshop.dao;

import java.util.Set;

import workshop.model.Klant;

public interface KlantDAOInterface {

	int createKlant(Klant klant);
	/*
	void createKlantEnAdres(Klant klant, Adres adres);
	void createKlantEnAdresEnBestelling(Klant klant, Adres adres, Bestelling bestelling);
	void createKlantEnBestelling(Klant klant, Bestelling bestelling);
	*/
	Klant readKlantWithId(int klant_id);
	Set<Klant> readKlantWithFirstName(String voornaam);
	Set<Klant> readKlantByAchternaam(String achter);
	Klant readKlantWithFirstLastName(String voornaam, String tussenvoegsel, String achternaam);
	void UpdateKlantNaam(int klant_id, Klant klant);
	void updateEmail(int klant_id, String email);
	Set<Klant> readAll();
	void deleteAllFromKlantId(int klant_id);
	void deleteAllFromKlantNaam(String voornaam, String achternaam, String tussenvoegsel);
	
}
