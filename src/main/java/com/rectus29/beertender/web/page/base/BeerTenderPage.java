package com.rectus29.beertender.web.page.base;

import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Packaging;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IservicePackaging;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.tools.SecurityUtils;
import com.rectus29.beertender.web.component.avatarimage.AvatarImage;
import com.rectus29.beertender.web.page.home.HomePage;
import com.rectus29.beertender.web.page.profile.ProfilePage;
import com.rectus29.beertender.web.panel.searchpanel.searchpanel;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 06/07/2018 11:41               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BeerTenderPage extends BeerTenderBasePage {

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;

	@SpringBean(name = "servicePackaging")
	private IservicePackaging servicePackaging;

	public BeerTenderPage() {
	}

	public BeerTenderPage(IModel model) {
		super(model);
	}

	public BeerTenderPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();


		add(new BookmarkablePageLink<ProfilePage>("profilPageLink", ProfilePage.class) {
					@Override
					public boolean isEnabled() {
						return SecurityUtils.getSubject().isPermitted("profil:access");
					}
				}
				.add(new AvatarImage("avatarImg"))
		);
		add(new Label("login", serviceUser.getCurrentUser().getFormattedName()));
		add(new searchpanel("searchPanel"));

		LoadableDetachableModel model = new LoadableDetachableModel<List<Packaging>>() {
			@Override
			protected List<Packaging> load() {
				List<Packaging> out = servicePackaging.getAll(Arrays.asList(State.ENABLE));
				Collections.sort(out, Comparator.comparing(Packaging::getSortOrder));
				return out;
			}
		};

		add(new ListView<Packaging>("rvLink", model) {
			@Override
			protected void populateItem(ListItem<Packaging> listItem) {

				listItem.add(new BookmarkablePageLink("link",
						HomePage.class,
						new PageParameters().add("package", listItem.getModelObject().getShortName()))
						.add(new Label("label", listItem.getModelObject().getName()))
						.add(new AttributeAppender("class", (BeerTenderSession.get().getBeerTenderFilter().getPackagingFilter().getObject() == listItem.getModelObject()) ? " active" : ""))
				);
				listItem.add(new AttributeAppender("class", (BeerTenderSession.get().getBeerTenderFilter().getPackagingFilter().getObject() == listItem.getModelObject()) ? " active" : ""));
				List<Category> categories = servicePackaging.getChildCategoryFor(listItem.getModelObject());
				Collections.sort(categories);
				listItem.add(new ListView<Category>("rvChildLink", categories) {
					@Override
					protected void populateItem(ListItem<Category> item) {
						item.add(new BookmarkablePageLink("childLink",
										HomePage.class,
										new PageParameters().add("package", listItem.getModelObject().getShortName()).add("category", item.getModelObject().getShortName())
								)
										.add(new Label("childLabel", item.getModelObject().getName()))
										.add(new AttributeAppender("class", (BeerTenderSession.get().getBeerTenderFilter().getCategoryFilterModel().getObject() == item.getModelObject()) ? " active" : ""))
						);
					}
				});
			}
		});
	}
}
