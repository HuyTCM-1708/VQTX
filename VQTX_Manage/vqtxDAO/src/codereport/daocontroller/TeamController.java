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

import codereport.entity.Team;

/**
 * @author HuyTCM
 *
 */
public class TeamController implements Serializable {
	/** . */
	private static final long serialVersionUID = 1L;
	/** . */
	private static final Logger LOGGER = Logger.getLogger(TeamController.class);
	/** . */
	private EntityManagerFactory emf = null;

	/**
	 * Initial TeamController constructor.
	 * 
	 * @param emf
	 *            - Entity manager factory.
	 */
	public TeamController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * @return entity manager
	 */
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * Create new team.
	 * 
	 * @param team
	 *            - team.
	 * @throws Exception
	 *             /.
	 */
	public void create(Team team) throws Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(team);
			em.getTransaction().commit();
		} catch (Exception ex) {
			em.getTransaction().rollback();
			throw new Exception(ex);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**
	 * Edit team information.
	 * 
	 * @param team
	 *            - team.
	 * @throws Exception
	 *             .
	 */
	public void edit(Team team) throws Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			team = em.merge(team);
			em.getTransaction().commit();
		} catch (Exception ex) {
			em.getTransaction().rollback();
			throw new Exception(ex);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**
	 * Delete team.
	 * 
	 * @param teamCode
	 *            - team code.
	 * @throws Exception
	 *             .
	 */
	public void destroy(String teamCode) throws Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Team team;
			try {
				team = em.getReference(Team.class, teamCode);
				team.getTeamCode();
			} catch (Exception ex) {
				throw new Exception(ex);
			}
			em.remove(team);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**
	 * Find team by team code.
	 * 
	 * @param teamCode
	 *            - team code.
	 * @return team
	 */
	public Team findUser(Integer teamCode) {
		EntityManager em = getEntityManager();
		return em.find(Team.class, teamCode);
	}

	/**
	 * Get team entity.
	 * 
	 * @return list teams
	 */
	public List<Team> findTeamEntity() {
		EntityManager em = getEntityManager();
		try {
			String jqpl = "Team.findAll";
			Query query = em.createNamedQuery(jqpl);
			@SuppressWarnings("unchecked")
			List<Team> listTeam = query.getResultList();
			return listTeam;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return null;
		}
	}

	/**
	 * Get team findTeamByUsername.
	 * 
	 * @return list teams
	 */
	public Team findTeamByUsername(String username) {
		EntityManager em = getEntityManager();
		try {
			String jqpl = "Team.findTeamByUsername";
			Query query = em.createNamedQuery(jqpl);
			query.setParameter("username", username);
			Team team = (Team) query.getSingleResult();
			return team;
		} catch (Exception ex) {
			return null;
		}
	}

	public Team findTeamUnUpdateInfo(String username) {
		EntityManager em = getEntityManager();
		try {
			String jqpl = "Team.findTeamUnUpdateInfo";
			Query query = em.createNamedQuery(jqpl);
			query.setParameter("username", username);
			Team team = (Team) query.getSingleResult();
			return team;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * List team code by station code.
	 * 
	 * @param stationCode
	 *            - station code.
	 * @return list team code.
	 */
	public List<Integer> listTeams(String stationCode) {
		EntityManager em = getEntityManager();
		try {
			String sql = "SELECT a.TeamCode "
					+ "FROM (SELECT tblC.StationCode,tblC.TeamCode,sc.Score1,sc.Score2,sc.Score3 " + "FROM score sc "
					+ "RIGHT JOIN (SELECT * FROM team t CROSS JOIN station st) tblC "
					+ "ON sc.TeamCode = tblC.TeamCode AND sc.StationCode = tblC.StationCode) a"
					+ " WHERE  a.Score1 is null " + "AND a.Score2 is null " + "AND a.score3 is null"
					+ " AND a.StationCode =1";
			Query query = em.createQuery(sql);
			// query.setParameter("stationCode", stationCode);
			@SuppressWarnings("unchecked")
			List<Integer> listTeams = query.getResultList();
			return listTeams;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return null;
		}
	}
}
