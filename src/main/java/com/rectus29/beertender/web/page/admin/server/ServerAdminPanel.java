package com.rectus29.beertender.web.page.admin.server;

/*-----------------------------------------------------*/

/* User: Rectus_29       Date: 09/08/13 11:55 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/



import com.rectus29.beertender.entities.Config;
import com.rectus29.beertender.service.IserviceConfig;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.bootstrapfeedbackpanel.BootstrapFeedbackPanel;
import com.rectus29.beertender.web.page.admin.AdminPage;
import com.rectus29.beertender.web.page.admin.server.panels.SchedulerPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ServerAdminPanel extends Panel {

    @SpringBean(name = "serviceConfig")
    private IserviceConfig serviceConfig;
    @SpringBean(name = "serviceUser")
    private IserviceUser serviceUser;
    private boolean isSuperAdmin;
    private String msgAdmin;
    private String supportAdr;
    private String supportPhone;
    private String server_url;


    public ServerAdminPanel(String id) {
        super(id);
        isSuperAdmin = serviceUser.getCurrentUser().hasRole("superadmin");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final WebMarkupContainer wmcStatus = new WebMarkupContainer("status") {
            @Override
            public boolean isVisible() {
                return isSuperAdmin;
            }
        };
        wmcStatus.setOutputMarkupId(true);
        add(wmcStatus);

        Label online = new Label("online", new LoadableDetachableModel<String>() {
            @Override
            protected String load() {
                return "0".equals(serviceConfig.getByKey("maintenance_mod").getValue()) ? "online" : "offline";
            }
        });
        wmcStatus.add(online.add(new AttributeAppender("class", (serviceConfig.getByKey("maintenance_mod").getValue().equals("0"))?" label-warning":"")));

        AjaxLink switchMode = new AjaxLink("switchmode") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                Config c = serviceConfig.getByKey("maintenance_mod");
                c.setValue(c.getValue().equals("0") ? "1" : "0");
                serviceConfig.save(c);
                ajaxRequestTarget.add(wmcStatus);
            }
        };
        wmcStatus.add(switchMode);

        /****************** post Message ********************/

        msgAdmin = serviceConfig.getByKey("admin_msg").getValue();

        final Form msgPostForm = new Form("msgPost");
        add(msgPostForm);

        TextField tmsgAdmin = new TextField("msgAdmin", new PropertyModel(this, "msgAdmin"));
        msgPostForm.add(tmsgAdmin);

        AjaxSubmitLink msgPostSubmit = new AjaxSubmitLink("msgPostSubmit") {

            @Override
            protected void onSubmit(AjaxRequestTarget ajaxRequestTarget, Form<?> form) {
                Config c = serviceConfig.getByKey("admin_msg");
                c.setValue(msgAdmin == null ? "" : msgAdmin);
                serviceConfig.save(c);
                setResponsePage(new AdminPage());
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                //TODO feedback
            }
        };
        msgPostForm.add(msgPostSubmit);



        Config serverUrlConfig = serviceConfig.getByKey("server_url");
        server_url = serverUrlConfig.getValue();
        add(new Form("serverUrlForm")
                .add(new TextField<String>("serverUrl", new PropertyModel<String>(this, "server_url")))
                .add(new BootstrapFeedbackPanel("serverUrlfeed"))
                .add(new AjaxSubmitLink("serverUrlSubmit") {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        Config c = serviceConfig.getByKey("server_url");
                        if (c == null) {
                            c = new Config();
                            c.setKey("server_url");
                        }
                        c.setValue(server_url == null ? "" : server_url);
                        serviceConfig.save(c);
                        success(new ResourceModel("success").getObject());
                        target.add(form);
                    }

                    @Override
                    protected void onError(AjaxRequestTarget target, Form<?> form) {
                        target.add(form);
                    }
                })
                .setOutputMarkupId(true)
        );

        add(new SchedulerPanel("schedulerPanel"));


    }
}
