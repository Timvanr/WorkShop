
import java.sql.SQLException;


public class Klant {
	private int klant_id;
	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	Adres adres;
	private int adres_id;
	private String email;
			
	public Klant(){
		
	}
	
	public Klant(String voornaam, String tussenvoegsel, String achternaam, String email){
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.tussenvoegsel = tussenvoegsel;
		this.email = email;
		
	}
	
	public static Klant get(int id){
		Klantbestand klantb = new Klantbestand();
		try {
			return klantb.readKlantWithId(id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Adres getAdres(){
		Adreslijst adressen = new Adreslijst();
		// try {
			return adressen.searchById(this.adres_id);
		/*	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; */
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
	
	public int getAdres_id(){
		return this.adres_id;
	}
	
	public void setAdres_id(int adres_id){
		this.adres_id = adres_id;
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
