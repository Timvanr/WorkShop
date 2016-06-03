package com.workshop.service;

import java.util.List;

import com.workshop.model.Account;

public interface AccountService {

	 Account findAccount(long account_id);
	 void addAccount(Account account);
	 List<Account> listAccounts();
	 void deleteAccount(long account_id);
}