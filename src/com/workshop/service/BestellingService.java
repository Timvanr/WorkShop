package com.workshop.service;

import java.util.List;

import com.workshop.model.Bestelling;

public interface BestellingService {

	Bestelling findBestelling(long bestelling_id);
	 void addAdres(Bestelling bestelling);
	 List<Bestelling> listBestellingen();
	 void deleteBestelling(long bestelling_id);
}
