package no.hvl.dat107;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class AvdelingDAO {
	
	private EntityManagerFactory emf;
	
	public AvdelingDAO() {
		emf = Persistence.createEntityManagerFactory("ansattPersistenceUnit",
				Map.of("javax.persistence.jdbc.password", "kamelløk"));
	}
	
	public void createAvdeling(Avdeling a) {
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
	
}
