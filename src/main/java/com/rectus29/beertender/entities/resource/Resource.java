package com.rectus29.beertender.entities.resource;

import com.rectus29.beertender.entities.GenericEntity;

import javax.persistence.*;
import java.util.UUID;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 26/10/2018 14:52               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Entity
@Table(name = "resource")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(
		discriminatorType = DiscriminatorType.STRING,
		name = "resourceType",
		columnDefinition = "varchar(50)"
)
public abstract class Resource extends GenericEntity<Resource> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String uid = UUID.randomUUID().toString();
	private ResourceType resourceType = ResourceType.DOCUMENT;

	protected Resource() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	protected Resource setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
		return this;
	}

	protected enum ResourceType {
		IMAGE,
		AVATAR,
		DOCUMENT
	}
}
