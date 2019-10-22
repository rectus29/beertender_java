package com.rectus29.beertender.entities.resource.impl;

import com.rectus29.beertender.entities.resource.Resource;
import org.hibernate.annotations.Type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 26/10/2018 15:09               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


@Entity(name = "DocumentResource")
@Table(name = "documentresource", indexes = {
		@Index(name = "uniqueId", columnList = "uniqueId", unique = true)}
)
@DiscriminatorValue("DOCUMENT")
public class DocumentResource extends Resource {

	public DocumentResource() {
		this.setResourceType(ResourceType.DOCUMENT);
	}

	@Type(type="text")
	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public DocumentResource setFilePath(String filePath) {
		this.filePath = filePath;
		return this;
	}
}
