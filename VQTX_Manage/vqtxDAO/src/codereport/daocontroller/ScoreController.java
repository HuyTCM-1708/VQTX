/**
 * Licensed to HSLK.Info under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * HSLK.Info licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package codereport.daocontroller;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import codereport.entity.Score;
import codereport.entity.ScorePK;

/**
 * @author HuyTCM
 *
 */
public class ScoreController implements Serializable {

	/** . */
	private static final long serialVersionUID = 1L;
	/** Initial Logger . */
	private static final Logger LOGGER = Logger
			.getLogger(ScoreController.class);
	/** Declare entity manager factory . */
	private EntityManagerFactory emf = null;

	/**
	 * Constructor ScoreController.
	 * 
	 * @param emf
	 *            - entity manager factory.
	 */
	public ScoreController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * @return entity manager.
	 */
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * Create new score.
	 * 
	 * @param score
	 *            - object score.
	 * @throws Exception
	 *             - exception.
	 */
	public void create(Score score) throws Exception {
		EntityManager entityManager = null;
		try {
			entityManager = getEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(score);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			entityManager.getTransaction().rollback();
			throw new Exception(ex);
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	/**
	 * Update score.
	 * 
	 * @param score
	 *            - score.
	 * @throws Exception
	 *             - exception.
	 */
	public void edit(Score score) throws Exception {
		EntityManager entityManager = null;
		try {
			entityManager = getEntityManager();
			entityManager.getTransaction().begin();
			score = entityManager.merge(score);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	/**
	 * Check if record score is existed.
	 * 
	 * @param scorePK
	 *            - scorePK.
	 * @return score == null.
	 */
	public Score checkExist(ScorePK scorePK) {
		EntityManager entityManager = null;
		try {
			entityManager = getEntityManager();
			Score score = entityManager.find(Score.class, scorePK);
			return score;
		} catch (Exception ex) {
			LOGGER.error("Exception check exist", ex);
			return null;
		}
	}

	/**
	 * Get score by score PK.
	 * 
	 * @param scorePK
	 *            - scorePK.
	 * @return score.
	 */
	public Score getScore(ScorePK scorePK) {
		EntityManager entityManager = null;
		try {
			entityManager = getEntityManager();
			String jqpl = "Score.getScore";
			Query query = entityManager.createNamedQuery(jqpl);
			query.setParameter("scorePK", scorePK);
			return (Score) query.getSingleResult();
		} catch (Exception ex) {
			return null;
		}
	}

	public Score getCurrScore(Integer teamCode) {
		EntityManager entityManager = null;
		try {
			entityManager = getEntityManager();
			String jqpl = "Score.getCurrScore";
			Query query = entityManager.createNamedQuery(jqpl);
			query.setParameter("teamCode", teamCode);
			query.setParameter("completed", false);
			return (Score) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Score getScoreWithEnrollCode(ScorePK scorePK,String enrollCode) {
		EntityManager entityManager = null;
		try {
			entityManager = getEntityManager();
			String jqpl = "Score.getEnrollCode";
			Query query = entityManager.createNamedQuery(jqpl);
			query.setParameter("scorePK", scorePK);
			query.setParameter("enrollCode", enrollCode);
			return (Score) query.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("getScoreWithEnrollCode:", e);
			return null;
		}
	}
	public Score getScoreWithOverCode(ScorePK scorePK,String overCode) {
		EntityManager entityManager = null;
		try {
			entityManager = getEntityManager();
			String jqpl = "Score.getOverCode";
			Query query = entityManager.createNamedQuery(jqpl);
			query.setParameter("scorePK", scorePK);
			query.setParameter("overCode", overCode);
			return (Score) query.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("getScoreWithOverCode:", e);
			return null;
		}
	}
	public List<Score> getAllScoreOfTeam(Integer teamCode) {
		EntityManager entityManager = null;
		try {
			entityManager = getEntityManager();
			String jqpl = "Score.getCurrScore";
			Query query = entityManager.createNamedQuery(jqpl);
			query.setParameter("teamCode", teamCode);
			query.setParameter("completed", true);
			@SuppressWarnings("unchecked")
			List<Score> listScores = query.getResultList();
			return listScores;
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getFinalScore() {
		EntityManager entityManager = null;
		try {
			entityManager = getEntityManager();
			String sql = "SELECT s.scorePK.teamCode, SUM(s.sumScore)"
						+"FROM Score s "
						+"GROUP BY s.scorePK.teamCode"
						+"ORDER BY SUM(s.sumScore) ASC";
			Query query = entityManager.createQuery(sql);
			List<Object[]> result = query.getResultList();
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
