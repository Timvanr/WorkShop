package workshop.controller;


import java.io.IOException;
import java.sql.SQLException;

import workshop.Service;
import workshop.dao.ArtikelDAOInterface;

public class ArtikelController {
	
		private Service service; // view
		private ArtikelDAOInterface artikelLijst; // model
		
		public ArtikelController(Service service, ArtikelDAOInterface artikellijst) {

			this.service = service;
			this.artikelLijst = artikellijst;
		}
/*
		public void getArtikel() throws SQLException, IOException{
			artikelLijst.getArtikelWithArtikelId(service.artikelIdPrompt());
		}
/*
		public void verwijderArtikelUitBestelling() throws SQLException{
			artikelLijst.verwijderArtikelUitBestelling(service.bestellingIdPrompt(), artikel_id);(bestellijst.haalBestellijst());
		}
		
		public void updateBestelling() throws SQLException, IOException {
			bestellijst.updateBestelling(bestellijst.getBestelling(service.bestellingIdPrompt()));
		}
		
		public void deleteBestelling() throws SQLException, IOException {
			bestellijst.verwijderBestelling(service.bestellingIdPrompt());
		}
*/
}
