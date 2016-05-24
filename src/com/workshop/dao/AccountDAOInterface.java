package com.workshop.dao;

import java.util.List;

import com.workshop.model.Account;

public interface AccountDAOInterface {
	
	void createAccount(Account account);
	Account readAccountmetId(long Account_id);
	List<Account> readAll();
	void UpdateAccount(long account_id, Account account);
	void deleteAccount(long account_id);
}
