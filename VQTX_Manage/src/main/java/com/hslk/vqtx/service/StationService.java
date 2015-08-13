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
package com.hslk.vqtx.service;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import codereport.daocontroller.CryptogramController;
import codereport.daocontroller.StationController;
import codereport.daocontroller.UserController;
import codereport.entity.Cryptogram;
import codereport.entity.Station;
import codereport.entity.User;

/**
 * @author HuyTCM
 *
 */
public class StationService {
	/** For logging. */
	private static Logger logger = Logger.getLogger(StationService.class);
	/** Initial Entity Manager Factory . */
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DaoGeneratePU");

	public StationService() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Add new stations.
	 * 
	 * @param noStation
	 *            - number of stations.
	 */
	public void addStations(long noStation) {
		StationController stationController = new StationController(entityManagerFactory);
		CryptogramController cryptogramController = new CryptogramController(entityManagerFactory);
		for (int i = 0; i < noStation; i++) {
			Station station = new Station();
			// TODO: Edit later
			int stationCode = i + 1;
			station.setStationCode(stationCode);
			station.setEnrollCode("abc" + stationCode);
			station.setOverCode("xyz" + stationCode);
			station.setPlace("Place" + stationCode);
			station.setStationName("StationName" + stationCode);

			Cryptogram cryptogram = new Cryptogram();
			cryptogram.setStationCode(stationCode);
			cryptogram.setHint1("Gợi ý " + 1 + " trạm " + stationCode);
			cryptogram.setHint2("Gợi ý " + 2 + " trạm " + stationCode);
			cryptogram.setHint3("Gợi ý " + 3 + " trạm " + stationCode);
			cryptogram.setCryptogramCode("MMT" + stationCode);

			try {
				stationController.create(station);
				cryptogramController.create(cryptogram);
			} catch (Exception ex) {
				logger.error("Exception occurs when add new station", ex);
			}
		}
	}

	/**
	 * List all stations.
	 * 
	 * @return listStations.
	 */
	public List<Station> listAllStation() {
		StationController stationController = new StationController(entityManagerFactory);
		return stationController.findStationEntity();
	}
}
