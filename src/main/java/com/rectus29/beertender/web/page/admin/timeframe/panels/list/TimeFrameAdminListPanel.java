package com.rectus29.beertender.web.page.admin.timeframe.panels.list;

import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.event.OnEvent;
import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.service.IserviceTimeFrame;
import com.rectus29.beertender.web.component.confirmation.ConfirmationLink;
import com.rectus29.beertender.web.component.wicketmodal.BeerTenderModal;
import com.rectus29.beertender.web.page.admin.timeframe.panels.edit.TimeFrameAdminEditPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.EnumLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 13/07/2018 16:55               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class TimeFrameAdminListPanel extends Panel {

	private static final Logger log = LogManager.getLogger(TimeFrameAdminListPanel.class);
	@SpringBean(name = "serviceTimeFrame")
	private IserviceTimeFrame serviceTimeFrame;
	private String name;
	private Form form;
	private WebMarkupContainer wmc;
	private LoadableDetachableModel<List<TimeFrame>> ldm;
	private PageableListView plv;
	private BeerTenderModal modal;
	private PagingNavigator navigator;


	public TimeFrameAdminListPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		ldm = new LoadableDetachableModel<List<TimeFrame>>() {
			@Override
			protected List<TimeFrame> load() {
				List<TimeFrame> out;
				if (name != null && name.length() > 2)
					out = new ArrayList<>(serviceTimeFrame.getAllByProperty("name", name));
				else
					out = serviceTimeFrame.getAll(new State[]{State.ENABLE, State.PENDING, State.DISABLE});
				return out;
			}
		};

		add((form = new Form("form"))
				.add(new TextField<>("name", new PropertyModel<>(this, "name")))
				.setOutputMarkupId(true)
		);

		form.add(new AjaxSubmitLink("filterSubmitButton") {
			@Override
			public void onSubmit(AjaxRequestTarget target, Form form) {
				ldm.detach();
				target.add(wmc, form, navigator);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		});

		add((wmc = new WebMarkupContainer("wmc")).setOutputMarkupId(true));

		wmc.add(plv = new PageableListView<TimeFrame>("sorting", ldm, getItemsByPage()) {
			@Override
			protected void populateItem(final ListItem<TimeFrame> item) {
				item.add(new Label("id", item.getModelObject().getId() + ""));
				item.add(new Label("name", item.getModelObject().getName()));
				item.add(new Label("startDate", item.getModelObject().getStartDate()));
				item.add(new Label("endDate", item.getModelObject().getEndDate()));
				item.add(new EnumLabel<State>("state", item.getModelObject().getState()));
				item.add(new AjaxLink("edit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						modal.setTitle(new ResourceModel("UserEditPanel.editUser").getObject());
						modal.setContent(new TimeFrameAdminEditPanel(modal.getContentId(), item.getModel()) {
							@Override
							public void onSubmit(AjaxRequestTarget target) {
								modal.close(target);
								send(getApplication(), Broadcast.BREADTH, new RefreshEvent(target));
							}

							@Override
							public void onCancel(AjaxRequestTarget target) {
								modal.close(target);
							}
						});
						modal.show(target);
					}

					@Override
					public boolean isVisible() {
						return SecurityUtils.getSubject().isPermitted("system:timeFrame:edit");
					}
				});
				item.add(new ConfirmationLink("remove", new ResourceModel("UserEditPanel.confirmMsg2").getObject()) {
					@Override
					public void onClick(AjaxRequestTarget target) {
						serviceTimeFrame.delete(item.getModelObject());
						ldm.detach();
						target.add(wmc);
					}

					@Override
					public boolean isVisible() {
						return SecurityUtils.getSubject().isPermitted("system:timeFrame:delete");
					}
				});
			}
		});
		add((navigator = new PagingNavigator("navigator", plv) {
			@Override
			public boolean isVisible() {
				return ldm.getObject().size() > getItemsByPage();
			}
		}).setOutputMarkupId(true));

		add((modal = new BeerTenderModal("modal")).setOutputMarkupId(true));
		add(new AjaxLink("add") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				modal.setTitle("Editer une TimeFrame");
				modal.setContent(new TimeFrameAdminEditPanel(modal.getContentId()) {
					@Override
					public void onSubmit(AjaxRequestTarget target) {
						modal.close(target);
						send(getApplication(), Broadcast.BREADTH, new RefreshEvent(target));
					}

					@Override
					public void onCancel(AjaxRequestTarget target) {
						modal.close(target);
					}
				});
				modal.show(target, BeerTenderModal.ModalFormat.MEDIUM);
			}
		});
	}

	@OnEvent
	public void onEvent(RefreshEvent event) {
		ldm.detach();
		event.getTarget().add(wmc, navigator);

	}

	protected int getItemsByPage(){
		return 20;
	}
}
