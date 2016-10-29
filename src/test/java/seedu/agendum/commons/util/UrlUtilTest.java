package seedu.agendum.commons.util;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the UrlUtil methods.
 */
public class UrlUtilTest {

    @Test
    public void compareBaseUrlsDifferentCapitalSuccess() throws MalformedURLException {
        URL url1 = new URL("https://www.Google.com/a");
        URL url2 = new URL("https://www.google.com/A");
        assertTrue(UrlUtil.compareBaseUrls(url1, url2));
    }

    @Test
    public void compareBaseUrlsTestWithAndWithoutWwwSuccess() throws MalformedURLException {
        URL url1 = new URL("https://google.com/a");
        URL url2 = new URL("https://www.google.com/a");
        assertTrue(UrlUtil.compareBaseUrls(url1, url2));
    }

    @Test
    public void compareBaseUrlsDifferentSlashesSuccess() throws MalformedURLException {
        URL url1 = new URL("https://www.Google.com/a/acb/");
        URL url2 = new URL("https://www.google.com/A/acb");
        assertTrue(UrlUtil.compareBaseUrls(url1, url2));
    }

    @Test
    public void compareBaseUrlsDifferentUrlFail() throws MalformedURLException {
        URL url1 = new URL("https://www.Google.com/a/ac_b/");
        URL url2 = new URL("https://www.google.com/A/acb");
        assertFalse(UrlUtil.compareBaseUrls(url1, url2));
    }

    @Test
    public void compareBaseUrlsNullFalse() throws MalformedURLException {
        URL url1 = new URL("https://www.Google.com/a/ac_b/");
        URL url2 = new URL("https://www.google.com/A/acb");
        assertFalse(UrlUtil.compareBaseUrls(url1, null));
        assertFalse(UrlUtil.compareBaseUrls(null, url2));
        assertFalse(UrlUtil.compareBaseUrls(null, null));
    }
}
