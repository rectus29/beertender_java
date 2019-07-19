package com.rectus29.beertender.web.page.base;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.OrderItem;
import com.rectus29.beertender.enums.ErrorCode;
import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.realms.BeerTenderRealms;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceTimeFrame;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.BeerTenderApplication;
import com.rectus29.beertender.web.component.productimage.ProductImage;
import com.rectus29.beertender.web.component.wicketmodal.BeerTenderModal;
import com.rectus29.beertender.web.page.admin.AdminPage;
import com.rectus29.beertender.web.page.billspage.BillsPage;
import com.rectus29.beertender.web.page.home.HomePage;
import com.rectus29.beertender.web.security.error.ErrorPage;
import com.rectus29.beertender.web.security.signout.SignoutPage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

/*-----------------------------------------------------*/
/*                       Rectus29                      */
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
	private WebMarkupContainer wmc;
	private LoadableDetachableModel<List<OrderItem>> ldm;

	public BeerTenderBasePage() {
		super();
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

		ldm = new LoadableDetachableModel<List<OrderItem>>() {
			@Override
			protected List<OrderItem> load() {
				Order order = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());
				if (order != null) {
					return order.getOrderItemList();
				} else {
					setResponsePage(ErrorPage.class, new PageParameters().add("errorCode", ErrorCode.NO_ORDER_FOUND));
				}
				return new ArrayList<>();
			}
		};

		wmc = new WebMarkupContainer("cartHolder");
		add(wmc.setOutputMarkupId(true));
		wmc.add(new Label("cartNbProduct", ldm.getObject().size()) {
			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisible(ldm.getObject().size() > 0);
			}
		}.setOutputMarkupId(true));
		wmc.add(new ListView<OrderItem>("cartLv", ldm) {
			@Override
			protected void populateItem(ListItem<OrderItem> item) {
				item.add(new ProductImage("productImg", item.getModelObject().getProduct()));
				item.add(new Label("productName", item.getModelObject().getProduct().getName()));
				item.add(new Label("qte", "x" + item.getModelObject().getQuantity()));
				item.add(new Label("productPackage", item.getModelObject().getProduct().getPackaging().getName()));
			}
		});
		wmc.add(new BookmarkablePageLink("cartLink", BillsPage.class) {
			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisibilityAllowed(BeerTenderSession.get().isOrderEnable());
			}
		});
		add((modal = new BeerTenderModal("modal")).setOutputMarkupId(true));
	}

	@Override
	public void onEvent(IEvent event) {
		if (event.getPayload() instanceof RefreshEvent) {
			ldm.detach();
			((RefreshEvent) event.getPayload()).getTarget()
					.add(wmc);
		}
	}
}
