package workshop.controller;

import java.io.IOException;
import java.sql.SQLException;

import workshop.Service;
import workshop.dao.KlantDAOInterface;

public class KlantController {

	private Service service;
	private KlantDAOInterface klantDAO;
	
	public KlantController(Service service, KlantDAOInterface klantDAO) {

		this.service = service;
		this.klantDAO = klantDAO;
	}
/*
	public void printKlantmetId() throws SQLException, IOException{
		service.printKlant(klantDAO.readKlantWithId(service.id_Prompt()));
	}

	public void printKlantmetNaam() throws SQLException, IOException{
		service.printKlant(klantDAO.readKlantWithFirstLastName(service.voornaamPrompt(), service.tussenvoegselPrompt(), service.achternaamPrompt()));
	}
	
	public void printKlantmetVoornaam() throws SQLException, IOException{
		service.printKlanten(klantDAO.readKlantWithFirstName(service.voornaamPrompt()));
	}
	public void printAlleKlanten() throws SQLException{
		service.printKlanten(klantDAO.readAll());
	}
	
	public void createKlant() throws SQLException, IOException{
		Klant klant = new Klant(service.voornaamPrompt(), service.tussenvoegselPrompt(), service.achternaamPrompt(), service.emailPrompt());
		klantDAO.createKlant(klant);
	}
	
	public void createKlantenAdres() throws SQLException, IOException{ //dit zou evt ook in de servicelaag kunnen komen
		Klant klant = service.newKlant();
		Adres adres = service.newAdres();
		klantDAO.createKlantEnAdres(klant, adres);
	}
	
	public void createKlantenAdresEnBestelling() throws SQLException, IOException{
		Klant klant = service.newKlant();
		Adres adres = service.newAdres();
		Bestelling bestelling = service.newBestelling();
		klantDAO.createKlantEnAdresEnBestelling(klant, adres, bestelling);
	}
	
	public void createKlantenBestelling() throws SQLException, IOException{
		Klant klant = service.newKlant();
		Bestelling bestelling = service.newBestelling();
		klantDAO.createKlantEnBestelling(klant, bestelling);
	}
	
	public void updateKlant() throws SQLException, IOException{
		klantDAO.UpdateKlantNaam(service.id_Prompt());
	}
	
	public void updateKlantEmail() throws SQLException, IOException{
		klantDAO.updateEmail(service.id_Prompt());
	}
	
	public void deleteKlantmetKlantId() throws SQLException, IOException{
		klantDAO.deleteAllFromKlantId(service.id_Prompt());
		System.out.println("Klant succesvol verwijderd.");
	}
	
	public void deleteKlantmetKlantNaam() throws SQLException, IOException{
		Klant klant = klantDAO.readKlantWithFirstLastName(service.voornaamPrompt(), service.tussenvoegselPrompt(), service.achternaamPrompt());
		klantDAO.deleteAllFromKlantId(klant.getId());
		System.out.println("Klant succesvol verwijderd.");
	}
	*/
}
