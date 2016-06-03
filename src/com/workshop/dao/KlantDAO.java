package com.workshop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.model.Klant;
@Transactional
public interface KlantDAO extends JpaRepository<Klant, Long> {
	
	//Extra methodes. Methodes uit de genericDAO kun je gewoon aanspreken.
	List<Klant> readByVoornaam(String voornaam);
	
	
	
	
}
