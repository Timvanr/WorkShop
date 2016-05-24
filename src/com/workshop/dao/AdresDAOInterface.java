package com.workshop.dao;

import java.util.List;

import com.workshop.model.Adres;

public interface AdresDAOInterface {
	
	void createAdres(Adres adres);
	List<Adres> readAll();
	Adres getAdresmetId(long adres_id);
	void updateAdres(Adres adres);
	void deleteAdres(long adres_id);
	
}
