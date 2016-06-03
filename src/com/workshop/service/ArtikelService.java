package com.workshop.service;

import java.util.List;

import com.workshop.model.Artikel;

public interface ArtikelService {

	Artikel findArtikel(long artikel_id);
	 void addArtikel(Artikel artikel);
	 List<Artikel> listArtikelen();
	 void deleteArtikel(long artikel_id);
}
