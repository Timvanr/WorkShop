import java.math.BigDecimal;


public class Artikel {
	private int artikel_id;
	private String artikel_naam;
	private BigDecimal artikel_prijs;
	
	public Artikel(){
		this(0, null, null);
	}
	
	public Artikel(int id, String naam, BigDecimal prijs){
		this.artikel_id = id;
		this.artikel_naam = naam;
		this.artikel_prijs = prijs;
	}
	
	public int getId() {
		return this.artikel_id;
	}
	public void setId(int artikel_id) {
		this.artikel_id = artikel_id;
	}
	public String getNaam() {
		return this.artikel_naam;
	}
	public void setNaam(String artikel_naam) {
		this.artikel_naam = artikel_naam;
	}
	public BigDecimal getPrijs() {
		return this.artikel_prijs;
	}
	public void setPrijs(BigDecimal artikelPrijs) {
		this.artikel_prijs = artikelPrijs;
	}
	
	@Override
	public String toString(){
		return "Artikel- ID: " + this.artikel_id + " Naam: " + this.artikel_naam + " Prijs: " + this.artikel_prijs;
	}
	
	
	
	
}
