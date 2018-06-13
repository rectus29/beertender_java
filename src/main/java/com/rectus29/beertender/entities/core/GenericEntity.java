package com.rectus29.beertender.entities.core;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class GenericEntity implements DomainObject, Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date created = new Date();
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated = new Date();


	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
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
}
