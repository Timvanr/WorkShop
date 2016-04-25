package workshop.controller;

import java.io.IOException;
import java.sql.SQLException;

import workshop.Service;
import workshop.dao.*;

public class BestellingController {
	
		private Service service; // view
		private BestellingDAOInterface bestellijst; // model
		private ArtikelDAOInterface artikellijst;
		
		
		public BestellingController(Service service, BestellingDAOInterface bestellijst, ArtikelDAOInterface artikellijst) {
			
			this.service = service;
			this.bestellijst = bestellijst;
			this.artikellijst = artikellijst;
		}
/*
		public void createBestellingVoorBestaandeKlant() throws IOException, SQLException{
			bestellijst.voegBestellingToe(service.id_Prompt(), service.newBestelling());	
		}
		
		public void printBestellingmetId() throws SQLException, IOException{
			service.printBestelling(bestellijst.getBestelling(service.bestellingIdPrompt()));
		}

		public void printAlleBestellingen() throws SQLException{
			service.printBestellingen(bestellijst.haalBestellijst());
		}
		/*
		public void updateBestelling() throws SQLException, IOException { 
			int bestelling_id = service.bestellingIdPrompt();
			service.printBestelling(bestellijst.getBestelling(bestelling_id));
			switch (service.updateBestellingPrompt()){
			case 1: artikellijst.verwijderArtikelUitBestelling(bestelling_id, service.artikelIdPrompt()); break;
			case 2: artikellijst.voegArtikelToeAanBestelling(bestelling_id, service.artikelIdPrompt(), service.artikelAantalPrompt()); break;
			case 3: artikellijst.verwijderArtikelUitBestelling(bestelling_id, service.artikelIdPrompt()); 
			artikellijst.voegArtikelToeAanBestelling(bestelling_id, service.artikelIdPrompt(), service.artikelAantalPrompt()); break;
			}
		}
		*/
		public void deleteArtikelVanBestelling() throws SQLException, IOException{
			//artikellijst.verwijderArtikelUitBestelling(service.bestellingIdPrompt(), service.artikelIdPrompt());
		}
	
		public void deleteBestelling() throws SQLException, IOException {
			//bestellijst.verwijderBestelling(service.bestellingIdPrompt());
		}

}
