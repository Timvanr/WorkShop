
public class Adres{

	protected String straatnaam;
	protected String postcode;
	protected int huisnummer;
	protected String toevoeging;
	protected String woonplaats;
	
	public String getStraatnaam() {
		return straatnaam;
	}
	public void setStraatnaam(String straatnaam) {
		this.straatnaam = straatnaam;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
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
	public String getWoonplaats() {
		return woonplaats;
	}
	public void setWoonplaats(String woonplaats) {
		this.woonplaats = woonplaats;
	}
	
	@Override
	public String toString(){
		if (toevoeging == null){
			toevoeging = "";
		}
		return "Adres: " + straatnaam + " " + huisnummer + toevoeging + "\n" + postcode + "\n" + woonplaats;
	}
	
}
