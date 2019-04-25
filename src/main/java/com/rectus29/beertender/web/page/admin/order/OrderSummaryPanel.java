package com.rectus29.beertender.web.page.admin.order;

/*-----------------------------------------------------*/
/* User: Rectus_29       Date: 09/08/13 11:55 			*/
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.service.IserviceTimeFrame;
import com.rectus29.beertender.web.Config;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class OrderSummaryPanel extends Panel {

	@SpringBean(name = "serviceTimeFrame")
	private IserviceTimeFrame serviceTimeFrame;

	private Form form;
	private Panel childPanel;
	private IModel<TimeFrame> timeFrameModel = new Model<>();

	public OrderSummaryPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		this.timeFrameModel = new Model<>(serviceTimeFrame.getCurrentTimeFrame());
		add((form = new Form("orderSelectionForm")).setOutputMarkupId(true));
		LoadableDetachableModel<List<TimeFrame>> ldmOrder = new LoadableDetachableModel<List<TimeFrame>>() {
			@Override
			protected List<TimeFrame> load() {
				for (TimeFrame temp : serviceTimeFrame.getAll()) {
					if (temp.getState().isEnable()) {
						timeFrameModel = new Model<>(temp);
						break;
					}
				}
				return serviceTimeFrame.getAll();
			}
		};
		form.add(new DropDownChoice<>(
						"orderSelector",
						timeFrameModel,
						ldmOrder,
						new ChoiceRenderer<TimeFrame>() {
							@Override
							public Object getDisplayValue(TimeFrame object) {
								return object.getName() + " - " + Config.get().dateFormat(object.getEndDate()) + "(" + object.getState() + ")";
							}
						}
				).add(new AjaxFormComponentUpdatingBehavior("change") {
					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						if (timeFrameModel.getObject() != null) {
							childPanel = (Panel) childPanel.replaceWith(new OrderSummaryChildPanel("panel", timeFrameModel).setOutputMarkupId(true));
							target.add(childPanel);
						}
					}
				})
		);
		//place emptyPanel by default
		add((childPanel = (timeFrameModel != null)
						? new OrderSummaryChildPanel("panel", timeFrameModel)
						: new EmptyPanel("panel")
				).setOutputMarkupId(true)
		);
	}
}
