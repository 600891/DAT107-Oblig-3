package no.hvl.dat107;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class AnsattDAO {
	
	private EntityManagerFactory emf;
	
	public AnsattDAO() {
		emf = Persistence.createEntityManagerFactory("ansattPersistenceUnit", 
				Map.of("javax.persistence.jdbc.password", "kamelløk"));
	}
	
	public void createAnsatt(Ansatt a) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			em.persist(a);
			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
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
	
	public void updateLonn(int ansatt_id, double nyLonn) {
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
	
	public void updateStilling(int id, String nyStilling) {
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
	
	public void skrivUtAlleAnsatte() {
		List<Ansatt> ansatte = finnAlleAnsatte();
		System.out.println("\n--- "+ "Her er alle ansatte i databasen" +" ---");
		ansatte.forEach(System.out::println);		
	}
}
