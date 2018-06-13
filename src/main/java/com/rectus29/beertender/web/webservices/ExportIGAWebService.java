package com.rectus29.beertender.web.webservices;



/*-----------------------------------------------------*/
/*                    by Rectus_29                     */
/*                Date: 18/02/16 11:20                 */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import com.rectuscorp.evetool.web.Config;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.wicket.markup.MarkupType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ExportIGAWebService extends WebPage {

    private static final Logger log = LogManager.getLogger(ExportIGAWebService.class);

    private static String USERID = "userId";
    private static String FORMID = "formId";
    private String userId = null;
    private String formId = null;

    public ExportIGAWebService(PageParameters pp) {
        userId = pp.get(this.USERID).toString();
        formId = pp.get(this.FORMID).toString();
    }

    @Override
    protected void onRender() {
        PrintWriter pw = new PrintWriter(getResponse().getOutputStream());
        String data = null;
        log.debug("Request > " + userId + " | " + formId);
       /* try {
           data = FileUtils.readFileToString(new File(Config.get().getXMLExportFolder().getPath() + File.separator + formId + "_" + userId + ".xml"));
        } catch (IOException e) {
            log.error("Error while web service export " + e.getMessage(), e);
            data = "";
        }*/
        pw.write(data);
        pw.close();
    }

    @Override
    public MarkupType getMarkupType() {
        return new MarkupType("xml", MarkupType.XML_MIME);
    }
}
