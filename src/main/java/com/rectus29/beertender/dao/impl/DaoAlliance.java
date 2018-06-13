package com.rectus29.beertender.dao.impl;

import com.rectus29.beertender.entities.crest.Alliance;
import com.rectuscorp.evetool.dao.IdaoAlliance;
import com.rectuscorp.evetool.entities.crest.Alliance;
import com.rectuscorp.evetool.api.EveCRESTApi;
import org.springframework.stereotype.Repository;

/**
 * User: Rectus_29
 * Date: 10/02/16
 */
@Repository("daoAlliance")
public class DaoAlliance extends GenericDaoHibernate<Alliance, Long> implements IdaoAlliance {

	public DaoAlliance() {
		super(Alliance.class);
	}

	@Override
	public Alliance get(Long id) {
		Alliance out = super.get(id);
		if (out == null) {
			out = EveCRESTApi.get().getAlliance(id.toString());
			if (out != null) {
				out = save(out);
			}
		}
		return out;
	}

}