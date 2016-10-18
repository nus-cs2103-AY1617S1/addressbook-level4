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
    
    //@@author A0140022H
    @Test 
    public void modifyDate() {
        String dateToModify = "06/09/2016 2200";
        
        String frequencyDay = "day";
        String frequencyWeek = "week";
        String frequencyMonth = "month";
        String frequencyYear = "year";
        
        String expectedDay = "07/09/2016 2200";
        String expectedWeek = "13/09/2016 2200";
        String expectedMonth = "06/10/2016 2200";
        String expectedYear = "06/09/2017 2200";
        
        String modifiedDay = DateTimeUtil.modifyDate(dateToModify, frequencyDay);
        String modifiedWeek = DateTimeUtil.modifyDate(dateToModify, frequencyWeek);
        String modifiedMonth = DateTimeUtil.modifyDate(dateToModify, frequencyMonth);
        String modifiedYear = DateTimeUtil.modifyDate(dateToModify, frequencyYear);
        
        Assert.assertEquals(expectedDay, modifiedDay);
        Assert.assertEquals(expectedWeek, modifiedWeek);
        Assert.assertEquals(expectedMonth, modifiedMonth);
        Assert.assertEquals(expectedYear, modifiedYear);
    }
}
