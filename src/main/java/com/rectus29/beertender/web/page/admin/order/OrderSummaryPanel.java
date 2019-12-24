package com.rectus29.beertender.web.page.admin.order;

/*-----------------------------------------------------*/
/* User: Rectus_29       Date: 09/08/13 11:55 			*/
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.service.IserviceTimeFrame;
import com.rectus29.beertender.web.BeerTenderConfig;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class OrderSummaryPanel extends Panel {

	@SpringBean(name = "serviceTimeFrame")
	private IserviceTimeFrame serviceTimeFrame;

	private Form form;
	private Panel childPanel;
	private TimeFrame timeFrame;

	public OrderSummaryPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		this.timeFrame = serviceTimeFrame.getCurrentTimeFrame();
		add((form = new Form("orderSelectionForm")).setOutputMarkupId(true));
		LoadableDetachableModel<List<TimeFrame>> ldmOrder = new LoadableDetachableModel<List<TimeFrame>>() {
			@Override
			protected List<TimeFrame> load() {
				for (TimeFrame temp : serviceTimeFrame.getAll()) {
					if (temp.getState().isEnable()) {
						timeFrame = temp;
						break;
					}
				}
				return serviceTimeFrame.getAll();
			}
		};
		form.add(new DropDownChoice<>(
						"orderSelector",
				new PropertyModel<>(this, "timeFrame"),
						ldmOrder,
						new ChoiceRenderer<TimeFrame>() {
							@Override
							public Object getDisplayValue(TimeFrame object) {
								return object.getName() + " - " + BeerTenderConfig.get().dateFormat(object.getEndDate()) + " (" + object.getState() + ")";
							}
						}
				).add(new AjaxFormComponentUpdatingBehavior("change") {
					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						if (timeFrame != null) {
							childPanel = (Panel) childPanel.replaceWith(new OrderSummaryChildPanel("panel", new Model<>(timeFrame)).setOutputMarkupId(true));
							target.add(childPanel);
						}
					}
				})
		);
		//place emptyPanel by default
		add((childPanel = (timeFrame != null)
				? new OrderSummaryChildPanel("panel", new Model<>(timeFrame))
						: new EmptyPanel("panel")
				).setOutputMarkupId(true)
		);
	}
}
