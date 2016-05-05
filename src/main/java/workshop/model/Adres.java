package workshop.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Adres")
public class Adres implements java.io.Serializable{
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	private long adres_id;
	@Column
	private String straatnaam;
	@Column
	private String postcode;
	@Column
	private String toevoeging;
	@Column
	private int huisnummer;
	@Column
	private String woonplaats;
	@ManyToMany(mappedBy="adressen")
	private Set<Klant> bewoners;

	public Adres() {
		this.bewoners = new HashSet();
	}

	public Adres(String straat, int huisnummer, String toevoeging, String postcode,	String plaats) {
		this.straatnaam = straat;
		this.huisnummer = huisnummer;
		this.toevoeging = toevoeging;
		this.postcode = postcode;
		this.woonplaats = plaats;
		this.bewoners = new HashSet();
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

	public Set<Klant> getBewoners() {
		return this.bewoners;
	}
	public void setBewoners(Set<Klant> bewoners){
		this.bewoners = bewoners;
	}

}

