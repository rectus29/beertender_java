package com.rectus29.beertender.web.security.forgotpassword;

import com.rectus29.beertender.entities.core.User;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.BootStrapFeedbackPanel.BootStrapFeedbackPanel;
import com.rectus29.beertender.web.page.base.BasePage;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Date;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Rectus for
 * Date: 29/11/11
 * Time: 11:15
 */
public class ForgotPasssword extends BasePage {

    private static final Logger log = LogManager.getLogger(ForgotPasssword.class);

    @SpringBean(name = "serviceUser")
    private IserviceUser serviceUser;
//
//    @SpringBean(name = "serviceMail")
//    private IserviceMail serviceMail;

    private String identity;

    public ForgotPasssword() {

        Form form = new Form("wocForgotPasswordForm");
        add(form);

        final TextField id = new TextField("identifier", new PropertyModel(this, "identity"));
        form.add(id);

        final FeedbackPanel feed = new BootStrapFeedbackPanel("feedback");
        feed.setOutputMarkupId(true);
        add(feed);

        final IndicatingAjaxButton submit = new IndicatingAjaxButton("submit") {
            @Override
            protected void onSubmit(AjaxRequestTarget ajaxRequestTarget, Form<?> form) {
                try {
                    User test = serviceUser.getByProperty("accountID", identity, true);
                    if (test == null) {
                        test = serviceUser.getUserByMail(identity);
                    }
                    if (test != null) {
                        String session = UUID.randomUUID().toString();
                        test.setRestoreSession(session);
                        test.setRestoreSessionDate(new Date());
                       // serviceMail.sendRestoreMail(test, session);
                        serviceUser.save(test);
                        info(getString("success"));
                        id.setEnabled(false);
                        this.setEnabled(false);
                    } else {
                        error(getString("noaccount"));

                    }
                } catch (Exception ex) {
                    error(getString("error"));
                }

                ajaxRequestTarget.add(feed, id, this);
            }

            @Override
            protected void onError(AjaxRequestTarget ajaxRequestTarget, Form<?> form) {
                error(getString("error"));
                ajaxRequestTarget.add(feed);
            }
        };
        form.add(submit);


    }

    @Override
    public String getTitleContribution() {
        return getString("title");
    }
}
