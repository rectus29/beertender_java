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
public class AvatarResource extends ImageResource {

	public AvatarResource() {
		this.setResourceType(Resource.ResourceType.AVATAR);
	}

	public AvatarResource(byte[] imageBytes) {
		this();
		this.setImageBytes(imageBytes);
	}

	@Override
	public AvatarResource setImageBytes(byte[] fileImage) {
		return (AvatarResource) super.setImageBytes(fileImage);
	}
}
