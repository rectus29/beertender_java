package com.rectus29.beertender.web.page.admin.product.panels.list;

import com.rectus29.beertender.entities.Packaging;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IservicePackaging;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.web.component.confirmation.ConfirmationLink;
import com.rectus29.beertender.web.component.labels.CurrencyLabel;
import com.rectus29.beertender.web.component.wicketmodal.BeerTenderModal;
import com.rectus29.beertender.web.page.admin.product.panels.edit.ProductAdminEditPanel;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.EnumLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.*;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductAdminListPanel extends Panel {

	final static int NB_ITEMS_BY_PAGE = 20;
	private static final Logger log = LogManager.getLogger(ProductAdminListPanel.class);
	@SpringBean(name = "serviceProduct")
	private IserviceProduct serviceProduct;
	@SpringBean(name = "servicePackaging")
	private IservicePackaging servicePackaging;
	private String name;
	private Packaging searchPackaging;
	private Form form;
	private WebMarkupContainer wmc;
	private LoadableDetachableModel<List<Product>> ldm;
	private PageableListView plv;
	private BeerTenderModal modal;
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
				List<Product> out = new ArrayList<>();
				List<Product> productList = serviceProduct.getAll();
				if (StringUtils.isNotBlank(ProductAdminListPanel.this.name)) {
					for (Product temp : productList) {
						if (temp.getName().contains(ProductAdminListPanel.this.name)) {
							out.add(temp);
						}
					}
				}else{
					out = productList;
				}
				if (ProductAdminListPanel.this.searchPackaging != null) {
					List<Product> out2 = out;
					out = new ArrayList<>();
					for (Product temp : out2) {
						if (temp.getPackaging().equals(ProductAdminListPanel.this.searchPackaging)) {
							out.add(temp);
						}
					}
				} else {
					out = productList;
				}
				return out;
			}
		};

		add((form = new Form("form")).setOutputMarkupId(true));
		form.add(new TextField<String>("name", new PropertyModel<String>(this, "name")));
		form.add(new DropDownChoice<Packaging>("pckg", new PropertyModel<Packaging>(this, "searchPackaging"), servicePackaging.getAll(Arrays.asList(State.ENABLE)), new ChoiceRenderer<Packaging>("name")).setNullValid(true));
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
				item.add(new Label("id", item.getModelObject().getId() + ""));
				item.add(new Label("prodName", item.getModelObject().getName()));
				item.add(new Label("packaging", item.getModelObject().getPackaging().getName()));
				item.add(new CurrencyLabel("price", new Model<Long>(item.getModelObject().getPrice().longValue())));
				item.add(new EnumLabel<State>("state", item.getModelObject().getState()));
				item.add(new AjaxLink("edit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						modal.setTitle("Editer un produit");
						modal.setContent(new ProductAdminEditPanel(modal.getContentId(), item.getModel()) {
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
						modal.show(target, BeerTenderModal.ModalFormat.LARGE);
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
		}));
		navigator.setOutputMarkupId(true);

		add((modal = new BeerTenderModal("modal")).setOutputMarkupId(true));
		add(new AjaxLink("add") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				modal.setTitle("Editer un produit");
				modal.setContent(new ProductAdminEditPanel(modal.getContentId()) {
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
				modal.show(target, BeerTenderModal.ModalFormat.LARGE);
			}
		});

	}
}
