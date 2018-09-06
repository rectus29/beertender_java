package com.rectus29.beertender.service.impl;


import com.rectus29.beertender.entities.Packaging;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IservicePackaging;
import com.rectus29.beertender.tools.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: rectus_29
 * Date: 03 sept. 2012
 * Time: 05:32:27
 */

@Service("servicePackaging")
public class ServicePackaging extends GenericManagerImpl<Packaging, Long> implements IservicePackaging {


	public ServicePackaging() {
		super(Packaging.class);
	}

	@Override
	public List<Packaging> getAll(List<State> stateList) {
		List<Packaging> out = new ArrayList<>();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.getEntityClass());
		detachedCriteria.add(Restrictions.in("state",stateList));
		out.addAll((List<Packaging>)getHibernateTemplate().findByCriteria(getDetachedCriteria()));
		return out;
	}

	@Override
	public Packaging save(Packaging object) {
		//clean and set the short name
		String cleanValue = StringUtils.stripAccents(object.getName());
		cleanValue = StringUtils.deleteWhitespace(cleanValue);
		object.setShortName(cleanValue);
		return super.save(object);
	}
}

