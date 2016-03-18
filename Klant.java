import java.sql.SQLException;


public class Klant {
	private int klant_id;
	private Naam naam;
	private int adres_id;
	private String email;
			
	public Klant(){
		this(new Naam(), 0, "Sorry geen e-mail");
	}
	
	public Klant(Naam naam, int adres_id, String email){
		this.naam = naam;
		this.adres_id = adres_id;
		this.email = email;
		
	}
	
	public static Klant get(int id){
		try {
			return Addressbook.haalKlant(id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Adres getAdres(){
		Adreslijst adressen = new Adreslijst();
		try {
			return adressen.searchById(this.adres_id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
			
	public int getId() {
		return klant_id;
	}

	public void setId(int klant_id) {
		this.klant_id = klant_id;
	}

	public String getVoornaam() {
		return this.naam.getVoornaam();
	}

	public void setVoornaam(String voornaam) {
		this.naam.setVoornaam(voornaam);
	}
	
	public String getTussenvoegsel() {
		return this.naam.getTussenvoegsel();
	}

	public void setTussenvoegsel(String tussenvoegsel) {
		this.naam.setTussenvoegsel(tussenvoegsel);
	}
	
	public String getAchternaam() {
		return this.naam.getAchternaam();
	}

	public void setAchternaam(String achternaam) {
		this.naam.setAchternaam(achternaam);
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
		return this.naam.toString() + "\n" + getAdres().toString() + "\n" + this.email;
	}
}
