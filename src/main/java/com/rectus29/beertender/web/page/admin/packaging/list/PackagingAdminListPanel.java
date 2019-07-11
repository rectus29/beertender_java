package com.rectus29.beertender.web.page.admin.packaging.list;

import com.rectus29.beertender.entities.Packaging;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.event.OnEvent;
import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.service.IservicePackaging;
import com.rectus29.beertender.web.component.wicketmodal.BeerTenderModal;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.EnumLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 11/07/2019                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class PackagingAdminListPanel extends Panel {

	@SpringBean(name = "servicePackaging")
	private IservicePackaging servicePackaging;
	private WebMarkupContainer wmc;
	private LoadableDetachableModel<List<Packaging>> ldm;
	private PageableListView plv;
	private BeerTenderModal modal;
	private PagingNavigator navigator;


	public PackagingAdminListPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		ldm = new LoadableDetachableModel<List<Packaging>>() {
			@Override
			protected List<Packaging> load() {
				return servicePackaging.getAll(State.enabledState);
			}
		};

		add((wmc = new WebMarkupContainer("wmc")).setOutputMarkupId(true));

		wmc.add(plv = new PageableListView<Packaging>("sorting", ldm, getItemsByPage()) {
			@Override
			protected void populateItem(final ListItem<Packaging> item) {
				item.add(new Label("id", item.getModelObject().getId() + ""));
				item.add(new Label("name", item.getModelObject().getName()));

				item.add(new EnumLabel<>("state", item.getModelObject().getState()));
			}
		});
		add((navigator = new PagingNavigator("navigator", plv) {
			@Override
			public boolean isVisible() {
				return ldm.getObject().size() > getItemsByPage();
			}
		}).setOutputMarkupId(true));

		add((modal = new BeerTenderModal("modal")).setOutputMarkupId(true));
	}

	@OnEvent
	public void onEvent(RefreshEvent event) {
		ldm.detach();
		event.getTarget().add(wmc, navigator);

	}

	protected int getItemsByPage() {
		return 20;
	}
}
