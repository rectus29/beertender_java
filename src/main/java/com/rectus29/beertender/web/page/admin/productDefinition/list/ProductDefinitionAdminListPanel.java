package com.rectus29.beertender.web.page.admin.productDefinition.list;

import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.entities.ProductDefinition;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceProductDefinition;
import com.rectus29.beertender.web.component.confirmation.ConfirmationLink;
import com.rectus29.beertender.web.component.wicketmodal.BeerTenderModal;
import com.rectus29.beertender.web.page.admin.product.panels.edit.ProductAdminEditPanel;
import com.rectus29.beertender.web.page.admin.productDefinition.edit.ProductDefifnitionAdminEditPanel;
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
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

public class ProductDefinitionAdminListPanel extends Panel {

	final static int NB_ITEMS_BY_PAGE = 20;
	private static final Logger log = LogManager.getLogger(ProductDefinitionAdminListPanel.class);

	@SpringBean(name = "serviceProductDefinition")
	private IserviceProductDefinition serviceProductDefinition;

	private String name;
	private Form form;
	private WebMarkupContainer wmc;
	private LoadableDetachableModel<List<ProductDefinition>> ldm;
	private PageableListView plv;
	private BeerTenderModal modal;
	private PagingNavigator navigator;


	public ProductDefinitionAdminListPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		ldm = new LoadableDetachableModel<List<ProductDefinition>>() {
			@Override
			protected List<ProductDefinition> load() {
				List<ProductDefinition> out;
				if (name != null && name.length() > 2)
					out = new ArrayList<ProductDefinition>(serviceProductDefinition.getAllByProperty("name", name));
				else
					out = serviceProductDefinition.getAll(State.ENABLE, State.DISABLE, State.PENDING);
				return out;
			}
		};

		add((form = new Form("form")).setOutputMarkupId(true));
		form.add(new TextField<>("name", new PropertyModel<String>(this, "name")));
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

		wmc.add(plv = new PageableListView<ProductDefinition>("sorting", ldm, NB_ITEMS_BY_PAGE) {
			@Override
			protected void populateItem(final ListItem<ProductDefinition> item) {
				item.add(new Label("id", item.getModelObject().getId() + ""));
				item.add(new Label("prodName", item.getModelObject().getName()));
				item.add(new EnumLabel<State>("state", item.getModelObject().getState()));
				item.add(new AjaxLink("edit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						modal.setTitle("Editer une definition");
						modal.setContent(new ProductDefifnitionAdminEditPanel(modal.getContentId(), item.getModel()){
							@Override
							public void onSubmit(AjaxRequestTarget target, IModel<ProductDefinition> productModel) {
								ldm.detach();
								target.add(wmc);
								modal.close(target);
							}

							@Override
							public void onCancel(AjaxRequestTarget target) {
								modal.close(target);
							}
						});
						modal.show(target, BeerTenderModal.ModalFormat.LARGE);
					}

					@Override
					public boolean isVisible() {
						return SecurityUtils.getSubject().isPermitted("system:productDefinition:edit");
					}
				});
				item.add(new ConfirmationLink("remove", new ResourceModel("UserEditPanel.confirmMsg2").getObject()) {
					@Override
					public void onClick(AjaxRequestTarget target) {
						serviceProductDefinition.delete(item.getModelObject());
						ldm.detach();
						modal.close(target);
						target.add(wmc);
					}

					@Override
					public boolean isVisible() {
						return SecurityUtils.getSubject().isPermitted("system:productDefinition:delete");
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

		add((modal = new BeerTenderModal("modal")).setOutputMarkupId(true));
		add(new AjaxLink("add"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				modal.setTitle(new ResourceModel("add").getObject());
				modal.setContent(new ProductDefifnitionAdminEditPanel(modal.getContentId()){
					@Override
					public void onSubmit(AjaxRequestTarget target, IModel<ProductDefinition> productIModel) {
						ldm.detach();
						modal.close(target);
						target.add(wmc);
					}

					@Override
					public void onCancel(AjaxRequestTarget target) {
						modal.close(target);
					}
				});
				modal.show(target, BeerTenderModal.ModalFormat.LARGE);
			}
		});


	}
}
