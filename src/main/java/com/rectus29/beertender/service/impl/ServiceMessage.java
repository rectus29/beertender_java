package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.mail.Message;
import com.rectus29.beertender.service.IserviceMessage;
import org.springframework.stereotype.Service;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 08/03/2019                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Service("serviceMessage")
public class ServiceMessage extends GenericManagerImpl<Message, Long> implements IserviceMessage {

	public ServiceMessage() {
		super(Message.class);
	}

}
