package workshop.model;
import java.math.BigDecimal;
import java.util.Date;


import javax.persistence.*;

@Entity
@Table
public class Betaling {

public enum Betaalwijze {
	IDeal, PinBetaling, Contant, CreditCard;
	
}
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long betaling_id;
	
	@Temporal(TemporalType.DATE)
	private Date betaalDatum;
	
	@Enumerated(EnumType.STRING)
	@Column(name="betaalwijze")
	private Betaalwijze betaalWijze;
	
	@ManyToOne
	@JoinColumn (name="klant_id")
	protected Klant klant;
	
	@ManyToOne
	@JoinColumn (name="factuur_id")
	private Factuur factuur;
	
	@Column
	private String betalingsGegevens;
	
	@Column
	private BigDecimal betaaldBedrag;
	
public Betaling(){}
	
	public Betaling(Factuur factuur, BigDecimal bedrag){
		this.factuur = factuur;
		this.betaaldBedrag = bedrag;
		this.betaalDatum = new Date();
	}
	
	public Betaling(Factuur factuur, BigDecimal bedrag, String gegevens){
		this.factuur = factuur;
		this.betaaldBedrag = bedrag;
		this.betaalDatum = new Date();
		this.betalingsGegevens = gegevens;
	}
	public long getBetaling_id() {
		return betaling_id;
	}
	
	public void setBetaling_id(long betaling_id) {
		this.betaling_id = betaling_id;
	}
	
	public Date getBetaalDatum() {
		return betaalDatum;
	}
	public void setBetaalDatum(Date betaalDatum) {
		this.betaalDatum = betaalDatum;
	}
	
	public Betaalwijze getBetaalWijze() {
	return betaalWijze;
	}
	
	public void setBetaalWijze(Betaalwijze betaalWijze) {
		this.betaalWijze = betaalWijze;
	}
	
	public Klant getKlant() {
	return klant;
	}
	public void setKlant(Klant klant) {
		this.klant = klant;
	}
	
	
	public Factuur getFactuur() {
		return factuur;
	}
	
	public void setFactuur(Factuur factuur) {
		this.factuur = factuur;
	}
	
	
	public String getBetalingsGegevens() {
		return betalingsGegevens;
	}
	
	public void setBetalingsGegevens(String betalingsGegevens) {
		this.betalingsGegevens = betalingsGegevens;
	}
	
	public BigDecimal getBetaaldBedrag() {
		return betaaldBedrag;
	}

	public void setBetaaldBedrag(BigDecimal betaaldBedrag) {
		this.betaaldBedrag = betaaldBedrag;
	}
	
}
