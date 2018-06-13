package com.rectus29.beertender.dao.impl;

import com.rectus29.beertender.entities.crest.Corporation;
import com.rectuscorp.evetool.dao.IdaoCorporation;
import com.rectuscorp.evetool.entities.crest.Corporation;
import com.rectuscorp.evetool.api.EveCRESTApi;
import org.apache.wicket.Application;
import org.springframework.stereotype.Repository;

/**
 * User: Rectus_29
 * Date: 10/02/16
 */
@Repository("daoCorporation")
public class DaoCorporation extends GenericDaoHibernate<Corporation, Long> implements IdaoCorporation {

	public DaoCorporation() {
		super(Corporation.class);
	}

	@Override
	public Corporation get(Long id) {
		//Application.get().
		Corporation out = super.get(id);
		if (out == null) {
			out = EveCRESTApi.get().getCorporation(id.toString());
			if (out != null) {
				out = save(out);
			}
		}
		return out;
	}
}