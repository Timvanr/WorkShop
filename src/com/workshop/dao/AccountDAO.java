package com.workshop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.model.Account;
import com.workshop.model.Artikel;

@Transactional
public interface AccountDAO extends JpaRepository<Account, Long>{
	
	Account findByNaamAndWachtwoord(String account_naam, String wachtwoord);
	Account findByNaam(String account_naam);
	
}