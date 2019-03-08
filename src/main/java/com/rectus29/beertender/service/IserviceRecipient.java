package com.rectus29.beertender.service;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.entities.mail.Recipient;

import java.util.List;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 08/03/2019                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public interface IserviceRecipient extends GenericManager<Recipient, Long> {

	public Recipient save(Recipient c);

	public List<Recipient> getUnreadFor(User u);

	public List<Recipient> getAllFor(User u);
}
