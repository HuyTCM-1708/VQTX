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
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author HuyTCM
 *
 */
@Entity
@Table(name = "Score")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Score.getScore" , query = "SELECT s FROM Score s WHERE s.scorePK = :scorePK"),
    @NamedQuery(name = "Score.getCurrScore" , query = "SELECT s FROM Score s WHERE s.scorePK.teamCode = :teamCode AND s.completed = :completed"),
    @NamedQuery(name = "Score.getEnrollCode",
    query = "SELECT s FROM Score s WHERE s.scorePK = :scorePK AND s.enrollCode = :enrollCode")

})
public class Score implements Serializable {

    /** . */
    private static final long serialVersionUID = -1L;
    /**ID score .*/
    @EmbeddedId
    private ScorePK scorePK;
    
    /**Score 1 .*/
    @Column(name = "Score1")
    private Integer score1;
    /**Score 2 .*/
    @Column(name = "Score2")
    private Integer score2;
    /**Score 3 .*/
    @Column(name = "Score3")
    private Integer score3;
    /**Bonus score .*/
    @Column(name = "Bonus")
    private Integer bonus;
    /**Bonus note .*/
    @Column(name = "BonusNote")
    private String bonusNote;
    /**Penalty score .*/
    @Column(name = "Penalty")
    private Integer penalty;
    /**Penalty note .*/
    @Column(name = "PenaltyNote")
    private String penaltyNote;
    /**Note if exist, optional .*/
    @Column(name = "Note")
    private String note;
    /**Date time when submit, get from server .*/
    @Column(name = "Date")
    private Date date;
    @Column(name = "Completed")
    private boolean completed;
    @Column(name = "numOfHint")
    private Integer numOfHint;
    @Column(name = "EnrollCode")
    private String enrollCode;
    @Column(name = "Processing")
    private boolean processing;
    /**
	 * @return the processing
	 */
	public boolean isProcessing() {
		return processing;
	}
	/**
	 * @param processing the processing to set
	 */
	public void setProcessing(boolean processing) {
		this.processing = processing;
	}
	/**
	 * @return the enrollCode
	 */
	public String getEnrollCode() {
		return enrollCode;
	}
	/**
	 * @param enrollCode the enrollCode to set
	 */
	public void setEnrollCode(String enrollCode) {
		this.enrollCode = enrollCode;
	}
	/**
     * Get value of the scorePK.
     * @return the scorePK
     */
    public ScorePK getScorePK() {
        return scorePK;
    }
    /**
     * Set value for the scorePK.
     * @param scorePK the scorePK to set
     */
    public void setScorePK(ScorePK scorePK) {
        this.scorePK = scorePK;
    }
    /**
	 * @return the numOfHint
	 */
	public Integer getNumOfHint() {
		return numOfHint;
	}
	/**
	 * @param numOfHint the numOfHint to set
	 */
	public void setNumOfHint(Integer numOfHint) {
		this.numOfHint = numOfHint;
	}
	/**
     * Get value of the score1.
     * @return the score1
     */
    public Integer getScore1() {
        return score1;
    }
    /**
     * Set value for the score1.
     * @param score1 the score1 to set
     */
    public void setScore1(Integer score1) {
        this.score1 = score1;
    }
    /**
     * Get value of the score2.
     * @return the score2
     */
    public Integer getScore2() {
        return score2;
    }
    /**
     * Set value for the score2.
     * @param score2 the score2 to set
     */
    public void setScore2(Integer score2) {
        this.score2 = score2;
    }
    /**
     * Get value of the score3.
     * @return the score3
     */
    public Integer getScore3() {
        return score3;
    }
    /**
     * Set value for the score3.
     * @param score3 the score3 to set
     */
    public void setScore3(Integer score3) {
        this.score3 = score3;
    }
    /**
     * Get value of the note.
     * @return the note
     */
    public String getNote() {
        return note;
    }
    /**
     * Set value for the note.
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }
    
    /**
     * Get value of the penalty.
     * @return the penalty
     */
    public Integer getPenalty() {
        return penalty;
    }
    /**
     * Set value for the penalty.
     * @param penalty the penalty to set
     */
    public void setPenalty(Integer penalty) {
        this.penalty = penalty;
    }
    /**
	 * @return the bonus
	 */
	public Integer getBonus() {
		return bonus;
	}
	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}
	/**
	 * @return the bonusNote
	 */
	public String getBonusNote() {
		return bonusNote;
	}
	/**
	 * @param bonusNote the bonusNote to set
	 */
	public void setBonusNote(String bonusNote) {
		this.bonusNote = bonusNote;
	}
	/**
	 * @return the penaltyNote
	 */
	public String getPenaltyNote() {
		return penaltyNote;
	}
	/**
	 * @param penaltyNote the penaltyNote to set
	 */
	public void setPenaltyNote(String penaltyNote) {
		this.penaltyNote = penaltyNote;
	}
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (scorePK != null ? scorePK.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Score)) {
            return false;
        }
        Score other = (Score) object;
        if ((this.scorePK == null && other.scorePK != null) 
                || (this.scorePK != null && !this.scorePK.equals(other.scorePK))) {
            return false;
        }
        return true;
    }
	/**
	 * @return the completed
	 */
	public boolean isCompleted() {
		return completed;
	}
	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
}