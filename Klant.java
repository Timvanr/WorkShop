import org.apache.commons.validator.routines.EmailValidator;

public class Klant {
	private int id;
	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	private String email;
	protected Adres adres;
	protected Bestelling bestelling;
	EmailValidator emailVal = EmailValidator.getInstance();
	
	public Klant(){
		bestelling = new Bestelling();
		adres = new Adres();
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getVoornaam() {
		return voornaam;
	}
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}
	public String getTussenvoegsel() {
		return tussenvoegsel;
	}
	public void setTussenvoegsel(String tussenvoegsel) {
		this.tussenvoegsel = tussenvoegsel;
	}
	public String getAchternaam() {
		return achternaam;
	}
	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		
		if (email == null){
			this.email = null;
		}
		else if (emailVal.isValid(email)){
			this.email = email;
		}
		else {
			System.out.println("E-mail does not apply to criteria. Enter e-mail adress like abc@123.com");
		}
	}

	public boolean heeftAdres(){
		if (this.adres != null)
			return true;
		else 
			return false;
	}
	
	public boolean heeftBestelling(){
		if (this.bestelling != null)
			return true;
		else
			return false;
	}
	
	public Bestelling getBestelling(){
		return this.bestelling;
	}
	
	public Adres getAdres(){
		return this.adres;
	}
	
	
	@Override
	public String toString(){
		return "Klant: " + id + " " + voornaam + " " + tussenvoegsel + " " + achternaam + " " + email;
			
	}
	
}
