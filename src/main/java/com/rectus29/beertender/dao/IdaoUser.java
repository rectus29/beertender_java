package com.rectus29.beertender.dao;


import com.rectus29.beertender.entities.core.User;
import com.rectus29.beertender.entities.core.User;

import java.util.List;

/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 11:22 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public interface IdaoUser extends GenericDao<User, Long> {



    public User getUserByUsername(String username);


    public User getUserByMail(String property);


}
