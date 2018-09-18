package com.rectus29.beertender.web.component.productimage;

import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.exception.BeerTenderException;
import com.rectus29.beertender.web.BeerTenderApplication;
import com.rectus29.beertender.web.page.admin.product.panels.edit.ProductAdminEditPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.Application;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.BlobImageResource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.sql.Blob;

/**
 * Created by IntelliJ IDEA.
 * User: Rectus for
 * Date: 06/03/12
 * Time: 15:13
 */
public class ProductImage extends Image {

	private static final Logger log = LogManager.getLogger(ProductAdminEditPanel.class);
	private IModel<Product> model;

	public ProductImage(String id, IModel<Product> productModel) {
		super(id);
		this.model = productModel;
	}

	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);

		try {
			if (model.getObject().getFileImage() == null) {
				throw new BeerTenderException("no image set use default");
			}
			Boolean isImage = ImageIO.read(new ByteArrayInputStream(model.getObject().getFileImage())) != null;
			if (isImage) {
				DynamicImageResource imageResource = new DynamicImageResource() {
					@Override
					protected byte[] getImageData(Attributes attributes) {
						return model.getObject().getFileImage();
					}
				};
				setImageResource(imageResource);
			}else{
				throw new BeerTenderException("no comptible media found");
			}
		} catch (Exception ex) {
			log.debug(ex);
			tag.put("src", ((BeerTenderApplication) Application.get()).getAppCtx().getApplicationName() + "/img/products/default-bottle.png");
		}

	}
}