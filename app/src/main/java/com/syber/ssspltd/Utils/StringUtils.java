package com.syber.ssspltd.Utils;

public class StringUtils {
    // Private constructor to prevent instantiation
    private StringUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Method to check if a URL contains ".pdf"
    public static boolean containsPdf(String url) {
        if (url != null) {
            return url.toLowerCase().contains(".pdf");
        }
        return false;
    }


}
