import java.util.ArrayList;

public class Bestelling{

	private int bestelling_id;
	protected Artikel artikel;
	ArrayList<Artikel> artikelen;
	
	public Bestelling(){
		artikelen = new ArrayList<>();
	}
	
	public int getBestelling_id() {
		return bestelling_id;
	}
	public void setBestelling_id(int bestelling_id) {
		this.bestelling_id = bestelling_id;
	}
	
	public int getArtikel_id() {
		return artikel.getArtikel_id();
	}
	
	public String getArtikel_naam(){
		return artikel.getArtikel_naam();
	}
	
	public int getArtikel_aantal(){
		return artikel.getArtikel_aantal();
	}
	
	public double getArtikel_prijs(){
		return artikel.getArtikel_prijs();
	}
	
	public void voegArtikelToeAanBestelling(Artikel artikel){
		artikelen.add(artikel);
	}
	
	public void printBestelling(){
		for (Artikel artikel: artikelen){
			System.out.print(artikel.toString());
			System.out.println();
		}
	}
	
}
