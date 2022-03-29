package no.hvl.dat107;

import javax.persistence.*;

public class ProsjektDeltaker {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int deltaker_id;
	
	private float timer;
	
	@ManyToOne
	@JoinColumn (name = "ansatt_id")
	private Ansatt ansatt;
	
	@ManyToOne
	@JoinColumn (name = "prosjekt_id")
	private Prosjekt prosjekt;
	
    public ProsjektDeltaker(Ansatt ansatt, Prosjekt prosjekt, float timer) {
        this.ansatt = ansatt;
        this.prosjekt = prosjekt;
        this.timer = timer;
        
        //Hvis vi gjør dette her slipper vi å gjøre det i EAO
        ansatt.leggTilProsjektdeltagelse(this);
        prosjekt.leggTilProsjektdeltagelse(this);
    }

	public float getTimer() {
		return timer;
	}

	public void setTimer(float timer) {
		this.timer = timer;
	}
    
    
}
