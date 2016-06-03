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

import com.workshop.model.*;

@Repository
public class AccountDAO implements AccountDAOInterface {
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	Scanner scInput = new Scanner(System.in);
	protected static Logger logger = LoggerFactory.getLogger(AccountDAO.class);
	
	private SessionFactory sessionFactory;
	 
	public SessionFactory getSessionFactory() {
	return sessionFactory;
	}
	 
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
	}
	
	public AccountDAO(){			
	}
	
	@Override
	public void createAccount(Account account){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			session.persist(account);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}
	
	@Override
	public Account readAccountmetId(long Account_id) {
		Session session = sessionFactory.openSession();
		Account account = null;
		try {	
			account = (Account) session.get(Account.class, Account_id);
		}catch (HibernateException e){
			e.printStackTrace();
		}finally {
			session.close();
		}
		return account;
	}
	
	@Override
	public List<Account> readAll(){
		Session session = sessionFactory.openSession();
		List<Account> accountList = null;
		try{
			accountList = (List<Account>)session.createQuery("from Account").list();
		}catch(HibernateException e){
			e.printStackTrace();
		}finally {
			session.close(); 
		}
		return accountList;
	}
	
	@Override
	public void UpdateAccount(long account_id, Account account1){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Account account = (Account)session.load(Account.class, account_id);
			account.setNaam(account1.getNaam());
			session.update(account); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}
	
	@Override
	public void deleteAccount(long account_id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{ 
			tx = session.beginTransaction();
			Account account = (Account)session.get(Account.class, account_id);
			session.delete(account);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}
}
