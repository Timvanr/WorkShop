package com.workshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.model.Factuur;

@Transactional
public interface FactuurDAO extends JpaRepository<Factuur, Long>{

	Factuur findByBestelling_id(Long bestelling_id);
	// void updateFactuurBedrag(Long factuur_id) dit moet wel een custom methode worden denk ik
}