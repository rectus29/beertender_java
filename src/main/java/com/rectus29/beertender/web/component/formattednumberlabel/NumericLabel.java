package com.rectus29.beertender.web.component.formattednumberlabel;


/*-----------------------------------------------------*/
/* User: Rectus for          Date: 03/04/13 10:57 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import com.rectus29.beertender.web.Config;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

public class NumericLabel<T extends Number> extends Label {

    public NumericLabel(String id) {
        super(id);
    }

    public NumericLabel(String id, String label) {
        super(id, label);
    }

    public NumericLabel(String id, Serializable label) {
        super(id, label);
    }

    public NumericLabel(String id, IModel<?> model) {
        super(id, model);
    }

    @Override
    public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        Number n = (Number) getDefaultModelObject();
        if (n == null) {
            replaceComponentTagBody(markupStream, openTag, "-");
        } else {
            replaceComponentTagBody(markupStream, openTag, Config.get().format(n.doubleValue()));
        }
    }
}
