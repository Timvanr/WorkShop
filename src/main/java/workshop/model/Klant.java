package workshop.model;

import workshop.dao.*;
import java.sql.SQLException;
import java.util.*;

public class Klant {
	private int klant_id;
	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	private String email;
			
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
	
	public Set<Adres> getAdres(){
		AdresDAOInterface adi = DAOFactory.getAdresDAO();
		return adi.readAdressenPerKlant(this.klant_id); 
	}
			
	public int getId() {
		return klant_id;
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
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	
	@Override
	public String toString(){
		return this.voornaam + " " + this.tussenvoegsel + " " + this.achternaam + "\n" + getAdres().toString() + "\n" + this.email;
	}
}
