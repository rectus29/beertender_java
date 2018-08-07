package com.rectus29.beertender.service;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.State;

import java.util.List;

public interface IserviceOrder extends GenericManager<Order, Long> {

	Order getCurrentOrderFor(User user);

	List<Order> getAll(State[] stateArray);
}
