package com.workshop.model;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table
public class Factuur {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="factuur_id")
	protected Long factuur_id;
	
	@Column
	private String factuurNummer;
	
	@Temporal(TemporalType.DATE)
	private Date factuurDatum;
	
	@OneToMany (mappedBy="factuur")
	protected Set<Betaling> betalingSet;
	
	@OneToOne
	@JoinColumn(name="bestelling_id")
	protected Bestelling bestelling;
	
	@Column
	private BigDecimal totaalBedrag;
	
	
	public void setFactuur_id(long factuur_id) {
		this.factuur_id = factuur_id;
	}
	
	public String getFactuurNummer() {
		return this.factuurNummer;
	}
	public void setFactuurNummer(String factuurNummer) {
		this.factuurNummer = factuurNummer;
	}
	
	public Date getFactuurDatum() {
		return this.factuurDatum;
	}
	public void setFactuurDatum(Date factuurDatum) {
		this.factuurDatum = factuurDatum;
	}
	
	
	public Set<Betaling> getBetalingSet() {
		return this.betalingSet;
	}
	
	public void setBetalingSet(Set<Betaling> betalingSet) {
		this.betalingSet = betalingSet;
	}
	
	public Bestelling getBestelling() {
		return this.bestelling;
	}
	public void setBestelling(Bestelling bestelling) {
		this.bestelling = bestelling;
	}
	
	public BigDecimal getOpenstaandBedrag() {
		BigDecimal totaal = this.totaalBedrag; 
		
		for (Betaling b : this.betalingSet){
			totaal = totaal.subtract(b.getBetaaldBedrag()); 
		}
		return totaal;
	}
	
	public BigDecimal getTotaalBedrag() {
		return this.totaalBedrag;
	}
// Als er achteraf nog een korting of boete ofzo toegekend wordt misschien
	public void setTotaalBedrag(BigDecimal totaalBedrag) {
		this.totaalBedrag = totaalBedrag;
	}

	//openstaand en betaaldbedrag nog toevoegen
	@Override
	public String toString(){
		return "Factuur: " + this.factuur_id + " factuur datum: " + this.factuurDatum + " factuur nummer: " + this.factuurNummer;
	}
}
