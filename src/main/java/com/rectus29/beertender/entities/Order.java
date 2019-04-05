package com.rectus29.beertender.entities;


import com.rectus29.beertender.entities.payment.Payment;
import com.rectus29.beertender.enums.PaymentStatus;
import com.rectus29.beertender.enums.State;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * User: rectus_29
 * Date: 17/01/13
 * Time: 15:13
 */
@Entity
@Table(name = "bills", indexes = {
		@Index(name = "uniqueId", columnList = "uniqueId", unique = true)}
)
public class Order extends BasicGenericEntity<Order> {

	@OneToMany(targetEntity = OrderItem.class, cascade = CascadeType.ALL, mappedBy = "referenceOrder")
	private List<OrderItem> orderItemList = new ArrayList<>();

	@ManyToOne
	private User user;

	@ManyToOne(targetEntity = TimeFrame.class)
	@JoinColumn(name = "timeframe_id", nullable = false)
	private TimeFrame timeFrame;

	@OneToMany(targetEntity = Payment.class, mappedBy = "order")
	private List<Payment> paymentList = new ArrayList<>();
	private PaymentStatus paymentStatus = PaymentStatus.WAITING;

	public Order() {
		this.state = State.PENDING;
	}

	public Order(User user, TimeFrame timeFrame) {
		this();
		this.user = user;
		this.timeFrame = timeFrame;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public Order setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
		return this;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getOrderPrice() {
		BigDecimal res = new BigDecimal(0);
		for (OrderItem orderItem : orderItemList)
			res = res.add(orderItem.getOrderItemPrice());
		return res;
	}

	public int getNbProductInOrder() {
		int nbProduct = 0;
		for (OrderItem orderItem : orderItemList)
			nbProduct += orderItem.getQuantity();
		return nbProduct;
	}

	public Order addProduct(Product product, long qte) {
		OrderItem toAdd = new OrderItem(product, qte, this);
		if (orderItemList.contains(toAdd)) {
			OrderItem foundItem = null;
			for (OrderItem item : orderItemList) {
				if (toAdd.equals(item)) {
					foundItem = item;
					break;
				}
			}
			if (foundItem.getQuantity() + toAdd.getQuantity() > 0) {
				foundItem.setQuantity(foundItem.getQuantity() + toAdd.getQuantity());
			} else {
				removeProduct(product);
			}
		} else {
			orderItemList.add(new OrderItem(product, qte, this));
		}
		return this;
	}

	public Order removeProduct(Product product) {
		OrderItem toRemove = new OrderItem(product, 0L, this);
		for (OrderItem item : orderItemList) {
			if (toRemove.equals(item)) {
				orderItemList.remove(item);
				break;
			}
		}
		return this;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public TimeFrame getTimeFrame() {
		return timeFrame;
	}

	public void setTimeFrame(TimeFrame timeFrame) {
		this.timeFrame = timeFrame;
	}

	public List<Payment> getPaymentList() {
		return paymentList;
	}

	public Order setPaymentList(List<Payment> paymentList) {
		this.paymentList = paymentList;
		return this;
	}

	@Override
	public int compareTo(Order object) {
		return defaultCompareTo(object);
	}
}
