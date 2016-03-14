import java.util.ArrayList;


public class Bestelling {
	private int id;
	private int klant_id;
	private ArrayList<Artikel> artikelen;
	
	public Bestelling(int klant_id, ArrayList<Artikel> artikelen){
		this.klant_id = klant_id;
		this.artikelen = artikelen;
	}
	
	public Bestelling(int klant_id){
		this.klant_id = klant_id;
		this.artikelen = new ArrayList<>();
	}
	
	public void voegArtikelToe(Artikel artikel){
		if (this.artikelen.size() < 3)
			this.artikelen.add(artikel);
		else
			System.out.println("De artikellijst is vol\nArtikel toevoeging is mislukt");
	}
	
	public void verwijderArtikel(Artikel artikel){
		if (this.artikelen.contains(artikel))
			this.artikelen.remove(artikel);
		else
			System.out.println("Artikel staat niet in de lijst\nArtikel verwijdering is mislukt");
	}
	
	public int getKlant_id(){
		return this.klant_id;
	}
	
	public Klant getKlant(){
		return Klant.get(this.klant_id);
	}
	
	public Artikel haalArtikel(int artikel_id){
		Artikel artikel = null;
		
		for (Artikel a: this.artikelen){
			if (a.getId() == artikel_id)
				artikel = a;
		}
		
		return artikel;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public ArrayList<Artikel> getArtikelen(){
		return this.artikelen;
	}
	
	@Override
	public String toString(){
		String artikellijst = "";
		for (Artikel a: this.artikelen)
			artikellijst += a.toString() + "\n";

		return "Bestellingnummer: " + this.id + " Klantnummer: " + this.klant_id + "\n" + artikellijst;
	}


}
