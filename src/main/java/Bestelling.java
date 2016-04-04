import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class Bestelling {
	private int klant_id;
	private int bestelling_id;
	private HashMap<Artikel, Integer> artikelen;
	
	public Bestelling(int klant_id) {
		this.klant_id = klant_id;
		this.artikelen = new HashMap<>();
	}
	public Bestelling(){
		this(0);
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
	
	public Klant getKlant(){
		Klantbestand klantbestand = new Klantbestand();
		Klant klant = new Klant();
		try {
			klant = klantbestand.readKlantWithId(this.klant_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return klant;
	}
		
	@Override
	public String toString(){
		String artikelLijst = "";
		for (Map.Entry<Artikel, Integer> entry: this.artikelen.entrySet()){
			artikelLijst += entry.getKey().toString() + " " + entry.getValue() + "\n";
		}
		return "Bestellingnummer: " + this.bestelling_id + " Klantnummer: " + this.klant_id + "\n" + artikelLijst;
	}
	
}
