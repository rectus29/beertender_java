package com.rectus29.beertender.api.typeresolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.rectus29.beertender.entities.Product;
import org.apache.commons.codec.binary.Base64;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 23/10/2018 10:33               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class ProductTypeResolver implements GraphQLResolver<Product> {

	public String packaging(Product product) {
		return product.getPackaging().getName();
	}

	public String fileImage(Product product) {
		if (product.getFileImage() != null && product.getFileImage().getImageBytes().length > 0) {
			return Base64.encodeBase64String(product.getFileImage().getImageBytes());
		} else {
			return "";
		}
	}
}
