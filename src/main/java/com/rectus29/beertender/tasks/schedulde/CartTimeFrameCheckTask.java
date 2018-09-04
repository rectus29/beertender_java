package com.rectus29.beertender.tasks.schedulde;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 30/06/2016 15:20               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.entities.Config;
import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceConfig;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceTimeFrame;
import com.rectus29.beertender.tasks.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * this task check every cart to  check the time frame and disable cart if needed
 */
@Transactional
@Service
public class CartTimeFrameCheckTask extends Task {

	private static final Logger log = LogManager.getLogger(CartTimeFrameCheckTask.class);

	@Autowired
	@Qualifier("serviceTimeFrame")
	private IserviceTimeFrame serviceTimeFrame;

	@Autowired
	@Qualifier("serviceOrder")
	private IserviceOrder serviceOrder;

	@Override
	//minute, hour, day of month, month, and day of week
	//@Scheduled(cron = "0 * * * * *")
	public void process() {
		log.debug("start task");

		//disable timeframe in past
		for(Order order : serviceOrder.getAll(new State[]{State.ENABLE})){
			if(order.getTimeFrame().getEndDate().before(new Date())){
				log.debug("disable timeFrame #" + order.getId());
				order.setState(State.DISABLE);
			}
		}
		//enable the first found
		for(Order order : serviceOrder.getAll(new State[]{State.PENDING})){
			if(order.getTimeFrame().getStartDate().after(new Date())){
				log.debug("enable timeFrame #" + order.getId());
				order.setState(State.ENABLE);
			}
		}
		log.debug("task done");

	}
}
