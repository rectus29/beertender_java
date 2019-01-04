package com.rectus29.beertender.web.page.test;

import com.rectus29.beertender.entities.ProductDefinition;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.entities.search.ISearchable;
import com.rectus29.beertender.service.*;
import com.rectus29.beertender.service.impl.serviceSearch;
import com.rectus29.beertender.web.page.base.BasePage;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 19/09/2018 17:19               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class TestPage extends BasePage {

    @SpringBean(name = "serviceProduct")
    private IserviceProduct serviceProduct;
    @SpringBean(name = "serviceCategory")
    private IserviceCategory serviceCategory;
    @SpringBean(name = "serviceProductDefinition")
    private IserviceProductDefinition serviceProductDefinition;
    @SpringBean(name = "serviceUser")
    private IserviceUser serviceUser;
    @SpringBean(name = "serviceMail")
    private IserviceMail serviceMail;
	@SpringBean(name = "serviceSearch")
	private IserviceSearch serviceSearch;


	public TestPage() {
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

		serviceSearch.initIndex();
		List<ISearchable> searchableList = serviceSearch.search("triple");

        ProductDefinition pd = serviceProductDefinition.get(1l);
		User user = serviceUser.getUserByMail("rectus29@gmail.com");
		serviceMail.sendEnrollmentMail(user);

    }
}
