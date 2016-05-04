package workshop.model;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table
public class Factuur {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long factuur_id;
	
	@Column
	private String factuurNummer;
	
	@Temporal(TemporalType.DATE)
	private Date factuurDatum;
	
	@OneToMany (mappedBy="factuur")
	private Set<Betaling> betalingSet;
	
	@OneToOne
	@JoinColumn(name="bestelling_id")
	private Bestelling bestelling;
	
	@Column
	private BigDecimal openstaandBedrag;
	
	
	
	public Factuur(){
		this.factuurDatum = new Date();
		this.betalingSet = new HashSet<Betaling>();
	}
	
	public Factuur(Bestelling bestelling){
		this.bestelling = bestelling;
		this.factuurDatum = new Date();
		this.betalingSet = new HashSet<Betaling>();
	}
	public long getFactuur_id() {
		return factuur_id;
	}
	
	public void setFactuur_id(long factuur_id) {
		this.factuur_id = factuur_id;
	}
	
	public String getFactuurNummer() {
		return factuurNummer;
	}
	public void setFactuurNummer(String factuurNummer) {
		this.factuurNummer = factuurNummer;
	}
	
	public Date getFactuurDatum() {
		return factuurDatum;
	}
	public void setFactuurDatum(Date factuurDatum) {
		this.factuurDatum = factuurDatum;
	}
	
	
	public Set<Betaling> getBetalingSet() {
		return betalingSet;
	}
	
	public void setBetalingSet(Set<Betaling> betalingSet) {
		this.betalingSet = betalingSet;
	}
	
	public Bestelling getBestelling() {
		return bestelling;
	}
	public void setBestelling(Bestelling bestelling) {
		this.bestelling = bestelling;
	}
	
	public BigDecimal getOpenstaandBedrag() {
		return openstaandBedrag;
	}

	public void setOpenstaandBedrag(BigDecimal openstaandBedrag) {
		this.openstaandBedrag = openstaandBedrag;
	}

	
}
