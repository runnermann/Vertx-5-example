package com.runnermann.wolf.example.utility;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * For media file names we are using a similar hash algorithm based on the images
 * that the user is saved. The file name is derived from the (for example) the
 * appearance of the image. Since most hashing algorithms change drastically by small
 * changes, we use a different hashing algorithm in order to tell if the files are similar.
 * See https://reposhub.com/java/imagery/KilianB-JImageHash.html
 * Credit to Kilian Brachtendorf for this hashing capability. MIT license
 * <p>
 * File Naming provides a unique name, and provides a location in local storage and S3.
 * Image and Video fileNames are the following syntax. UserHash_DeckFileName_MediaHash.mime
 * Shapes fileNames are UserHash_DeckFileName_MediaHash_millis.shp
 * DeckNames are the creatorHash_distributorHash$Deckname.dec
 *
 * @author Lowell Stadelman
 */

public class NamingUtility {

    //private final static ch.qos.logback.classic.Logger LOGGER = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(NamingUtility.class);
    private static final Logger LOGGER = LoggerFactory.getLogger(NamingUtility.class);

    // *** contains the resulting name  ***
    private String fileName;


    public NamingUtility() { /* no args constructor */ }

    /**
     * <p>Creates a fileName for images, Use getImgFileName to retrieve the
     * fileName. Does not include the path.</p>
     *
     * @param hash   either the imageHash or prefered unique information for video and audio
     * @param location The location for the file such as img-profile, or img-athlete.
     * @param ending The file type ending including the leading ".". All image files should be .webp
     */
    public NamingUtility(String hash, String location, String ending) {
        // if file is an image
        setMediaFileName(hash + "-" + location, ending);
    }


    public String getMimeType(String fileName) {
        int n = fileName.lastIndexOf('.');
        if (5 < n && n > 2 ) {
            return fileName.substring(n + 1).toLowerCase();
        }
        else {
            return "!failed";
        }
    }

    public static String hashToHex(String s) {
        return DigestUtils
                .md5Hex(s);
    }

    /**
     * This method extracts the decks common s3 media sub directory name.
     * Ref s3 media storage: A decks media is always stored in the original hash
     * subdirectory. The parent deck, and the child decks share the same s3 Dir
     * allowing us to conserve memory and transfers when a deck is shared between
     * users. Decks and Media do not share the same s3 buckets. The first hash is the
     * original deck hash, and the second hash is this child's hash. Media in s3 is stored
     * using the first hash in the deck file name.
     *
     * @param deckFileName
     * @return The common s3 media directory name
     */
    public static final String getDeckMediaSubDir(String deckFileName) {
        String hash = deckFileName.substring(0, deckFileName.indexOf('_'));
        String deckName = deckFileName.substring(deckFileName.indexOf('$'), deckFileName.length() - 4);
        return hash + "_0" + deckName;
    }

    public static final String getQRFileName(String deckFileName) {
        deckFileName = deckFileName.replace(" ", "_");
        return deckFileName.substring(0, deckFileName.length() - 4) + ".png";
    }


    private void setMediaFileName(String mediaName, String ending) {
        String temp = mediaName.replaceAll(" ", "_");
        this.fileName = temp + ending;
    }


    /**
     * The name of the image file
     *
     * @return Returns the media file Name
     */
//    public final String getMediaFileName() {
//        String temp = fileName.replaceAll(" ", "_");
//        return temp;
//    }

    public final String getQRCloudFileName() {
        String temp = fileName.replaceAll(" ", "_");
        temp = "qr" + temp;
        return temp;
    }

     public final String getKeyName() {
        return this.fileName;
     }


    public static String getVideoName(String cID, char uOrL, String mime) {
        int num = cID.indexOf("_");
        return videoName(cID.substring(0, num) + uOrL + "_" + System.currentTimeMillis(), mime);
    }

    private static String videoName(String name, String mime) {
        return 'v' + name + "v." + mime;
    }


    /**
     * Helper method for getHash
     */
    private static String bytesToHex(byte[] bytes) {
        char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
        char[] chars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int x = 0x0F & bytes[i];
            chars[i * 2] = HEX_CHARS[x >>> 4];
            chars[1 + i * 2] = HEX_CHARS[0x0F & x];
        }
        return new String(chars);
    }

    public static final String getDeckLabelName(String str) {
        int num = str.indexOf("$");
        return str.substring(0, num - 1);
    }

    /**
     * Filters a string and removes any non-legal identifiers.
     * Note that this removes almost everything except alpha numeric
     * characters including some characters which are legal working
     * java identifiers such as '@' and '$'.
     *
     * @param str
     * @return
     */
    public static final String nameSanitize(String str) {
        StringBuilder sb = new StringBuilder();

        str.codePoints()
                .filter(c -> {
                    if (sanitizeHelper(c)) {
                        return true;
                    } else {
                        return Character.isJavaIdentifierPart(c);
                    }
                }).forEach(sb::appendCodePoint);
        return sb.toString();
    }

    /**
     * any non character is deleted from the nameSanitize method.
     * If there are any characters that should be allowable, add
     * it in this method.
     *
     * @param c the character to be evaluated.
     * @return true if we allow this character, false otherwise.
     */
    private static final boolean sanitizeHelper(int c) {
        switch (c) {
            case '_':
            case '@':
            case ' ': {
                return true;
            }
            default: {
                return false;
            }
        }
    }

}

