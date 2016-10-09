package seedu.address.logic.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.*;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DateTimeParserTest {
    private DateTimeParser dateTimeParser = new DateTimeParser();
    private LocalDateTime now = LocalDateTime.now();

    @Before
    public void setup() {
    }

    @After
    public void teardown() {
    }

    @Test
    public void parseDateTime_emptyString() throws Exception {
        assertTrue(!dateTimeParser.parseDateTime("").isPresent());
    }

    @Test
    public void parseDateTime_noDate() throws Exception {
        assertTrue(!dateTimeParser.parseDateTime("No date").isPresent());
    }

    @Test
    public void parseDateTime_ddMMMyyyy_HHmm() throws Exception {
        assertDateTimeMatches(
            dateTimeParser.parseDateTime("28 Feb 2016 23:59").orElse(null),
            28, 2, 2016, 23, 59, 0
        );
    }

    @Test
    public void parseDateTime_ddMMM_HHmm() throws Exception {
        assertDateTimeMatches(
            dateTimeParser.parseDateTime("28 Feb 23:59").orElse(null),
            28, 2, now.getYear(), 23, 59, 0
        );
    }

    // TODO: Add more datetime format tests

    private void assertDateTimeMatches(Date date, int day, int month, int year, int hours, int mins, int secs) {
        assertTrue(date != null);

        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneOffset.systemDefault());

        assertEquals(day, dateTime.getDayOfMonth());
        assertEquals(month, dateTime.getMonthValue());
        assertEquals(year, dateTime.getYear());
        assertEquals(hours, dateTime.getHour());
        assertEquals(mins, dateTime.getMinute());
        assertEquals(secs, dateTime.getSecond());
    }
}
