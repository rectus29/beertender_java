package com.rectus29.beertender.tools;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 08/04/11
 * Time: 09:48
 * To change this template use File | Settings | File Templates.
 */
public class XmlUtils {

    private static final Logger log = LogManager.getLogger(XmlUtils.class);

    public static String asXMLWithoutHeaders(Node n){
        OutputFormat format = new OutputFormat("",false);
        format.setOmitEncoding(true);
        format.setSuppressDeclaration(true);
        format.setExpandEmptyElements(true);
        format.setPadText(true);
        format.setTrimText(true);

        StringWriter buffer = new StringWriter();
        XMLWriter writer = new XMLWriter(buffer, format);
        try {
            writer.write(n);
        } catch (IOException e) {
            log.error("erreur d'écriture");
        }

        return buffer.toString();
    }

    public static String asXMLWithoutHeaders(Document doc){
        OutputFormat format = new OutputFormat("",false);
        format.setOmitEncoding(true);
        format.setSuppressDeclaration(true);
        format.setExpandEmptyElements(true);
        format.setPadText(true);
        format.setTrimText(true);

        StringWriter buffer = new StringWriter();
        XMLWriter writer = new XMLWriter(buffer, format);
        try {
            writer.write(doc);
        } catch (IOException e) {
            log.error("erreur d'écriture");
        }

        return buffer.toString();
    }

}
