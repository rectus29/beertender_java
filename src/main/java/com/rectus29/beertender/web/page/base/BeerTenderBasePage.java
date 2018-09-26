package com.rectus29.beertender.web.page.base;

import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.labels.CurrencyLabel;
import com.rectus29.beertender.web.component.wicketmodal.BeerTenderModal;
import com.rectus29.beertender.web.page.admin.AdminPage;
import com.rectus29.beertender.web.page.home.HomePage;
import com.rectus29.beertender.web.panel.cartmodalpanel.CartModalPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.math.BigDecimal;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 06/07/2018 11:41               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BeerTenderBasePage extends ProtectedPage {

	protected BeerTenderModal modal;
	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;
	@SpringBean(name = "serviceOrder")
	private IserviceOrder serviceOrder;
	private Label nbProductLabel, cartCostLabel;

	public BeerTenderBasePage() {
	}

	public BeerTenderBasePage(IModel model) {
		super(model);
	}

	public BeerTenderBasePage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new BookmarkablePageLink<HomePage>("homeLink", HomePage.class));
		add(new Label("login", serviceUser.getCurrentUser().getFormatedName()));
		add(new BookmarkablePageLink("admin", AdminPage.class) {
			@Override
			public boolean isVisible() {
				return serviceUser.getCurrentUser().isAdmin();
			}
		});


		add(new AjaxLink("cartLink") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						modal.setTitle("Votre Panier");
						modal.setContent(new CartModalPanel(modal.getContentId()));
						modal.show(target);
					}
				}
						.add((nbProductLabel = new Label("nbProduct", new LoadableDetachableModel<Integer>() {
							@Override
							protected Integer load() {
								return serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser()).getNbProductInOrder();
							}
						})).setOutputMarkupId(true))
						.add((cartCostLabel = new CurrencyLabel("cartCostLabel", new LoadableDetachableModel<BigDecimal>() {
							@Override
							protected BigDecimal load() {
								return serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser()).getOrderPrice();
							}
						})).setOutputMarkupId(true))
		);
		add((modal = new BeerTenderModal("modal")).setOutputMarkupId(true));
	}

	@Override
	public void onEvent(IEvent event) {
		if (event.getPayload() instanceof RefreshEvent) {
			((RefreshEvent) event.getPayload()).getTarget()
					.add(nbProductLabel, cartCostLabel);
		}
	}
}
