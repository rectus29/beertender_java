package com.rectus29.beertender.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class GenericEntity<T> implements Serializable, Comparable<T> {

	@Column
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	protected Date created = new Date();

	@Column
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	protected Date updated = new Date();

	@Column(nullable = false, columnDefinition = "varchar(128)")
	private String uniqueId = UUID.randomUUID().toString();

	public abstract Long getId();

	public abstract void setId(Long id);

	public String getUniqueId() {
		return uniqueId;
	}

	public Date getCreated() {
		return created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	public int compareTo(T object) {
		return defaultCompareTo(object);
	}

	protected int defaultCompareTo(T object) {
		return this.getId().compareTo(((GenericEntity) object).getId());
	}

}
