package com.rectus29.beertender.tasks.schedulde;

/*-----------------------------------------------------*/
/*                       Rectus29                      */
/*                Date: 30/06/2016 15:20               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.enums.State;
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

@Transactional
@Service
public class TimeFrameTask extends Task {

	private static final Logger log = LogManager.getLogger(TimeFrameTask.class);

	@Autowired
	@Qualifier("serviceTimeFrame")
	private IserviceTimeFrame serviceTimeFrame;

	@Override
	@Scheduled(cron = "0 0 * * * *")
	public void process() {
		log.debug("start timeFrame Management task");

		//disable timeframe in past
		for(TimeFrame tf : serviceTimeFrame.getAll(new State[]{State.ENABLE})){
			if(tf.getEndDate().before(new Date())){
				log.debug("disable timeFrame #" + tf.getId());
				tf.setState(State.DISABLE);
				//lock all the linked Order
				for (Order order : tf.getOrderList()) {
					order.setState(State.LOCKED);
				}
			}
		}
		//enable the first found
		for(TimeFrame tf : serviceTimeFrame.getAll(new State[]{State.PENDING})){
			if(tf.getStartDate().before(new Date()) && tf.getEndDate().after(new Date())){
				log.debug("enable timeFrame #" + tf.getId());
				tf.setState(State.ENABLE);
				//unlock all the linked Order
				for (Order order : tf.getOrderList()) {
					order.setState(State.PENDING);
				}
			}
		}

		log.debug("timeFrame task done");
	}
}
