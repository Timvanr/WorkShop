package com.workshop.service;



import java.math.BigDecimal;
import java.util.List;

import com.workshop.model.Artikel;

public interface ArtikelService {
	
	Artikel findByArtikelId(Long artikel_id);
	Artikel findByNaam(String artikel_naam);
	List<Artikel> findAlleArtikelen();
	List<Artikel> findAlleArtikelenDuurderDan(BigDecimal artikel_prijs);
	List<Artikel> findAlleArtikelenGoedkoperDan(BigDecimal artikel_prijs);
}
