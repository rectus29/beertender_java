package com.rectus29.beertender.web.page.admin.product.panels.list;

import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.web.BeerTenderApplication;
import com.rectus29.beertender.web.component.confirmation.ConfirmationLink;
import com.rectus29.beertender.web.component.labels.CurrencyLabel;
import com.rectus29.beertender.web.component.wicketmodal.WicketModal;
import com.rectus29.beertender.web.page.admin.product.panels.edit.ProductAdminEditPanel;
import com.rectus29.beertender.web.page.admin.users.panels.edit.UserEditPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.EnumLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.*;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

public class ProductAdminListPanel extends Panel {

	final static int NB_ITEMS_BY_PAGE = 20;
	private static final Logger log = LogManager.getLogger(ProductAdminListPanel.class);
	@SpringBean(name = "serviceProduct")
	private IserviceProduct serviceProduct;
	private String name;
	private Form form;
	private WebMarkupContainer wmc;
	private LoadableDetachableModel<List<Product>> ldm;
	private PageableListView plv;
	private WicketModal modal;
	private PagingNavigator navigator;


	public ProductAdminListPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		super.onInitialize();

		ldm = new LoadableDetachableModel<List<Product>>() {
			@Override
			protected List<Product> load() {
				List<Product> out;
				if (name != null && name.length() > 2)
					out = new ArrayList<Product>(serviceProduct.getAllByProperty("name", name));
				else
					out = serviceProduct.getAll();
				return out;
			}
		};

		add((form = new Form("form")).setOutputMarkupId(true));
		form.add(new TextField<String>("name", new PropertyModel<String>(this, "name")));
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

		wmc.add(plv = new PageableListView<Product>("sorting", ldm, NB_ITEMS_BY_PAGE) {
			@Override
			protected void populateItem(final ListItem<Product> item) {
				item.add(new Label("id", item.getModelObject().getId()+""));
				item.add(new Label("name", item.getModelObject().getName()));
				item.add(new CurrencyLabel("price", new Model<Long>(item.getModelObject().getPrice().longValue())));
				item.add(new EnumLabel<State>("state", item.getModelObject().getState()));
				item.add(new AjaxLink("edit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						modal.setTitle(new ResourceModel("UserEditPanel.editUser").getObject());
						modal.setContent(new ProductAdminEditPanel(modal.getContentId(), item.getModel()){
							@Override
							public void onSubmit(AjaxRequestTarget target, IModel<Product> productImodel) {
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
						//serviceUser.disable(item.getModelObject());
						ldm.detach();
						target.add(wmc);
					}

					@Override
					public boolean isVisible() {
						return SecurityUtils.getSubject().isPermitted("system:user:delete");
					}
				});
			}
		});
		add((navigator = new PagingNavigator("navigator", plv) {
			@Override
			public boolean isVisible() {
				return ldm.getObject().size() > NB_ITEMS_BY_PAGE;
			}
		}).setOutputMarkupId(true));

		add((modal = new WicketModal("modal")).setOutputMarkupId(true));
		add(new AjaxLink("add"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				modal.setTitle(new ResourceModel("UserEditPanel.addUser").getObject());
				modal.setContent(new ProductAdminEditPanel(modal.getContentId()){
					@Override
					public void onSubmit(AjaxRequestTarget target, IModel<Product> productIModel) {
						ldm.detach();
						modal.close(target);
						target.add(wmc);
					}

					@Override
					public void onCancel(AjaxRequestTarget target) {
						modal.close(target);
					}
				});
				modal.show(target, WicketModal.ModalFormat.MEDIUM);
			}
		});

	}
}