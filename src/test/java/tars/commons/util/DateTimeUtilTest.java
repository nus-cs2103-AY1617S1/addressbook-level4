package tars.commons.util;

import static org.junit.Assert.*;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import tars.commons.core.Messages;
import tars.model.task.DateTime;
import tars.model.task.DateTime.IllegalDateException;

public class DateTimeUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void natty_parser_invalid_date() {
        thrown.expect(DateTimeException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_DATE);

        DateTimeUtil.getDateTimeFromArgs("abc");
        DateTimeUtil.getDateTimeFromArgs("hello world");

        DateTimeUtil.getDateTimeFromArgs("+1");
        DateTimeUtil.getDateTimeFromArgs("-1");
    }

    @Test
    public void natty_parser_empty_args() {
        String[] expected = new String[] { "", "" };
        String[] actual = DateTimeUtil.getDateTimeFromArgs(" ");

        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);

        actual = DateTimeUtil.getDateTimeFromArgs("");
        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);
    }

    @Test
    public void extract_date_successful() {
        String[] expectedDateTime = { "", "01/01/2016 1500" };
        String[] actualDateTime = DateTimeUtil.getDateTimeFromArgs("1/1/2016 1500");

        Assert.assertArrayEquals(expectedDateTime, actualDateTime);
    }

    // @@author A0140022H
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

    @Test
    public void isWithinWeek_dateTimeNullValue_returnFalse() {
        LocalDateTime nullDateTime = null;
        assertFalse(DateTimeUtil.isWithinWeek(nullDateTime));
    }

    @Test
    public void isWithinWeek_dateTimeWithinWeek_returnTrue() {
        LocalDateTime today = LocalDateTime.now();
        assertTrue(DateTimeUtil.isWithinWeek(today));
    }

    @Test
    public void isWithinWeek_dateTimeNotWithinWeek_returnFalse() {
        LocalDateTime nextMonth = LocalDateTime.now().plus(1, ChronoUnit.MONTHS);
        LocalDateTime lastMonth = LocalDateTime.now().minus(1, ChronoUnit.MONTHS);
        assertFalse(DateTimeUtil.isWithinWeek(nextMonth));
        assertFalse(DateTimeUtil.isWithinWeek(lastMonth));
    }

    @Test
    public void isOverDue_dateTimeNullValue_returnFalse() {
        LocalDateTime nullDateTime = null;
        assertFalse(DateTimeUtil.isOverDue(nullDateTime));
    }

    @Test
    public void isOverDue_dateTimeOverDue_returnTrue() {
        LocalDateTime yesterday = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
        assertTrue(DateTimeUtil.isOverDue(yesterday));
    }

    @Test
    public void isOverDue_dateTimeNotOverDue_returnFalse() {
        LocalDateTime tomorrow = LocalDateTime.now().plus(1, ChronoUnit.DAYS);
        assertFalse(DateTimeUtil.isOverDue(tomorrow));
    }

    @Test
    public void isDateTimeWithinRange_emptyDateTimeSource() throws DateTimeException, IllegalDateException {
        DateTime dateTimeSource = new DateTime("", "");
        DateTime dateTimeQuery = new DateTime("17/01/2016 1200", "18/01/2016 1200");
        assertFalse(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQuery));
    }

    @Test
    public void isDateTimeWithinRange_dateTimeOutOfRange() throws DateTimeException, IllegalDateException {
        DateTime dateTimeSource = new DateTime("15/01/2016 1200", "16/01/2016 1200");
        DateTime dateTimeSource2 = new DateTime("19/01/2016 1200", "20/01/2016 1200");
        DateTime dateTimeQuery = new DateTime("17/01/2016 1200", "18/01/2016 1200");

        assertFalse(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQuery));
        assertFalse(DateTimeUtil.isDateTimeWithinRange(dateTimeSource2, dateTimeQuery));
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
        DateTime dateTimeSource = new DateTime("15/01/2016 1200", "17/01/2016 1100");
        DateTime dateTimeSourceWithoutStartDate = new DateTime("", "16/01/2016 1200");
        DateTime dateTimeQuery = new DateTime("14/01/2016 2000", "17/01/2016 1200");
        DateTime dateTimeQueryWithoutStartDate = new DateTime("", "16/01/2016 1200");
        DateTime dateTimeQueryWithoutStartDate2 = new DateTime("", "18/01/2016 1200");

        assertTrue(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQuery));
        assertFalse(DateTimeUtil.isDateTimeWithinRange(dateTimeSource, dateTimeQueryWithoutStartDate2));
        assertTrue(DateTimeUtil.isDateTimeWithinRange(dateTimeSourceWithoutStartDate, dateTimeQuery));
        assertTrue(DateTimeUtil.isDateTimeWithinRange(dateTimeSourceWithoutStartDate, dateTimeQueryWithoutStartDate));
        assertFalse(DateTimeUtil.isDateTimeWithinRange(dateTimeSourceWithoutStartDate, dateTimeQueryWithoutStartDate2));
    }

}
