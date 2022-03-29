package no.hvl.dat107;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

public class Prosjekt {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int prosjekt_id;
	
	private String navn;
	private String beskrivelse;
	
	@OneToMany(mappedBy = "deltaker_id")
	private List<ProsjektDeltaker> prosjektDeltaker = new ArrayList<>();
	
	public Prosjekt (String navn, String beskrivelse) {
		this.navn = navn;		this.beskrivelse = beskrivelse;
	}
	
	public void leggTilProsjektdeltagelse(ProsjektDeltaker deltaker) {
        prosjektDeltaker.add(deltaker);
    }

    public void fjernProsjektdeltagelse(ProsjektDeltaker deltaker) {
        prosjektDeltaker.remove(deltaker);
    }

	public int getProsjekt_id() {
		return prosjekt_id;
	}

	public String getNavn() {
		return navn;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}

	public List<ProsjektDeltaker> getProsjektDeltaker() {
		return prosjektDeltaker;
	}
	
}
