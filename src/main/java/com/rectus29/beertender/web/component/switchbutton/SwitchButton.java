package com.rectus29.beertender.web.component.switchbutton;

/*-----------------------------------------------------*/
/*                                                     */
/* User: Rectus_29      Date: 01/10/13 16:59           */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.StringValue;

public abstract class SwitchButton extends FormComponent {

	private IModel<String> activ = new Model<>("off");
	private AbstractDefaultAjaxBehavior adab;

	public SwitchButton(String id) {
		super(id);
		setModel(activ);
	}

	public SwitchButton(String id, IModel<String> model) {
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
		this.adab =new AbstractDefaultAjaxBehavior() {
			@Override
			protected void respond(AjaxRequestTarget target) {
				StringValue param = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("state");
				if ("true".equals(param.toString())) {
					activ.setObject("on");
					onPush(target);
				}else{
					activ.setObject("off");
					onRelease(target);
				}
			}
		};
        this.add(adab);
    }

	@Override
	public void renderHead(IHeaderResponse response) {
		StringBuilder sb = new StringBuilder();
		sb.append(" $('#" + this.getMarkupId() + "').bootstrapToggle();");
		sb.append(" $(document).on('change', '#" + this.getMarkupId() + "', function(){" +
				"Wicket.Ajax.get({u:'"+this.adab.getCallbackUrl()+"&state=' + $(" + this.getMarkupId() + ").prop('checked')})" +
				"});");
		response.render(new OnDomReadyHeaderItem(sb.toString()));
		super.renderHead(response);
	}


	@Override
	protected void onAfterRender() {
		super.onAfterRender();
	}

	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		checkComponentTag(tag, "input");
		checkComponentTagAttribute(tag, "type", "checkbox");
		tag.put("data-toggle", "toggle");
		tag.put("data-on", getOnLabel());
		tag.put("data-off", getOffLabel());
		tag.put("data-onstyle", getOnStyle());
		tag.put("data-offstyle", getOffStyle());
	}

	protected String getOnStyle() {
		return "primary";
	}

	protected String getOffStyle() {
		return "light";
	}

	protected String getOnLabel() {
		return "On";
	}

	protected String getOffLabel() {
		return "Off";
	}

	public abstract void onPush(AjaxRequestTarget target);

	public abstract void onRelease(AjaxRequestTarget target);
}
