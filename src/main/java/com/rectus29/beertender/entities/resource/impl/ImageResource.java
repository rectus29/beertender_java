package com.rectus29.beertender.entities.resource.impl;

import com.rectus29.beertender.entities.resource.Resource;

import javax.persistence.*;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 26/10/2018 14:54               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

@Entity(name = "ImageResource")
@Table(name = "imageresource")
@DiscriminatorValue("IMAGE")
public class ImageResource extends Resource {

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] imageBytes;

	public ImageResource() {
		this.setResourceType(ResourceType.IMAGE);
	}

	public ImageResource(byte[] imageBytes) {
		this();
		this.imageBytes = imageBytes;
	}

	public byte[] getImageBytes() {
		return imageBytes;
	}

	public ImageResource setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
		return this;
	}
}
