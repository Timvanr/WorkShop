package workshop.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
//import javax.annotation.Resource;
import javax.persistence.*;

@Entity
@Table(name= "Klant")
public class Klant implements java.io.Serializable {
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	@Column(name="klant_id")
	private long klant_id;
	@Column
	private String voornaam;
	@Column
	private String tussenvoegsel;
	@Column
	private String achternaam;
	@Column(unique = true)
	private String email;

	//@Resource(name="bestellingen")
	@OneToMany(mappedBy = "klant", targetEntity = Bestelling.class)//, fetch = FetchType.EAGER) 
	//@Cascade({CascadeType.SAVE_UPDATE})
	private Set<Bestelling> bestellingen;
/*
	//@Resource(name="Factuur")
	@OneToMany(mappedBy = "klant", targetEntity = Factuur.class)//, fetch = FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE})
	private Set<Factuur> facturen;
*/	
	//@Resource(name="accounts")
	@OneToMany(mappedBy = "klant", targetEntity = Account.class, cascade=CascadeType.ALL)//, fetch = FetchType.EAGER)
	//@Cascade({CascadeType.SAVE_UPDATE})
	private Set<Account> accounts;
	
	//@Resource(name="adressen")
	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(name="klant_has_adres", joinColumns=@JoinColumn(name="klant_id"),
		inverseJoinColumns=@JoinColumn(name="adrestype_id"))
	@MapKeyJoinColumn(name = "adres_id", table = "klant_has_adres")
	private Map<Adres, AdresType> adressen;


	public Klant() {}

	public Klant(String voornaam, String tussenvoegsel, String achternaam, String email){
		this(0, voornaam, tussenvoegsel, achternaam, email);
	} 

	public Klant(int id, String voornaam, String tussenvoegsel, String achternaam, String email){
		this.klant_id = id;
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.tussenvoegsel = tussenvoegsel;
		this.email = email;
		this.adressen = new HashMap();
	}

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
