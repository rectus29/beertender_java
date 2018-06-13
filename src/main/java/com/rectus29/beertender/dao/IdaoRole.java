package com.rectus29.beertender.dao;


import com.rectus29.beertender.entities.core.Role;
import com.rectus29.beertender.entities.core.User;
import com.rectuscorp.evetool.entities.core.Role;
import com.rectuscorp.evetool.entities.core.User;

import java.util.List;


/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 11:22 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public interface IdaoRole {

    public Role getRoleByName(String roleName);

    public List<Role> getAuthorizedRole(User u);


}
