package com.rectus29.beertender.entities;

import com.rectus29.beertender.entities.resource.Resource;
import com.rectus29.beertender.entities.resource.impl.ImageResource;

/**
 * The interface Decorable element.
 */
public interface DecorableElement {

	Long getId();

	String getFormattedName();

	ImageResource getDecoration();
}
