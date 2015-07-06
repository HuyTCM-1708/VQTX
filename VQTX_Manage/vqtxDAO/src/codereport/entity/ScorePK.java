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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author HuyTCM
 *
 */
@Embeddable
public class ScorePK implements Serializable {

    /** . */
    private static final long serialVersionUID = 1L;
    /** Station code . */
    @Basic(optional = false)
    @Column(name = "StationCode")
    private Integer stationCode;

    /** Team code . */
    @Basic(optional = false)
    @Column(name = "TeamCode")
    private Integer teamCode;

    /**
     * Constructor with no parameter.
     */
    public ScorePK() {
    }
    /**
     * Constructor with two parameter.
     * @param stationCode - station code.
     * @param teamCode - team code.
     */
    public ScorePK(Integer stationCode, Integer teamCode) {
        this.stationCode = stationCode;
        this.teamCode = teamCode;
    }

    /**
     * Get value of the stationCode.
     * 
     * @return the stationCode
     */
    public Integer getStationCode() {
        return stationCode;
    }

    /**
     * Set value for the stationCode.
     * 
     * @param stationCode
     *            the stationCode to set
     */
    public void setStationCode(Integer stationCode) {
        this.stationCode = stationCode;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stationCode != null ? stationCode.hashCode() : 0);
        hash += (teamCode != null ? teamCode.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScorePK)) {
            return false;
        }
        ScorePK other = (ScorePK) object;
        if ((this.stationCode == null && other.stationCode != null)
                || (this.stationCode != null && !this.stationCode.equals(other.stationCode))) {
            return false;
        }
        if ((this.teamCode == null && other.teamCode != null)
                || (this.teamCode != null && !this.teamCode.equals(other.teamCode))) {
            return false;
        }
        return true;
    }
}
