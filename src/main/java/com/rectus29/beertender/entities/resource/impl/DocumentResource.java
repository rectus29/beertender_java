package com.rectus29.beertender.entities.resource.impl;

import com.rectus29.beertender.entities.resource.Resource;

import javax.persistence.*;

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

	@Column(columnDefinition = "MEDIUMTEXT")
	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public DocumentResource setFilePath(String filePath) {
		this.filePath = filePath;
		return this;
	}
}
