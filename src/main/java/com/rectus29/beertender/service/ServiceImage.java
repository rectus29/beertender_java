package com.rectus29.beertender.service;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 18/09/2018 22:11                */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * An implementation of ImageService.
 *
 * @author pchapman
 */
class ImageServiceImpl {

    private static final Log logger = LogFactory.getLog(ImageServiceImpl.class);

    /**
     * Creates a new instance.
     */
    public ImageServiceImpl(){
        super();
    }
/*
    public byte[] getImage(ImageEntry imageEntry) throws IOException {
        if (isImageAvailable(imageEntry)) {
            // Open the file, then read it in.
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            InputStream inStream =
                    new FileInputStream(new File(imageEntry.getFileName()));
            copy(inStream, outStream);
            inStream.close();
            outStream.close();
            return outStream.toByteArray();
        } else {
            return createNotAvailableImage(imageEntry.getContentType());
        }
    }

    public Date getLastModifyTime(ImageEntry imageEntry) {
        File f = new File(imageEntry.getFileName());
        return new Date(f.lastModified());
    }

    public boolean isImageAvailable(ImageEntry imageEntry) {
        return (
                new File(imageEntry.getFileName()).exists() &&
                        new File(imageEntry.getThumbName()).exists()
        );
    }




    public byte[] getThumbnail(ImageEntry imageEntry)
            throws IOException
    {
        if (isImageAvailable(imageEntry)) {
            // Open the file, then read it in.
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            InputStream inStream =
                    new FileInputStream(new File(imageEntry.getThumbName()));
            copy(inStream, outStream);
            inStream.close();
            outStream.close();
            return outStream.toByteArray();
        } else {
            byte[] imageData = createNotAvailableImage(imageEntry.getContentType());
            return scaleImage(imageData, getThumbnailSize());
        }
    }



    /**
     * Copies data from src into dst.
     */
    private void copy(InputStream source, OutputStream destination)
            throws IOException
    {
        try {
            // Transfer bytes from source to destination
            byte[] buf = new byte[1024];
            int len;
            while ((len = source.read(buf)) > 0) {
                destination.write(buf, 0, len);
            }
            source.close();
            destination.close();
            if (logger.isDebugEnabled()) {
                logger.debug("Copying image...");
            }
        } catch (IOException ioe) {
            logger.error(ioe);
            throw ioe;
        }
    }
/*
    private File createImageFile(String suffix)
    {
        UUID uuid = UUID.randomUUID();
        File file = new File(imageDir, uuid.toString() + suffix);
        if (logger.isDebugEnabled()) {
            logger.debug("File " + file.getAbsolutePath() + " created.");
        }
        return file;
    }

    private byte[] createNotAvailableImage(String contentType)
            throws IOException
    {
        // Load the "Image Not Available"
        // image from jar, then write it out.
        StringBuffer name = new StringBuffer("com/mysticcoders/resources/ImageNotAvailable.");
        if ("image/jpeg".equalsIgnoreCase(contentType)) {
            name.append("jpg");
        } else if ("image/png".equalsIgnoreCase(contentType)) {
            name.append("png");
        } else {
            name.append("gif");
        }
        URL url = getClass().getClassLoader().getResource(
                name.toString()
        );
        InputStream in = url.openStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in, out);
        in.close();
        out.close();
        return out.toByteArray();
    }


    public void save(ImageEntry imageEntry, InputStream imageStream)
            throws IOException
    {
        // Read in the image data.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copy(imageStream, baos);
        baos.close();
        byte[] imageData = baos.toByteArray();
        baos = null;

        // Get the image's suffix
        String suffix = null;
        if ("image/gif".equalsIgnoreCase(imageEntry.getContentType())) {
            suffix = ".gif";
        } else if ("image/jpeg".equalsIgnoreCase(imageEntry.getContentType())) {
            suffix = ".jpeg";
        } else if ("image/png".equalsIgnoreCase(imageEntry.getContentType())) {
            suffix = ".png";
        }

        // Create a unique name for the file in the image directory and
        // write the image data into it.
        File newFile = createImageFile(suffix);
        OutputStream outStream = new FileOutputStream(newFile);
        outStream.write(imageData);
        outStream.close();
        imageEntry.setFileName(newFile.getAbsolutePath());

        // Create a thumbnail
        newFile = createImageFile(".jpeg");
        byte[] thumbnailData = scaleImage(imageData, getThumbnailSize());
        outStream = new FileOutputStream(newFile);
        outStream.write(thumbnailData);
        outStream.close();
        imageEntry.setThumbName(newFile.getAbsolutePath());

        // Save the image info in the database
        imageEntryDAO.save(imageEntry);
    }
*/
    private byte[] scaleImage(byte[] imageData, int maxSize)
            throws IOException
    {
        if (logger.isDebugEnabled()) {
            logger.debug("Scaling image...");
        }
        // Get the image from a file.
        Image inImage = new ImageIcon(imageData).getImage();

        // Determine the scale.
        double scale = (double) maxSize / (double) inImage.getHeight(null);
        if (inImage.getWidth(null) > inImage.getHeight(null)) {
            scale = (double) maxSize / (double) inImage.getWidth(null);
        }

        // Determine size of new image.
        // One of the dimensions should equal maxSize.
        int scaledW = (int) (scale * inImage.getWidth(null));
        int scaledH = (int) (scale * inImage.getHeight(null));

        // Create an image buffer in which to paint on.
        BufferedImage outImage = new BufferedImage(
                scaledW, scaledH, BufferedImage.TYPE_INT_RGB
        );

        // Set the scale.
        AffineTransform tx = new AffineTransform();

        // If the image is smaller than the desired image size,
        // don't bother scaling.
        if (scale < 1.0d) {
            tx.scale(scale, scale);
        }

        // Paint image.
        Graphics2D g2d = outImage.createGraphics();
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.drawImage(inImage, tx, null);
        g2d.dispose();

        // JPEG-encode the image
        // and write to file.
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
        encoder.encode(outImage);
        os.close();
        if (logger.isDebugEnabled()) {
            logger.debug("Scaling done.");
        }
        return os.toByteArray();
    }
}