package com.rectus29.beertender.event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

public class EditObjectEvent implements Serializable {

    private final AjaxRequestTarget target;
    private Class targetClass;
    private IModel obj;


    public EditObjectEvent(AjaxRequestTarget target, IModel obj) {
        this.target = target;
        this.obj = obj;
    }

    public EditObjectEvent(AjaxRequestTarget target,IModel obj, Class targetClass) {
        this.obj = obj;
        this.target = target;
        this.targetClass= targetClass;
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public IModel getObj() {
        return obj;
    }

    public void setObj(IModel obj) {
        this.obj = obj;
    }
}
