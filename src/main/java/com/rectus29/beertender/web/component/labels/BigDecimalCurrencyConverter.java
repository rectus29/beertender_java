package com.rectus29.beertender.web.component.labels;

import org.apache.wicket.util.convert.converter.AbstractNumberConverter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * User: rectus_29
 * Date: 04/04/13
 * Time: 09:41
 * Project : mismacore
 */
public class BigDecimalCurrencyConverter extends AbstractNumberConverter {
    @Override
    public NumberFormat getNumberFormat(Locale locale) {
        return NumberFormat.getCurrencyInstance(Locale.FRANCE);
    }

    @Override
    protected Class getTargetType() {
        return BigDecimal.class;
    }

	@Override
	protected NumberFormat newNumberFormat(Locale locale) {
		return getNumberFormat(locale);
	}

    public Object convertToObject(String s, Locale locale) {
        // string to bigdecimal.
        try {
            return newNumberFormat(locale).parse(s);
        } catch (ParseException ex) {
            throw new RuntimeException("exception when trying to parse currency value:" + ex.getMessage());
        }
    }

    @Override
    public String convertToString(Object value, Locale locale) {
        // bigdecimal to string
        return getNumberFormat(locale).format(value);
    }

}
