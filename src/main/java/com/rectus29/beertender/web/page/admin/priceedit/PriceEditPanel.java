package com.rectus29.beertender.web.page.admin.priceedit;

import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.service.IserviceProduct;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.math.BigDecimal;
import java.util.List;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 13/06/2019                  */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class PriceEditPanel extends Panel {

	@SpringBean(name = "serviceProduct")
	private IserviceProduct serviceProduct;

	public PriceEditPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		LoadableDetachableModel<List<Product>> ldm = new LoadableDetachableModel<List<Product>>() {
			@Override
			protected List<Product> load() {
				List<Product> out = serviceProduct.getAll();
				out.sort((o1, o2) -> {
					if (o1.getPackaging().equals(o2.getPackaging())) {
						return Integer.compare(o1.getSeqOrder(), o2.getSeqOrder());
					} else {
						return Integer.compare(o1.getPackaging().getSeqOrder(), o2.getPackaging().getSeqOrder());
					}
				});
				return out;
			}
		};

		final WebMarkupContainer wmc = new WebMarkupContainer("innerwmc");
		wmc.setOutputMarkupId(true);
		add(wmc);

		wmc.add(new ListView<Product>("lvProduct", ldm) {
			@Override
			protected void populateItem(ListItem<Product> item) {
				item.add(new Label("productName", item.getModelObject().getProductDefinition().getName()));
				item.add(new Label("productPack", item.getModelObject().getPackaging().getName()));
				item.add(new Form("priceForm")
						.add(new NumberTextField<BigDecimal>("productPrice", new PropertyModel<>(item.getModel(), "price"))
								.add(new AjaxFormComponentUpdatingBehavior("change") {
									@Override
									protected void onUpdate(AjaxRequestTarget target) {
										Product pord = item.getModelObject();
										serviceProduct.save(pord);
										this.getComponent().add(AttributeAppender.replace("class", "pulse"));
										ldm.detach();
										target.add(item);
									}
								}))
				);
				item.setOutputMarkupId(true);
			}
		});

	}
}
