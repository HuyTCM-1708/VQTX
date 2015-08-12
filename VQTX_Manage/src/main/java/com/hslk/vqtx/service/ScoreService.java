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

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import codereport.daocontroller.ScoreController;
import codereport.daocontroller.StationController;
import codereport.daocontroller.TeamController;
import codereport.entity.Score;
import codereport.entity.ScorePK;
import codereport.entity.Station;

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
	 * 
	 * @param stationCode
	 *            - station code.
	 * @param teamCode
	 *            - team code.
	 * @param score1
	 *            - score 1.
	 * @param score2
	 *            - score 2.
	 * @param score3
	 *            - score 3.
	 * @param penalty
	 *            - penalty.
	 * @param note
	 *            - note (optional)
	 */
	public void addScore(String stationCode, String teamCode, String score1, String score2, String score3, String bonus,
			String bonusNote, String penalty, String penaltyNote, String note, String username) {
		ScoreController scoreController = new ScoreController(entityManagerFactory);
		ScorePK scorePK = new ScorePK(Integer.valueOf(stationCode), Integer.valueOf(teamCode));
		Score score = scoreController.checkExist(scorePK);
		String log = score.getLog() + " <br>User:" + username;
		Integer sumScore = score.getCryptogramScore();
		if (score != null) {
			if (score1 != "") {
				score.setScore1(Integer.valueOf(score1));
				log += " - Score1:" + score1;
				sumScore += Integer.valueOf(score1);
			}
			if (score2 != "") {
				score.setScore2(Integer.valueOf(score2));
				log += " - Score2:" + score2;
				sumScore += Integer.valueOf(score2);
			}
			if (score3 != "") {
				score.setScore3(Integer.valueOf(score3));
				log += " - Score3:" + score3;
				sumScore += Integer.valueOf(score3);
			}
			if (bonus != "") {
				int existBonus;
				try {
					existBonus = score.getBonus();
				} catch (Exception e) {
					existBonus = 0;
				}
				score.setBonus(Integer.valueOf(bonus) + existBonus);
				String existBonusNote = score.getBonusNote();
				if (existBonusNote == null) {
					existBonusNote = "";
				}
				score.setBonusNote(existBonusNote + bonus + ": " + bonusNote + "<br>");

				log += "-Bonus: " + score.getBonusNote();
				sumScore += Integer.valueOf(bonus);
			}
			if (penalty != "") {
				int existPenalty;
				try {
					existPenalty = score.getPenalty();
				} catch (Exception e) {
					existPenalty = 0;
				}
				score.setPenalty(Integer.valueOf(penalty) + existPenalty);
				String existPenaltyNote = score.getPenaltyNote();
				if (existPenaltyNote == null) {
					existPenaltyNote = "";
				}
				score.setPenaltyNote(existPenaltyNote + penalty + ": " + penaltyNote + "<br>");

				log += "-Penalty: " + score.getPenaltyNote();
				sumScore -= Integer.valueOf(penalty);
			}
			score.setNote(note);

			if (note != null)
				log += "- Note: " + note;

			// Calculate sum score.
			score.setSumScore(sumScore);

			score.setLog(log);

			try {
				scoreController.edit(score);
			} catch (Exception ex) {
				logger.error("Cannot update", ex);
			}
		}
	}

	/**
	 * Get score.
	 * 
	 * @param stationCode
	 *            - station code.
	 * @param teamCode
	 *            - team code.
	 * @return score of team in station.
	 */
	public Score getScore(String stationCode, String teamCode) {
		ScorePK scorePK = new ScorePK(Integer.valueOf(stationCode), Integer.valueOf(teamCode));
		ScoreController scoreController = new ScoreController(entityManagerFactory);
		return scoreController.getScore(scorePK);
	}

	public Score getCurrScore(Integer teamCode) {
		ScoreController scoreController = new ScoreController(entityManagerFactory);
		return scoreController.getCurrScore(teamCode);
	}

	public Station getCurrStation(Integer stationCode) {
		StationController stationController = new StationController(entityManagerFactory);
		return stationController.findStation(stationCode);
	}

	/**
	 * Parse object Score to JSON type.
	 * 
	 * @param score
	 *            - score.
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
		String bonusNote;
		try {
			bonusNote = score.getBonusNote().toString();
		} catch (Exception e) {
			bonusNote = "";
		}
		String penaltyNote;
		try {
			penaltyNote = score.getPenaltyNote().toString();
		} catch (Exception ex) {
			penaltyNote = "";
		}

		String note;
		try {
			note = score.getNote().toString();
		} catch (Exception ex) {
			note = "";
		}

		String jsonData = "{ \"score1\": \"" + score1 + "\"," + "\"score2\": \"" + score2 + "\"," + "\"score3\": \""
				+ score3 + "\"," + "\"bonusNote\": \"" + bonusNote + "\"," + "\"penaltyNote\": \"" + penaltyNote + "\","
				+ "\"note\": \"" + note + "\"}";

		return jsonData;
	}

	public HashMap<String, Integer> finallyScore() {
		ScoreController scoreController = new ScoreController(entityManagerFactory);
		TeamController teamController = new TeamController(entityManagerFactory);
		HashMap<String, Integer> finalScore = new HashMap<String, Integer>();
		List<Object[]> listResult = scoreController.getFinalScore();
		for (Object[] result : listResult) {
			Integer teamCode = Integer.valueOf((String) result[0]);
			String team = teamController.findUser(teamCode).getTeam();
			Integer score = Integer.valueOf((String) result[1]);
			finalScore.put(team, score);
		}
		return finalScore;
	}
}
