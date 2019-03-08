package com.rectus29.beertender.web.component.avatarimage;

import com.rectus29.beertender.entities.IDecorableElement;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.exception.BeerTenderException;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.BeerTenderApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.Application;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;

/*------------------------------------------------------*/
/* User: Rectus_29      Date: 06/03/12 18:28 			*/
/*                                                     	*/
/*                 All right reserved                  	*/
/*------------------------------------------------------*/

public class AvatarImage extends Image {

	private static final Logger log = LogManager.getLogger(AvatarImage.class);
	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;
	private IModel<? extends IDecorableElement> model;
	private boolean isDefaultImg = false;

	public AvatarImage(String id) {
		super(id);
		this.model = new Model<User>() {
			@Override
			public User getObject() {
				return serviceUser.getCurrentUser();
			}
		};
		init();
	}

	public AvatarImage(String id, IModel<IDecorableElement> userIModel) {
		super(id);
		this.model = userIModel;
		init();
	}


	private void init() {
		try {
			if (model.getObject().getDecoration() == null) {
				throw new BeerTenderException("no image set use default");
			}
			Boolean isImage = ImageIO.read(new ByteArrayInputStream(model.getObject().getDecoration().getImageBytes())) != null;
			if (isImage) {
				DynamicImageResource imageResource = new DynamicImageResource() {
					@Override
					protected byte[] getImageData(Attributes attributes) {
						return model.getObject().getDecoration().getImageBytes();
					}
				};
				setImageResource(imageResource);
				setImageResources();
			} else {
				throw new BeerTenderException("no compatible media found");
			}
		} catch (Exception ex) {
			log.debug(ex);
			isDefaultImg = true;
		}

	}

	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		if (isDefaultImg) {
			tag.put("src", ((BeerTenderApplication) Application.get()).getAppCtx().getApplicationName() + "/img/defaultAvatar.png");
		}
	}
}