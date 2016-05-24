package com.workshop.model;

import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.persistence.*;

import org.springframework.stereotype.Component;

@Entity
public class Artikel {
	@Id @GeneratedValue
	@Column(name="artikel_id")
	private long id;
	
	private String artikel_naam;
	@Lob
	private String omschrijving;
	
	private BigDecimal artikel_prijs;
	
	
	public Artikel(){
		this(0, null, null, null);
	}
	//zonder omschrijving
	public Artikel(String naam, BigDecimal prijs){
		this(0, naam, null, prijs);
	}
	
	public Artikel(int id, String naam, BigDecimal prijs){
		this(id, naam, null, prijs);
	}
	
	public Artikel(String naam, String disc, BigDecimal prijs){
		this(0, naam, disc, prijs);
	}
	
	public Artikel(int id, String naam, String disc, BigDecimal prijs){
		this.id = id;
		this.artikel_naam = naam;
		this.omschrijving = disc;
		this.artikel_prijs = prijs;
	}
	
	public long getId() {
		return this.id;
	}
	public void setId(long artikel_id) {
		this.id = artikel_id;
	}
	public String getNaam() {
		return this.artikel_naam;
	}
	public void setNaam(String artikel_naam) {
		this.artikel_naam = artikel_naam;
	}
	public String getOmschrijving(){
		return this.omschrijving;
	}
	public void setOmschrijving(String disc){
		this.omschrijving = disc;
	}
	public BigDecimal getPrijs() {
		return this.artikel_prijs;
	}
	public void setPrijs(BigDecimal artikelPrijs) {
		this.artikel_prijs = artikelPrijs;
	}
	
	@Override
	public boolean equals(Object a){
		if (a instanceof Artikel){
			Artikel artikel = (Artikel)a;
			return this.getId() == artikel.getId();
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return (int)this.id;
	}	
	
	@Override
	public String toString(){
		return "Artikelnummer: " + this.id + " Naam: " + this.artikel_naam + " Prijs: " + NumberFormat.getCurrencyInstance().format(this.artikel_prijs);
	}
	
	
	
	
}
