package workshop.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Adres {
	@Id @GeneratedValue
	private int adres_id;
	private String straatnaam;
	private int huisnummer;
	private String toevoeging;
	private String postcode;
	private String woonplaats;
	@ManyToMany
	@JoinTable(name="klant_has_adres", 
			joinColumns=@JoinColumn(name="adres_id"),
			inverseJoinColumns=@JoinColumn(name="klant_id")
	)
	private Set<Klant> bewoners;
	
	public Adres(){
		this(0, null, 0, null, null, null, new HashSet());
	}
	
	public Adres(String straat, int huisnummer, String postcode, String plaats){
		this(0, straat, huisnummer, null, postcode, plaats, new HashSet());
	}
	
	public Adres(String straat, int huisnummer, String toevoeging, String postcode, String plaats){
		this(0, straat, huisnummer, toevoeging, postcode, plaats, new HashSet());
	}
	
	public Adres(int adres_id, String straat, int huisnummer, String toevoeging, String postcode, String plaats, HashSet<Klant> bewoners){
		this.adres_id = adres_id;
		this.straatnaam = straat;
		this.huisnummer = huisnummer;
		this.toevoeging = toevoeging;
		this.postcode = postcode;
		this.woonplaats = plaats;
		this.bewoners = bewoners;
	}

	public Set<Klant> getBewoners() {
		return this.bewoners;
	}

	public void setBewoners(Set<Klant> bewoners) {
		this.bewoners = bewoners;
	}

	public int getId(){
		return this.adres_id;
	}
	
	public void setId(int adres_id){
		this.adres_id = adres_id;
	}
	
	public String getStraatnaam() {
		return this.straatnaam;
	}

	public void setStraatnaam(String straatnaam) {
		this.straatnaam = straatnaam;
	}

	public int getHuisnummer() {
		return this.huisnummer;
	}

	public void setHuisnummer(int huisnummer) {
		this.huisnummer = huisnummer;
	}

	public String getToevoeging() {
		return this.toevoeging;
	}

	public void setToevoeging(String toevoeging) {
		this.toevoeging = toevoeging;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getWoonplaats() {
		return this.woonplaats;
	}

	public void setWoonplaats(String woonplaats) {
		this.woonplaats = woonplaats;
	}

	public String toString(){
		return this.straatnaam + " " + this.huisnummer + " " + this.toevoeging + "\n" + 
				this.postcode + " " + this.woonplaats;
	}
	
	public String toDB(){
		return "'" + this.toString().replaceAll("[ \n]", "', '") + "'";	
				//"'" + this.straatnaam + "', " + this.huisnummer + ", '" + this.toevoeging + "', " + this.postcode + "', " + this.woonplaats + "', ";
	}
}
