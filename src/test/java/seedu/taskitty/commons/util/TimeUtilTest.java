package seedu.taskitty.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.DateTimeException;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class TimeUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseTime_validTime() {
        LocalTime correctTime = LocalTime.of(10, 55);
        
        assertEquals(TimeUtil.parseTime("10:55"), correctTime);
        assertEquals(TimeUtil.parseTime("1055"), correctTime);
    }
    
    @Test
    public void parseTime_invalidHour() {
        thrown.expect(DateTimeException.class);
        TimeUtil.parseTime("24:00");
    }
    
    @Test
    public void parseTime_invalidMinute() {
        thrown.expect(DateTimeException.class);
        TimeUtil.parseTime("10:60");
    }
}
