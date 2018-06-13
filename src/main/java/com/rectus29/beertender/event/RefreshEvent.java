package com.rectus29.beertender.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

import java.io.Serializable;

/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 11:22 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public class RefreshEvent implements Serializable {

    private final AjaxRequestTarget target;
    private Class targetClass;

    public RefreshEvent(AjaxRequestTarget target) {
        this.target = target;
    }

    public RefreshEvent(AjaxRequestTarget target, Class targetClass) {
        this.target = target;
        this.targetClass= targetClass;
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }

    public Class getTargetClass() {
        return targetClass;
    }
}
