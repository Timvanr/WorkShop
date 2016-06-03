package com.workshop.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workshop.model.Artikel;


public interface ArtikelDAO extends JpaRepository<Artikel, Long>{

	Artikel findByNaam(String naam);
	List<Artikel> findByPrijsLessThan(BigDecimal artikel_prijs);
	List<Artikel> findByPrijsGreaterThan(BigDecimal artikel_prijs);
	
	/*static Connection getConnection();
	
	void updateArtikel(int id, Artikel artikel);
	void deleteArtikelWithArtikelId(int artikel_id);
	
	//void voegArtikelToeAanBestelling(int bestelling_id, int artikel_id, int artikel_aantal) throws SQLException;
	//void verwijderArtikelUitBestelling(int bestelling_id, int artikel_id) throws SQLException;
	*/

}
