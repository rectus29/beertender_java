package com.rectus29.beertender.service;

import com.rectus29.beertender.entities.User;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 27/09/2018 14:41               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public interface IserviceMail {

	public void sendEmail(final Long userId, final String subject, final String content);

	public void sendRestoreMail(final User user, final String session);
}
