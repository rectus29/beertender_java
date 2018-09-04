package com.rectus29.beertender.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * User: rectus_29
 * Date: 05/02/13
 * Time: 14:32
 */
@Entity
@Table(name = "bills_item")
public class OrderItem extends GenericEntity<OrderItem> {

	@ManyToOne(targetEntity = Order.class)
	private Order referenceOrder;

	@ManyToOne
	private Product product;

	@Column(precision = 12, scale = 3)
	private BigDecimal productPrice = BigDecimal.ZERO;

	@Column
	private Long quantity = 0l;


	public OrderItem() {
	}

	public OrderItem(Product product, Long quantity, Order referenceOrder) {
		this.product = product;
		this.quantity = quantity;
		this.productPrice = this.product.getPrice();
		this.referenceOrder = referenceOrder;
	}

	public Product getProduct() {
		return product;
	}

	public OrderItem setProduct(Product product) {
		this.product = product;
		return this;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public OrderItem setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
		return this;
	}

	public Long getQuantity() {
		return quantity;
	}

	public OrderItem setQuantity(Long quantity) {
		this.quantity = quantity;
		return this;
	}


	public BigDecimal getOrderItemPrice() {
		return getProductPrice().multiply(new BigDecimal(getQuantity()));
	}

	public Order getReferenceOrder() {
		return referenceOrder;
	}

	public OrderItem setReferenceOrder(Order referenceOrder) {
		this.referenceOrder = referenceOrder;
		return this;
	}

	public double getSum() {
		return product.getPrice().multiply(new BigDecimal(quantity)).doubleValue();

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderItem orderItem = (OrderItem) o;
		return Objects.equals(referenceOrder, orderItem.referenceOrder) &&
				Objects.equals(product, orderItem.product) &&
				Objects.equals(productPrice, orderItem.productPrice);
	}

	@Override
	public int hashCode() {
		return Objects.hash(referenceOrder, product, productPrice);
	}

	@Override
	public int compareTo(OrderItem object) {
		return defaultCompareTo(object);
	}
}
