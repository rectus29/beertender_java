package com.rectus29.beertender.event;

import com.rectus29.beertender.entities.mail.Recipient;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

public class ReplyMailEvent implements Serializable {

	private final AjaxRequestTarget target;
	private Class targetClass;
	private IModel<Recipient> obj;

	public ReplyMailEvent(AjaxRequestTarget target) {
		this.target = target;
	}

	public ReplyMailEvent(AjaxRequestTarget target, IModel<Recipient> obj) {
		this.target = target;
		this.obj = obj;
	}

	public ReplyMailEvent(AjaxRequestTarget target, IModel<Recipient> obj, Class targetClass) {
		this.obj = obj;
		this.target = target;
		this.targetClass = targetClass;
	}

	public AjaxRequestTarget getTarget() {
		return target;
	}

	public Class getTargetClass() {
		return targetClass;
	}

	public IModel<Recipient> getObj() {
		return obj;
	}

	public void setObj(IModel<Recipient> obj) {
		this.obj = obj;
	}
}
