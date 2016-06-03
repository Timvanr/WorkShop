package com.workshop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.model.Account;

@Transactional
public interface AccountDAO extends JpaRepository<Account, Long>{
	
	
}