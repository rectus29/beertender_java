package com.rectus29.beertender.web.component.avatarimage;

import com.rectus29.beertender.entities.DecorableElement;
import com.rectus29.beertender.entities.core.User;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.BeerTenderApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.File;


/*------------------------------------------------------*/
/* User: Rectus_29      Date: 06/03/12 18:28 			*/
/*                                                     	*/
/*                 All right reserved                  	*/
/*------------------------------------------------------*/

public class AvatarImage extends WebComponent {

	private static final Logger log = LogManager.getLogger(AvatarImage.class);

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;
	private IModel<String> context;
	private IModel<? extends DecorableElement> model;

	public AvatarImage(String id) {
		super(id);
		this.model = new Model<User>() {
			@Override
			public User getObject() {
				return serviceUser.getCurrentUser();
			}
		};
	}

	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		checkComponentTag(tag, "img");
		File file;
		String path = "/img/user.png";
		tag.put("src", BeerTenderApplication.get().getServletContext().getContextPath() + path);
	}
}