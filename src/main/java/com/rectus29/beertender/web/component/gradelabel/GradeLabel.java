package com.rectus29.beertender.web.component.gradelabel;

/*-----------------------------------------------------*/

/* User: Rectus_29      Date: 25/02/2015 15:30 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class GradeLabel extends Label {

    private Float factor = 1f;

    public GradeLabel(String id, Float label) {
        super(id, label);
    }

    public GradeLabel(String id, Float label, Float factor) {
        super(id, label);
        this.factor = factor;
    }

    public GradeLabel(String id, String label) {
        super(id, label);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        if (this.getDefaultModel().getObject() instanceof Number) {
            this.setDefaultModel(new Model((Float)this.getDefaultModel().getObject() *getFactor()));
            Float data = (Float) this.getDefaultModel().getObject();
            this.add(new AttributeAppender("class", (data > 0.6 * getFactor()) ? " success" : (data > 0.4 * getFactor()) ? " warning" : " danger"));
        }

    }

    @Override
    public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        if (this.getDefaultModel().getObject() instanceof Number) {
            String s = this.getDefaultModelObjectAsString() + " / " + getFactor().toString();
            replaceComponentTagBody(markupStream, openTag,s);
        }
    }

    public Float getFactor() {
        return factor;
    }

    public void setFactor(Float factor) {
        this.factor = factor;
    }
}
