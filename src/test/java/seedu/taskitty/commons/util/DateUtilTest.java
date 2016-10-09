package seedu.taskitty.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.taskitty.commons.util.StringUtil;

import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseDate_validDate() {
        LocalDate correctDate = LocalDate.of(2016, 10, 10);
        
        assertEquals(DateUtil.parseDate("10/10/2016"), correctDate);
        assertEquals(DateUtil.parseDate("10102016"), correctDate);
        assertEquals(DateUtil.parseDate("10oct2016"), correctDate);
        assertEquals(DateUtil.parseDate("10Oct2016"), correctDate);
        assertEquals(DateUtil.parseDate("10 oct 2016"), correctDate);
    }
    
    @Test
    public void parseDate_invalidDay() {
        thrown.expect(DateTimeException.class);
        DateUtil.parseDate("29022015");
    }
    
    @Test
    public void parseDate_invalidMonth() {
        thrown.expect(DateTimeException.class);
        DateUtil.parseDate("10/13/2016");
    }
    
    @Test
    public void parseDate_invalidMonthString() {
        thrown.expect(DateTimeException.class);
        DateUtil.parseDate("10invalid2016");
    }

    /*@Test
    public void getDetails_nullGiven_assertionError(){
        thrown.expect(AssertionError.class);
        StringUtil.getDetails(null);
    }*/


}
