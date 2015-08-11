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
@Table(name = "STATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Station.findAll"
            , query = "SELECT s FROM Station s")
    })
public class Station implements Serializable {
    /** . */
    private static final long serialVersionUID = 1L;
    /**Station code .*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "StationCode")
    private Integer stationCode;
    /**Chief .*/
    @Column(name = "Chief")
    private String chief;
    /**
     * Get value of the chief.
     * @return the chief
     */
    public String getChief() {
        return chief;
    }
    /**
     * Set value for the chief.
     * @param chief the chief to set
     */
    public void setChief(String chief) {
        this.chief = chief;
    }
    /**Chief's name .*/
    @Column(name = "EnrollCode")
    private String enrollCode;
    /**
     * Get value of the stationCode.
     * @return the stationCode
     */
    public Integer getStationCode() {
        return stationCode;
    }
    /**
     * Set value for the stationCode.
     * @param stationCode the stationCode to set
     */
    public void setStationCode(Integer stationCode) {
        this.stationCode = stationCode;
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
}
