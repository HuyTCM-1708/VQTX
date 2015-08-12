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

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import codereport.daocontroller.CryptogramController;
import codereport.daocontroller.ScoreController;
import codereport.daocontroller.StationController;
import codereport.daocontroller.TeamController;
import codereport.daocontroller.UserController;
import codereport.entity.Cryptogram;
import codereport.entity.Score;
import codereport.entity.ScorePK;
import codereport.entity.Station;
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
                String username;
                if(i < 10) {
                	username = "VQTX0" + team.getTeamCode();
                } else {
					username = "VQTX" + team.getTeamCode();
				}
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
    public boolean updateTeam(Integer teamCode, String teamName, String teamLead, String phone, String member1,
    		String member2, String member3, String member4, String member5, String member6, String member7) {
    	TeamController teamController = new TeamController(entityManagerFactory);
    	Team team = teamController.findUser(teamCode);
    	ScorePK scorePK = new ScorePK(0, Integer.valueOf(teamCode));
    	ScoreController scoreController = new ScoreController(entityManagerFactory);
    	if (team != null) {
			team.setTeamName(teamName);
			team.setTeamLead(teamLead);
			team.setPhone(phone);
			team.setMember1(member1);
			team.setMember2(member2);
			team.setMember3(member3);
			team.setMember4(member4);
			team.setMember5(member5);
			team.setMember6(member6);
			team.setMember7(member7);
			try {
				teamController.edit(team);
				Score score = scoreController.checkExist(scorePK);
				if (score == null) {
					score = new Score();
					score.setScorePK(scorePK);
					score.setCompleted(true);
					score.setProcessing(true);
					score.setNumOfHint(3);
					Calendar calendar = Calendar.getInstance();
					score.setDate(new Date(calendar.getTime().getTime()));
					
					StationController stationController = new StationController(entityManagerFactory);
					Station station = stationController.findStation(score.getScorePK().getStationCode());
					score.setEnrollCode(station.getEnrollCode());
					
					score.setLog("Initial at: " + new Date(calendar.getTime().getTime()));
				}
				scoreController.create(score);
				return true;
			} catch (Exception e) {
				logger.error("Update team:",e);
				return false;
			}
		}
    	return false;
    }
    /**
     * Get team code
     * @param username
     * @return teamCode or -1 if team is not exist.
     */
    public Integer getTeamCodeByUsername(String username) {
    	TeamController teamController = new TeamController(entityManagerFactory);
    	Team team = teamController.findTeamByUsername(username);
    	if(team != null) {
    		return team.getTeamCode();
    	}
    	return -1;
    }
    public Team getUnUpdateTeam(String username) {
		TeamController teamController = new TeamController(entityManagerFactory);
    	return (Team) teamController.findTeamUnUpdateInfo(username);
	}
    
    public List<Score> getScoreByTeamCode(Integer teamCode) {
    	ScoreController scoreController = new ScoreController(entityManagerFactory);
    	return scoreController.getAllScoreOfTeam(teamCode);
    }
    
    public Boolean onSubmitCryptogramCode(String cryptogramCode, Integer teamCode) {
    	CryptogramController cryptogramController = new CryptogramController(entityManagerFactory);
    	Cryptogram cryptogram = cryptogramController.findCryptogram(cryptogramCode);
    	if(cryptogram != null) {
    		ScorePK scorePK = new ScorePK(cryptogram.getStationCode(), teamCode);
    		ScoreController scoreController = new ScoreController(entityManagerFactory);
    		Score score = scoreController.checkExist(scorePK);
    		if (score == null) {
				score = new Score();
				score.setScorePK(scorePK);
				score.setCompleted(false);
				score.setNumOfHint(0);
				Calendar calendar = Calendar.getInstance();
				score.setDate(new Date(calendar.getTime().getTime()));
				
				StationController stationController = new StationController(entityManagerFactory);
				Station station = stationController.findStation(score.getScorePK().getStationCode());
				score.setEnrollCode(station.getEnrollCode());
				
				score.setLog("Initial at: " + new Date(calendar.getTime().getTime()));
				try {
					scoreController.create(score);
					return true;
				} catch (Exception e) {
					logger.error("onSubmitCryptogramCode", e);
				}
			}
    	}
    	//end if, return false onSubmitCryptogramCode
		return false;
    }
    
    public String getHint(Integer numOfHint, Integer teamCode, Integer stationCode) {
    	CryptogramController cryptogramController = new CryptogramController(entityManagerFactory);
    	ScoreController scoreController = new ScoreController(entityManagerFactory);
    	ScorePK scorePK = new ScorePK(Integer.valueOf(stationCode), teamCode);
    	Score score = scoreController.getScore(scorePK);
    	Calendar calendar = Calendar.getInstance();
    	Date date = new Date(calendar.getTime().getTime());
    	long diff = (date.getTime() - score.getDate().getTime()) / (60*1000);
    	Cryptogram cryptogram = cryptogramController.findCryptogramByStationCode(stationCode);
    	String hints = "";
    	if (cryptogram != null && score != null) {
    		if (diff >= 20) {
    			if(numOfHint > 0) hints = "Gợi ý 1: " + cryptogram.getHint1().toString();
    			if(numOfHint > 1) hints = hints + "<br>Gợi ý 2: " + cryptogram.getHint2().toString();
    			if(numOfHint > 2) hints = hints + "<br>Gợi ý 3: " + cryptogram.getHint3().toString();
    			score.setNumOfHint(numOfHint);
			} else {
				hints = "Gợi ý chỉ được kích hoạt sau 20' kể từ lúc nhập mã mật thư";
			}
		}
    	try {
			scoreController.edit(score);
		} catch (Exception e) {
			logger.error(e);
		}
    	return hints;
    }
    
    public Boolean enrollStation(String enrollCode, Integer teamCode, Integer stationCode) {
    	ScorePK scorePK = new ScorePK(stationCode, teamCode);
    	ScoreController scoreController = new ScoreController(entityManagerFactory);
    	Score score = scoreController.getScoreWithEnrollCode(scorePK, enrollCode);
    	if (score != null) {
    		score.setCryptogramScore(50 - score.getNumOfHint()*10);
			score.setProcessing(true);
			try {
				scoreController.edit(score);
				return true;
			} catch (Exception e) {
				logger.error("enrollStation:",e);
				return false;
			}
		}
    	return false;
    }
    public Boolean overStation(String overCode, Integer teamCode, Integer stationCode) {
    	ScorePK scorePK = new ScorePK(stationCode, teamCode);
    	ScoreController scoreController = new ScoreController(entityManagerFactory);
    	Score score = scoreController.getScoreWithOverCode(scorePK, overCode);
    	if (score != null) {
			score.setProcessing(false);
			score.setCompleted(true);
			try {
				scoreController.edit(score);
				return true;
			} catch (Exception e) {
				logger.error("Over Station:",e);
				return false;
			}
		}
    	return false;
    }
}
