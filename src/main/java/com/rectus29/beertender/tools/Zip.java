package com.rectus29.beertender.tools;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.ZipOutputStream;

public class Zip {

    private static final String ZIP_EXTENSION = ".zip";

    private static final int DEFAULT_LEVEL_COMPRESSION =
            Deflater.BEST_COMPRESSION;

    // Remplace l'extension si le fichier cible ne fini pas par '.zip'
    private static File getZipTypeFile(final File source, final File target)
            throws IOException {
        if (target.getName().toLowerCase().endsWith(ZIP_EXTENSION))
            return target;
        final String tName = target.isDirectory()
                ? source.getName()
                : target.getName();
        final int index = tName.lastIndexOf('.');
        return new File(
                new StringBuilder(target.isDirectory()
                        ? target.getCanonicalPath()
                        : target.getParentFile().getCanonicalPath())
                        .append(File.separatorChar)
                        .append(index < 0 ? tName : tName.substring(0, index))
                        .append(ZIP_EXTENSION)
                        .toString()
        );
    }

    // Compresse un fichier
    private static void compressFile(final ZipOutputStream out,
                                           final String parentFolder, final File file)
            throws IOException {
        final String zipName = new StringBuilder(parentFolder)
                .append(file.getName())
                .append(file.isDirectory() ? '/' : "")
                .toString();

        // Définition des attributs du fichier
        final ZipArchiveEntry entry = new ZipArchiveEntry(zipName);
        entry.setSize(file.length());
        entry.setTime(file.lastModified());
        out.putNextEntry(entry);

        // Traitement récursif s'il s'agit d'un répertoire
        if (file.isDirectory()) {
            for (final File f : file.listFiles())
                compressFile(out, zipName.toString(), f);
            return;
        }

        // Ecriture du fichier dans le zip
        final InputStream in = new BufferedInputStream(
                new FileInputStream(file));
        try {
            final byte[] buf = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = in.read(buf)))
                out.write(buf, 0, bytesRead);
        } finally {
            in.close();
        }
    }
    public static void compressFiles(final File file, final File target)throws IOException {
        final File source = file.getCanonicalFile();

        // Création du fichier zip
        final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                getZipTypeFile(source, target.getCanonicalFile())));
        out.setMethod(ZipOutputStream.DEFLATED);
        out.setLevel(DEFAULT_LEVEL_COMPRESSION);

        // Définition des attributs du fichier
        // Traitement récursif s'il s'agit d'un répertoire
        /*compressFile(out,"", file.listFiles()[0]);
        compressFile(out,"", file.listFiles()[1]);*/
        if (file.isDirectory()) {
            for (final File f : file.listFiles())
                compressFile(out,"", f);
        }

        out.close();
    }
    // Compresse un fichier à l'adresse pointée par le fichier cible.
    // Remplace le fichier cible s'il existe déjà.
    public static void compress(final File file, final File target,final int compressionLevel) throws IOException {
        final File source = file.getCanonicalFile();

        // Création du fichier zip
        final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                getZipTypeFile(source, target.getCanonicalFile())));
        out.setMethod(ZipOutputStream.DEFLATED);
        out.setLevel(compressionLevel);

        // Ajout du(es) fichier(s) au zip
        compressFile(out, "", source);
        out.close();
    }

    public static void compress(final File file, final int compressionLevel)
            throws IOException {
        compress(file, file, compressionLevel);
    }

    public static void compress(final File file, final File target)
            throws IOException {
        compress(file, target, DEFAULT_LEVEL_COMPRESSION);
    }

    public static void compress(final File file) throws IOException {
        compress(file, file, DEFAULT_LEVEL_COMPRESSION);
    }

    public static void compress(final String fileName, final String targetName,
                                final int compressionLevel) throws IOException {
        compress(new File(fileName), new File(targetName), compressionLevel);
    }

    public static void compress(final String fileName,
                                final int compressionLevel) throws IOException {
        compress(new File(fileName), new File(fileName), compressionLevel);
    }

    public static void compress(final String fileName, final String targetName)
            throws IOException {
        compress(new File(fileName), new File(targetName),
                DEFAULT_LEVEL_COMPRESSION);
    }

    public static void compress(final String fileName) throws IOException {
        compress(new File(fileName), new File(fileName),
                DEFAULT_LEVEL_COMPRESSION);
    }

    // Décompresse un fichier zip à l'adresse indiquée par le dossier
    public static void decompress(final File file, final File outputFile,
                                  final boolean deleteZipAfter) throws IOException {

        FileInputStream is = new FileInputStream(file);
        final ArchiveInputStream in;
        try {
            in = new ArchiveStreamFactory().createArchiveInputStream("zip", is);

            outputFile.mkdirs();


            try {
                ArchiveEntry entry;
                entry = in.getNextEntry();
                while (entry != null) {

                    if (entry.isDirectory()) {
                        new File(outputFile, entry.getName()).mkdirs();
                    } else {

                        //correction des chemin fichier en fonction de l'OS
                        String newPath = entry.getName().replace("\\", File.separator);
                        final File fileEntry = new File(outputFile, newPath);
                        fileEntry.getParentFile().mkdirs();

                        final OutputStream out;
                        out = new FileOutputStream(fileEntry);
                        try {
                            IOUtils.copy(in, out);
                        } catch (final IOException e) {
                            throw new IOException(e);
                        } finally {
                            out.close();
                        }
                    }
                    entry = in.getNextEntry();
                }
            } finally {
                in.close();
            }
        } catch (ArchiveException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    // Décompresse un fichier zip à l'adresse indiquée par le dossier
    /*public static void decompress(final File file, final File folder,
                                  final boolean deleteZipAfter)
            throws IOException {
        final ZipInputStream zis = new ZipInputStream(new BufferedInputStream(
                new FileInputStream(file.getCanonicalFile())));
        ZipEntry ze;
        try {
            // Parcourt tous les fichiers
            while (null != (ze = zis.getNextEntry())) {
                //correction des chemin fichier en fonction de l'OS
                String newPath = ze.getName().replace("\\", File.separator);
                final File f = new File(folder.getCanonicalPath(), newPath);
                if (f.exists())
                    f.delete();

                // Création des dossiers
                if (ze.isDirectory()) {
                    f.mkdirs();
                    continue;
                }
                f.getParentFile().mkdirs();
                final OutputStream fos = new BufferedOutputStream(
                        new FileOutputStream(f));

                // Ecriture des fichiers
                try {
                    try {
                        final byte[] buf = new byte[8192];
                        int bytesRead;
                        while (-1 != (bytesRead = zis.read(buf)))
                            fos.write(buf, 0, bytesRead);
                    } finally {
                        fos.close();
                    }
                } catch (final IOException ioe) {
                    f.delete();
                    throw ioe;
                }
            }
        } finally {
            zis.close();
        }
        if (deleteZipAfter)
            file.delete();
    } */

    public static void decompress(final String fileName, final String folderName,
                                  final boolean deleteZipAfter)
            throws IOException {
        decompress(new File(fileName), new File(folderName), deleteZipAfter);
    }

    public static void decompress(final String fileName, final String folderName)
            throws IOException {
        decompress(new File(fileName), new File(folderName), false);
    }

    public static void decompress(final File file, final boolean deleteZipAfter)
            throws IOException {
        decompress(file, file.getCanonicalFile().getParentFile(), deleteZipAfter);
    }

    public static void decompress(final String fileName,
                                  final boolean deleteZipAfter)
            throws IOException {
        decompress(new File(fileName), deleteZipAfter);
    }

    public static void decompress(final File file)
            throws IOException {
        decompress(file, file.getCanonicalFile().getParentFile(), false);
    }

    public static void decompress(final String fileName)
            throws IOException {
        decompress(new File(fileName));
    }
 }