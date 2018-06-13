package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.crest.Type;
import com.rectuscorp.evetool.api.EveCRESTApi;
import com.rectuscorp.evetool.dao.impl.DaoType;
import com.rectuscorp.evetool.entities.crest.Type;
import com.rectuscorp.evetool.service.IserviceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 16 mars 2010
 * Time: 11:25:42
 * To change this template use File | Settings | File Templates.
 */
@Service("serviceType")
public class ServiceType extends GenericManagerImpl<Type, Long> implements IserviceType {

	private DaoType daoType;

	@Autowired
	public ServiceType(DaoType daoType) {
		super(daoType);
		this.daoType = daoType;
	}

	@Override
	public Type get(Long id) {
		Type out = super.get(id);
		if (out == null) {
			out = EveCRESTApi.get().getType(id.toString());
			if (out != null) {
				out = save(out);
			}
		}
		return out;
	}

}