package com.rectus29.beertender.web.component.BootstrapTabbedPAnel;


/*-----------------------------------------------------*/
/* User: Rectus for          Date: 11/01/13 11:14 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.list.LoopItem;

import java.util.List;

public class BootstrapAjaxTabbedPanel<T extends ITab> extends AjaxTabbedPanel {

    public BootstrapAjaxTabbedPanel(String id, List tabs) {
        super(id, tabs);
    }

    @Override
    protected LoopItem newTabContainer(final int tabIndex) {
        return new LoopItem(tabIndex) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onComponentTag(final ComponentTag tag) {
                super.onComponentTag(tag);
                String cssClass = tag.getAttribute("class");
                if (cssClass == null) {
                    cssClass = " ";
                }
                cssClass += " tab" + getIndex();

                if (getIndex() == getSelectedTab()) {
                    cssClass += " active";
                }
                if (getIndex() == getTabs().size() - 1) {
                    cssClass += " last";
                }
                tag.put("class", cssClass.trim());
            }

            @Override
            public boolean isVisible() {
                return ((ITab)getTabs().get(tabIndex)).isVisible();
            }
        };
    }


}
