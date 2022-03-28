package no.hvl.dat107;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class Databasehjelper {

	private EntityManagerFactory emf;
	
	public Databasehjelper() {
		emf = Persistence.createEntityManagerFactory("ansattPersistenceUnit", 
				Map.of("javax.persistence.jdbc.password", "kamell�k"));
	}
	
//	//Create - Hvordan opprette ny(e) rad(er) i databasen
//	public void createPerson(Ansatt a) {
//		
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction tx = em.getTransaction();
//
//		try {
//			tx.begin();
//			em.persist(a); //Oppretter en ny rad i databasen
//			tx.commit();
//		
//		} catch (Throwable e) {
//			e.printStackTrace();
//			tx.rollback();
//		} finally {
//			em.close();
//		}
//	}
	
	//Read1 - Hvordan hente ut data fra databasen
	public Ansatt retrieveAnsatt(int ansatt_id) {

		EntityManager em = emf.createEntityManager();

		Ansatt a = null;
		try {
			a = em.find(Ansatt.class, ansatt_id); //Henter ut på primærnøkkel
		} finally {
			em.close();
		}
		
		return a;
	}
	
//	//Read2 - Hvordan hente ut data fra databasen
//	public List<Ansatt> retrieveAlleAnsatte() {
//
//		EntityManager em = emf.createEntityManager();
//
//		List<Ansatt> ansatte = null;
//		try {
//			TypedQuery<Ansatt> query = em.createQuery(
//			        "SELECT a FROM ansatt as a order by a.ansatt_id", Ansatt.class);
//			ansatte = query.getResultList(); //Henter ut basert på spørring
//		} finally {
//			em.close();
//		}
//		return ansatte;
//	}
	
//	//Read3 - Hvordan hente ut data fra databasen
//	public List<Ansatt> retrieveAlleAnsatteNQ() {
//		/* Tester ut NamedQuery */
//
//		EntityManager em = emf.createEntityManager();
//
//		List<Ansatt> ansatte = null;
//		try {
//			TypedQuery<Ansatt> query = em.createNamedQuery(
//					"hentAlleAnsatte", Ansatt.class);
//			ansatte = query.getResultList(); //Henter ut basert på spørring
//		} finally {
//			em.close();
//		}
//		return ansatte;
//	}
	
//	//Update - Hvordan oppdatere data i databasen
//	public void updateAnsatt(int ansatt_id, String nyttNavn) {
//
//		EntityManager em = emf.createEntityManager();
//
//		try {
//			em.getTransaction().begin();
//			
//			Person p = em.find(Person.class, id); //Finne rad som skal oppdateres
//			p.setNavn(nyttNavn); //Oppdatere managed oject p => sync med database
//		
//			em.getTransaction().commit();
//			
//		} catch (Throwable e) {
//			e.printStackTrace();
//			em.getTransaction().rollback();
//		} finally {
//			em.close();
//		}
//	}
	
//	//Delete - Hvordan slette rad(er) fra databasen
//	public void deletePerson(int id) {
//		
//		EntityManager em = emf.createEntityManager();
//
//		try {
//			em.getTransaction().begin();
//			
//			Person p = em.find(Person.class, id); //Finne rad som skal slettes
//			em.remove(p); //Slette rad som tilsvarer managed oject p
//			
//			em.getTransaction().commit();
//		
//		} catch (Throwable e) {
//			e.printStackTrace();
//			em.getTransaction().rollback();
//		} finally {
//			em.close();
//		}
//	}

	
}
