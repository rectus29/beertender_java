package com.rectus29.beertender.entities;

import com.rectus29.beertender.serializers.GenericEntitySerialier;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class GenericEntity<T> implements DomainObject, Serializable, Comparable<T> {

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	protected Date created = new Date();
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	protected Date updated = new Date();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public abstract int compareTo(T object);

	protected int defaultCompareTo(T object){
		return this.getId().compareTo(((GenericEntity)object).getId());
	}

}
