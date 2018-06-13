package com.rectus29.beertender.dao.impl;

import com.rectus29.beertender.entities.crest.Type;
import com.rectuscorp.evetool.dao.IdaoType;
import com.rectuscorp.evetool.entities.crest.Type;
import com.rectuscorp.evetool.api.EveCRESTApi;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 11 avr. 2010
 * Time: 00:34:16
 * To change this template use File | Settings | File Templates.
 */
@Repository("daoType")
public class DaoType extends GenericDaoHibernate<Type, Long> implements IdaoType {

	public DaoType() {
		super(Type.class);
	}

}