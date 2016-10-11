package seedu.taskman.logic.parser;

import org.junit.Test;
import seedu.taskman.commons.exceptions.IllegalValueException;

import java.time.*;

import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DateTimeParserTest {
    private static final long timeDifferenceThreshold = 30L; // 30 seconds

    //TODO: write more test cases!

    // specify time after date
    @Test
    public void parse_FormalDateTime_Success() throws Exception {
        String testDateTimeFormal = "07/05/16 2359";
        long testDateTimeUnix = 1467763140L;

        long unixDateTime = DateTimeParser.getUnixTime(testDateTimeFormal);
        assertEquals(testDateTimeUnix ,unixDateTime);
    }

    // specify time before date
    @Test(expected = IllegalValueException.class)
    public void parse_FormalTimeBeforeDate_Exception() throws Exception {
        String testTimeDate = "2359 07/05/16";
        DateTimeParser.getUnixTime(testTimeDate);
    }

    @Test
    public void parse_RelativeDate_Success() throws Exception {
        long unixDateTime1 = DateTimeParser.getUnixTime("2 weeks from now");
        long unixDateTime2 = DateTimeParser.getUnixTime("in 2 weeks");

        long timeNow = Instant.now().getEpochSecond();
        long durationInSeconds = 2 * 7 * 24 * 60 * 60;

        assertEquals(timeNow + durationInSeconds ,unixDateTime1);
        assertEquals(timeNow + durationInSeconds ,unixDateTime2);
    }


    @Test
    public void parse_RelativeDateTime_Success() throws Exception {
        long parsedUnixTime = DateTimeParser.getUnixTime("wed 10am");

        ZonedDateTime input = OffsetDateTime.now().atZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime nextWed = input.with(nextOrSame(DayOfWeek.WEDNESDAY))
                .withHour(10)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        assertEquals(nextWed.toEpochSecond(), parsedUnixTime);
    }

    @Test
    public void parse_DurationDays_Success() throws Exception {
        String testDurationNatural = "3 days";
        long testDurationSeconds = 259200L;

        long timeNow = Instant.now().getEpochSecond();
        long expectedEndTime = timeNow + testDurationSeconds;
        long parsedTime = DateTimeParser.durationToUnixTime(timeNow, testDurationNatural);
        assertTrue(Math.abs(expectedEndTime - parsedTime) < timeDifferenceThreshold);
    }


}
