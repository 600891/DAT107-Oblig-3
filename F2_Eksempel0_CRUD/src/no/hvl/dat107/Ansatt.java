package no.hvl.dat107;

import java.sql.Date;
import java.sql.Statement;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(schema = "obligatorisk3")
//@NamedQuery(name = "hentAlleAnsatte", query ="SELECT a FROM Ansatt as a order by a.ansatt_id")
public class Ansatt {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer ansatt_id;
	
	private String brukernavn;
	private String fornavn;
	private String etternavn;
	private Date ans_dato;
	private String stilling;
	private double mnd_lonn;
	
    @OneToMany(mappedBy="ansatt_id")
    private List<ProsjektDeltaker> deltagelser;
	
	@ManyToOne
	@JoinColumn(name = "avd_fk", referencedColumnName = "avd_id")
	private Avdeling avd_fk;
	
	public Ansatt() {}
	
	public Ansatt(String brukernavn, String fornavn, String etternavn, String ans_dato, String stilling, double mnd_lonn, Avdeling avd_fk) {
		this.brukernavn = brukernavn;
		this.fornavn = fornavn;						this.etternavn = etternavn;
		this.ans_dato = Date.valueOf(ans_dato);		this.stilling = stilling;
		this.mnd_lonn = mnd_lonn;					this.avd_fk = avd_fk;				
	}
	
	public boolean erSjef() {
		if(this.equals(getAvd().getSjef())){
			return true;
		}
		return false;
	}
	
    public void leggTilProsjektdeltagelse(ProsjektDeltaker deltaker) {
        deltagelser.add(deltaker);
    }

    public void fjernProsjektdeltagelse(ProsjektDeltaker deltaker) {
        deltagelser.remove(deltaker);
    }
	
	public Integer getAnsatt_id() {
		return ansatt_id;
	}

	public String getBrukernavn() {
		return brukernavn;
	}

	public void setBrukernavn(String brukernavn) {
		this.brukernavn = brukernavn;
	}

	public String getFornavn() {
		return fornavn;
	}

	public void setFornavn(String fornavn) {
		this.fornavn = fornavn;
	}

	public String getEtternavn() {
		return etternavn;
	}

	public void setEtternavn(String etternavn) {
		this.etternavn = etternavn;
	}

	public Date getAns_dato() {
		return ans_dato;
	}

	public void setAns_dato(String ans_dato) {
		this.ans_dato = Date.valueOf(ans_dato);
	}

	public String getStilling() {
		return stilling;
	}

	public void setStilling(String stilling) {
		this.stilling = stilling;
	}

	public double getMnd_lonn() {
		return mnd_lonn;
	}

	public void setMnd_lonn(double mnd_lonn) {
		this.mnd_lonn = mnd_lonn;
	}

	public Avdeling getAvd() {
		return avd_fk;
	}

	public void setAvd(Avdeling avd_fk) {
		this.avd_fk = avd_fk; 
	}

	@Override
	public String toString() {
		return String.format("Ansatt-id = %s \t Brukernavn = %s \t Fornavn = %s \t Etternavn = %s \t Ansettelsesdato = %s \t Stilling = %s \t"
				+ " Månedslønn = %s \t Avdeling = %s", 
				ansatt_id, brukernavn, fornavn, etternavn, ans_dato, stilling, mnd_lonn, avd_fk.toString());
	}
}
