package com.rectus29.beertender.service.factory;
/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 13/06/2018 16:28               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.entities.core.GenericEntity;
import com.rectus29.beertender.service.GenericManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HibernateSessionFactory implements ApplicationListener<ContextRefreshedEvent>, ServiceFactory {

    private static final Logger log = LogManager.getLogger(HibernateSessionFactory.class);

    private Map<Class<? extends GenericEntity>, GenericManager<?, Long>> services
            = new HashMap<Class<? extends GenericEntity>, GenericManager<?, Long>>();

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext ctx = contextRefreshedEvent.getApplicationContext();
        services.clear();
        Map<String, GenericManager> detectedServices = ctx.getBeansOfType(GenericManager.class);
        for (GenericManager service : detectedServices.values()) {
            services.put(service.getEntityClass(), service);
        }
    }

    public <T extends GenericEntity> GenericManager<T, Long> getServiceFor(Class<T> entityClass) {
        return (GenericManager<T, Long>) services.get(entityClass);
    }
}
