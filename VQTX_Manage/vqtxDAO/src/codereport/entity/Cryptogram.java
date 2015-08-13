/**
 * 
 */
package codereport.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author HuyTCM.1708
 *
 */
@Entity
@Table(name = "CRYPTOGRAM")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Cryptogram.findAll", query = "SELECT c FROM Cryptogram c"),
		@NamedQuery(name = "Cryptogram.cryptogramByStationCode", query = "SELECT c FROM Cryptogram c WHERE c.stationCode = :stationCode")

})
public class Cryptogram implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Cryptogram Code . */
	@Id
	@Column(name = "CryptogramCode")
	private String cryptogramCode;
	@Column(name = "Hint1")
	private String hint1;
	@Column(name = "Hint2")
	private String hint2;
	@Column(name = "Hint3")
	private String hint3;
	@Column(name = "StationCode")
	private Integer stationCode;

	/**
	 * @return the cryptogramCode
	 */
	public String getCryptogramCode() {
		return cryptogramCode;
	}

	/**
	 * @param cryptogramCode
	 *            the cryptogramCode to set
	 */
	public void setCryptogramCode(String cryptogramCode) {
		this.cryptogramCode = cryptogramCode;
	}

	/**
	 * @return the hint1
	 */
	public String getHint1() {
		return hint1;
	}

	/**
	 * @param hint1
	 *            the hint1 to set
	 */
	public void setHint1(String hint1) {
		this.hint1 = hint1;
	}

	/**
	 * @return the hint2
	 */
	public String getHint2() {
		return hint2;
	}

	/**
	 * @param hint2
	 *            the hint2 to set
	 */
	public void setHint2(String hint2) {
		this.hint2 = hint2;
	}

	/**
	 * @return the hint3
	 */
	public String getHint3() {
		return hint3;
	}

	/**
	 * @param hint3
	 *            the hint3 to set
	 */
	public void setHint3(String hint3) {
		this.hint3 = hint3;
	}

	/**
	 * @return the stationCode
	 */
	public Integer getStationCode() {
		return stationCode;
	}

	/**
	 * @param stationCode
	 *            the stationCode to set
	 */
	public void setStationCode(Integer stationCode) {
		this.stationCode = stationCode;
	}
}
