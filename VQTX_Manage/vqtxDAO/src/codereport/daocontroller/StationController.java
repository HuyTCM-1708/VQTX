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

import codereport.entity.Station;

/**
 * @author HuyTCM
 *
 */
public class StationController implements Serializable {
	/** . */
	private static final long serialVersionUID = 1L;
	/** . */
	private static final Logger LOGGER = Logger.getLogger(StationController.class);
	/** . */
	private EntityManagerFactory emf = null;

	/**
	 * Initial StationController constructor.
	 * 
	 * @param emf
	 *            - Entity manager factory.
	 */
	public StationController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * @return entity manager
	 */
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * Create new station.
	 * 
	 * @param station
	 * @throws Exception
	 */
	public void create(Station station) throws Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(station);
			em.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**
	 * Edit station information.
	 * 
	 * @param station
	 * @throws Exception
	 */
	public void edit(Station station) throws Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			station = em.merge(station);
			em.getTransaction().commit();
		} catch (Exception ex) {

		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**
	 * Delete station.
	 * 
	 * @param stationCode
	 *            - stationCode.
	 * @throws Exception
	 *             .
	 */
	public void destroy(String stationCode) throws Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Station station;
			try {
				station = em.getReference(Station.class, stationCode);
				station.getStationCode();
			} catch (Exception ex) {
				throw new Exception(ex);
			}
			em.remove(station);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	/**
	 * Find station by station code.
	 * 
	 * @param stationCode
	 *            - station code.
	 * @return station
	 */
	public Station findStation(Integer stationCode) {
		EntityManager em = getEntityManager();
		return em.find(Station.class, stationCode);
	}

	/**
	 * Get station entity.
	 * 
	 * @return list stations
	 */
	public List<Station> findStationEntity() {
		EntityManager em = getEntityManager();
		try {
			String jqpl = "Station.findAll";
			Query query = em.createNamedQuery(jqpl);
			@SuppressWarnings("unchecked")
			List<Station> listStation = query.getResultList();
			return listStation;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return null;
		}
	}
}
