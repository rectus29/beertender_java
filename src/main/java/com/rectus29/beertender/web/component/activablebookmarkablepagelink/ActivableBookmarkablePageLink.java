package com.rectus29.beertender.web.component.activablebookmarkablepagelink;

/*-----------------------------------------------------*/

/* User: Rectus_29      Date: 03/03/2015 17:01 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Activable bookmarkable place automaticly the active class on hymself and on his parent if the activ page is his own targeted page
 */
public class ActivableBookmarkablePageLink extends BookmarkablePageLink {
    /**
     * {@inheritDoc}
     */
    public ActivableBookmarkablePageLink(String id, Class pageClass) {
        super(id, pageClass);
    }

    /**
     *  {@inheritDoc}
     */
    public ActivableBookmarkablePageLink(String id, Class pageClass, PageParameters parameters) {
        super(id, pageClass, parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.add(new AttributeAppender("class", (getPage().getPageClass().equals(this.getPageClass()))?" active":""));
        this.getParent().add(new AttributeAppender("class", (getPage().getPageClass().equals(this.getPageClass()))?" active":""));
    }
}
