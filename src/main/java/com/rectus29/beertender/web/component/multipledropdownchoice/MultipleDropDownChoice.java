package com.rectus29.beertender.web.component.multipledropdownchoice;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.IModel;

import java.util.List;

/*-----------------------------------------------------*/
/*                Date: 02/10/2018 12:09               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class MultipleDropDownChoice extends ListMultipleChoice {

	public MultipleDropDownChoice(String id) {
		super(id);
	}

	public MultipleDropDownChoice(String id, List choices) {
		super(id, choices);
	}

	public MultipleDropDownChoice(String id, List choices, IChoiceRenderer renderer) {
		super(id, choices, renderer);
	}

	public MultipleDropDownChoice(String id, IModel model, List choices) {
		super(id, model, choices);
	}

	public MultipleDropDownChoice(String id, IModel model, List choices, IChoiceRenderer renderer) {
		super(id, model, choices, renderer);
	}

	public MultipleDropDownChoice(String id, IModel choices) {
		super(id, choices);
	}

	public MultipleDropDownChoice(String id, IModel model, IModel choices) {
		super(id, model, choices);
	}

	public MultipleDropDownChoice(String id, IModel choices, IChoiceRenderer renderer) {
		super(id, choices, renderer);
	}

	public MultipleDropDownChoice(String id, IModel model, IModel choices, IChoiceRenderer renderer) {
		super(id, model, choices, renderer);
	}

	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		this.add(AttributeModifier.append("multiple", "multiple"));
		this.add(AttributeModifier.append("class", "selectpicker"));
		this.add(AttributeModifier.append("data-live-search", isDataLiveSearch()));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(new OnDomReadyHeaderItem("$('#" + this.getMarkupId() + "').selectpicker();"));
		super.renderHead(response);
	}

	private boolean isDataLiveSearch(){
		return false;
	}
}
