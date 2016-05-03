package workshop.model;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

@Entity
public class Factuur {
	@Id @GeneratedValue
	@JoinColumn(name="factuur_id")
	private int id;
	
	private Date datum;
	
	@ManyToOne
	@JoinColumn(name="bestelling_id", referencedColumnName="id")
	private Bestelling bestelling;
	
	@ElementCollection
	@CollectionTable(name="factuur_has_betaling", 
			joinColumns=@JoinColumn(name="factuur_id")
			//JoinColumns=@JoinColumn(name="betaling_id")
	)
	private Set<Betaling> betalingSet;
	
	public Factuur(){
		this.datum = new Date();
		this.betalingSet = new HashSet();
	}
	
	public Factuur(Bestelling bestelling){
		this.bestelling = bestelling;
		this.datum = new Date();
		this.betalingSet = new HashSet();
	}
	
	public Bestelling getBestelling() {
		return this.bestelling;
	}
	public void setBestelling(Bestelling bestelling) {
		this.bestelling = bestelling;
	}
	public Set<Betaling> getBetalingSet() {
		return this.betalingSet;
	}
	public void setBetalingSet(Set<Betaling> betalingSet) {
		this.betalingSet = betalingSet;
	}
	public BigDecimal getOpenstaandBedrag(){
		BigDecimal totaal = this.bestelling.getTotaalPrijs();
		for (Betaling b : getBetalingSet()){
			totaal = totaal.subtract(b.getBetaaldBedrag());
		}
		return totaal;
	}
	
}
