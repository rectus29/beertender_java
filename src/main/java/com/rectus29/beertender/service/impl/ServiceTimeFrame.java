package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceTimeFrame;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 16 mars 2010
 * Time: 11:25:42
 * To change this template use File | Settings | File Templates.
 */
@Service("serviceTimeFrame")
public class ServiceTimeFrame extends GenericManagerImpl<TimeFrame, Long> implements IserviceTimeFrame {


    public ServiceTimeFrame() {
        super(TimeFrame.class);
    }

	@Override
	public TimeFrame getCurrentTimeFrame() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.persistentClass);
		detachedCriteria.add(Restrictions.eq("state", State.ENABLE));
		List<TimeFrame> result = (List<TimeFrame>) getHibernateTemplate().findByCriteria(detachedCriteria);
		if (result.size() == 0)
			return null;
		return result.get(0);
	}
}