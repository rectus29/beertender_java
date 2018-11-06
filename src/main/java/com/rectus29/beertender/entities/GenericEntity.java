package com.rectus29.beertender.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class GenericEntity<T> implements Serializable, Comparable<T> {

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	protected Date created = new Date();
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	protected Date updated = new Date();

	public abstract Long getId();

	public abstract void setId(Long id);

	public Date getCreated() {
		return created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public int compareTo(T object){
		return defaultCompareTo(object);
	}

	protected int defaultCompareTo(T object){
		return this.getId().compareTo(((GenericEntity)object).getId());
	}

}
