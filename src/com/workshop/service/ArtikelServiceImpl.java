package com.workshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.dao.ArtikelDAOInterface;
import com.workshop.model.Adres;
import com.workshop.model.Artikel;

@Service
@Transactional
public class ArtikelServiceImpl implements ArtikelService{

	@Autowired
	private ArtikelDAOInterface artikelDAO;
	
	@Override
	public Artikel findArtikel(long artikel_id) {
		return artikelDAO.findOne(artikel_id);
	}

	@Override
	public void addArtikel(Artikel artikel) {
		artikelDAO.save(artikel);
	}

	@Override
	public List<Artikel> listArtikelen() {
		return (List<Artikel>) artikelDAO.findAll();
	}

	@Override
	public void deleteArtikel(long artikel_id) {
		artikelDAO.delete(artikel_id);
	}

}
