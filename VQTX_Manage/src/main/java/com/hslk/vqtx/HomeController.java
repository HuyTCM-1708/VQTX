package com.hslk.vqtx;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import codereport.entity.Score;
import codereport.entity.Station;
import codereport.entity.Team;
import codereport.entity.User;

import com.hslk.vqtx.service.HomeService;
import com.hslk.vqtx.service.ScoreService;
import com.hslk.vqtx.service.StationService;
import com.hslk.vqtx.service.TeamService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	/**Declare adminPage .*/
	private final String adminPage = "adminPage";
	/**Declare chiefPage .*/
	private final String chiefPage = "chiefPage";
	/**Declare team page .*/
	private final String teamPage = "teamPage";
	/**Initial log .*/
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	/**Declare HomeService  .*/
	@Resource (name = "homeService")
	private HomeService homeService;
	/**Declare StationService  .*/
    @Resource (name = "stationService")
    private StationService stationService;
    /**Declare TeamService .*/
    @Resource (name = "teamService")
    private TeamService teamService;
    /**Declare ScoreService .*/
    @Resource (name = "scoreService")
    private ScoreService scoreService;
	/** .*/
    private final int permissionDeny = 403;
    /**Time out cookie .*/
    private final int timeCookie = 60 * 60;
	/**
	 * Simply selects the home view to render by returning its name.
	 * @param locale - locale.
	 * @param model - model.
	 * @return home
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate);
		
		return "home";
	}
	/**
	 * @param request - HTTP servlet request.
	 * @param response - HTTP servlet response
	 * @param model - model.
	 * @return Admin: AdminManagePage,
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
	    String username = request.getParameter("txtUsername");
	    String password = request.getParameter("txtPassword");
	    
	    User user = homeService.checkLogin(username, password);
	    if (user != null) {
	    	HttpSession session = request.getSession();
            session.setAttribute("USER", user);
	        try {
	            if (user.getRole()) {
	                response.sendRedirect(adminPage);
	            } else {
                    response.sendRedirect(chiefPage);
	            }
	        } catch (Exception ex) {
                try {
                	response.sendRedirect(teamPage);
				} catch (Exception e) {
					LOGGER.error("Send redirect team error: ",e);
					model.addAttribute("msg","Có lỗi xảy ra, hãy thử lại! Hoặc liên hệ admin để được hỗ trợ.");
					return "home";
				}
	        }
	    } else {
	        //do nothing
        }
	    model.addAttribute("msg", "Invalid username or password!");
	    return "home";
	}
	/**
	 * Send redirect to administration page.
	 * @param request - HTTP servlet request.
	 * @param response - HTTP servlet response.
	 * @param model - model.
	 * @return AdminManagePage.
	 */
	@RequestMapping(value = "adminPage")
	public String adminPage(HttpServletRequest request, HttpServletResponse response, Model model) {
	    HttpSession session = request.getSession();
	    User user;
	    try {
	        user = (User) session.getAttribute("USER");
	        if (user.getRole()) {
	            List<Station> listStations = stationService.listAllStation();
	            model.addAttribute("listStations", listStations);
	            
	            List<Team> listTeams = teamService.listAllTeam();
	            model.addAttribute("listTeams", listTeams);
	        } else {
	            try {
	                response.sendError(permissionDeny, "You do not have permission to login this page!");
	            } catch (IOException ex1) {
	                LOGGER.error("Send redirect error occur exception!");
	            }
            }
        } catch (Exception ex) {
            try {
                response.sendError(permissionDeny, "You do not have permission to login this page!");
            } catch (IOException ex1) {
                LOGGER.error("Send redirect error occur exception!");
            }
        }
	    return "/admin/AdminManagePage";
	}
	/**
	 * Send redirect to chief page.
	 * @param request - HTTP servlet request.
     * @param response - HTTP servlet response.
     * @param model - model.
	 * @return ChiefManagePage.
	 */
	@RequestMapping(value = "chiefPage")
    public String chiefPage(HttpServletRequest request, HttpServletResponse response, Model model) {
	    HttpSession session = request.getSession();
        User user;
        try {
            user = (User) session.getAttribute("USER");
            if (!user.getRole()) {
                List<Station> listStations = stationService.listAllStation();
                model.addAttribute("listStations", listStations);
                
                List<Team> listTeams = teamService.listAllTeam();
                model.addAttribute("listTeams", listTeams);
                
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("currStation")) {
                        Station station = scoreService.getCurrStation(Integer.valueOf(cookie.getValue())); 
                        model.addAttribute("CURRSTATION", station);
                    }
                }
            }
        } catch (Exception ex) {
            try {
                response.sendError(permissionDeny, "You do not have permission to login this page!");
            } catch (IOException ex1) {
                LOGGER.error("Send redirect error occur exception!");
            }
        }
        return "/chief/ChiefManagePage";
    }
	/**
	 * Add new stations.
	 * @param request - request.
	 * @param response - send redirect to admin page.
	 */
	@RequestMapping(value = "addStation")
    public void addStation(HttpServletRequest request, HttpServletResponse response) {
	    String txtNoStation = request.getParameter("txtNoStation");
	    try {
	        long noStation = Long.valueOf(txtNoStation);
	        stationService.addStations(noStation);
        } catch (NumberFormatException ex) {
            LOGGER.error("Add station:", ex);
        } finally {
            try {
                response.sendRedirect(adminPage);
            } catch (IOException ex) {
                LOGGER.error("Add station, send redirect: ", ex);
            }
        }
    }
	/**
     * Add new teams.
     * @param request - request.
     * @param response - send redirect to admin page.
     */
    @RequestMapping(value = "addTeam")
    public void addTeam(HttpServletRequest request, HttpServletResponse response) {
        String txtNoTeam = request.getParameter("txtNoTeam");
        try {
            long noTeam = Long.valueOf(txtNoTeam);
            teamService.addTeams(noTeam);
        } catch (NumberFormatException ex) {
            LOGGER.error("Add team:", ex);
        } finally {
            try {
                response.sendRedirect(adminPage);
            } catch (IOException ex) {
                LOGGER.error("Add team, send redirect: ", ex);
            }
        }
    }
//    /**
//     * Generate Chief Account.
//     * @param response - response.
//     */
//    @RequestMapping(value = "/generateChiefAcc")
//    public void generateChiefAcc(HttpServletResponse response) {
//        stationService.generateAcc();
//        try {
//            response.sendRedirect(adminPage);
//        } catch (IOException ex) {
//            // TODO Auto-generated catch block
//            ex.printStackTrace();
//        }
//    }
    /**
     * Generate Team Account.
     * @param response - response.
     */
    @RequestMapping(value = "/generateTeamAcc")
    public void generateTeamAcc(HttpServletResponse response) {
        teamService.generateAcc();
        try {
            response.sendRedirect(adminPage);
        } catch (IOException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }
    /**
     * Choose current station.
     * @param request - request.
     * @param response - response.
     */
    @RequestMapping(value = "chooseStation")
    public void chooseStation(HttpServletRequest request, HttpServletResponse response) {
        String slStation = request.getParameter("slStation");
        System.out.println(slStation);
        Cookie currStation = new Cookie("currStation", slStation);
        currStation.setMaxAge(timeCookie);
        response.addCookie(currStation);
        try {
            response.sendRedirect(chiefPage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Delete current station.
     * @param request - request.
     * @param response - response.
     */
    @RequestMapping(value = "deleteCurrStation")
    public void deleteCurrStation(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("currStation")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        try {
            response.sendRedirect(chiefPage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Add score.
     * @param request - request.
     * @param response - response.
     */
    @RequestMapping(value = "addScore")
    public void addScore(HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("USER");
    	
        String stationCode = request.getParameter("stationCode");
        String teamCode = request.getParameter("teamCode");
        String score1 = request.getParameter("txtScore1");
        String score2 = request.getParameter("txtScore2");
        String score3 = request.getParameter("txtScore3");
        String bonus = request.getParameter("bonus");
        String bonusNote = request.getParameter("bonusNote");
        String penalty = request.getParameter("penalty");
        String penaltyNote = request.getParameter("penaltyNote");
        String note = request.getParameter("note");
        
        if (teamCode != "0") {
        	try {
        		scoreService.addScore(stationCode, teamCode, score1, score2, score3, bonus, bonusNote, penalty, penaltyNote, note, user.getUsername());
        		try {
                    response.sendRedirect(chiefPage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
			} catch (Exception e) {
				e.printStackTrace();
				try {
	                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Đã có lỗi khi nhập điểm, hãy thử lại lần nữa."
	                		+ "\n Chú ý quy tắc nhập điểm");
	            } catch (IOException ex1) {
	                LOGGER.error("Send redirect error occur exception!");
	            }
			}
        }
    }
    /**
     * Get Score.
     * @param request - request.
     * @return score - jSon.
     */
    @RequestMapping (value = "getScore")
    @ResponseBody
    public String getScore(HttpServletRequest request) {
        String stationCode = request.getParameter("stationCode");
        String teamCode = request.getParameter("teamCode");
        Score score = scoreService.getScore(stationCode, teamCode);
        String jsonData = scoreService.parseScoreToJSON(score);
        return jsonData;
    }
}
