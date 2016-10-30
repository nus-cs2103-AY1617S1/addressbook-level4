package seedu.agendum.commons.util;

import java.net.URL;

/**
 * A utility class for URL
 */
public class UrlUtil {

    /**
     * Returns true if both URLs have the same base URL
     */
    public static boolean compareBaseUrls(URL url1, URL url2) {

        return !(url1 == null || url2 == null) && url1.getHost().replaceFirst("www.", "").equalsIgnoreCase(url2.getHost().replaceFirst("www.", "")) && url1.getPath().replaceAll("/", "").equalsIgnoreCase(url2.getPath().replaceAll("/", ""));
    }

}
