package com.workshop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.model.Adres;

@Transactional
public interface AdresDAO extends JpaRepository<Adres, Long>{
	

	
}