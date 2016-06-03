package com.workshop.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.workshop.dao.ArtikelDAO;
import com.workshop.model.Artikel;

public class ArtikelServiceImpl implements ArtikelService{

	@Autowired
	private ArtikelDAO artikelDao;
	
	@Override
	public Artikel findByArtikelId(Long artikel_id) {
		return artikelDao.findOne(artikel_id);
	}

	@Override
	public Artikel findByNaam(String artikel_naam) {
		return artikelDao.findByNaam(artikel_naam);
	}

	@Override
	public List<Artikel> findAlleArtikelen() {
		return artikelDao.findAll();
	}

	@Override
	public List<Artikel> findAlleArtikelenDuurderDan(BigDecimal artikel_prijs) {
		return artikelDao.findByPrijsGreaterThan(artikel_prijs);
	}

	@Override
	public List<Artikel> findAlleArtikelenGoedkoperDan(BigDecimal artikel_prijs) {
		return artikelDao.findByPrijsLessThan(artikel_prijs);
	}

}
