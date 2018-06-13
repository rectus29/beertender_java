package com.rectus29.beertender.dao;

import com.rectus29.beertender.entities.core.GenericEntity;
import com.rectuscorp.evetool.entities.core.GenericEntity;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IncrementGenerator;

import java.io.Serializable;

/**
 * Created by Rectus_29 on 10/02/2016.
 */
public class EveToolIDGenerator extends IncrementGenerator {

	@Override
	public synchronized Serializable generate(SharedSessionContractImplementor session,
			Object object) throws HibernateException {
		if(((GenericEntity) object).getId() != null)
			return ((GenericEntity) object).getId();
		return super.generate(session, object);
	}
}
