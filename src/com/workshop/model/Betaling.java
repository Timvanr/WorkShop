package com.workshop.model;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table
public class Betaling {
	
	public enum Betaalwijze {
		IDeal, PinBetaling, Contant, CreditCard;
		
	}
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="betaling_id")
	protected Long betaling_id;
	
	@Temporal(TemporalType.DATE)
	private Date betaalDatum;
	
	@Enumerated(EnumType.STRING)
	@Column(name="betaalwijze")
	protected Betaalwijze betaalwijze;
	
	@ManyToOne
	@JoinColumn (name="klant_id", nullable=false)
	protected Klant klant;
	
	@ManyToOne
	@JoinColumn (name="factuur_id")
	protected Factuur factuur;
	
	@Column
	private String betalingsGegevens;
	
	@Column
	private BigDecimal betaaldBedrag;
	
	@Deprecated
	public Betaling(){}
	
	public Betaling(Factuur factuur, BigDecimal bedrag){
		this.factuur = factuur;
		this.betaaldBedrag = bedrag;
		this.betaalDatum = new Date();
		this.klant = factuur.getBestelling().getKlant();//moet alles wel geSet zijn.
	}
	
	public Betaling(Factuur factuur, BigDecimal bedrag, String gegevens){
		this.factuur = factuur;
		this.betaaldBedrag = bedrag;
		this.betaalDatum = new Date();
		this.betalingsGegevens = gegevens;
		this.klant = factuur.getBestelling().getKlant();
	}
	public long getBetaling_id() {
		return this.betaling_id;
	}
	
	public void setBetaling_id(long betaling_id) {
		this.betaling_id = betaling_id;
	}
	
	public Date getBetaalDatum() {
		return this.betaalDatum;
	}
	public void setBetaalDatum(Date betaalDatum) {
		this.betaalDatum = betaalDatum;
	}
	
	public Betaalwijze getBetaalWijze() {
	return this.betaalwijze;
	}
	
	public void setBetaalWijze(Betaalwijze betaalWijze) {
		this.betaalwijze = betaalWijze;
	}
	
	public Klant getKlant() {
		return this.klant;
	}
	
	public void setKlant(Klant klant) {
		this.klant = klant;
	}
	
	public Factuur getFactuur() {
		return this.factuur;
	}
	
	public void setFactuur(Factuur factuur) {
		this.factuur = factuur;
	}
	
	
	public String getBetalingsGegevens() {
		return this.betalingsGegevens;
	}
	
	public void setBetalingsGegevens(String betalingsGegevens) {
		this.betalingsGegevens = betalingsGegevens;
	}
	
	public BigDecimal getBetaaldBedrag() {
		return this.betaaldBedrag;
	}

	public void setBetaaldBedrag(BigDecimal betaaldBedrag) {
		this.betaaldBedrag = betaaldBedrag;
	}
	
}
