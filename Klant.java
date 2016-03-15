import java.sql.SQLException;


public class Klant {
	private int klant_id;
	private Naam naam;
	private Adres adres;
	private String email;
			
	public Klant(){
		this(new Naam(), new Adres(), "Sorry geen e-mail");
	}
	
	public Klant(Naam naam, Adres adres, String email){
		this.naam = naam;
		this.adres = adres;
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

	public String getStraatnaam() {
		return this.adres.getStraatnaam();
	}
	
	public int getHuisnummer(){
		return this.adres.getHuisnummer();
	}
	
	public String getToevoeging(){
		return this.adres.getToevoeging();
	}
	
	public String getPostcode(){
		return this.adres.getPostcode();
	}
	
	public String getWoonplaats(){
		return this.adres.getWoonplaats();
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	/*
	public String toDB(){
		return this.naam.toDB() + ",  " + this.adres.toDB() + ", '" + this.email + "'";
	}
	*/
	@Override
	public String toString(){
		return this.naam.toString() + "\n" + this.adres.toString() + "\n" + this.email;
	}
}
