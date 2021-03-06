package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.*;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceTimeFrame;
import com.rectus29.beertender.web.BeerTenderConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 16 mars 2010
 * Time: 11:25:42
 * To change this template use File | Settings | File Templates.
 */
@Service("serviceOrder")
public class ServiceOrder extends GenericManagerImpl<Order, Long> implements IserviceOrder {

	private static final Logger log = LogManager.getLogger(BeerTenderConfig.class);

	private IserviceTimeFrame serviceTimeFrame;

	@Autowired
	public ServiceOrder(IserviceTimeFrame serviceTimeFrame) {
		super(Order.class);
		this.serviceTimeFrame = serviceTimeFrame;
	}

	public static boolean orderContains(Order order, Product product) {
		if (order != null && product != null) {
			for (OrderItem item : order.getOrderItemList()) {
				if (Objects.equals(item.getProduct(), product)) {
					return true;
				}
			}
		}
		return false;

	}

	@Override
	public Order getCurrentOrderFor(User user) {
		//retreive the current timeFrame
		TimeFrame timeFrame = serviceTimeFrame.getCurrentTimeFrame();
		//if no time frame retunr null
		if (timeFrame == null) {
			log.error("no active Time Frame");
			return null;
		} else {
			DetachedCriteria dc = getDetachedCriteria();
			dc.add(Restrictions.eq("user", user));
			dc.add(Restrictions.eq("timeFrame", timeFrame));
			List<Order> order = (List<Order>) getHibernateTemplate().findByCriteria(dc);
			if (order.isEmpty()){
				log.info("no Order for this user create one");
				return new Order(user, timeFrame);
			}else{
				if(State.DISABLE.equals(order.get(0).getState())){
					log.error("Order disable for user " + user.getUniqueId());
					return null;
				}else {
					return order.get(0);
				}
			}

		}
	}

	@Override
	public List<Order> getAll(State[] stateArray) {
		List<Order>  out =(List<Order>) getHibernateTemplate()
				.findByCriteria(
						getDetachedCriteria()
								.add(Restrictions.in("state", stateArray)
								)
				);
		return out;
	}
}