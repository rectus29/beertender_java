package com.rectus29.beertender.entities.resource.impl;

import com.rectus29.beertender.entities.resource.Resource;

import javax.persistence.*;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 26/10/2018 14:54               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

@Entity(name = "AvatarResource")
@Table(name = "avatarresource")
@DiscriminatorValue("AVATAR")
public class AvatarResource extends Resource {

	@Lob
	@Column(columnDefinition = "BLOB")
	private byte[] imageBytes;


	public AvatarResource() {
		this.setResourceType(Resource.ResourceType.AVATAR);
	}

	public AvatarResource(byte[] imageBytes) {
		this();
		this.imageBytes = imageBytes;
	}

	public byte[] getImageBytes() {
		return imageBytes;
	}

	public AvatarResource setImageBytes(byte[] fileImage) {
		this.imageBytes = fileImage;
		return this;
	}

}
