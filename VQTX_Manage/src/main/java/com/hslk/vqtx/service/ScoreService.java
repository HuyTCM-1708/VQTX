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

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import codereport.daocontroller.ScoreController;
import codereport.entity.Score;
import codereport.entity.ScorePK;

/**
 * @author HuyTCM
 *
 */
public class ScoreService {
    /** For logging. */
    private static Logger logger = Logger.getLogger(ScoreService.class);
    /** Initial Entity Manager Factory . */
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DaoGeneratePU");
    
    /**
     * Add score to a team in the station. If exist, update it.
     * @param stationCode - station code.
     * @param teamCode - team code.
     * @param score1 - score 1.
     * @param score2 - score 2.
     * @param score3 - score 3.
     * @param penalty - penalty.
     * @param note - note (optional)
     */
    public void addScore(String stationCode, String teamCode, String score1, String score2, String score3,
            String penalty, String note) {
        ScoreController scoreController = new ScoreController(entityManagerFactory);
        ScorePK scorePK = new ScorePK(Integer.valueOf(stationCode), Integer.valueOf(teamCode));
        Score score = new Score();
        score.setScorePK(scorePK);
        if (score1 != "") {
            score.setScore1(Integer.valueOf(score1));
        }
        if (score2 != "") {
            score.setScore2(Integer.valueOf(score2));
        }
        if (score3 != "") {
            score.setScore3(Integer.valueOf(score3));
        }
        if (penalty != "") {
            score.setPenalty(Integer.valueOf(penalty));
        }
        score.setNote(note);
        if (scoreController.checkExist(scorePK)) {
            try {
                scoreController.create(score);
            } catch (Exception ex) {
                logger.error("Can not add score!", ex);
            }
        } else {
            try {
                scoreController.edit(score);
            } catch (Exception ex) {
                logger.error("Cannot update", ex);
            }
        }
    }
    /**
     * Get score.
     * @param stationCode - station code.
     * @param teamCode - team code.
     * @return score of team in station.
     */
    public Score getScore(String stationCode, String teamCode) {
        ScorePK scorePK = new ScorePK(Integer.valueOf(stationCode), Integer.valueOf(teamCode));
        ScoreController scoreController = new ScoreController(entityManagerFactory);
        return scoreController.getScore(scorePK);
    }
    /**
     * Parse object Score to JSON type.
     * @param score - score.
     * @return jSON as String.
     */
    public String parseScoreToJSON(Score score) {
        String score1;
        try {
            score1 = score.getScore1().toString();
        } catch (Exception ex) {
            score1 = "";
        }
        
        String score2;
        try {
            score2 = score.getScore2().toString();
        } catch (Exception ex) {
            score2 = "";
        }
        
        String score3;
        try {
            score3 = score.getScore3().toString();
        } catch (Exception ex) {
            score3 = "";
        }
        
        String penalty;
        try {
            penalty = score.getPenalty().toString();
        } catch (Exception ex) {
            penalty = "";
        }
        
        String note;
        try {
            note = score.getNote().toString();
        } catch (Exception ex) {
            note = "";
        }

        String jsonData = "{ \"score1\": \"" + score1 + "\","
                + "\"score2\": \"" + score2 + "\","
                        + "\"score3\": \"" + score3 + "\","
                                + "\"penalty\": \"" + penalty + "\","
                                        + "\"note\": \"" + note + "\"}";
        
        return jsonData;
    }
}
