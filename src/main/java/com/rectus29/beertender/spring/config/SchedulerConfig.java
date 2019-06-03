package com.rectus29.beertender.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 08/04/2019                  */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
//@Configuration
//@EnableScheduling()
public class SchedulerConfig implements SchedulingConfigurer {
	private static final Logger LOG = LoggerFactory.getLogger(SchedulerConfig.class);
	private final int POOL_SIZE = 10;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
		threadPoolTaskScheduler.setThreadNamePrefix("BeerTender-task-pool-");
		threadPoolTaskScheduler.initialize();
		taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
	}
}
