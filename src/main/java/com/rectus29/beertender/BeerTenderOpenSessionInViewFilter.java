package com.rectus29.beertender;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 05/06/2019                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BeerTenderOpenSessionInViewFilter extends OpenSessionInViewFilter {

	@Override
	protected Session openSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
		try {
			Session session = sessionFactory.openSession();
			session.setFlushMode(FlushMode.AUTO);
			return session;
		} catch (HibernateException ex) {
			throw new DataAccessResourceFailureException("Could not open Hibernate Session", ex);
		}
	}
}
