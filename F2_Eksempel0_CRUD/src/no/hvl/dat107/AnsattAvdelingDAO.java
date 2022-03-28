package no.hvl.dat107;

import javax.persistence.*;

import java.util.List;
import java.util.Map;

public class AnsattAvdelingDAO {
	
	private EntityManagerFactory emf;
	
	public AnsattAvdelingDAO() {
		emf = Persistence.createEntityManagerFactory("ansattPersistenceUnit",
				Map.of("javax.persistence.jdbc.password", "kamelløk"));
	}
	//Lag ny ansatt og håndter avdeling ordentlig
	public Ansatt lagNyAnsatt(String brukernavn, String fornavn, String etternavn, String ans_dato, String stilling, double mnd_lonn, Avdeling avdeling) {
		
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
	//Lag ny avdeling med insetting av sjef
	public Avdeling lagNyAvdeling(String navn, Ansatt sjef) {
		
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
	
	//------ ANSATT-METODER ------//
	
	//Finne ansatt med bruk av ID
	public Ansatt finnAnsattMedId(int ansatt_id) {
		EntityManager em = emf.createEntityManager();
		
		Ansatt a = null;
		try {
			a = em.find(Ansatt.class, ansatt_id);
		} finally {
			em.close();
		}
		
		return a;
	}
	
	//Finne ansatt med bruk av brukernavn
	public Ansatt finnAnsattMedBrukernavn(String brukernavn) {
		EntityManager em = emf.createEntityManager();
		
		Ansatt a = null;
		try {
			a = (Ansatt) em.createQuery("SELECT a FROM Ansatt a WHERE a.brukernavn LIKE :brukernavn")
					.setParameter("brukernavn", brukernavn).getSingleResult();
		} finally {
			em.close();
		}
		
		return a;
	}
	
	//Hent en liste med alle ansatte uavhengig av avdeling
	public List<Ansatt> finnAlleAnsatte() {
		
		EntityManager em = emf.createEntityManager();
		
		List<Ansatt> ansatte = null;
		try {
			TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a ORDER BY a.ansatt_id", Ansatt.class);
			ansatte = query.getResultList();
		} finally {
			em.close();
		}
		
		return ansatte;
	}
	
	//Oppdater lønnen til en ansatt
	public void oppdaterLonn(int ansatt_id, double nyLonn) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			Ansatt e = em.find(Ansatt.class, ansatt_id);
			e.setMnd_lonn(nyLonn);
			tx.commit();
		} catch (Throwable e){
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	//Oppdater stillingstittelen til en ansatt
	public void oppdaterStilling(int id, String nyStilling) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			Ansatt e = em.find(Ansatt.class, id);
			e.setStilling(nyStilling);
			tx.commit();
		} catch (Throwable e){
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	//Oppdater avdelingen til en ansatt
	public void oppdaterAvdeling(int id, Avdeling nyAvdeling) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Avdeling a = finnAnsattMedId(id).getAvd();
		try {
			tx.begin();
			Ansatt e = em.find(Ansatt.class, id);
			a.removeAnsatt(e);
			nyAvdeling.addAnsatt(e);
			tx.commit();
		} catch (Throwable e){
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	//Skriv ut alle ansatte i bedriften
	public void skrivUtAlleAnsatte() {
		List<Ansatt> ansatte = finnAlleAnsatte();
		Ansatt ansatt = null;
		Object[][] printTab = new String[ansatte.size()+1][];
		System.out.println("\n--- "+ "Her er alle ansatte i databasen" +" ---");
		printTab[0] = new String[] {"ANSATT ID", "BRUKERNAVN", "FORNAVN", "ETTERNAVN", "ANS.DATO", "STILLING", "MND. LØNN", "AVDELINGSNR", "AVDELING"};
		for(int i = 0; i < ansatte.size(); i++) {
			ansatt = ansatte.get(i);
			printTab[i+1] = new String[] {ansatt.getAnsatt_id().toString(), ansatt.getBrukernavn(), ansatt.getFornavn(), ansatt.getEtternavn(), ansatt.getAns_dato().toString(),
					ansatt.getStilling(), Double.toString(ansatt.getMnd_lonn()), ansatt.getAvd().getAvd_id().toString(), ansatt.getAvd().getNavn()};
		}
		for(Object[] rad : printTab) {
			System.out.format("%20s%20s%20s%20s%20s%20s%20s%20s%20s%n", rad);
		}
	}
	
	//------ AVDELING-METODER ------//
	
	//Finn avdeling med avdelings-ID
	public Avdeling finnAvdelingMedId(int avd_id) {
		EntityManager em = emf.createEntityManager();
		
		Avdeling a = null;
		
		try {
			a = em.find(Avdeling.class, avd_id);
		} finally {
			em.close();
		}
		
		return a;
	}
	
	//Skriv ut alle ansatte i en avdeling
	public void skrivUtAlleAnsatteIAvdeling(int avd_id) {
		List<Ansatt> ansatte = finnAvdelingMedId(avd_id).getAnsatte();
		Ansatt ansatt = null;
		Object[][] printTab = new String[ansatte.size()+1][];
		System.out.println("\n--- "+ "Her er alle ansatte i avdeling " + finnAvdelingMedId(avd_id).getNavn() + ": " +" ---");
		printTab[0] = new String[] {"ANSATT ID", "BRUKERNAVN", "FORNAVN", "ETTERNAVN", "ANS.DATO", "STILLING", "MND. LØNN"};
		for(int i = 0; i < ansatte.size(); i++) {
			ansatt = ansatte.get(i);
			if(ansatt.erSjef()) {
				printTab[i+1] = new String[] {"SJEF: ||  " + ansatt.getAnsatt_id().toString(), ansatt.getBrukernavn(), ansatt.getFornavn(), ansatt.getEtternavn(), ansatt.getAns_dato().toString(),
						ansatt.getStilling(), Double.toString(ansatt.getMnd_lonn()) + "  ||"};
			} else {
				printTab[i+1] = new String[] {ansatt.getAnsatt_id().toString(), ansatt.getBrukernavn(), ansatt.getFornavn(), ansatt.getEtternavn(), ansatt.getAns_dato().toString(),
						ansatt.getStilling(), Double.toString(ansatt.getMnd_lonn())};
			}
		}
		for(Object[] rad : printTab) {
			System.out.format("%20s%20s%20s%20s%20s%20s%20s%n", rad);
		}
	}
}
