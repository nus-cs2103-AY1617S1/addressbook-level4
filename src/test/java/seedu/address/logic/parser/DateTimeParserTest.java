package seedu.address.logic.parser;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Test;

/**
 * beware of tests involving relative dates as the current date
 * is taken to be the current system time (whoever is building)
 * @author darren
 */
public class DateTimeParserTest {

    @Test
    public void extractExplicitStartDateTimeTest() {
        String input = "16 september 2016 5pm to 17 september 2016 6pm";
        LocalDateTime start = LocalDateTime.of(LocalDate.of(2016, 9, 16), LocalTime.of(17, 0));
        DateTimeParser parser = new DateTimeParser(input);
        assertEquals(parser.extractStartDate(), start);
    }
    
    @Test
    public void extractExplicitEndDateTimeTest() {
        String input = "16 september 2016 5pm to 17 september 2016 6pm";
        LocalDateTime end = LocalDateTime.of(LocalDate.of(2016, 9, 17), LocalTime.of(18, 0));
        DateTimeParser parser = new DateTimeParser(input);
        assertEquals(parser.extractEndDate(), end);
    }

}
