package com.rectus29.beertender.web.page.base;


/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 11:22 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import com.rectus29.beertender.service.IserviceUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

public class BasePage extends WebPage implements TitleContributor {

    @SpringBean(name = "serviceUser")
    private IserviceUser serviceUser;

    private static final Logger log = LogManager.getLogger(BasePage.class);

    public BasePage() {
    }

    public BasePage(IModel<?> model) {
        super(model);
    }

    public BasePage(PageParameters parameters) {
        super(parameters);
    }

    public String getTitle() {
        final StringBuilder title = new StringBuilder();
		title.append(" - ");
        title.append(getTitleContribution());
        visitChildren(new IVisitor<Component, Object>() {
            public void component(Component component, IVisit<Object> objectIVisit) {
                if (component instanceof TitleContributor) {
                    title.append(" :: ");
                    title.append(((TitleContributor) component).getTitleContribution());
                }
            }
        });
        return title.toString();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Label("title", "BeerTender " + new PropertyModel(this, "title").getObject()));
    }

    public String getTitleContribution() {
        return "";
    }

}
