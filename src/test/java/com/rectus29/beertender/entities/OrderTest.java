package com.rectus29.beertender.entities;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 22/10/2018 14:10               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class OrderTest {

	@Test
	public void addProduct() {

		TimeFrame dummyTimeFrame = new TimeFrame();
		Order toTestOrder = new Order();
		toTestOrder.setTimeFrame(dummyTimeFrame);

		ProductDefinition pd1  = new ProductDefinition().setName("product1");
		ProductDefinition pd2  = new ProductDefinition().setName("product2");
		ProductDefinition pd3  = new ProductDefinition().setName("product3");

		Product product1 = new Product();
		product1.setProductDefinition(pd1);
		product1.setPrice(new BigDecimal(2));
		product1.setPackaging(new Packaging().setName("packeaging1"));

		Product product2 = new Product();
		product2.setProductDefinition(pd2);
		product2.setPrice(new BigDecimal(6));
		product2.setPackaging(new Packaging().setName("packeaging1"));


		Product product3 = new Product();
		product3.setProductDefinition(pd2);
		product3.setPrice(new BigDecimal(6));
		product3.setPackaging(new Packaging().setName("packeaging2"));

		toTestOrder.addProduct(product1, 10);
		Assert.assertEquals(10, toTestOrder.getNbProductInOrder());

		toTestOrder.addProduct(product2, 5);
		Assert.assertEquals(15, toTestOrder.getNbProductInOrder());
		Assert.assertEquals(2, toTestOrder.getOrderItemList().size());

		toTestOrder.addProduct(product1, 5);
		Assert.assertEquals(20, toTestOrder.getNbProductInOrder());
		Assert.assertEquals(2, toTestOrder.getOrderItemList().size());

		toTestOrder.addProduct(product3, 5);
		Assert.assertEquals(25, toTestOrder.getNbProductInOrder());
		Assert.assertEquals(3, toTestOrder.getOrderItemList().size());

	}
}