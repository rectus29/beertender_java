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
public class Order extends GenericEntity<Order> {

	@OneToMany(targetEntity = OrderItem.class, cascade = CascadeType.ALL, mappedBy = "referenceOrder")
	private List<OrderItem> orderItemList = new ArrayList<>();

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
		for (OrderItem orderItem : orderItemList)
			res = res.add(orderItem.getOrderItemPrice());
		return res;
	}

	public Order addProduct(Product product, long qte) {
		OrderItem toAdd = new OrderItem(product, qte, this);
		if (orderItemList.contains(toAdd)) {
			OrderItem foundItem = null;
			for(OrderItem item : orderItemList){
				if(toAdd.equals(item)){
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
        OrderItem toRemove = new OrderItem(product, 0l, this);
        for(OrderItem item : orderItemList){
            if(toRemove.equals(item)){
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public int compareTo(Order object) {
		return defaultCompareTo(object);
	}
}
