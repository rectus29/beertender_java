package com.rectus29.beertender.event;/**

 /*-----------------------------------------------------*/
/* User: rectus_29 for         Date: 21/12/12 11:22 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import org.apache.wicket.Component;
import org.apache.wicket.IEventDispatcher;
import org.apache.wicket.event.IEvent;

import java.lang.reflect.Method;

public class DispatchOnEventMethod implements IEventDispatcher {

    @Override
    public void dispatchEvent(Object sink, IEvent<?> event, Component component) {
        Method[] sinkMethods = sink.getClass().getMethods();
        for (Method sinkMethod : sinkMethods) {
            if (sinkMethod.isAnnotationPresent(OnEvent.class)) {
                if (sinkMethod.getParameterTypes()[0] == event.getPayload().getClass())
                    try {
                        sinkMethod.invoke(sink, event.getPayload());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
            }
        }
    }
}