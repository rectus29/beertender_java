package com.rectus29.beertender.web;

import com.rectus29.beertender.service.IserviceConfig;
import com.rectus29.beertender.spring.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.util.file.Folder;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 11:22 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Transactional
public class Config implements Serializable{

    private static final Logger log = LogManager.getLogger(Config.class);
	private IserviceConfig serviceConfig;
    private static Config ourInstance = new Config();
    public static final String RESOURCE_PATH = "files";
	private Folder uploadFolder = null, rootFolder = null,XMLExportFolder = null,  resourceFolder = null;
	private String dateFormat = "dd/MM/yyyy";
    private String fullDateFormat = dateFormat + " HH:mm:ss";
	private SimpleDateFormat dateFormater;
	private PeriodFormatter durationFormater;
    private DecimalFormat formatter;
    private DecimalFormat currencyFormatter;
    private String defaultColor[] = {};

    public Config() {

    }

    public static Config get() {
        return ourInstance;
    }


	public void set(String rootPath) {
		rootFolder = new Folder(rootPath);
		uploadFolder = new Folder(System.getProperty("java.io.tmpdir"), "upload_tmp");
		uploadFolder.mkdirs();
		XMLExportFolder = new Folder(rootPath + File.separator + "XMLExportFile");
		XMLExportFolder.mkdirs();
		dateFormater = new SimpleDateFormat(dateFormat);
		durationFormater = new PeriodFormatterBuilder()
				.appendHours().printZeroAlways().minimumPrintedDigits(2).appendSeparator(":")
				.appendMinutes().printZeroAlways().minimumPrintedDigits(2).appendSeparator(":")
				.appendSeconds().printZeroAlways().minimumPrintedDigits(2)
				.toFormatter();
		formatter = new DecimalFormat("###,###,###,###.##");
		formatter.setMinimumFractionDigits(2);
		currencyFormatter = new DecimalFormat("###,###,###,###.00");
		resourceFolder = new Folder(rootFolder, File.separator + RESOURCE_PATH);
		resourceFolder.mkdirs();
		dateFormat = "dd/MM/yyyy";
		fullDateFormat = dateFormat + " HH:mm:ss";
		this.serviceConfig = (IserviceConfig)  AppContext.getApplicationContext().getBean("serviceConfig");
		defaultColor = (serviceConfig.getByKey("graphColor") != null)?serviceConfig.getByKey("graphColor").getValue().replaceAll(" ","").split(","): defaultColor;
	}

    public Folder getUploadFolder() {
        return uploadFolder;
    }

    public String getAbsoluteRessourcePath() {
        return "/" + RESOURCE_PATH;
    }

    public String getAbsoluteProductRessourcePath() {
        return "/" + RESOURCE_PATH + "/product";
    }

    public void setUploadFolder(Folder uploadFolder) {
        this.uploadFolder = uploadFolder;
    }

    public Folder getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(Folder rootFolder) {
        this.rootFolder = rootFolder;
    }

    public Folder getResourceFolder() {
        return new Folder(rootFolder, File.separator + RESOURCE_PATH);
    }

    public Folder getProductResourceFolder() {
        return new Folder(rootFolder, File.separator + RESOURCE_PATH + File.separator + "product");
    }

	public String getDefaultColor() {
        String out = "";
		for (String aDefaultColor : defaultColor) {
			out += "'" + aDefaultColor + "',";
		}
        return out.substring(0, out.length() - 1);
    }

	public String getFullDateFormat() {
		return fullDateFormat;
	}

	public String getDateFormat() {
		return this.dateFormat;
	}

    public String dateFormat(Date date) {
        return dateFormater.format(date);
    }

	public String dateHourFormat(Date date) {
        dateFormater.applyPattern(getFullDateFormat());
        String out = dateFormater.format(date);
        dateFormater.applyPattern(getDateFormat());
        return out;
    }

    public String dateFormat(java.sql.Date date) {
        return dateFormater.format(date);
    }

    public String dateFormat(String pattern, Date date) {
        dateFormater.applyPattern(pattern);
        String out = dateFormater.format(date);
        dateFormater.applyPattern(getDateFormat());
        return out;
    }

    public String format(double number) {
        return formatter.format(number);
    }

    public String formatCurrency(double number) {
        return currencyFormatter.format(number);
    }

}
