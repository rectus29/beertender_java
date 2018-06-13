package com.rectus29.beertender.web.component.confirmation;

import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 16/01/13
 * Time: 18:48
 */
public abstract class ConfirmationLink<T> extends AjaxLink<T>
{
	private static final long serialVersionUID = 1L;
	private final String text;

	public ConfirmationLink( String id, String text )
	{
		super( id );
		this.text = text;
	}

	@Override
	protected void updateAjaxAttributes( AjaxRequestAttributes attributes )
	{
		super.updateAjaxAttributes( attributes );

		AjaxCallListener ajaxCallListener = new AjaxCallListener();
		ajaxCallListener.onPrecondition( "return confirm('" + text + "');" );
		attributes.getAjaxCallListeners().add( ajaxCallListener );
	}
}
