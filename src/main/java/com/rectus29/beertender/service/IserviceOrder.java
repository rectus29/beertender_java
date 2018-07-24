package com.rectus29.beertender.service;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.User;

public interface IserviceOrder extends GenericManager<Order, Long> {

	Order getCurrentOrderFor(User user);
}
