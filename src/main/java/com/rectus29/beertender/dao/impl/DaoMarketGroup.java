package com.rectus29.beertender.dao.impl;

import com.rectus29.beertender.entities.crest.MarketGroup;
import com.rectuscorp.evetool.dao.IdaoMarketGroup;
import com.rectuscorp.evetool.entities.crest.MarketGroup;
import org.springframework.stereotype.Repository;

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
@Repository("daoMarketGroup")
public class DaoMarketGroup extends GenericDaoHibernate<MarketGroup, Long> implements IdaoMarketGroup {

	public DaoMarketGroup() {
		super(MarketGroup.class);
	}

}