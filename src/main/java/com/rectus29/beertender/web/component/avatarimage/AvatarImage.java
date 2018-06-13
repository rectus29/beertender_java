package com.rectus29.beertender.web.component.avatarimage;

import com.rectus29.beertender.entities.DecorableElement;
import com.rectus29.beertender.entities.crest.Corporation;
import com.rectus29.beertender.service.IserviceUser;
import com.rectuscorp.evetool.entities.DecorableElement;
import com.rectuscorp.evetool.entities.core.Character;
import com.rectuscorp.evetool.entities.crest.Corporation;
import com.rectuscorp.evetool.service.IserviceUser;
import com.rectuscorp.evetool.api.EveXmlApi;
import com.rectuscorp.evetool.web.Config;
import com.rectuscorp.evetool.web.EveToolApplication;
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
		this.model = new Model<Character>() {
			@Override
			public Character getObject() {
				return serviceUser.getCurrentUser().getMainCharacter();
			}
		};
	}

	public AvatarImage(String id, Character model) {
		super(id);
		this.model = new Model<Character>(model);
	}

	public AvatarImage(String id, Corporation model) {
		super(id);
		this.model = new Model<Corporation>(model);
	}

	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		checkComponentTag(tag, "img");
		File file;
		String path = "/img/user.png";
		if (model.getObject() instanceof Character) {
			file = new File(Config.get().getCharacterFolder() + File.separator + model.getObject().getId() + "_256.jpg");
			if(!file.exists())
				EveXmlApi.get().getImage(model.getObject());
			if (file.exists()) {
				path = "/files/avatar/character/" + file.getName();
			}
		}else if (model.getObject() instanceof Corporation){
			file = new File(Config.get().getCorporationFolder() + File.separator + model.getObject().getId() + "_256.png");
			if(!file.exists())
				EveXmlApi.get().getImage(model.getObject());
			if (file.exists()) {
				path = "/files/avatar/corporation/" + file.getName();
			}
		}
		tag.put("src", EveToolApplication.get().getServletContext().getContextPath() + path);
	}
}