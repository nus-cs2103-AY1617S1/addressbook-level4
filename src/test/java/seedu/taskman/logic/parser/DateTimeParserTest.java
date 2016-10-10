package seedu.taskman.logic.parser;

import org.junit.Test;
import seedu.taskman.commons.exceptions.IllegalValueException;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DateTimeParserTest {
    private static final long timeDifferenceThreshold = 30L; // 30 seconds

    //TODO: write more test cases!

    // specify time after date
    @Test
    public void parseNaturalDateTime_FormalDateTime_Success() throws Exception {
        String testDateTimeFormal = "07/05/16 2359";
        long testDateTimeUnix = 1467763140L;

        long unixDateTime = DateTimeParser.getUnixTime(testDateTimeFormal);
        assertEquals(testDateTimeUnix ,unixDateTime);
    }

    // specify time before date
    @Test(expected = IllegalValueException.class)
    public void parseNaturalDateTime_FormalTimeDate_Exception() throws Exception {
        String testTimeDate = "2359 07/05/16";
        DateTimeParser.getUnixTime(testTimeDate);
    }

    @Test
    public void parseNaturalDateTime_RelativeDate_Success() throws Exception {
        long unixDateTime = DateTimeParser.getUnixTime("2 weeks from now");

        long timeNow = Instant.now().getEpochSecond();
        long durationInSeconds = 2 * 7 * 24 * 60 * 60;
        assertEquals(timeNow + durationInSeconds ,unixDateTime);
    }

    @Test
    public void parseNaturalDurationToEndTime_DaysHours_Success() throws Exception {
        String testDurationNatural = "3 days";
        long testDurationSeconds = 259200L;

        long timeNow = Instant.now().getEpochSecond();
        long expectedEndTime = timeNow + testDurationSeconds;
        long parsedTime = DateTimeParser.durationToUnixTime(timeNow, testDurationNatural);
        assertTrue(Math.abs(expectedEndTime - parsedTime) < timeDifferenceThreshold);
    }


}
