package com.rectus29.beertender.web.component.bootstrapdatefield;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

import java.util.Date;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 25/04/2019                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BootstrapDateField extends FormComponent {

	public BootstrapDateField(String id) {
		super(id);
	}

	public BootstrapDateField(String id, IModel<Date> model) {
		super(id, model);
	}

	@Override
	protected void onInvalid() {
		super.onInvalid();
	}

	@Override
	protected void onValid() {
		super.onValid();
	}

	@Override
	public void convertInput() {
		super.convertInput();
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put("data-provide", "datepicker");
	}
}
