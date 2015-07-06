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

import codereport.daocontroller.TeamController;
import codereport.daocontroller.UserController;
import codereport.entity.Team;
import codereport.entity.User;

/**
 * @author HuyTCM
 *
 */
public class TeamService {
    /**Declare max size of random password .*/
    private final int sizePass = 7;
    /** For logging. */
    private static Logger logger = Logger.getLogger(TeamService.class);
    /**Initial Entity Manager Factory .*/
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DaoGeneratePU");

    /**
     * Add new teams.
     * @param noTeam - number of team.
     */
    public void addTeams(long noTeam) {
        TeamController teamController = new TeamController(entityManagerFactory);
        for (int i = 0; i < noTeam; i++) {
            Team team = new Team();
            try {
                teamController.create(team);
            } catch (Exception ex) {
                logger.error("Exception occurs when add new team", ex);
            }
        }
    }
    /**
     * List all teams.
     * @return listTeams.
     */
    public List<Team> listAllTeam() {
        TeamController teamController = new TeamController(entityManagerFactory);
        return teamController.findTeamEntity();
    }
    /**
     * List team code.
     * @param stationCode - station code.
     * @return list teams code.
     */
    public List<Integer> listTeams(String stationCode) {
        TeamController teamController = new TeamController(entityManagerFactory);
        return teamController.listTeams(stationCode);
    }
    /**
     * Auto generate team account.
     */
    public void generateAcc() {
        List<Team> listTeams = listAllTeam();
        int size = listTeams.size();
        for (int i = 0; i < size; i++) {
            Team team = (Team) listTeams.get(i);
            if (team.getTeam() == null) {
                //Initial new user
                User user = new User();
                String username = "VQTX_DOI_" + team.getTeamCode();
                user.setUsername(username);
                //set random password
                String password = UUID.randomUUID().toString().substring(0, sizePass);
                user.setPassword(password);
                user.setRole(null);
                
                UserController userController = new UserController(entityManagerFactory);
                try {
                    userController.create(user);
                    //add chief into station
                    team.setTeam(user.getUsername());
                    
                    TeamController teamController = new TeamController(entityManagerFactory);
                    teamController.edit(team);
                } catch (Exception ex) {
                    logger.error("Generate Team account error", ex);
                }
            } else {
                //do nothing
            }
        }
    }
}
