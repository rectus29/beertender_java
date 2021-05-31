package com.rectus29.beertender.entities;

import com.rectus29.beertender.entities.resource.Resource;
import com.rectus29.beertender.entities.resource.impl.AvatarResource;

import java.io.Serializable;

/**
 * The interface Decorable element.
 */
public interface IDecorableElement<T extends Resource> extends Serializable {

	Long getId();

	String getFormattedName();

	T getDecoration();
}
