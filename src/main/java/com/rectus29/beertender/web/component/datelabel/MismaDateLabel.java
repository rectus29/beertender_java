package com.rectus29.beertender.web.component.datelabel;


/*-----------------------------------------------------*/
/* User: Rectus for          Date: 03/04/13 10:12 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import com.rectus29.beertender.web.Config;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import java.io.Serializable;
import java.util.Date;

public class MismaDateLabel<T extends Date> extends Label {

    public MismaDateLabel(String id) {
        super(id);
    }

    public MismaDateLabel(String id, String label) {
        super(id, label);
    }

    public MismaDateLabel(String id, Serializable label) {
        super(id, label);
    }

    public MismaDateLabel(String id, IModel<?> model) {
        super(id, model);
    }

    @Override
    public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        Date d =  (Date)getDefaultModelObject();
        if(d == null){
            replaceComponentTagBody(markupStream, openTag, "-");
        }                                                  else{
            replaceComponentTagBody(markupStream, openTag, Config.get().dateFormat(d));
        }
    }
}
