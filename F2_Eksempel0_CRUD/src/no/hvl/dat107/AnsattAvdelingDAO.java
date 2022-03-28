package no.hvl.dat107;

import javax.persistence.*;
import java.util.Map;

public class AnsattAvdelingDAO {
	
	private EntityManagerFactory emf;
	
	public AnsattAvdelingDAO() {
		emf = Persistence.createEntityManagerFactory("ansattPersistenceUnit",
				Map.of("javax.persistence.jdbc.password", "kamelløk"));
	}
	
	public Ansatt createNewAnsatt(String brukernavn, String fornavn, String etternavn, String ans_dato, String stilling, double mnd_lonn, Avdeling avdeling) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Ansatt a = null;
		
		try {
			tx.begin();
			a = new Ansatt(brukernavn, fornavn, etternavn, ans_dato, stilling, mnd_lonn, avdeling);
			em.persist(a);
			avdeling.addAnsatt(a);
			em.merge(avdeling);
			
			tx.commit();
			
		} catch (Throwable e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		
		return a;
	}
	
	public Avdeling createNewAvdeling(String navn, Ansatt sjef) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Avdeling a = null;
		
		try {
			tx.begin();
			//antar at sjefen allerede er ansatt i en annen avdeling
			Avdeling oldAvd = sjef.getAvd();
			oldAvd.removeAnsatt(sjef);
			em.merge(oldAvd);
			
			a = new Avdeling();
			a.setNavn(navn);
			a.setSjef(sjef);
			
			em.persist(a);
			
			sjef.setAvd(a);
			em.merge(sjef);
			
			tx.commit();
		} catch (Throwable e){
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		
		return a;
	}
}
