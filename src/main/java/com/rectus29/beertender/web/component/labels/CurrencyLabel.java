package com.rectus29.beertender.web.component.labels;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

import java.math.BigDecimal;

/**
 * User: rectus_29
 * Date: 04/04/13
 * Time: 09:43
 * Project : mismacore
 */
public class CurrencyLabel extends Label {

    public CurrencyLabel(String id, IModel<?> model) {
        super(id, model);
    }

    public CurrencyLabel(String id) {
        this(id, null);
    }

    public IConverter getConverter(Class type) {
        if(type == BigDecimal.class)
            return new BigDecimalCurrencyConverter();
        return new DoubleCurrencyConverter();
    }
}
