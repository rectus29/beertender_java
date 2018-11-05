package com.rectus29.beertender.entities;

import com.rectus29.beertender.entities.resource.impl.AvatarResource;

/**
 * The interface Decorable element.
 */
public interface DecorableElement {

	Long getId();

	String getFormattedName();

	AvatarResource getDecoration();
}
