package com.rectus29.beertender.dao;


import com.rectus29.beertender.entities.core.Config;
import com.rectuscorp.evetool.entities.core.Config;


/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 11:22 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public interface IdaoConfig extends GenericDao<Config, Long> {
    public Config getByKey(String key);
}
