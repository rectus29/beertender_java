package com.rectus29.beertender.web.page;

import com.rectus29.beertender.web.panel.menucontributionpanel.MenuElement;

import java.util.List;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 30/06/2016 11:54               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public interface IMenuContributor {

	List<MenuElement> getMenuContribution();
}
