package com.workshop.dao;

import java.util.Set;

import com.workshop.model.Artikel;


public interface ArtikelDAOInterface {

	//static Connection getConnection();
	int createArtikel(Artikel artikel);
	Artikel getArtikelWithArtikelId(int artikel_id);
	Artikel getArtikelWithNaam(String naam);
	Set<Artikel> getArtikellijst();
	void updateArtikel(int id, Artikel artikel);
	void deleteArtikelWithArtikelId(int artikel_id);
	
	//void voegArtikelToeAanBestelling(int bestelling_id, int artikel_id, int artikel_aantal) throws SQLException;
	//void verwijderArtikelUitBestelling(int bestelling_id, int artikel_id) throws SQLException;
	

}
