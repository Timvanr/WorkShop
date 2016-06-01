package com.workshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.dao.AccountDAOInterface;
import com.workshop.model.Account;

@Service
@Transactional
public class AccountServiceImple implements AccountService{
	
	@Autowired
	private AccountDAOInterface accountDAO;

	@Override
	public Account findAccount(long account_id) {
		return accountDAO.findOne(account_id);
	}

	@Override
	public void addAccount(Account account) {
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

}
