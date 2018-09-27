package com.rectus29.beertender.web.component.switchbutton;

/*-----------------------------------------------------*/
/*                                                     */
/* User: Rectus_29      Date: 01/10/13 16:59           */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

public abstract class SwitchButton extends FormComponent {

    private IModel<Boolean> activ = new Model<>(Boolean.FALSE);

    public SwitchButton(String id) {
        super(id);
        setModel(activ);
    }

	public SwitchButton(String id, IModel<Boolean> model) {
		super(id, model);
		this.activ = model;
	}

	@Override
	public FormComponent setType(Class type) {
		return super.setType(Boolean.class);
	}



	@Override
    protected void onInitialize() {
        super.onInitialize();
		setOutputMarkupId(true);
        this.add(new AjaxFormComponentUpdatingBehavior("change") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                if (activ.getObject()) {
                    onPush(target);
                }else{
                    onRelease(target);
                }
            }
        });
    }

	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(new JavaScriptContentHeaderItem("alert('hello');", null, ""));
		super.renderHead(response);
	}

	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		checkComponentTag(tag, "input");
		checkComponentTagAttribute(tag, "type", "checkbox");
		tag.put("data-toggle", "toggle");
	}

    public abstract void onPush(AjaxRequestTarget target);

    public abstract void onRelease(AjaxRequestTarget target);

    public Boolean getActiv() {
        return activ.getObject();
    }

    public void setActiv(Boolean activ) {
        this.activ.setObject(activ);
    }
}
