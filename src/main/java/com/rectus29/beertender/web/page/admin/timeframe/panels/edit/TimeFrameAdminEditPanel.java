package com.rectus29.beertender.web.page.admin.timeframe.panels.edit;

import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceTimeFrame;
import com.rectus29.beertender.web.component.bootstrapdatefield.BootstrapDateField;
import com.rectus29.beertender.web.component.bootstrapfeedbackpanel.BootstrapFeedbackPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Arrays;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 13/07/2018 16:56               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public abstract class TimeFrameAdminEditPanel extends Panel {

	private static final Logger log = LogManager.getLogger(TimeFrameAdminEditPanel.class);

	private IModel<TimeFrame> timeFrameIModel;
	private Form form;
	private BootstrapFeedbackPanel feed;

	@SpringBean(name = "serviceTimeFrame")
	private IserviceTimeFrame serviceTimeFrame;

	public TimeFrameAdminEditPanel(String id) {
		super(id);
		this.timeFrameIModel = new Model<TimeFrame>(new TimeFrame());
	}

	public TimeFrameAdminEditPanel(String id, IModel<TimeFrame> model) {
		super(id, model);
		this.timeFrameIModel = model;

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add((form = new Form("form") {
			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new TextField<>("name", new PropertyModel<String>(timeFrameIModel, "name")).setRequired(true));
				add(new BootstrapDateField("startdate", new PropertyModel<>(timeFrameIModel, "startDate")).setRequired(true));
				add(new BootstrapDateField("enddate", new PropertyModel<>(timeFrameIModel, "endDate")).setRequired(true));
				add(new DropDownChoice<>("state",
						new PropertyModel<>(timeFrameIModel, "state"),
						Arrays.asList(State.values()),
						new EnumChoiceRenderer<>()
				).setRequired(true));
				add(new AjaxSubmitLink("submit") {
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form form) {
						if (timeFrameIModel.getObject().getStartDate().after(timeFrameIModel.getObject().getEndDate())) {
							error("les Dates ne sont pas correct");
						} else {
							serviceTimeFrame.save(timeFrameIModel.getObject());
							success(new ResourceModel("success").getObject());
							TimeFrameAdminEditPanel.this.onSubmit(target);
						}
					}

					@Override
					public void onError(AjaxRequestTarget target, Form<?> form) {
						target.add(feed);
					}

				});
				add(new AjaxLink("cancel") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						onCancel(target);
					}
				});
				add((feed = new BootstrapFeedbackPanel("feed")).setOutputMarkupId(true));
			}
		}).setOutputMarkupId(true));
	}

	public void onSubmit(AjaxRequestTarget target) {

	}

	public void onCancel(AjaxRequestTarget target) {

	}
}
