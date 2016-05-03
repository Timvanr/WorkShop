package workshop.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Betaling {
	@Id @GeneratedValue
	@JoinColumn(name="betaling_id")
	private int id;
	private Date datum;
	@ManyToOne
	@JoinColumn(name="factuur_id", referencedColumnName="id")
	private Factuur factuur;
	//private int betaalwijze;
	private String betalingsgegevens;
	private BigDecimal betaaldBedrag;
	
	public Betaling(){}
	
	public Betaling(Factuur factuur, BigDecimal bedrag){
		this.factuur = factuur;
		this.betaaldBedrag = bedrag;
		this.datum = new Date();
	}
	
	public Betaling(Factuur factuur, BigDecimal bedrag, String gegevens){
		this.factuur = factuur;
		this.betaaldBedrag = bedrag;
		this.datum = new Date();
		this.betalingsgegevens = gegevens;
	}
	
	public BigDecimal getBetaaldBedrag(){
		return this.betaaldBedrag;
	}
	public void setBetaaldBedrag(BigDecimal bedrag){
		this.betaaldBedrag = bedrag;
	}
}
