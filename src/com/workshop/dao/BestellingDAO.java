package com.workshop.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workshop.model.Bestelling;

public interface BestellingDAO extends JpaRepository<Bestelling, Long>{
	/*
	//void maakTabel() throws SQLException ;
	int haalKlant_id(int bestelling_id);
	void verwijderBestelling(int id);
	void updateBestelling(int bestelling_id, Bestelling bestelling);
	//void verwijderTabel() throws SQLException ;
	Set<Bestelling> haalBestellijst();
	Set<Bestelling> getBestellijstByKlant(int klant_id);
	void voegBestellingToe(Bestelling bestelling);
	*/
}
