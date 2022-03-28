package no.hvl.dat107;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(schema = "obligatorisk3")
public class Avdeling {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer avd_id;
	
	private String navn;
	
	@OneToMany(mappedBy = "avd_fk")
	private List<Ansatt> ansatte = new ArrayList<>();
	
	@OneToOne
	@JoinColumn (name = "sjef")
	private Ansatt sjef;
	
	public void printAvdAnsatte() {
		printAvd();
		ansatte.forEach(Ansatt::toString);
	}
	
	public void printAvd() {
		System.out.printf(" Avdeling %d(%s): %d ansatte, sjef = %s%n",
				avd_id, navn, ansatte.size(), sjef.getFornavn());
	}
	
	public void addAnsatt(Ansatt ansatt) {
		ansatte.add(ansatt);
		ansatt.setAvd(this);
	}
	
	public void removeAnsatt(Ansatt ansatt) {
		ansatte.remove(ansatt);
		ansatt.setAvd(null);
	}
	
	
	
	public List<Ansatt> getAnsatte(){
		return ansatte;
	}
	
	public Integer getAvd_id() {
		return avd_id;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public Ansatt getSjef() {
		return sjef;
	}

	public void setSjef(Ansatt sjef) {
		this.sjef = sjef;
	}
	
	public String toString() {
		return getNavn();
	}

}
