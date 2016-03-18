
public class Adres {
	private int adres_id;
	private String straatnaam;
	private int huisnummer;
	private String toevoeging;
	private String postcode;
	private String woonplaats;
	
	public Adres(){
		this("Onbekend", 0, null, "Onbekend", "Onbekend");
	}
	
	public Adres(String straat, int nummer, String toevoeging, String postcode, String plaats){
		this.straatnaam = straat;
		this.huisnummer = nummer;
		this.toevoeging = toevoeging;
		this.postcode = postcode;
		this.woonplaats = plaats;
	}
	
	public Adres(int adres_id, String straat, int nummer, String toevoeging, String postcode, String plaats){
		this.adres_id = adres_id;
		this.straatnaam = straat;
		this.huisnummer = nummer;
		this.toevoeging = null;
		this.postcode = postcode;
		this.woonplaats = plaats;
	}

	public Adres(String straat, int nummer, String postcode, String plaats){
		this.straatnaam = straat;
		this.huisnummer = nummer;
		this.toevoeging = null;
		this.postcode = postcode;
		this.woonplaats = plaats;
	}
	
	public String getStraatnaam() {
		return straatnaam;
	}

	public void setStraatnaam(String straatnaam) {
		this.straatnaam = straatnaam;
	}

	public int getHuisnummer() {
		return huisnummer;
	}

	public void setHuisnummer(int huisnummer) {
		this.huisnummer = huisnummer;
	}

	public String getToevoeging() {
		return toevoeging;
	}

	public void setToevoeging(String toevoeging) {
		this.toevoeging = toevoeging;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getWoonplaats() {
		return woonplaats;
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
