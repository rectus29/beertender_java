package com.rectus29.beertender.tools;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 15 avr. 2010
 * Time: 16:21:07
 * To change this template use File | Settings | File Templates.
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private static final Logger log = LogManager.getLogger(FileUtils.class);

    public static String getExtension(String filename) {
        int pos = filename.lastIndexOf(".");
        if (pos != -1)
            return filename.substring(pos + 1, filename.length());
        return "";
    }

    public static String getTimeStamp() {
        // create a calendar instance
        Calendar calendar = Calendar.getInstance();

        // get a java.util.Date from the Calendar instance.
        // this date will represent the current instant, or "now".
        java.util.Date now = calendar.getTime();

        // create a JDBC Timestamp instance
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        return new Long(currentTimestamp.getTime()).toString();
    }

    public static String readFileAsString(String filePath) throws IOException {
        return readFileAsString(new File(filePath));
    }


    public static String readFileAsString(File file) throws IOException {
        byte[] buffer = new byte[(int) file.length()];
        BufferedInputStream f = new BufferedInputStream(new FileInputStream(file));
        f.read(buffer);
        return new String(buffer, "UTF-8");
    }

    public static void writeStringToFile(String txt, File outFile) throws IOException {
        FileWriter out = new FileWriter(outFile);
        out.write(txt);
        out.close();
    }


    public static void copyFile(File src, File dest) throws IOException {
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(dest);

        java.nio.channels.FileChannel channelSrc = fis.getChannel();
        java.nio.channels.FileChannel channelDest = fos.getChannel();

        channelSrc.transferTo(0, channelSrc.size(), channelDest);

        fis.close();
        fos.close();
    }


}
