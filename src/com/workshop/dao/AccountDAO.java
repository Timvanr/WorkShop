package com.workshop.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.workshop.model.Account;

@Repository
@Transactional
public class AccountDAO implements AccountDAOInterface {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void createAccount(Account account) {
		this.em.persist(account);	
	}
	
	@Override
	public Account getAccountmetId(long account_id){
		return this.em.find(Account.class, account_id);
	}
	
	@Override
	public List<Account> getAccountlijst() {
		return this.em.createNativeQuery("SELECT * FROM ", Account.class).getResultList();
	}
	
	@Override
	public void updateAccount(long account_id, Account account) {
		Account oud = getAccountmetId(account_id);
		this.em.getTransaction().begin();
		oud.setNaam(account.getNaam());
		this.em.getTransaction().commit();
	}
	
	@Override
	public void deleteAccount(long account_id) {
		this.em.remove(getAccountmetId(account_id));
	}
}
