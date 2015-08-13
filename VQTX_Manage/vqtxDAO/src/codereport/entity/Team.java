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
package codereport.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author HuyTCM
 *
 */
@Entity
@Table(name = "TEAM")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t"),
		@NamedQuery(name = "Team.findTeamByUsername", query = "SELECT t FROM Team t WHERE t.team = :username"),
		@NamedQuery(name = "Team.findTeamUnUpdateInfo", query = "SELECT t FROM Team t WHERE t.team = :username AND t.teamName = null")
		// ,
		// @NamedQuery(name = "Team.findTeam", query = "SELECT a.teamCode "
		// +
		// "FROM (SELECT
		// tblC.stationCode,tblC.teamCode,sc.score1,sc.score2,sc.score3 "
		// + "FROM Score sc "
		// + "RIGHT JOIN (SELECT t.teamCode,st.stationCode "
		// + "FROM Team t "
		// + "CROSS JOIN Station st) tblC "
		// + "ON sc.teamCode = tblC.teamCode AND
		// sc.stationCode=tblC.stationCode) a "
		// + "WHERE a.score1 is null AND a.score2 is null AND a.score3 is null "
		// + "AND a.stationCode = :stationCode")
})
public class Team implements Serializable {
	/** . */
	private static final long serialVersionUID = 1L;
	/** Team Code . */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "TeamCode")
	private Integer teamCode;
	/** Team's account . */
	@Column(name = "Team")
	private String team;
	/** Team leader's name . */
	@Column(name = "TeamLead")
	private String teamLead;
	/** Team leader's phone . */
	@Column(name = "Phone")
	private String phone;
	/** Member 1 . */
	@Column(name = "Member1")
	private String member1;
	/** Member 2 . */
	@Column(name = "Member2")
	private String member2;

	/**
	 * Get value of the team.
	 * 
	 * @return the team
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * Set value for the team.
	 * 
	 * @param team
	 *            the team to set
	 */
	public void setTeam(String team) {
		this.team = team;
	}

	/** Member 3 . */
	@Column(name = "Member3")
	private String member3;
	/** Member 4 . */
	@Column(name = "Member4")
	private String member4;
	/** Member 5 . */
	@Column(name = "Member5")
	private String member5;
	/** Member 6 . */
	@Column(name = "Member6")
	private String member6;
	/** Member 7 . */
	@Column(name = "Member7")
	private String member7;
	/** Member 8 . */
	@Column(name = "TeamName")
	private String teamName;
	/** Member 9 . */
	@Column(name = "Approved")
	private boolean approved;

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName
	 *            the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the approved
	 */
	public boolean isApproved() {
		return approved;
	}

	/**
	 * @param approved
	 *            the approved to set
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	/**
	 * Get value of the teamCode.
	 * 
	 * @return the teamCode
	 */
	public Integer getTeamCode() {
		return teamCode;
	}

	/**
	 * Set value for the teamCode.
	 * 
	 * @param teamCode
	 *            the teamCode to set
	 */
	public void setTeamCode(Integer teamCode) {
		this.teamCode = teamCode;
	}

	/**
	 * Get value of the teamLead.
	 * 
	 * @return the teamLead
	 */
	public String getTeamLead() {
		return teamLead;
	}

	/**
	 * Set value for the teamLead.
	 * 
	 * @param teamLead
	 *            the teamLead to set
	 */
	public void setTeamLead(String teamLead) {
		this.teamLead = teamLead;
	}

	/**
	 * Get value of the phone.
	 * 
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Set value for the phone.
	 * 
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Get value of the member1.
	 * 
	 * @return the member1
	 */
	public String getMember1() {
		return member1;
	}

	/**
	 * Set value for the member1.
	 * 
	 * @param member1
	 *            the member1 to set
	 */
	public void setMember1(String member1) {
		this.member1 = member1;
	}

	/**
	 * Get value of the member2.
	 * 
	 * @return the member2
	 */
	public String getMember2() {
		return member2;
	}

	/**
	 * Set value for the member2.
	 * 
	 * @param member2
	 *            the member2 to set
	 */
	public void setMember2(String member2) {
		this.member2 = member2;
	}

	/**
	 * Get value of the member3.
	 * 
	 * @return the member3
	 */
	public String getMember3() {
		return member3;
	}

	/**
	 * Set value for the member3.
	 * 
	 * @param member3
	 *            the member3 to set
	 */
	public void setMember3(String member3) {
		this.member3 = member3;
	}

	/**
	 * Get value of the member4.
	 * 
	 * @return the member4
	 */
	public String getMember4() {
		return member4;
	}

	/**
	 * Set value for the member4.
	 * 
	 * @param member4
	 *            the member4 to set
	 */
	public void setMember4(String member4) {
		this.member4 = member4;
	}

	/**
	 * Get value of the member5.
	 * 
	 * @return the member5
	 */
	public String getMember5() {
		return member5;
	}

	/**
	 * Set value for the member5.
	 * 
	 * @param member5
	 *            the member5 to set
	 */
	public void setMember5(String member5) {
		this.member5 = member5;
	}

	/**
	 * Get value of the member6.
	 * 
	 * @return the member6
	 */
	public String getMember6() {
		return member6;
	}

	/**
	 * Set value for the member6.
	 * 
	 * @param member6
	 *            the member6 to set
	 */
	public void setMember6(String member6) {
		this.member6 = member6;
	}

	/**
	 * Get value of the member7.
	 * 
	 * @return the member7
	 */
	public String getMember7() {
		return member7;
	}

	/**
	 * Set value for the member7.
	 * 
	 * @param member7
	 *            the member7 to set
	 */
	public void setMember7(String member7) {
		this.member7 = member7;
	}

}
