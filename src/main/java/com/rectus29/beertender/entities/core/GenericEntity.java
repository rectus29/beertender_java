package com.rectus29.beertender.entities.core; /**
 *
 * User: Rectus29
 * Date: 24/07/11
 * Time: 07:47
 */


import com.rectuscorp.evetool.enums.State;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class GenericEntity implements DomainObject, Serializable {

    @Id
	@GeneratedValue(generator="EveGenerator",strategy = GenerationType.AUTO)
	@GenericGenerator(name="EveGenerator", strategy="com.rectuscorp.evetool.dao.EveToolIDGenerator")
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
