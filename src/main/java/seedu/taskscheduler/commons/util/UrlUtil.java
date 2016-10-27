package seedu.taskscheduler.commons.util;

import java.net.URL;

/**
 * An utility class for URL
 */
public class UrlUtil {

    /**
     * Returns true if both URLs have the same base URL
     */
    public static boolean compareBaseUrls(URL url1, URL url2) {

        if (url1 == null || url2 == null) {
            return false;
        }
        return url1.getHost().replaceFirst("www.", "")
                .equalsIgnoreCase(url2.getHost().replaceFirst("www.", "").toLowerCase())
                && url1.getPath().replaceAll("/", "")
                .equalsIgnoreCase(url2.getPath().replaceAll("/", "").toLowerCase());
    }

}
