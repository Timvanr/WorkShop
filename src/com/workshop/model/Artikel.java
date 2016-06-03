package com.workshop.model;

import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.persistence.*;

@Entity
public class Artikel {
	
	@Id @GeneratedValue (strategy=GenerationType.AUTO)
	@Column(name="artikel_id")
	private long id;
	@Column
	private String naam;
	@Lob
	private String omschrijving;
	@Column
	private BigDecimal prijs;
	
	
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
		this.naam = naam;
		this.omschrijving = disc;
		this.prijs = prijs;
	}
	
	public long getId() {
		return this.id;
	}
	public void setId(long artikel_id) {
		this.id = artikel_id;
	}
	public String getNaam() {
		return this.naam;
	}
	public void setNaam(String artikel_naam) {
		this.naam = artikel_naam;
	}
	public String getOmschrijving(){
		return this.omschrijving;
	}
	public void setOmschrijving(String disc){
		this.omschrijving = disc;
	}
	public BigDecimal getPrijs() {
		return this.prijs;
	}
	public void setPrijs(BigDecimal artikelPrijs) {
		this.prijs = artikelPrijs;
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
		return "Artikelnummer: " + this.id + " Naam: " + this.naam + " Prijs: " + NumberFormat.getCurrencyInstance().format(this.prijs);
	}
	
	
	
	
}
