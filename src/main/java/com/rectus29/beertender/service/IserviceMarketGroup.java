package com.rectus29.beertender.service;

import com.rectus29.beertender.entities.crest.MarketGroup;
import com.rectuscorp.evetool.entities.crest.MarketGroup;

import java.util.List;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 12/10/2016 10:46               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public interface IserviceMarketGroup extends GenericManager<MarketGroup, Long> {

	public List<MarketGroup> getAllRootMarketGroup();
}