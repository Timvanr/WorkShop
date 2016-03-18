public class Naam {
	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	
	public Naam(){
		this("Onbekend", null, "Onbekend");
	}
	
	public Naam(String voor, String tussen, String achter){
		this.voornaam = voor;
		this.tussenvoegsel = tussen;
		this.achternaam = achter;
	}
	
	public Naam(String voor, String achter){
		this.voornaam = voor;
		this.tussenvoegsel = null;
		this.achternaam = achter;
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

	public String toDB(){
		return "'" + this.toString().replaceAll("[ ]", "', '") + "'";
				//"'" + this.voornaam + "', '" + this.tussenvoegsel + "', '" + this.achternaam + "'"; 
	}
	@Override
	public String toString(){
		return this.voornaam + " " + this.tussenvoegsel + " " + this.achternaam;
	}
}
