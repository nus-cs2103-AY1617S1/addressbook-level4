package tars.commons.util;

import org.junit.Assert;
import org.junit.Test;

public class DateTimeUtilTest {

    @Test
    public void extract_date_successful() {
        String[] expectedDateTime = {"", "01/01/2016 1500"};
        String[] actualDateTime = DateTimeUtil.getDateTimeFromArgs("1/1/2016 1500");

        Assert.assertArrayEquals(expectedDateTime, actualDateTime);
    }
}
