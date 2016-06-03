package com.workshop.service;

import java.util.List;

import com.workshop.model.*;

public interface KlantService {

	List<Klant> listKlanten();
	void deleteKlant(long id);
	void addKlant(Klant k);
	Klant findKlant(long id);
	void createAccount(Klant k, Adres a, Account ac);
}
