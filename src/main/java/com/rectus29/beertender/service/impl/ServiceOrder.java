package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceTimeFrame;
import com.rectus29.beertender.web.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 16 mars 2010
 * Time: 11:25:42
 * To change this template use File | Settings | File Templates.
 */
@Service("serviceOrder")
public class ServiceOrder extends GenericManagerImpl<Order, Long> implements IserviceOrder {

	private static final Logger log = LogManager.getLogger(Config.class);

	private IserviceTimeFrame serviceTimeFrame;

	@Autowired
	public ServiceOrder(IserviceTimeFrame serviceTimeFrame) {
		super(Order.class);
		this.serviceTimeFrame = serviceTimeFrame;
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
			DetachedCriteria dc = DetachedCriteria.forClass(this.persistentClass);
			dc.add(Restrictions.eq("user", user));
			dc.add(Restrictions.eq("timeFrame", timeFrame));
			List<Order> order = (List<Order>) getHibernateTemplate().findByCriteria(dc);
			if (order.isEmpty()){
				log.info("no Order for this user create one id" + user.getUuid());
				return new Order(user, timeFrame);
			}else{
				if(State.DISABLE.equals(order.get(0).getState())){
					log.error("Order disable for user " + user.getUuid());
					return null;
				}else {
					return order.get(0);
				}
			}

		}
	}
}