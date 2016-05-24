package com.workshop.dao;

import java.util.List;

import com.workshop.model.Account;

public interface AccountDAOInterface {
	
	void createAccount(Account account);
	Account getAccountmetId(long account_id);
	List<Account> readAll();
	void updateAccount(long account_id, Account account);
	void deleteAccount(long account_id);
}
