
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;


public class Bestelling {
	private int klant_id;
	private int bestelling_id;
	private HashMap<Artikel, Integer> artikelen;
	
	public Bestelling(int klant_id) {
		this.klant_id = klant_id;
		this.artikelen = new HashMap<Artikel, Integer>();
	}
	public Bestelling(){
		this.artikelen = new HashMap<>();
	}
	
	public int getKlant_id() { 
		return this.klant_id;
	}
	
	public void setKlant_id(int klant_id){
		this.klant_id = klant_id;
	}
	
	public int getBestelling_id() {
		return this.bestelling_id;
	}
	
	public void setBestelling_id(int bestelling_id) {
		this.bestelling_id = bestelling_id;
	}
	
	public HashMap<Artikel, Integer> getArtikelen(){
		return this.artikelen;
	}
	
	public void voegArtikelToeAanBestelling(Artikel artikel, Integer aantal){
		this.artikelen.put(artikel, aantal);
	}
	
	public void removeArtikel(Artikel artikel){
		this.artikelen.remove(artikel);
	}
	
	public BigDecimal getTotaalPrijs(){
		BigDecimal total = BigDecimal.ZERO;
		
		for (Map.Entry<Artikel, Integer> entrySet: this.artikelen.entrySet()){
			BigDecimal artikelTimesAantal = entrySet.getKey().getPrijs().multiply(new BigDecimal(entrySet.getValue()));
			total = total.add(artikelTimesAantal);
		}
		
		return total;
	}
	
	public Klant getKlant(){
		Klantbestand klantbestand = new Klantbestand();
		Klant klant = klantbestand.readKlantWithId(this.klant_id);
		return klant;
	}
		
	@Override
	public String toString(){
		String artikelLijst = "";
		for (Map.Entry<Artikel, Integer> entry: this.artikelen.entrySet()){
			artikelLijst += entry.getKey().toString() + " aantal: " + entry.getValue() + "\n";
		}
		return "Bestellingnummer: " + this.bestelling_id + ", Klantnummer: " + this.klant_id + "\n" + 
				artikelLijst + "Totaalprijs: " + NumberFormat.getCurrencyInstance().format(getTotaalPrijs());
	}
	/*
	public static void main(String[] args){
		Bestelling bestelling = new Bestelling(555);
		bestelling.voegArtikelToeAanBestelling(new Artikel(34, "warm broodje", new BigDecimal(3.95)), 2);
		bestelling.voegArtikelToeAanBestelling(new Artikel(44, "latte machiato", new BigDecimal(2.95)), 2);
		System.out.println(bestelling);
	}
	*/
}
