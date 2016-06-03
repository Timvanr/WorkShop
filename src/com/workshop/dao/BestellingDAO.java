package com.workshop.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.model.Bestelling;

@Transactional
public interface BestellingDAO extends JpaRepository<Bestelling, Long>{

}
