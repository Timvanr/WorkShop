package com.workshop.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.Date;

import javax.persistence.*;

import org.springframework.stereotype.Component;

import com.workshop.dao.KlantDAOInterface;

@Entity
public class Bestelling {
	
	@Id	@GeneratedValue
	@Column(name="bestelling_id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="klant_id")
	private Klant klant;
	
	private Date datum;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="bestelling_has_artikel", joinColumns=@JoinColumn(name="bestelling_id"))
	@MapKeyJoinColumn(name="artikel_id", referencedColumnName="artikel_id")
	@Column(name="artikel_aantal")
	private Map<Artikel, Integer> artikelen;
	
	@OneToOne(mappedBy="bestelling")
	private Factuur factuur;

	public Bestelling(Klant klant) {
		this.klant = klant;
		this.datum = new Date();
		this.artikelen = new HashMap();
		this.factuur = new Factuur(this);
	}
	
	@Deprecated
	public Bestelling() {
		this(null);
	}
	
	public long getKlant_id() { 
		return this.klant.getId();
	}
	public Klant getKlant(){
		return this.klant;
	}
	public void setKlant(Klant klant){
		this.klant = klant;
	}
	public long getId() {
		return this.id;
	}
	public void setId(int bestelling_id) {
		this.id = bestelling_id;
	}
	public Date getDatum() {
		return this.datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public Map<Artikel, Integer> getArtikelen(){
		return this.artikelen;
	}
	public void setArtikelen(Map<Artikel, Integer> artikelen){
		this.artikelen = artikelen;
	}
	public Factuur getFactuur() {
		return this.factuur;
	}
	public void setFactuur(Factuur factuur) {
		this.factuur = factuur;
	}
	
	public void voegArtikelToe(Artikel artikel, Integer aantal){
		if (this.artikelen.containsKey(artikel)){
			Integer totaalAantal = new Integer(this.artikelen.get(artikel).intValue() + aantal.intValue());
			this.artikelen.put(artikel, totaalAantal);
		} else {
			this.artikelen.put(artikel, aantal);
		}
	}
	
	public void removeArtikel(Artikel artikel){
		if (this.artikelen.containsKey(artikel)){
			this.artikelen.remove(artikel);
		} else {
			System.out.println("Artikel staat niet in de lijst; verwijderen mislukt.");
		}
	}
	
	public BigDecimal getTotaalPrijs(){
		BigDecimal total = BigDecimal.ZERO;
		
		for (Map.Entry<Artikel, Integer> entrySet: this.artikelen.entrySet()){
			BigDecimal artikelTimesAantal = entrySet.getKey().getPrijs().multiply(new BigDecimal(entrySet.getValue()));
			total = total.add(artikelTimesAantal);
		}
		
		return total;
	}
		
	@Override
	public String toString(){
		String artikelLijst = "";
		for (Map.Entry<Artikel, Integer> entry: this.artikelen.entrySet()){
			artikelLijst += entry.getKey().toString() + " aantal: " + entry.getValue() + "\n";
		}
		return "Bestellingnummer: " + this.id + ", Klantnummer: " + getKlant_id() + "\n" + 
				artikelLijst + "Totaalprijs: " + NumberFormat.getCurrencyInstance().format(getTotaalPrijs());
	}
	/*
	public static void main(String[] args){
		Bestelling bestelling = new Bestelling(555);
		bestelling.voegArtikelToe(new Artikel(34, "warm broodje", new BigDecimal(3.95)), new Integer(2));
		bestelling.voegArtikelToe(new Artikel(44, "latte machiato", new BigDecimal(2.95)), new Integer(2));
		bestelling.removeArtikel(new Artikel(34, "warm broodje", new BigDecimal(3.95)), new Integer(2));
		System.out.println(bestelling);
	}
	*/
}
