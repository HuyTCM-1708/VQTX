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

import codereport.daocontroller.StationController;
import codereport.daocontroller.UserController;
import codereport.entity.Station;
import codereport.entity.User;

/**
 * @author HuyTCM
 *
 */
public class StationService {
    /**Declare max size of random password .*/
    private final int sizePass = 7;
    /** For logging. */
    private static Logger logger = Logger.getLogger(StationService.class);
    /**Initial Entity Manager Factory .*/
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DaoGeneratePU");
    
    /**
     * Add new stations.
     * @param noStation - number of stations.
     */
    public void addStations(long noStation) {
        StationController stationController = new StationController(entityManagerFactory);
        for (int i = 0; i < noStation; i++) {
            Station station = new Station();
            try {
                stationController.create(station);
            } catch (Exception ex) {
                logger.error("Exception occurs when add new station", ex);
            }
        }
    }
    /**
     * List all stations.
     * @return listStations.
     */
    public List<Station> listAllStation() {
        StationController stationController = new StationController(entityManagerFactory);
        return stationController.findStationEntity();
    }
    /**
     * Auto generate chief account.
     */
    public void generateAcc() {
        List<Station> listStations = listAllStation();
        int size = listStations.size();
        for (int i = 0; i < size; i++) {
            Station station = (Station) listStations.get(i);
            if (station.getChief() == null) {
                //Initial new user
                User chief = new User();
                String username = "VQTX_TRAM_" + station.getStationCode();
                chief.setUsername(username);
                //set random password
                String password = UUID.randomUUID().toString().substring(0, sizePass);
                chief.setPassword(password);
                chief.setRole(false);
                
                UserController userController = new UserController(entityManagerFactory);
                try {
                    userController.create(chief);
                    //add chief into station
                    station.setChief(chief.getUsername());
                    
                    StationController stationController = new StationController(entityManagerFactory);
                    stationController.edit(station);
                } catch (Exception ex) {
                    logger.error("Generate Chief account error", ex);
                }
            } else {
                //do nothing
            }
        }
    }
}
