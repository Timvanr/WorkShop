package com.workshop.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "Adres", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"postcode", "huisnummer", "toevoeging"}, name = "uniek_adres")
})
public class Adres implements java.io.Serializable{
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	private long adres_id;
	@Column
	private String straatnaam;
	@Column
	private int huisnummer;
	@Column
	private String toevoeging;
	@Column
	private String postcode;
	@Column
	private String woonplaats;
	/*
	//@Transient//werkt niet
	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(name="klant_has_adres", joinColumns=@JoinColumn(name="adres_id"),
		inverseJoinColumns=@JoinColumn(name="adrestype_id"))
	@MapKeyJoinColumn(table="klant_has_adres", name="klant_id", referencedColumnName="klant_id")
	private Map<Klant, AdresType> bewoners;
	
	public Adres() {
		//this.bewoners = new HashMap();
	}*/

	public Adres(String straat, int huisnummer, String toevoeging, String postcode,	String plaats) {
		this.straatnaam = straat;
		this.huisnummer = huisnummer;
		this.toevoeging = toevoeging;
		this.postcode = postcode;
		this.woonplaats = plaats;
		//this.bewoners = new HashMap();
	}

	public long getAdres_id() {
		return this.adres_id;
	}
	public void setAdres_id(long adres_id) {
		this.adres_id = adres_id;
	}

	public String getStraatnaam() {
		return this.straatnaam;
	}
	public void setStraatnaam(String straatnaam) {
		this.straatnaam = straatnaam;
	}

	public String getPostcode() {
		return this.postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getToevoeging() {
		return this.toevoeging;
	}
	public void setToevoeging(String toevoeging) {
		this.toevoeging = toevoeging;
	}

	public int getHuisnummer() {
		return this.huisnummer;
	}
	public void setHuisnummer(int huisnummer) {
		this.huisnummer = huisnummer;
	}

	public String getWoonplaats() {
		return this.woonplaats;
	}
	public void setWoonplaats(String woonplaats) {
		this.woonplaats = woonplaats;
	}
/*
	public Map<Klant, AdresType> getBewoners() {
		return this.bewoners;
	}
	public void setBewoners(Map<Klant, AdresType> bewoners){
		this.bewoners = bewoners;
	}

	public Set<Klant> getKlanten() {
		return this.klanten;
	}

	public void setKlanten(Set<Klant> klanten) {
		this.klanten = klanten;
	}

	public Set<AdresType> getAdrestypes() {
		return this.adrestypes;
	}

	public void setAdrestypes(Set<AdresType> adrestypes) {
		this.adrestypes = adrestypes;
	}
*/	
}

