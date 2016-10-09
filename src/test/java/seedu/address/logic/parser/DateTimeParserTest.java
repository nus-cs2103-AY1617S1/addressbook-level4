package seedu.address.logic.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.*;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
    public void parseDateTime_emptyString()  {
        assertFalse(dateTimeParser.parseDateTime("").isPresent());
    }

    @Test
    public void parseDateTime_noDate()  {
        assertFalse(dateTimeParser.parseDateTime("No date").isPresent());
    }

    @Test
    public void parseDateTime_ddMMMyyyy_HHmm()  {
        assertEquals(
            LocalDateTime.of(2016, 2, 28, 23, 59),
            dateTimeParser.parseDateTime("28 Feb 2016 23:59").orElse(null)
        );
    }

    @Test
    public void parseDateTime_ddMMM_HHmm()  {
        assertEquals(
            LocalDateTime.of(now.getYear(), 2, 28, 23, 59),
            dateTimeParser.parseDateTime("28 Feb 2016 23:59").orElse(null)
        );
    }

    @Test
    public void parseDateTime_ddMMM()  {
        // Time should be default, if no time specified
        assertEquals(
            LocalDateTime.of(now.getYear(), 2, 28,
                DateTimeParser.DefaultLocalTime.getHour(),
                DateTimeParser.DefaultLocalTime.getMinute()),
            dateTimeParser.parseDateTime("28 Feb 2016").orElse(null)
        );
    }

    // TODO: Add more datetime format tests
}
