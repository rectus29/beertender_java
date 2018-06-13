package com.rectus29.beertender.web.panel.lazyloadPanel;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;


/*-----------------------------------------------------*/
/*                    by Rectus_29                     */
/*                Date: 18/02/16 11:20                 */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class LazyLoadPanel extends AjaxLazyLoadPanel {

    private String htmlText = getString("current-computation") +"<br/>"+ getString("please-wait");

    public LazyLoadPanel(String id) {
        super(id);
    }

    public LazyLoadPanel(String id, String htmlText) {
        super(id);
        this.htmlText = htmlText;
    }

    public LazyLoadPanel(String id, IModel<?> model) {
        super(id, model);
    }

    @Override
    public Component getLoadingComponent(String markupId) {
        return new LoaderPanel(markupId);
    }

    protected void handleCallbackScript(IHeaderResponse response, String callbackScript) {
        super.handleCallbackScript(response, callbackScript, this);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
    }

    @Override
    public Component getLazyLoadComponent(String markupId) {
        return null;
    }

    public String getText(){
        return htmlText;
    }

    private class LoaderPanel extends Panel {
        private LoaderPanel(String id) {
            super(id);
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();
            add(new Label("loaderTxt", getText()).setEscapeModelStrings(false));
        }
    }
}
