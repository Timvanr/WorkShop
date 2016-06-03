package com.workshop.service;

import java.util.List;

import com.workshop.model.Klant;

public interface KlantService {

	public List<Klant> listKlanten();
	public void deleteKlant(long id);
	public void addKlant(Klant k);
	public Klant findKlant(long id);
	public List<Klant> findKlantByVoornaam(String voornaam);
}
