package workshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account implements java.io.Serializable{
	@Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
	private long account_id;
	@Column (name = "account_naam")
	private String naam;
	@ManyToOne
    @JoinColumn(name = "klant_id", nullable = false)
	private Klant klant; 
	@Column (name = "create_datum")
	private java.util.Date createDatum;
	
	public Account() {
    }
	
	
	public long getAccount_id() {
		return account_id;
	}
	public void setAccount_id(long account_id) {
		this.account_id = account_id;
	}

	public Klant getKlant() {
		return klant;
	}

	public void setKlant(Klant klant) {
		this.klant = klant;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}
	
	public java.util.Date getCreateDatum() {
		return createDatum;
	}

	public void setCreateDatum(java.util.Date createDatum) {
		this.createDatum = createDatum;
	}
}
