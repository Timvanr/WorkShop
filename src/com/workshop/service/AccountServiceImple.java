package com.workshop.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.dao.AccountDAO;
import com.workshop.model.Account;

@Service
@Transactional
public class AccountServiceImple implements AccountService{
	
	@Autowired
	private AccountDAO accountDAO;

	@Override
	public Account findAccount(long account_id) {
		return accountDAO.findOne(account_id);
	}

	@Override
	public void addAccount(Account account) {
		Date date = new Date();
		account.setCreateDatum(date);
		accountDAO.save(account);	
	}

	@Override
	public List<Account> listAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAccount(long account_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Account findByNaamAndWachtwoord(String account_naam, String wachtwoord) {
		return accountDAO.findByNaamAndWachtwoord(account_naam, wachtwoord);
	}

	@Override
	public Account findByNaam(String account_naam) {
		return accountDAO.findByNaam(account_naam);
	}

}
