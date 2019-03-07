package com.rectus29.beertender.web.page.profile;

import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.avatarimage.AvatarImage;
import com.rectus29.beertender.web.page.base.BeerTenderPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/*-----------------------------------------------------*/
/*						rectus29					   */
/*                Date: 28/02/2016                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class ProfilePage extends BeerTenderPage {

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;

	public ProfilePage() {
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Label("userName", serviceUser.getCurrentUser().getFormattedName()));
		add(new Label("userRole", serviceUser.getCurrentUser().getRole().getName()));
		add(new AvatarImage("userAvatar", new Model(serviceUser.getCurrentUser())));
	}

	@Override
	public String getTitleContribution() {
		return "Account";
	}

}
