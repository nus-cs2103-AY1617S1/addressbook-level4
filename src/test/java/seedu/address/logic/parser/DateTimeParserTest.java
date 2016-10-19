package seedu.address.logic.parser;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

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
        DateTimeParser parser = new DateTimeParser(input);

        LocalDateTime start = LocalDateTime.of(LocalDate.of(2016, 9, 16), LocalTime.of(17, 0));

        assertEquals(start, parser.extractStartDate());
    }
    
    @Test
    public void extractExplicitEndDateTimeTest() {
        String input = "16 september 2016 5pm to 17 september 2016 6pm";
        DateTimeParser parser = new DateTimeParser(input);

        LocalDateTime end = LocalDateTime.of(LocalDate.of(2016, 9, 17), LocalTime.of(18, 0));

        assertEquals(end, parser.extractEndDate());
    }

    @Test
    public void extractMissingEndDateTimeTest() {
        String input = "by 12 november 1996 at 5pm";
        DateTimeParser parser = new DateTimeParser(input);

        LocalDateTime deadline = LocalDateTime.of(LocalDate.of(1996, 11, 12), LocalTime.of(17, 0));

        // extractStartDate and extractEndDate return the same thing if there's only
        // one date token inside the input string
        assertEquals(deadline, parser.extractStartDate());
        assertEquals(deadline, parser.extractEndDate());
    }
    
    @Test
    public void changeDateToLocalDateTimeTest() {
        int year = 1996;
        int month = 11;
        int day = 12;
        int hour = 17;
        int minute = 0;

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month-1, day, hour, minute); //month-1 because Calendar treats JANUARY as 0
        Date date = cal.getTime();

        LocalDateTime answer = LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.of(hour, minute));
        
        assertEquals(answer, DateTimeParser.changeDateToLocalDateTime(date));
    }
    
}
