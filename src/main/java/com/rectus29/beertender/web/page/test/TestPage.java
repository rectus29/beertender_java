package com.rectus29.beertender.web.page.test;

import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.web.component.switchbutton.SwitchButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

/*-----------------------------------------------------*/
/*                     Adelya                          */
/*                                                     */
/*                Date: 19/09/2018 17:19               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class TestPage extends WebPage {


	@SpringBean(name = "serviceProduct")
	private IserviceProduct serviceProduct;
	@SpringBean(name = "serviceCategory")
	private IserviceCategory serviceCategory;

	public TestPage() {
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();


		add(new SwitchButton("box") {
			@Override
			public void onPush(AjaxRequestTarget target) {
				System.out.println("yolo");
			}

			@Override
			public void onRelease(AjaxRequestTarget target) {
				System.out.println("yola");
			}
		});


	}
}
