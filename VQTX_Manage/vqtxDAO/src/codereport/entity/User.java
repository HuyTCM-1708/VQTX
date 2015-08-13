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
import javax.persistence.Entity;
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
@Table(name = "USER")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.checkLogin", query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password") })
public class User implements Serializable {
	/** . */
	private static final long serialVersionUID = 1L;
	/** User name . */
	@Id
	@Basic(optional = false)
	@Column(name = "Username")
	private String username;
	/** Password . */
	@Basic(optional = false)
	@Column(name = "Password")
	private String password;
	/**
	 * Role of user: -True: administration. -False: chief -null: normal user .
	 */
	@Basic(optional = false)
	@Column(name = "Role")
	private Boolean role;

	/**
	 * Get value of the username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set value for the username.
	 * 
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get value of the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set value for the password.
	 * 
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get value of the role.
	 * 
	 * @return the role
	 */
	public Boolean getRole() {
		return role;
	}

	/**
	 * Set value for the role.
	 * 
	 * @param role
	 *            the role to set
	 */
	public void setRole(Boolean role) {
		this.role = role;
	}

}
