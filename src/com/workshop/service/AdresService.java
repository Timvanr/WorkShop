package com.workshop.service;

import java.util.List;

import com.workshop.model.Adres;

public interface AdresService {

	 Adres findAdres(long adres_id);
	 void addAdres(Adres adres);
	 List<Adres> listAdressen();
	 void deleteAdres(long id);
	
}
