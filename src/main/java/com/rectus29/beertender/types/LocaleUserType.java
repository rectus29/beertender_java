package com.rectus29.beertender.types;

import org.apache.commons.lang3.LocaleUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Locale;

/*-----------------------------------------------------*/
/*                     Adelya                          */
/*                                                     */
/*                Date: 08/11/2018 14:36               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class LocaleUserType implements UserType {

	@Override
	public int[] sqlTypes() {
		return new int[]{Types.VARCHAR};
	}

	@Override
	public Class returnedClass() {
		return Locale.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x != null)
			return x.equals(y);
		return false;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return (x).hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
		String value = rs.getString(names[0]);
		return (value != null ) ? LocaleUtils.toLocale(value) : null;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.VARCHAR);
		} else {
			st.setString(index, ((Locale) value).toString());
		}
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}
}
