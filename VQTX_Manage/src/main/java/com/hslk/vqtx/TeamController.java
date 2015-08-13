/**
 * 
 */
package com.hslk.vqtx;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import codereport.entity.Score;
import codereport.entity.Station;
import codereport.entity.Team;
import codereport.entity.User;

import com.hslk.vqtx.service.ScoreService;
import com.hslk.vqtx.service.TeamService;

/**
 * @author HuyTCM.1708
 *
 */
@Controller
public class TeamController {
	/** Declare team page . */
	private final String teamPage = "/team/team_home";
	/** Initial log . */
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	/** Declare TeamService . */
	@Resource(name = "teamService")
	private TeamService teamService;
	/** Declare ScoreService . */
	@Resource(name = "scoreService")
	private ScoreService scoreService;

	@RequestMapping(value = "teamPage")
	public String team_home(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("USER");
		Team team = teamService.getUnUpdateTeam(user.getUsername());
		if (team != null) {
			return "/team/update_team";
		} else {
			Score score = scoreService.getCurrScore(teamService.getTeamCodeByUsername(user.getUsername()));
			if (score != null) {
				Station station = scoreService.getCurrStation(score.getScorePK().getStationCode());
				String hint = teamService.getHint(score.getNumOfHint(), score.getScorePK().getTeamCode(),
						score.getScorePK().getStationCode());
				model.addAttribute("HINT", hint);
				model.addAttribute("CURRSTATION", station);
				model.addAttribute("SCORE", score);
			}
			return "/team/team_home";
		}
	}

	@RequestMapping(value = "updateTeam")
	public void updateTeam(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("USER");

		String teamName = request.getParameter("teamName");
		String teamLead = request.getParameter("teamLead");
		String phone = request.getParameter("phone");
		String member1 = request.getParameter("member1");
		String member2 = request.getParameter("member2");
		String member3 = request.getParameter("member3");
		String member4 = request.getParameter("member4");
		String member5 = request.getParameter("member5");
		String member6 = request.getParameter("member6");
		String member7 = request.getParameter("member7");
		Integer teamCode = teamService.getTeamCodeByUsername(user.getUsername());
		boolean result = teamService.updateTeam(teamCode, teamName, teamLead, phone, member1, member2, member3, member4,
				member5, member6, member7);
		if (result) {
			try {
				response.sendRedirect("teamPage");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				response.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,
						"Có lỗi xảy ra, hãy thử lại! Hoặc liên hệ admin để hỗ trợ.");
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
		}
	}

	@RequestMapping(value = "teamScore")
	public String getTeamScore(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("USER");
		Integer teamCode = teamService.getTeamCodeByUsername(user.getUsername());

		List<Score> listScores = teamService.getScoreByTeamCode(teamCode);
		if (listScores != null) {
			model.addAttribute("LISTSCORES", listScores);
		} else {
			model.addAttribute("msg", "Đội bạn vẫn chưa có điểm nào");
		}
		return "/team/team_score";
	}

	@RequestMapping(value = "finalScore")
	public String finalScore(Model model) {
		// HashMap<String, Integer> result = scoreService.finallyScore();
		// model.addAttribute("FINALSCORE", result);
		return "/team/finalScore";
	}

	@RequestMapping(value = "submitCryptogramCode")
	public String submitCryptogramCode(HttpServletRequest request, HttpServletResponse response, Model model) {
		String cryptogramCode = request.getParameter("cryptogramCode");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("USER");
		Integer teamCode = teamService.getTeamCodeByUsername(user.getUsername());
		if (teamCode >= 0) {
			teamService.onSubmitCryptogramCode(cryptogramCode, teamCode);
			try {
				response.sendRedirect("teamPage");
			} catch (IOException e) {
				model.addAttribute("msg", "Có lỗi xảy ra, hãy thử lại!\n Hoặc liên hệ admin để được hỗ trợ.");
				LOGGER.error("submitCryptogramCode: ", e);
				return teamPage;
			}
		}
		return team_home(request, response, model);
	}

	@RequestMapping(value = "getHint")
	public String getHint(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("USER");
		Integer teamCode = teamService.getTeamCodeByUsername(user.getUsername());
		String numOfHint = request.getParameter("numOfHint");
		String stationCode = request.getParameter("stationCode");
		String hint = teamService.getHint(Integer.valueOf(numOfHint) + 1, teamCode, Integer.valueOf(stationCode));
		System.out.println(hint);
		try {
			response.sendRedirect("teamPage");
		} catch (IOException e) {
			model.addAttribute("msg", "Có lỗi xảy ra, hãy thử lại!\n Hoặc liên hệ admin để được hỗ trợ.");
		}
		return team_home(request, response, model);
	}

	@RequestMapping(value = "enrollStation")
	public String enrollStation(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("USER");
		Integer teamCode = teamService.getTeamCodeByUsername(user.getUsername());
		String enrollCode = request.getParameter("enrollCode");
		String stationCode = request.getParameter("stationCode");
		teamService.enrollStation(enrollCode, teamCode, Integer.valueOf(stationCode));
		try {
			response.sendRedirect("teamPage");
		} catch (IOException e) {
			model.addAttribute("msg", "Có lỗi xảy ra, hãy thử lại!\n Hoặc liên hệ admin để được hỗ trợ.");
			LOGGER.error("enrollStation: ", e);
		}
		return team_home(request, response, model);
	}

	@RequestMapping(value = "overStation")
	public String overStation(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("USER");
		Integer teamCode = teamService.getTeamCodeByUsername(user.getUsername());
		String overCode = request.getParameter("overCode");
		String stationCode = request.getParameter("stationCode");
		teamService.overStation(overCode, teamCode, Integer.valueOf(stationCode));
		try {
			response.sendRedirect("teamPage");
		} catch (IOException e) {
			model.addAttribute("msg", "Có lỗi xảy ra, hãy thử lại!\n Hoặc liên hệ admin để được hỗ trợ.");
			LOGGER.error("enrollStation: ", e);
		}
		return team_home(request, response, model);
	}
}
