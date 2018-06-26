package com.rectus29.beertender.entities;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 12/08/2016 10:34               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.enums.State;

import java.io.Serializable;

public interface Individual extends Serializable {

    public Long getId();
    public String getFormattedName();
    public String getLastName();
    public String getFirstName();
    public State getState();
    public String getAvatarPath();
}
