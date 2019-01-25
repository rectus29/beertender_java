package com.rectus29.beertender.web.page.admin.users.panels.list;


import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.enums.UserAuthentificationType;
import com.rectus29.beertender.realms.BeerTenderRealms;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.BeerTenderApplication;
import com.rectus29.beertender.web.component.confirmation.ConfirmationLink;
import com.rectus29.beertender.web.component.wicketmodal.BeerTenderModal;
import com.rectus29.beertender.web.page.admin.users.panels.edit.UserEditPanel;
import com.rectus29.beertender.web.page.home.HomePage;
import com.rectus29.beertender.web.panel.enrollpanel.EnrollPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.EnumLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;


public class UserListPanel extends Panel {

	private static final Logger log = LogManager.getLogger(UserListPanel.class);
	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;
	private String name;
	private WebMarkupContainer wmc;
	private LoadableDetachableModel<List<User>> ldm;
	private PageableListView plv;
	private BeerTenderModal modal;
	private PagingNavigator navigator;

	public UserListPanel(String s) {
		super(s);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		ldm = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				List<User> out;
				if (name != null && name.length() > 2)
					out = new ArrayList<>(serviceUser.getAllByProperty("userName", name));
				else
					out = serviceUser.getAll();
				return out;
			}
		};

		add(new Form("form")
				.add(new TextField<String>("name", new PropertyModel<>(this, "name")))
				.add(new AjaxSubmitLink("filterSubmitButton") {
					@Override
					public void onSubmit(AjaxRequestTarget target, Form form) {
						ldm.detach();
						target.add(wmc, form, navigator);
					}

					@Override
					protected void onError(AjaxRequestTarget target, Form<?> form) {
						target.add(form);
					}
				})
				.setOutputMarkupId(true)
		);

		wmc = new WebMarkupContainer("wmc");
		add(wmc.setOutputMarkupId(true));

		wmc.add(plv = new PageableListView<User>("sorting", ldm, getNbUsersByPage()) {
			@Override
			protected void populateItem(final ListItem<User> item) {
				item.add(new Label("id", item.getModelObject().getId() + ""));
				item.add(new Label("auth").add(
						new AttributeAppender("class",
								(item.getModelObject().getUserAuthentificationType().equals(UserAuthentificationType.GOOGLE))
										? " fa-google"
										: (item.getModelObject().getUserAuthentificationType().equals(UserAuthentificationType.EMBED))
										? " fa-key" :
										" fa-lock")
						)
				);
				item.add(new Label("lastName", item.getModelObject().getFormattedName()));
				item.add(new Label("email", item.getModelObject().getEmail()));
				item.add(new Label("role", item.getModelObject().getRole().getName()));
				item.add(new EnumLabel<State>("state", item.getModelObject().getState()));
				item.add(new AjaxLink("edit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						modal.setTitle(new ResourceModel("UserEditPanel.editUser").getObject());
						modal.setContent(new UserEditPanel(modal.getContentId(), item.getModel()) {
							@Override
							public void onSubmit(AjaxRequestTarget target) {
								ldm.detach();
								modal.close(target);
								target.add(wmc);
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
						return SecurityUtils.getSubject().isPermitted("system:user:edit");
					}
				});
				item.add(new ConfirmationLink("remove", new ResourceModel("UserEditPanel.confirmMsg2").getObject()) {
					@Override
					public void onClick(AjaxRequestTarget target) {
						serviceUser.disable(item.getModelObject());
						ldm.detach();
						target.add(wmc);
					}

					@Override
					public boolean isVisible() {
						return SecurityUtils.getSubject().isPermitted("system:user:delete");
					}
				});
				item.add(new AjaxLink("runas") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						BeerTenderRealms realms = ((BeerTenderApplication) getApplication()).getRealms();
						PrincipalCollection pc = new SimplePrincipalCollection(item.getModelObject().getId(), realms.getName());
						SecurityUtils.getSubject().runAs(pc);
						((BeerTenderApplication) getApplication()).updateRights();
						setResponsePage(HomePage.class);
					}

					@Override
					public boolean isVisible() {
						return SecurityUtils.getSubject().isPermitted("system:user:runas");
					}

				});
			}
		});
		add((navigator = new PagingNavigator("navigator", plv) {
			@Override
			public boolean isVisible() {
				return ldm.getObject().size() > getNbUsersByPage();
			}
		}).setOutputMarkupId(true));

		add((modal = new BeerTenderModal("modal")).setOutputMarkupId(true));

		add(new AjaxLink("add") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				modal.setTitle(new ResourceModel("UserEditPanel.addUser").getObject());
				modal.setContent(new UserEditPanel(modal.getContentId()) {
					@Override
					public void onSubmit(AjaxRequestTarget target) {
						ldm.detach();
						modal.close(target);
						target.add(wmc);
					}

					@Override
					public void onCancel(AjaxRequestTarget target) {
						modal.close(target);
					}
				});
				modal.show(target, BeerTenderModal.ModalFormat.MEDIUM);
			}
		});

		add(new AjaxLink("enroll") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				modal.setTitle("Inviter un nouveau memebre");
				modal.setContent(new EnrollPanel(modal.getContentId()) {
					@Override
					protected void onSubmit(AjaxRequestTarget target, IModel<User> enrollUserModel) {
						modal.close(target);
					}

					@Override
					protected void onCancel(AjaxRequestTarget target) {
						modal.close(target);
					}
				});
				modal.show(target, BeerTenderModal.ModalFormat.SMALL);
			}
		});
	}

	protected int getNbUsersByPage(){
		return 20;
	}
}
