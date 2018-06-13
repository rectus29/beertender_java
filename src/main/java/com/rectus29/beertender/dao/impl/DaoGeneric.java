package com.rectus29.beertender.dao.impl;

import com.rectus29.beertender.entities.core.GenericEntity;
import com.rectuscorp.evetool.dao.IdaoGeneric;
import com.rectuscorp.evetool.entities.core.GenericEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * User: Rectus_29
 * Date: 10/02/16
 */
@Repository("daoGeneric")
public class DaoGeneric<T> extends GenericDaoHibernate<GenericEntity, Long> implements IdaoGeneric {

	public DaoGeneric() {
		super(GenericEntity.class);
	}

	@Override
	public GenericEntity save(GenericEntity object) {
		object.setUpdated(new Date());
		return super.save(object);
	}

	public List<T> getAll(Class<T> persistentClass) {
		return hibernateTemplate.loadAll(persistentClass);
	}

}