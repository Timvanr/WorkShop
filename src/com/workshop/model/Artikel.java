package com.workshop.model;

import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.persistence.*;


@Entity
@Table
public class Artikel {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="artikel_id")
	private Long artikel_id;
	
	@Column
	private String naam;
	
	@Column
	private String omschrijving;
	
	@Column
	private BigDecimal prijs;
	
	public Long getArtikel_id() {
		return artikel_id;
	}

	public void setArtikel_id(Long artikel_id) {
		this.artikel_id = artikel_id;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public BigDecimal getPrijs() {
		return prijs;
	}

	public void setPrijs(BigDecimal prijs) {
		this.prijs = prijs;
	}

	@Override
	public String toString(){
		return "Artikelnummer: " + this.artikel_id + " Naam: " + this.naam + " Prijs: " + this.prijs;
	}
	
	
	
	
}
