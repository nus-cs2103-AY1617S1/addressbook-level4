package seedu.taskitty.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.DateTimeException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class DateUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseDate_validDate() {
        LocalDate correctDate = LocalDate.of(2016, 10, 10);
        
        assertEquals(DateTimeUtil.parseDate("10/10/2016"), correctDate);
        assertEquals(DateTimeUtil.parseDate("10102016"), correctDate);
        assertEquals(DateTimeUtil.parseDate("10oct2016"), correctDate);
        assertEquals(DateTimeUtil.parseDate("10Oct2016"), correctDate);
        assertEquals(DateTimeUtil.parseDate("10 oct 2016"), correctDate);
    }
    
    @Test
    public void parseDate_invalidDay() {
        thrown.expect(DateTimeException.class);
        DateTimeUtil.parseDate("29022015");
    }
    
    @Test
    public void parseDate_invalidMonth() {
        thrown.expect(DateTimeException.class);
        DateTimeUtil.parseDate("10/13/2016");
    }
    
    @Test
    public void parseDate_invalidMonthString() {
        thrown.expect(DateTimeException.class);
        DateTimeUtil.parseDate("10invalid2016");
    }
}
