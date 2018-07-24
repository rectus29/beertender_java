package com.rectus29.beertender.entities;


import com.rectus29.beertender.enums.State;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: rectus_29
 * Date: 17/01/13
 * Time: 15:13
 */
@Entity
@Table(name = "bills")
public class Order extends GenericEntity {

	@OneToMany(targetEntity = OrderItem.class)
	@MapKey( name = "referenceOrder")
	private Map<Long, OrderItem> orderItemList = new HashMap<>();

	@ManyToOne
	private User user;

	@Column
	private State state = State.PENDING;

	@ManyToOne(targetEntity = TimeFrame.class)
	@JoinColumn(name = "timeframe_id", nullable = false)
	private TimeFrame timeFrame;

	public Order() {
	}

	public Order(User user, TimeFrame timeFrame) {
		this.user = user;
		this.timeFrame = timeFrame;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getOrderPrice() {
		BigDecimal res = new BigDecimal(0);
		for (OrderItem orderItem : orderItemList.values())
			res = res.add(orderItem.getOrderItemPrice());
		return res;
	}

	public Order addProduct(Product product, long qte) {
		if (orderItemList.get(product.getId()) != null) {
			if (orderItemList.get(product.getId()).getQuantity() + qte >= 0) {
				orderItemList.get(product.getId()).setQuantity(orderItemList.get(product.getId()).getQuantity() + qte);
			} else {
				removeProduct(product);
			}
		} else {
			orderItemList.put(product.getId(), new OrderItem(product, qte));
		}
		return this;
	}

	public Order removeProduct(Product product) {
		orderItemList.remove(product.getId());
		return this;
	}


	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
