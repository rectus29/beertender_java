package com.rectus29.beertender.web.page.searchresultpage;

import com.rectus29.beertender.entities.ProductDefinition;
import com.rectus29.beertender.entities.search.ISearchable;
import com.rectus29.beertender.service.IserviceSearch;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 04/01/2019 09:52               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class SearchResultPanel extends Panel {

	@SpringBean(name = "serviceSearch")
	private IserviceSearch serviceSearch;

	private IModel<String> searchStringIModel;

	public SearchResultPanel(String id, IModel<String> stringIModel) {
		super(id);
		this.searchStringIModel = stringIModel;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		List<ISearchable> searchableList = serviceSearch.search(this.searchStringIModel.getObject());


		add(new ListView<ISearchable>("rv", searchableList) {
			@Override
			protected void populateItem(ListItem<ISearchable> item) {
				ProductDefinition pd = (ProductDefinition )item.getModelObject();
				item.add(new Label("name", pd.getName()));
				item.add(new Label("description", pd.getDescription().substring(0, 250) + "..."));
			}
		});



	}
}
