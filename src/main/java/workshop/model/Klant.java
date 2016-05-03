package workshop.model;

import workshop.dao.*;
import java.sql.SQLException;
import java.util.*;

import javax.persistence.*;

@Entity
public class Klant {
	@Id @GeneratedValue
	@JoinTable(name="klant_has_adres", joinColumns=@JoinColumn(name="klant_id"))
	private int klant_id;
	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	private String email;
	@ManyToMany(mappedBy="bewoners")
	private Set<Adres> adressen;		
	
	public Klant(){
		this(0, null, null, null, null);
	}
	public Klant(String voornaam, String tussenvoegsel, String achternaam, String email){
		this(0, voornaam, tussenvoegsel, achternaam, email);
	}
	
	public Klant(int id, String voornaam, String tussenvoegsel, String achternaam, String email){
		this.klant_id = id;
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.tussenvoegsel = tussenvoegsel;
		this.email = email;
	}
	
	public static Klant get(int id){
		KlantDAOInterface klantDAO = DAOFactory.getKlantDAO();
		return klantDAO.readKlantWithId(id);
	}
	
	public Set<Adres> getAdressen(){
		return this.adressen;
	}
	
	public void setAdressen(Set<Adres> adresSet){
		this.adressen = adresSet;
	}
	
	public String adresSetString(){
		Set<Adres> adresSet = getAdressen();
		String adresString = "";
		for (Adres a: adresSet){
			adresString += "\nAdresnummer" + a.getId() + "\n" + a.toString();
		}
		return adresString;
	}
			
	public int getId() {
		return this.klant_id;
	}

	public void setId(int klant_id) {
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
	
	@Override
	public String toString(){
		return "Klantnummer: " + this.klant_id + "\n" + 
				this.voornaam + " " + this.tussenvoegsel + " " + this.achternaam + 
				adresSetString() + "\n" + this.email;
	}
}
