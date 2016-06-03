package com.workshop.model;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.*;

@Entity
@Table
public class Klant {
	
	private static final long serialVersionUID = 1270183298575805842L;
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	@Column(name="klant_id")
	private Long klant_id;
	@Column
	private String voornaam;
	@Column
	private String tussenvoegsel;
	@Column
	private String achternaam;
	@Column(unique = true)
	private String email;

	@Resource(name="bestellingen")
	@OneToMany(mappedBy = "klant", targetEntity = Bestelling.class, fetch = FetchType.EAGER) 
	//@Cascade({CascadeType.SAVE_UPDATE})
	private Set<Bestelling> bestellingen;
/*
	//@Resource(name="Factuur")
	@OneToMany(mappedBy = "klant", targetEntity = Factuur.class)//, fetch = FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE})
	private Set<Factuur> facturen;
*/	
	@Resource(name="accounts")
	@OneToMany(mappedBy = "klant", targetEntity = Account.class, cascade=CascadeType.ALL)//, fetch = FetchType.EAGER)
	//@Cascade({CascadeType.SAVE_UPDATE})
	private Set<Account> accounts;
	
	@Resource(name="adressen")
	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(name="klant_has_adres", joinColumns=@JoinColumn(name="klant_id"),
		inverseJoinColumns=@JoinColumn(name="adres_type_id"))
	@MapKeyJoinColumn(name = "adres_id", table = "klant_has_adres")
	private Map<Adres, AdresType> adressen;

	public long getId() {
		return this.klant_id;
	}
	public void setId(long klant_id) {
		this.klant_id = klant_id;
	}

	public String getVoornaam() {
		return this.voornaam;
	}
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getTussenvoegsel() {
		return this.tussenvoegsel;
	}
	public void setTussenvoegsel(String tussenvoegsel) {
		this.tussenvoegsel = tussenvoegsel;
	}

	public String getAchternaam() {
		return this.achternaam;
	}
	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Bestelling> getBestellingen() {
		return this.bestellingen;
	}
	public void setBestellingen(Set<Bestelling> bestellingen) {
		this.bestellingen = bestellingen;
	}

	public Set<Factuur> getFacturen() {
		Set<Factuur> facturen = new HashSet();
		
		for (Bestelling b : getBestellingen()){
			facturen.add(b.getFactuur());
		}
		return facturen;
	}
/* kan hier niks voor verzinnen
	public void setFacturen(Set<Factuur> facturen) {
		this.facturen = facturen;
	}
*/
	public Set<Account> getAccounts() {
		return this.accounts;
	}
	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Map<Adres, AdresType> getAdressen() {
		return this.adressen;
	}

	public void setAdressen(Map<Adres, AdresType> adressen) {
		this.adressen = adressen;
	}

	@Override
	public String toString(){
		return this.voornaam + " " + this.tussenvoegsel + " " + this.achternaam + "\n" + this.email;
	}

}
