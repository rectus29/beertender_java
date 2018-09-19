package com.rectus29.beertender.web.page.test;

import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.web.component.productimage.ProductImage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.DynamicImageResource;
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


		add(new Image("testimg", new DynamicImageResource() {
			@Override
			protected byte[] getImageData(Attributes attributes) {
				return serviceProduct.get(6l).getFileImage();
			}
		}));

		add(new ProductImage("productImage", new Model<Product>(serviceProduct.get(5l))));
	}
}
