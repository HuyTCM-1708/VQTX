/**
 * 
 */
package codereport.daocontroller;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.omg.CORBA.CTX_RESTRICT_SCOPE;

import codereport.entity.Cryptogram;

/**
 * @author HuyTCM.1708
 *
 */
public class CryptogramController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManagerFactory emf = null;

	public CryptogramController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManager getEntityManager() {
		return this.emf.createEntityManager();
	}

	public void create(Cryptogram cryptogram) throws Exception {
		EntityManager em = null;
		try {
			em = this.getEntityManager();
			em.getTransaction().begin();
			em.persist(cryptogram);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Cryptogram cryptogram) throws Exception {
		EntityManager em = null;
		try {
			em = this.getEntityManager();
			em.getTransaction().begin();
			cryptogram = em.merge(cryptogram);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public Cryptogram findCryptogram(String cryptogramCode) {
		EntityManager em = this.getEntityManager();
		return em.find(Cryptogram.class, cryptogramCode);
	}

	public Cryptogram findCryptogramByStationCode(Integer stationCode) {
		EntityManager em = null;
		try {
			em = this.getEntityManager();
			String jqpl = "Cryptogram.cryptogramByStationCode";
			Query query = em.createNamedQuery(jqpl);
			query.setParameter("stationCode", stationCode);
			Cryptogram cryptogram = (Cryptogram) query.getSingleResult();
			return cryptogram;
		} catch (Exception e) {
			return null;
		}
	}
}
