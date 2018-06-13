package com.rectus29.beertender.entities.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA. User: rectus_29 Date: 9 avr. 2010 Time: 15:30:53
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "config")
public class Config extends GenericEntity{

	@Column(name = "ke", nullable = false, length = 65536)
	private String key;

	@Column(name = "val", length = 65536)
	private String value;

	/**
	 * Instantiates a new Config.
	 */
	public Config() {
		// Default constructor
	}

	/**
	 * Instantiates a new Config.
	 *
	 * @param key
	 *        the key
	 * @param value
	 *        the value
	 */
	public Config(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets key.
	 *
	 * @param key
	 *        the key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets value.
	 *
	 * @param value
	 *        the value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = key != null ? key.hashCode() : 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}
}
