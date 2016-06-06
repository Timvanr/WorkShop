package com.workshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Account")
public class Account implements java.io.Serializable{

	private static final long serialVersionUID = -4448115878877995696L;
	@Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
	private long account_id;
	@Column (name = "account_naam")
	private String naam;
	@ManyToOne
    @JoinColumn(name = "klant_id", nullable = false)
	private Klant klant; 
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name = "create_datum")
	private java.util.Date createDatum;
	@Column (name = "wachtwoord")
	private String wachtwoord;
	
	public Account() {
    }
	
	
	public long getAccount_id() {
		return this.account_id;
	}
	public void setAccount_id(long account_id) {
		this.account_id = account_id;
	}

	public Klant getKlant() {
		return this.klant;
	}

	public void setKlant(Klant klant) {
		this.klant = klant;
	}

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}
	
	public java.util.Date getCreateDatum() {
		return this.createDatum;
	}

	public void setCreateDatum(java.util.Date createDatum) {
		this.createDatum = createDatum;
	}


	public String getWachtwoord() {
		return wachtwoord;
	}
	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}
}
