package com.rectus29.beertender.entities;

import com.rectus29.beertender.entities.resource.impl.AvatarResource;

import java.io.Serializable;

/**
 * The interface Decorable element.
 */
public interface IDecorableElement extends Serializable {

	Long getId();

	String getFormattedName();

	AvatarResource getDecoration();
}
