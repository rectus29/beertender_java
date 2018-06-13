package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.crest.Corporation;
import com.rectuscorp.evetool.dao.impl.DaoCorporation;
import com.rectuscorp.evetool.entities.crest.Corporation;
import com.rectuscorp.evetool.service.IserviceCorporation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Rectus_29
 * Date: 10/02/16
 */
@Service("serviceCorporation")
public class ServiceCorporation extends GenericManagerImpl<Corporation, Long> implements IserviceCorporation {

	private DaoCorporation daoCorporation;

	@Autowired
	public ServiceCorporation(DaoCorporation dao) {
		super(dao);
		this.daoCorporation = dao;
	}

}