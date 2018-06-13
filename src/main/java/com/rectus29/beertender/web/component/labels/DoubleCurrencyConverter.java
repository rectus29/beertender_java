package com.rectus29.beertender.web.component.labels;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.converter.AbstractNumberConverter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * User: rectus_29
 * Date: 04/04/13
 * Time: 09:41
 * Project : mismacore
 */
public class DoubleCurrencyConverter extends AbstractNumberConverter {
	@Override
	public NumberFormat getNumberFormat(Locale locale) {
		return NumberFormat.getCurrencyInstance(Locale.FRANCE);
	}

	@Override
	protected Class getTargetType() {
		return Double.class;
	}

	@Override protected NumberFormat newNumberFormat(Locale locale) {
		return getNumberFormat(locale);
	}

	public Object convertToObject(String s, Locale locale) throws ConversionException {
		try {
			return newNumberFormat(locale).parse(s).doubleValue();
		} catch (ParseException e) {
			return null;
		}
	}

	@Override
	public String convertToString(Object value, Locale locale) {
		// double to string
		return getNumberFormat(locale).format(value);
	}

}
