import java.math.BigDecimal;


public class Artikel extends Bestelling {

	protected int artikel_id;
	protected String artikel_naam;
	protected BigDecimal artikel_prijs;
	protected int artikel_aantal;
	
	public Artikel(){
		
	}
	
	public Artikel(int artikel_id, String artikel_naam, BigDecimal artikel_prijs, int artikel_aantal){
		this.artikel_id = artikel_id;
		this.artikel_naam = artikel_naam;
		this.artikel_prijs = artikel_prijs;
		this.artikel_aantal = artikel_aantal;
	}
	
	public int getId() {
		return artikel_id;
	}
	public void setId(int artikel_id) {
		this.artikel_id = artikel_id;
	}
	public String getNaam() {
		return artikel_naam;
	}
	public void setNaam(String artikel_naam) {
		this.artikel_naam = artikel_naam;
	}
	public BigDecimal getPrijs() {
		return artikel_prijs;
	}
	public void setPrijs(BigDecimal artikelPrijs) {
		this.artikel_prijs = artikelPrijs;
	}
	public int getAantal() {
		return artikel_aantal;
	}
	public void setAantal(int artikel_aantal) {
		this.artikel_aantal = artikel_aantal;
	}
	
	@Override
	public String toString(){
		return "Artikel- ID: " + artikel_id + " Naam: " + artikel_naam + " Prijs: " + artikel_prijs + " Aantal: " + artikel_aantal;
	}
	
	
	
	
}
