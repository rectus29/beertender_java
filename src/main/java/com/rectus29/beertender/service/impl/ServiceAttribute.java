package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.crest.Attribute;
import com.rectuscorp.evetool.api.EveCRESTApi;
import com.rectuscorp.evetool.dao.impl.DaoAttribute;
import com.rectuscorp.evetool.entities.crest.Attribute;
import com.rectuscorp.evetool.service.IserviceAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 16 mars 2010
 * Time: 11:25:42
 */
@Service("serviceAttribute")
public class ServiceAttribute extends GenericManagerImpl<Attribute, Long> implements IserviceAttribute {

	private DaoAttribute daoAttribute;

	@Autowired
	public ServiceAttribute(DaoAttribute daoAttribute) {
		super(daoAttribute);
		this.daoAttribute = daoAttribute;
	}

	@Override
	public Attribute get(Long id) {
		Attribute out =  super.get(id);
		//if value is null try to  get from Crest server
		if(out == null){
			out = EveCRESTApi.get().getAttribute(id.toString());
			out = save(out);
		}
		return out;
	}
}