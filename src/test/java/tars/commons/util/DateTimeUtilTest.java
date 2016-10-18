package tars.commons.util;

import static org.junit.Assert.*;

import java.time.DateTimeException;

import org.junit.Assert;
import org.junit.Test;

import tars.model.task.DateTime;
import tars.model.task.DateTime.IllegalDateException;

public class DateTimeUtilTest {

    @Test
    public void extract_date_successful() {
        String[] expectedDateTime = {"", "01/01/2016 1500"};
        String[] actualDateTime = DateTimeUtil.getDateTimeFromArgs("1/1/2016 1500");

        Assert.assertArrayEquals(expectedDateTime, actualDateTime);
    }
    
    @Test
    public void isDateTimeWithinRange_dateTimeOutOfRange() throws DateTimeException, IllegalDateException {
        DateTime dateTimeSource = new DateTime("15/01/2016 1200", "16/01/2016 1200");
        DateTime dateTimeQuery = new DateTime("17/01/2016 1200", "18/01/2016 1200");
        
        assertFalse(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQuery));
    }
    
    @Test
    public void isDateTimeWithinRange_dateTimeWithinRange() throws DateTimeException, IllegalDateException {
        DateTime dateTimeSource = new DateTime("14/01/2016 1200", "16/01/2016 1200");
        DateTime dateTimeQueryFullyInRange = new DateTime("14/01/2016 2000", "15/01/2016 1200");
        DateTime dateTimeQueryPartiallyInRange = new DateTime("13/01/2016 1000", "15/01/2016 1200");
        
        assertTrue(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQueryFullyInRange));
        assertTrue(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQueryPartiallyInRange));
    }
    
    @Test
    public void isDateTimeWithinRange_dateTimeWithoutStartDate() throws DateTimeException, IllegalDateException {
        DateTime dateTimeSource = new DateTime("", "16/01/2016 1200");
        DateTime dateTimeQuery = new DateTime("14/01/2016 2000", "17/01/2016 1200");
        DateTime dateTimeQueryWithoutStartDate = new DateTime("", "16/01/2016 1200");
        DateTime dateTimeQueryWithoutStartDate2 = new DateTime("", "18/01/2016 1200");
        
        assertTrue(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQuery));
        assertTrue(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQueryWithoutStartDate));
        assertFalse(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQueryWithoutStartDate2));
    }
}
