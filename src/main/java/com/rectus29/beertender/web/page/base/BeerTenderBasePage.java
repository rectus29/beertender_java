package com.rectus29.beertender.web.page.base;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.realms.BeerTenderRealms;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceTimeFrame;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.BeerTenderApplication;
import com.rectus29.beertender.web.component.avatarimage.AvatarImage;
import com.rectus29.beertender.web.component.labels.CurrencyLabel;
import com.rectus29.beertender.web.component.wicketmodal.BeerTenderModal;
import com.rectus29.beertender.web.page.admin.AdminPage;
import com.rectus29.beertender.web.page.home.HomePage;
import com.rectus29.beertender.web.panel.cartmodalpanel.CartModalPanel;
import com.rectus29.beertender.web.security.signout.SignoutPage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
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
	@SpringBean(name = "serviceTimeFrame")
	private IserviceTimeFrame serviceTimeFrame;
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
		add(new BookmarkablePageLink<SignoutPage>("logoutLink", SignoutPage.class));

		add(new Link("runAsUsual") {
			@Override
			public void onClick() {
				PrincipalCollection pc = SecurityUtils.getSubject().getPreviousPrincipals();
				SecurityUtils.getSubject().releaseRunAs();
				BeerTenderRealms realms = ((BeerTenderApplication) BeerTenderApplication.get()).getRealms();
				realms.clearCachedAuthorizationInfo(pc);
				setResponsePage(HomePage.class);
			}

			@Override
			protected void onConfigure() {
				setVisibilityAllowed(SecurityUtils.getSubject().isRunAs());
			}
		});
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

			@Override
			public boolean isEnabled() {
				return BeerTenderSession.get().isOrderEnable();
			}

			@Override
			protected void onInitialize() {
				super.onInitialize();
				if (BeerTenderSession.get().isOrderEnable()) {
					add(new BadgeFragment1("badge", "badgeFrag1", BeerTenderBasePage.this));
				} else {
					add(new BadgeFragment2("badge", "badgeFrag2", BeerTenderBasePage.this));
				}
			}
		});
		add((modal = new BeerTenderModal("modal")).setOutputMarkupId(true));
	}

	@Override
	public void onEvent(IEvent event) {
		if (event.getPayload() instanceof RefreshEvent) {
			((RefreshEvent) event.getPayload()).getTarget()
					.add(nbProductLabel, cartCostLabel);
		}
	}

	private class BadgeFragment1 extends Fragment {

		public BadgeFragment1(String id, String markupId, MarkupContainer markupProvider) {
			super(id, markupId, markupProvider);
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();
			add((nbProductLabel = new Label("nbProduct", new LoadableDetachableModel<Integer>() {
				@Override
				protected Integer load() {
					Order currentOrder = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());
					if (currentOrder != null) {
						return currentOrder.getNbProductInOrder();
					} else {
						return 0;
					}
				}
			})).setOutputMarkupId(true));
			add((cartCostLabel = new CurrencyLabel("cartCostLabel", new LoadableDetachableModel<BigDecimal>() {
				@Override
				protected BigDecimal load() {
					Order currentOrder = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());
					if (currentOrder != null) {
						return currentOrder.getOrderPrice();
					} else {
						return BigDecimal.ZERO;
					}
				}
			})).setOutputMarkupId(true));
		}
	}

	private class BadgeFragment2 extends Fragment {

		public BadgeFragment2(String id, String markupId, MarkupContainer markupProvider) {
			super(id, markupId, markupProvider);
		}
	}
}
