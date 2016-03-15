
public class Artikel {
	private int artikel_id;
	private String artikel_naam;
	private int artikel_aantal;
	private double artikel_prijs;
	
	public Artikel(int id, String naam, int aantal, double prijs){
		this.artikel_id = id;
		this.artikel_naam = naam;
		this.artikel_aantal = aantal;
		this.artikel_prijs = prijs;
	}
	
	public Artikel(){
		this(0, null, 0, 0.0);
	}

	public int getId() {
		return artikel_id;
	}

	public String getNaam() {
		return artikel_naam;
	}
	
	public int getAantal() {
		return artikel_aantal;
	}

	public void setArtikel_aantal(int artikel_aantal) {
		this.artikel_aantal = artikel_aantal;
	}

	public double getPrijs() {
		return artikel_prijs;
	}

	public void setArtikel_prijs(double artikel_prijs) {
		this.artikel_prijs = artikel_prijs;
	}
	
	public Artikel getRandomArtikel(){
		return new Artikel();
	}
	
	public String toString(){
		return this.artikel_id + " " + this.artikel_naam + " " + this.artikel_aantal + " " + this.artikel_prijs;
	}
}
