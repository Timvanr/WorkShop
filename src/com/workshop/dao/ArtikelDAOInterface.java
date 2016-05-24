package workshop.dao;

import java.util.List;
import java.util.Set;

import workshop.model.Artikel;


public interface ArtikelDAOInterface {

	long createArtikel(Artikel artikel);
	Artikel getArtikelWithArtikelId(long artikel_id);
	Artikel getArtikelWithNaam(String naam);
	List<Artikel> getArtikellijst();
	void updateArtikel(long id, Artikel artikel);
	void deleteArtikelWithArtikelId(long artikel_id);

}
