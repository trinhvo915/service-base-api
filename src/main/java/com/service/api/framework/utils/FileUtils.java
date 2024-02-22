package com.service.api.framework.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;

public class FileUtils {

    /**
     * System temporary directory
     * <br>
     * Windows includes path separators, but Linux does not,
     * With windows \\==\,
     * <pre>
     *       java.io.tmpdir
     *       windows : C:\Users/xxx\AppData\Local\Temp\
     *       linux: /temp
     * </pre>
     */
    public static final String SYSTEM_TEMP_DIR = System.getProperty("java.io.tmpdir") + File.separator;

    /**
     * Define the calculation constant of GB
     */
    private static final int GB = 1024 * 1024 * 1024;

    /**
     * Define the calculation constant of MB
     */
    private static final int MB = 1024 * 1024;

    /**
     * Define the calculation constant of KB
     */
    private static final int KB = 1024;

    /**
     * Format decimal
     */
    private static final DecimalFormat DF = new DecimalFormat("0.00");

    public static final String IMAGE = "Image";
    public static final String TXT = "Text";
    public static final String MUSIC = "Music";
    public static final String VIDEO = "Video";
    public static final String OTHER = "Other";

    /**
     * Get the file extension with the dot
     */
    public static String getExtensionName(final String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Get file name without extension
     */
    public static String getFileNameNoEx(final String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * File Size Converter
     */
    public static String getSize(final long size) {
        String resultSize;
        if (size / GB >= 1) {
            // If the current Byte value is greater than or equal to 1GB
            resultSize = DF.format(size / (float) GB) + " GB";
        } else if (size / MB >= 1) {
            // If the current Byte value is greater than or equal to 1MB
            resultSize = DF.format(size / (float) MB) + " MB";
        } else if (size / KB >= 1) {
            // If the current Byte value is greater than or equal to 1KB
            resultSize = DF.format(size / (float) KB) + " KB";
        } else {
            resultSize = size + " B";
        }
        return resultSize;
    }

    /**
     * Get type file
     * @param type
     * @return String
     */
    public static String getFileType(final String type) {
        final String documents = "txt doc pdf ppt pptx pps xlsx xls docx";
        final String music = "mp3 wav wma mpa ram ra aac aif m4a";
        final String video = "avi mpg mpe mpeg asf wmv mov qt rm mp4 flv m4v webm ogv ogg";
        final String image = "bmp dib pcp dif wmf gif jpg tif eps psd cdr iff tga pcd mpt png jpeg";
        if (image.toLowerCase().contains(type.toLowerCase())) {
            return IMAGE;
        } else if (documents.toLowerCase().contains(type.toLowerCase())) {
            return TXT;
        } else if (music.toLowerCase().contains(type.toLowerCase())) {
            return MUSIC;
        } else if (video.toLowerCase().contains(type.toLowerCase())) {
            return VIDEO;
        } else {
            return OTHER;
        }
    }

    /**
     * Check file size
     * @param maxSize
     * @param size
     * @return boolean
     */
    public static boolean checkSize(final long maxSize, final long size) {
        // 1M
        int len = 1024 * 1024;
        return size > (maxSize * len);
    }

    /**
     * Check path is directory
     * @param path
     * @return boolean
     */
    public static boolean isDirectory(final Path path) {
        if (path == null) {
            return false;
        }
        return Files.isDirectory(path);
    }

    /**
     * Removed special characters for fileName
     * @param fileName
     * @return String
     */
    public String standardizedFileName(String fileName) {
        String ret = fileName;

        ret = ret.replace("\"", "'");
        ret = ret.replace("\\\\", "_");
        ret = ret.replaceAll("[/:*?<>|]", "_");
        return ret;
    }

    public static String makeFileNameWithTime(final String filename) {
        String ext = FileUtils.getExtensionName(filename);
        String name = FileUtils.getFileNameNoEx(filename);
        return String.format("%s-%d.%s", name, System.currentTimeMillis(), ext);
    }

    public static ByteArrayOutputStream cropToImage(InputStream input, int size) throws IOException {
        String formatName = "JPEG";
        BufferedImage image = ImageIO.read(input);
        int width = image.getWidth();
        int height = image.getHeight();

        if (width <= size && height <= size) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, formatName, os);
            return os; // no need to crop
        }

        int x = 0, y = 0;
        int croppedSize = Math.min(width, height);

        if (width > height) {
            x = (width - croppedSize) / 2;
        } else {
            y = (height - croppedSize) / 2;
        }

        BufferedImage croppedImage = new BufferedImage(croppedSize, croppedSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = croppedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, croppedSize, croppedSize, x, y, x + croppedSize, y + croppedSize, null);
        g.dispose();

        BufferedImage scaledImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        g = scaledImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(croppedImage, 0, 0, size, size, null);
        g.dispose();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(scaledImage, formatName, os);
        return os;
    }
}
